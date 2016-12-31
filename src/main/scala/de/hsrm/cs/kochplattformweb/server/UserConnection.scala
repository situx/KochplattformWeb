/*
 * This file is part of KochplattformWeb.
 *
 *   KochplattformWeb is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *    KochplattformWeb is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with KochplattformWeb.  If not, see <http://www.gnu.org/licenses/>.
 *
 */


package de.hsrm.cs.kochplattformweb.server

import de.hsrm.cs.kochplattformweb.parser.XMLparser
import de.hsrm.cs.kochplattformweb.client.AbstractUser
import de.hsrm.cs.kochplattformweb.client.Admin
import de.hsrm.cs.kochplattformweb.client.MainUser
import de.hsrm.cs.kochplattformweb.db.DBException
import de.hsrm.cs.kochplattformweb.db.Recipe
import de.hsrm.cs.kochplattformweb.db.RecipeEntry
import de.hsrm.cs.kochplattformweb.messages._
import de.hsrm.cs.kochplattformweb.postgres.{DBConnectionAPI}
import de.hsrm.cs.kochplattformweb.safety.Cryptor
import de.hsrm.cs.kochplattformweb.utils.SWTLogger
import java.io.IOException
import java.math.BigInteger
import java.util.LinkedList





/**The class for the connection of the user.
 * @author Lehel Eses
 * @version $Date$
 */
class UserConnection(dbcon:DBConnectionAPI, user:AbstractUser,
                     var users:List[AbstractUser]) extends de.hsrm.cs.kochplattformweb.server.UserConnectionAPI {
  def handleMessage(message: Message) = ???

  /**Integervalue specifies the error if user has no rights to shut down server {@value}.**/
	val DOWNERROR = 1004
	/**Integervalue specifies server down error {@value}.**/
	val ERRORCODE = 1000
	/**Integervalue specifies User isn't logged in error {@value}.**/
	val NOLOGIN = 1005
	/**String which will be send if user has no Permission to do something {@value}.*/
	val NOPERMISSION = "Youhavenopermissiontodothis"
	/**String used if user is not logged in {@value}.*/
	val NOTLOGGEDIN= "Youarenotloggedin"
	/**Backup failed code {@value}.**/
	private val BACKUPFAILED = "Backup failed"
	/**Integervalue specifies Database exists error {@value}.**/
	private val DBERROR = 1100
	/**Integervalue specifies that an user with this name
	 * is already logged in {@value}.*/
	private val LOGGEDIN = 1011
	/**Integervalue specifies the error if send user doesn't match to user
	 * belonging to the Connection {@value}.**/
	private val MATCHERROR = 1006
	/** Integervalue specifies error of not existing backup, or empty backup in
	 * message.
	 */
	private val NOBACKUP = 1012
	/**Integervalue specifies that an user hasn't permission to do this {@value}.**/
	private val NOPERMERROR = 1009
	/**Integervalue specifies User doesn't exist error {@value}.**/
	private val NOUSER = 1001
	/**Expected Database result if no user is available: {@value}.**/
	private val NOUSERSTR = "<users></users>"
	/**Integervalue specifies User already voted for a recipe {@value}.*/
	private val NOVOTE = 1010
	/**Integervalue specifies that an user couldn't be registered in Database {@value}.**/
	private val REGERROR = 1008
	/**Integervalue specifies error if a wrong Object is send to server {@value}.**/
	private val UNKNOWNMESSAGE = 999

	/**String specifies error if a wrong Object is send to server {@value}.**/
	private val UNKNOWNMESSAGESTR = "Unknown Message"
	/**Integervalue specifies username exists error {@value}.**/
	private val USERNAME = 1003
	/**Integervalue specifies Password mismatch error {@value}.**/
	private val WRONGPW = 1002
	/**Integervalue specifies the error if the recipe with an given ID coudn't
	 * be found in Database {@value}.**/
	private val WRONGRECIPEID = 1007


	/**Flag if user has logged in.**/
	var authentificated=false
	/** Cryptor for en- and decrypting.*/
	var cryptor=new Cryptor()
	/**Reference to the Database-Connection.**/
	var dbconnection:DBConnectionAPI=null
	/**Errorlogger */
	val logger = SWTLogger
	val parser=new XMLparser
	/**The xml parser to parse a user.*/
	//var parser=new XMLparser()
	/**ResultMessage which will be returned.*/
	var toreturn:Message=null
	/**the connected user if he has logged in.**/
	var usertype:AbstractUser=null

	override def checkforuser(username:String):Boolean= {
		var temp:Boolean = false
		for(abs:AbstractUser<-this.users){
			if(abs.name.equals(username)){
				temp = true
			}
		}
		temp
	}

	override def handlemessage(message:AbstractUser):Message= {
		this.logger.writeerror(UNKNOWNMESSAGESTR
				+message.getClass.getSimpleName)
		ErrorReport(message.getClass.getSimpleName,
				UNKNOWNMESSAGE)
	}

	override def handlemessage(message:ChangeData):Message={
		if(this.authentificated){
			message.decryptme(this.cryptor)
      val temp: AbstractUser = message.user
			if((this.usertype.name.equals(temp.name)
				&& this.usertype.password.equals(temp.password))
				|| this.usertype.isAdmin){
				temp.name=message.newname
				temp.password=message.password
				try {
					this.dbconnection.updateUser(temp)
					temp.encryptme(this.cryptor)
					this.toreturn = temp
				} catch{ case e: DBException => 
					this.logger.writeerror("DB-Error ChangeData "
							+e.getMessage)
					this.toreturn = ErrorReport(

            e.getMessage,
            DBERROR)
				}
			}
			else{
				this.toreturn = ErrorReport(
          "Senduserdoesnotmatchtologgedinuser",
          MATCHERROR)
			}
		}
		else{
			this.toreturn = ErrorReport(NOTLOGGEDIN,
        NOLOGIN)
		}
		this.toreturn
	}

	override def handlemessage(message:ChangeRecipeMessage):Message={
		if(this.authentificated){
			if(this.usertype.isAdmin || this.usertype.id.equals(
        message.recipe.authorId)){
				try {
					this.dbconnection.updateRecipe(
						message.recipe.recipeid,
						message.recipe)
					this.toreturn =
						ConfirmReport("RecipeSuccesfullychanged")
				}catch{ case e: DBException => 
					this.logger.writeerror("DB-Error ChangeRecipe "+
							e.getMessage)
					this.toreturn = ErrorReport(

            e.getMessage,
            DBERROR)
				}
			}
			else{
				this.toreturn = ErrorReport(
          NOPERMISSION,
          NOPERMERROR)
			}
		}
		else{
			this.toreturn = ErrorReport(
        NOTLOGGEDIN,
        NOLOGIN)
		}
		this.toreturn
	}

	override def handlemessage(message:ConfirmReport):Message= {
		this.logger.writeerror(UNKNOWNMESSAGESTR
				+message.getClass.getSimpleName)
		this.toreturn = ErrorReport(message.getClass.getSimpleName,
      UNKNOWNMESSAGE)
		this.toreturn
	}

	override def handlemessage(message:DeleteRecipeMessage):Message={
		if(this.authentificated){
			if(this.usertype.isAdmin || this.usertype.id.equals(
				message.recipe.authorId)){
				try {
					this.dbconnection.deleteRecipe(
						message.recipe.recipeid)
					this.toreturn = ConfirmReport("recipesuccessfullydeleted")
				} catch{ case e: DBException => 
					this.logger.writeerror("DB-Error DeleteRecipe "
							+e.getMessage)
					this.toreturn = ErrorReport(

            e.getMessage,
            DBERROR)
				}
			}
			else{
				this.toreturn = ErrorReport(
          NOPERMISSION,
          NOPERMERROR)
			}
		}
		else{
			this.toreturn = ErrorReport(
        NOTLOGGEDIN, NOLOGIN)
		}
		this.toreturn
	}

	override def handlemessage(message:DeleteUserMessage):Message= {
		val tempuser: AbstractUser = message.user
		tempuser.decryptme(this.cryptor)
		if(this.isauthentification()){
			if(this.usertype.isAdmin ||
				this.usertype.name.equals(tempuser.name)){
				try {
					this.dbconnection.deleteUser(tempuser.id)
					this.toreturn = ConfirmReport(
            "Usersuccessfullydeleted")
				} catch{ case e: DBException => 
					this.logger.writeerror("DB-Error DeleteUser "
							+e.getMessage)
					this.toreturn = ErrorReport(

            e.getMessage,
            DBERROR)
				}
			}
			else{
				this.toreturn = ErrorReport(
          NOPERMISSION,
          NOPERMERROR)
			}
		}
		else{
			this.toreturn = ErrorReport(
        NOTLOGGEDIN, NOLOGIN)
		}
		this.toreturn
	}

	override def handlemessage(message:ErrorReport):Message={
		this.logger.writeerror(UNKNOWNMESSAGESTR
				+message.getClass.getSimpleName)
		this.toreturn = ErrorReport(message.getClass.getSimpleName,
      UNKNOWNMESSAGE)
		this.toreturn
	}

	override def handlemessage(message:FinishMessage):Message={
		ErrorReport(message.getClass.getSimpleName
				, UNKNOWNMESSAGE)
	}

	override def handlemessage(message:GetAllIngredients):Message={
		try {
			this.toreturn = XMLIngredientListMessage(
        this.dbconnection.selectAllIngredients(
          message.language))
		} catch{ case e: DBException => 
			this.logger.writeerror("DB-Error GetAllIngredientsMessage "+
					e.getMessage)
			this.toreturn = ErrorReport(

        e.getMessage,
        DBERROR)
		}
		this.toreturn
	}

	override def handlemessage(message:GetAllUsersMessage):Message={
		if(this.isauthentification()){
			if(this.usertype.isAdmin){
				try {
					val temp: String = this.dbconnection.getAllUsers()
          val ulist: List[AbstractUser] =
            this.parser.parseUsers(temp)
          val tulm: UserListMessage = new UserListMessage(ulist)
					tulm.decryptme(this.cryptor)
					this.toreturn = tulm
				} catch{ case e: DBException => 
					this.logger.writeerror("DB-Error GetAllUsersMessage "
							+e.getMessage)
					this.toreturn = ErrorReport(

            e.getMessage,
            DBERROR)
				}
			}
			else{
        val temp: List[AbstractUser] = List[AbstractUser](this.usertype)
				this.toreturn = UserListMessage(temp)
			}
		}
		else{
			this.toreturn = ErrorReport(
        NOTLOGGEDIN, NOLOGIN)
		}
		this.toreturn
	}

	override def handlemessage(message:GetRecipeByNameMessage):Message={
		try {
			val temp = this.dbconnection.selectByName(message.getMessage())
			this.toreturn = XMLRecipeMessage(temp, true)
		} catch{ case e: DBException => 
			this.logger.writeerror("DB-Error GetRecipeByNameMessage "
					+e.getMessage)
			this.toreturn = ErrorReport(

        e.getMessage,
        DBERROR)
		}
		this.toreturn
	}

	override def handlemessage(message:GetRecipeMessage):Message= {
		try {
			val temp = this.dbconnection.getRecipe(message.recipeid,false)
			if(temp.length()>1){
				this.toreturn = XMLRecipeMessage(temp, false)
			}
			else{
				this.toreturn = ErrorReport("Recipenotfound",
          WRONGRECIPEID)
			}
		} catch{ case e: DBException => 
			this.logger.writeerror("DB-Error GetRecipeMessage "+e.getMessage)
			this.toreturn = ErrorReport(
        e.getMessage, DBERROR)
		}
		this.toreturn
	}

	override def handlemessage(message:IngredientMessage):Message={
		try {
			var temp:String=""
			if(message.requestType){
				temp = this.dbconnection.select(message.ingredients)
			}
			else{
				temp = this.dbconnection.selectAll(message.ingredients)
			}
			this.toreturn = XMLRecipeMessage(temp, true)
		} catch{ case e: DBException => 
			this.logger.writeerror("DB-Error IngredientMessage "+e.getMessage)
			this.toreturn  = ErrorReport(
        e.getMessage, DBERROR)
		}
		this.toreturn
	}

	override def handlemessage(message:IngredientSetMessage):Message= {
		this.logger.writeerror(UNKNOWNMESSAGESTR
				+message.getClass.getSimpleName)
		ErrorReport(message.getClass.getSimpleName,
				UNKNOWNMESSAGE)
	}

	override def handlemessage(message:Loginmessage):Message={
		message.decryptme(this.cryptor)
		if(this.checkforuser(message.name)){
			this.toreturn = ErrorReport(
				"Userisalreadyloggedin", LOGGEDIN)
		}
		else{
			if(this.authentificated){
        this.users=this.users.filter(_ != this.usertype)
				this.authentificated = false
			}
			try {
				val dbres = this.dbconnection.selectUser(message.name)
				if(dbres.equals(NOUSERSTR)){
					this.toreturn= ErrorReport("Userdoesnotexist",
						NOUSER)
				}
				else{
					val result:AbstractUser =
						this.parser.parseUser(dbres).asInstanceOf[AbstractUser]
					if(result==null){
						this.toreturn = ErrorReport(
              "Userdoesnotexist",
              NOUSER)
					}
					else{
						if(result.password.equals(message.passw)){
							this.authentificated = true
							if(result.isAdmin){
								this.logger.writeinfo("Admin logged in")
								this.usertype = Admin(
                  message.name,
                  message.passw, BigInteger.ZERO, true)
								this.usertype.id= result.id
								this.toreturn =
									new Admin(this.usertype.asInstanceOf[Admin])

							}
							else{
								this.logger.writeinfo("MainUser logged in")
								this.usertype = MainUser(
                  message.name,
                  message.passw, BigInteger.ZERO, false)
								this.usertype.id=result.id
								this.toreturn =	new MainUser(this.usertype.asInstanceOf[MainUser])
							}
							this.users.::(this.usertype)
							this.logger.writeinfo(
									this.usertype.name+" "
									+this.usertype.id)
							this.toreturn.asInstanceOf[AbstractUser].
							encryptme(this.cryptor)
						}
						else{
							this.toreturn = ErrorReport(
                "Passworddoesnotmatch",
                WRONGPW)
						}
					}
				}
			} catch{ case e: DBException => 
				this.logger.writeerror("DB-Error LoginMessage "+e.getMessage)
				this.toreturn = ErrorReport(
          e.getMessage,
          DBERROR)
			}
		}
		this.toreturn
	}

	override def handlemessage(message:LogoutMessage):Message={
		if(this.authentificated){
			this.authentificated = false
			if(this.getuser()!=null){
        this.users=this.users.filter(_ != this.getuser())
			}
			this.toreturn = ConfirmReport("Logoutgranted")
		}
		else{
			this.toreturn = ErrorReport(
        NOTLOGGEDIN, NOLOGIN)
		}
		this.toreturn
	}

	override def handlemessage(message:Recipe):Message={
		if(this.authentificated){
			try {
				message.authorId= this.usertype.id
				this.dbconnection.insertRecipe(message)
				this.toreturn = ConfirmReport("Recipesuccessfullyinserted")
			} catch{ case e: DBException => 
				this.logger.writeerror("DB-Error Recipe "+e.getMessage)
				this.toreturn = ErrorReport(
          e.getMessage,
          DBERROR)
			case e: IOException =>
				this.logger.writeerror("IOExecption handeling ( "
						+message.getClass.getSimpleName+" )")
				this.toreturn = ErrorReport(
          e.getMessage,
          DBERROR)
			}
		}
		else{
			this.toreturn = ErrorReport(
        NOTLOGGEDIN,
        NOLOGIN)
		}
		this.toreturn
	}

	override def handlemessage(message:RecipeEntry):Message= {
		this.logger.writeerror(UNKNOWNMESSAGESTR
				+message.getClass.getSimpleName)
		ErrorReport("Unable to handle " + message.getClass.getSimpleName,
			UNKNOWNMESSAGE)
	}

	override def handlemessage(message:RecipeListMessage):Message={
		this.logger.writeerror(UNKNOWNMESSAGESTR
				+message.getClass.getSimpleName)
		ErrorReport(message.getClass.getSimpleName, UNKNOWNMESSAGE)
	}

	override def handlemessage(message:Registration):Message={
		try {
			message.decryptme(this.cryptor)
			val result = this.dbconnection.selectUser(message.getMessage())
			val test = this.parser.parseUser(result).asInstanceOf[AbstractUser]
			if(test==null){
				try {
					val temp: MainUser =
						new MainUser(message.getMessage(),
							message.passw, BigInteger.ZERO, false)
					if(this.dbconnection.insertUser(temp)){
						this.toreturn = ConfirmReport(
              "Usersuccessfullyregistered")
					}
					else{
						this.toreturn = ErrorReport(
              "Registeringuserfailed",
              REGERROR)
					}
				} catch{ case e: DBException => 
					this.logger.writeerror(
							"DBException while trying to insert user \n+" +
							e.getMessage)
					this.toreturn = ErrorReport(
            e.getMessage,
            DBERROR)
				}
			}
			else{
				this.toreturn = ErrorReport(
          "Usernamealreadyexists", USERNAME)
			}
		} catch{ case e: DBException => 
			this.logger.writeerror("DB-Error Registartion "+e.getMessage)
			this.toreturn = ErrorReport(
        e.getMessage,
        DBERROR)
		}
		this.toreturn
	}

	override def handlemessage(message:SetRatingMessage):Message={
		if(this.authentificated){
			try {
				if(this.dbconnection.checkUserVote(
						message.recipeid, this.usertype.id)){
					this.dbconnection.setRating(message.reciperating.rating,message.reciperating.comment, message.recipeid,this.usertype.id)
					this.toreturn = XMLRatingMessage(this.dbconnection.getRating(message.recipeid))
				}
				else{
					this.toreturn = ErrorReport("Youhavealreadyvotedforthisrecipe",
								NOVOTE)
				}
			} catch{ case e: DBException => 
				this.logger.writeerror("DB-Error SetRatingMessage "
						+e.getMessage)
				this.toreturn = ErrorReport(
          e.getMessage,
          DBERROR)
			}
		}
		else{
			this.toreturn = ErrorReport(NOTLOGGEDIN,NOLOGIN)
		}
		this.toreturn
	}

	override def handlemessage(message:ShutdownMessage):Message={
		ErrorReport(message.getClass.getSimpleName
				, UNKNOWNMESSAGE)
	}

	override def handlemessage(message:UpdateRecipesMessage):Message= {
		try {
			this.logger.writeinfo("Calling Recipes to update")
      val updates =
        this.dbconnection.getRecipeUpdates(message.updaterecipes)
			this.logger.writeinfo("Calling Recipes to insert")
      val inserts =
        this.dbconnection.getRecipeUpdates(message.insertrecipes)
			this.logger.writeinfo("Creating nre UpdateRecipeMessage to send back")
			this.toreturn = new UpdateRecipesMessage(updates, inserts)
		} catch{ case e: DBException => 
			this.toreturn = ErrorReport(
        e.getMessage,
        DBERROR)
		}
		this.toreturn
	}

	override def handlemessage(message:UserListMessage):Message= {
		this.logger.writeerror(UNKNOWNMESSAGESTR
				+message.getClass.getSimpleName)
		ErrorReport(message.getClass.getSimpleName, UNKNOWNMESSAGE)
	}

	override def handlemessage(message:XMLIngredientListMessage):Message={
		this.logger.writeerror(UNKNOWNMESSAGESTR
				+message.getClass.getSimpleName)
		ErrorReport(message.getClass.getSimpleName,
				UNKNOWNMESSAGE)
	}

	override def handlemessage(message:XMLRecipeMessage):Message={
		if(message.backup){
			if(message.userecipe){
				if(message.number==(-1)){
					try {
						val temp: String = this.dbconnection.selectRecipeDB()
						this.toreturn = XMLRecipeMessage(temp, true, true, -1, true)
					} catch{ case e: DBException => 
						this.logger.writeerror("DB-Error XMLRecipeMessage "
								+e.getMessage)
						this.toreturn = ErrorReport(
              e.getMessage,
              DBERROR)
					}
				}
				else{
					if(this.isauthentification()){
						if(this.usertype.isAdmin){
							message.number match {
							    case 0=>
								    try {
									    this.dbconnection.importBackup(
											true,
											"Backup_recipes_actual.xml")
									    this.toreturn =
										ConfirmReport("Backupinside")
								    } catch{ case e: DBException => 
									    this.toreturn = ErrorReport(
                        e.getMessage,
                        DBERROR)
								     case e: IOException =>
									    this.toreturn = ErrorReport(
                        "Backupfailed",
                        NOBACKUP)
								    }
							    case 1=>
								    try {
									    this.dbconnection.importBackup(
										    true,
										    "Backup_recipes_old_1.xml")
									    this.toreturn =
											ConfirmReport("Backupinside")
								    } catch{ case e: DBException => 
									    this.toreturn = ErrorReport(
                        e.getMessage,
                        DBERROR)
										case e:IOException =>
									    this.toreturn = ErrorReport(
                        "Backupfailed",
                        NOBACKUP)
								    }
							    case 2 =>
								    try {
									    this.dbconnection.importBackup(
											true,
											"Backup_recipes_old_2.xml")
									    this.toreturn =
											ConfirmReport("Backupinside")
								    } catch{ case e: DBException => 
								    	    this.toreturn = ErrorReport(
                            e.getMessage,
                            DBERROR)
								     case e: IOException =>
								    	    this.toreturn = ErrorReport(
                            "Backupfailed",
                            NOBACKUP)
								    }
								case _ =>
							    	    this.toreturn =
							    	    	ErrorReport(
                            message.getClass.getSimpleName,
                            UNKNOWNMESSAGE)
							}
						}
						else{
							this.toreturn = ErrorReport(
                NOPERMISSION,
                NOPERMERROR)
						}
					}
					else{
						this.toreturn = ErrorReport(
              NOTLOGGEDIN,
              NOLOGIN)
					}
				}
			}
			else{
				if(this.isauthentification()){
					if(this.usertype.isAdmin){
						message.number match {
						    case 0 =>
						            try {
						    		    this.dbconnection.importBackup(
						    		    		false,"Backup_users_actual.xml")
						    		    this.toreturn =
						    		    	ConfirmReport("Backupinside")
						    	    } catch{ case e: DBException => 
						    		    this.toreturn = ErrorReport(
                          e.getMessage,
                          DBERROR) case e: IOException =>
						    		    this.toreturn = ErrorReport(
                          "Backupfailed",
                          NOBACKUP)
						    	    }
						    case 1 =>
						            try {
						                    this.dbconnection.importBackup(
					    		    		    false,"Backup_users_old_1.xml")
					    		            this.toreturn =
					    		    	        ConfirmReport("Backupinside")
						            } catch{ case e: DBException => 
						            	    this.toreturn = ErrorReport(
                                e.getMessage,
                                DBERROR)
												 case e: IOException =>
					    		            this.toreturn = ErrorReport(
                                "Backupfailed",
                                NOBACKUP)
						            }
						    case 2 =>
						    	    try {
						    	            this.dbconnection.importBackup(
						    	    			false,"Backup_users_old_2.xml")
						    	            this.toreturn =
						    	    		    ConfirmReport("Backupinside")
						    	    } catch{ case e: DBException => 
						    	    	    this.toreturn = ErrorReport(
                              e.getMessage,
                              DBERROR)
						case e: IOException =>
						    	    	    this.toreturn = ErrorReport(
                              "Backupfailed",
                              NOBACKUP)
						    	    }
							case _ =>
						    	    this.toreturn =
					    	    		ErrorReport(
                          message.getClass.getSimpleName,
                          UNKNOWNMESSAGE)
						}
					}
					else{
						this.toreturn = ErrorReport(
              NOPERMISSION,
              NOPERMERROR)
					}
				}
				else{
					this.toreturn = ErrorReport(
            NOTLOGGEDIN,
            NOLOGIN)
				}
			}
		}
		else{
			this.toreturn = ErrorReport(message.getClass.getSimpleName,
        UNKNOWNMESSAGE)
		}

		this.toreturn
	}

	override def handlemessage(message:XMLRatingMessage):Message={
		this.logger.writeerror(UNKNOWNMESSAGESTR
				+message.getClass.getSimpleName)
		ErrorReport(message.getClass.getSimpleName, UNKNOWNMESSAGE)
	}

	override def handlemessage(message:XMLTimestampMessage):Message={
		try {
			this.logger.writeinfo("XMLTimestampMessage DB.getVersions()")
			val result = this.dbconnection.getVersions
			this.toreturn = XMLTimestampMessage(result.toString, message.unknownto)
		} catch{ case e: DBException => 
			this.toreturn = ErrorReport(
        e.getMessage,
        DBERROR)
			this.toreturn = ErrorReport(
        e.getMessage,
        DBERROR)
		}
		this.toreturn
	}

	override def visitmessage(message:Message):Message={
		message serverHandleMessage this
	}

	override def getuser():AbstractUser={
		 this.usertype
	}

	/**Getter authentificated.
	 * @return if user is authentificated or not
	 */
	def isauthentification()=this.authentificated


	override def setAuthentificated(auth:Boolean){
		this.authentificated = auth
	}

	override def setuser(user:AbstractUser){
		this.usertype = user
		this.authentificated = true
	}

}
