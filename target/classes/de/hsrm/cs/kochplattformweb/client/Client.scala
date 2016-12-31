/*
 * This file is part of KochplattformWeb.
 *
 *   KochplattformWeb is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *    KochplattformWeb is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with KochplattformWeb.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package de.hsrm.cs.kochplattformweb.client

import de.hsrm.cs.kochplattformweb.messages._
import de.hsrm.cs.kochplattformweb.utils.SWTLogger
import java.net.{Socket, SocketException, UnknownHostException}
import java.io.{IOException, ObjectInputStream, ObjectOutputStream}

import de.hsrm.cs.kochplattformweb.db.{DBException, Rating, Recipe, RecipeEntry}
import de.hsrm.cs.kochplattformweb.parser.XMLparser
import de.hsrm.cs.kochplattformweb.postgres.{DBConnection, DBConnectionAPI}
import de.hsrm.cs.kochplattformweb.safety.Cryptor

class Client(var host:String,var port:Int) extends ClientAPI {
  def sendRecipes(recipe: Recipe): Unit = ???

  def sendRegistration(registration: Registration): Message = ???

  def shutdown():Message = ???


  def deleteRecipe(recipe: Recipe): Message = ???

	def closeConnection():Message = ???

	def sendChangeData(data: ChangeData): Message = ???

	def sendIngredients(ingrmess: IngredientMessage): Message = ???

  /** Integervalue specifies Database exists error {@value }. **/
  private val DBERROR: Int = 1100
  /** Speciefies Backup numer is not 0,1 or 2 {@value }. **/
  private val WRONGBACKUP: Integer = 110
  /** Value for server not found {@value }. **/
  private val SERVERNOTFOUND: Integer = 111
	/** Timeout for testing DBConnection in milliseconds. **/
	private val TIME = 15000
	/** Integervalue specifies error if a wrong Object is send to server {@value }. **/
	private val UNKNOWNMESSAGE = 50
	private var sock = new Socket(this.host, this.port)
	private val logger = SWTLogger
	var status = false
	var connections=0
	var changeit = false
	var cryp: Cryptor=new Cryptor
	var loggedin = false
	var usertype: AbstractUser=null
	/** ObjectOutputStrem belonging to the users connection. **/
	var output: ObjectOutputStream=null
	/** ObjectInputStrem belonging to the users connection. **/
	var input: ObjectInputStream=null

  var connection:DBConnectionAPI=null

  val parser:XMLparser=new XMLparser()

	/** Function to change a Recipe.
		*
		* @param msg (Message) to send
		* @return a Message from the server **/
	def changeRecipe(msg: Message) = this.sendMessage(msg)

	/** Function connect starts a connection to the server.
		*
		* @return Confirmreport (Message) if the connection with the server was successful
		*         or Errorrerport (Message) if the connection failed. */
	override def connect:Message= {
		try {
			this.sock.setKeepAlive(true)
			this.sock.setSoTimeout(TIME)
			this.sock.setTcpNoDelay(true)
			this.output = new ObjectOutputStream(this.sock.getOutputStream)
			this.input = new ObjectInputStream(this.sock.getInputStream)
		} catch {
			case e: UnknownHostException => SWTLogger.logger.error("Client, connect():" + " Host not found: Connection failed")
				ErrorReport("HostnotfoundConnectionfailed", UNKNOWNMESSAGE)
			case e: IOException =>
				//SWTLogger.logger.writeerror("Client, connect(): " +"Connection failed\n"+e.getMessage())
				ErrorReport("Connectionfailed", UNKNOWNMESSAGE)
		}
		this.status = true
		SWTLogger.logger.info("Connect to the server successful - " +
			"status == true")
		ConfirmReport("Connecttotheserversuccessful")
	}

	/** Function to send an ID to the server and get back the Recipe.
		*
		* @param msg (Message) to send
		* @return a Message from the server **/
	def getRecipe(msg: Message) = this.sendMessage(msg)

	/** Function to get all Ingredients.
		*
		* @param lang as String to know  which language the user want
		* @return Message from Server with Ingredients
		* */
	def getAllIngredients(lang: String): Message = this.sendMessage(GetAllIngredients(lang))


	/** Function to get all users in a list.
		*
		* @return Message from server
		*/
	def getAllUsers: Message = this.sendMessage(GetAllUsersMessage())

	def handleMessage(message: AbstractUser):AbstractUser = {
		message.decryptme(this.cryp)
		if (this.changeit) {
			this.changeit = false
		}
		else {
			this.usertype = message
		}
		message
	}

	def handleMessage(message: ChangeData): Message = new ErrorReport(message.getClass.getSimpleName, UNKNOWNMESSAGE)

	def handleMessage(message: ChangeRecipeMessage): Message = new ErrorReport(message.getClass.getSimpleName, UNKNOWNMESSAGE)

	def handleMessage(message: ConfirmReport): Message = null

	def handleMessage(message: Nothing): Message = message

	def handleMessage(message: DeleteRecipeMessage): Message = new ErrorReport(message.getClass.getSimpleName, UNKNOWNMESSAGE)

	def handleMessage(message: DeleteUserMessage): Message = new ErrorReport(message.getClass.getSimpleName, UNKNOWNMESSAGE)

	def handleMessage(message: ErrorReport): Message = {
		this.logger.writeerror(message.code + "\t" + message.getMessage + "\t" + message.getMessage)
		message
	}

	def handleMessage(message: FinishMessage): Message = new ErrorReport(message.getClass.getSimpleName, UNKNOWNMESSAGE)

	def handleMessage(message: GetAllIngredients): Message = new ErrorReport(message.getClass.getSimpleName, UNKNOWNMESSAGE)

	def handleMessage(message: GetAllUsersMessage): Message = new ErrorReport(message.getClass.getSimpleName, UNKNOWNMESSAGE)

	def handleMessage(message: GetRecipeByNameMessage): Message = new ErrorReport(message.getClass.getSimpleName, UNKNOWNMESSAGE)

	def handleMessage(message: GetRecipeMessage): Message = new ErrorReport(message.getClass.getSimpleName, UNKNOWNMESSAGE)

	def handleMessage(message: IngredientMessage): Message = new ErrorReport(message.getClass.getSimpleName, UNKNOWNMESSAGE)

	def handleMessage(message: IngredientSetMessage): Message = new ErrorReport(message.getClass.getSimpleName, UNKNOWNMESSAGE)

	def handleMessage(message: Loginmessage): Message = new ErrorReport(message.getClass.getSimpleName, UNKNOWNMESSAGE)

	def handleMessage(message: LogoutMessage): Message = new ErrorReport(message.getClass.getSimpleName, UNKNOWNMESSAGE)

	def handleMessage(message: Any): Message = null

	def handleMessage(message: RecipeEntry): Message = new ErrorReport(message.getClass.getSimpleName, UNKNOWNMESSAGE)

	def handleMessage(message: RecipeListMessage): Message = new ErrorReport(message.getClass.getSimpleName, UNKNOWNMESSAGE)

	def handleMessage(message: Registration): Message = new ErrorReport(message.getClass.getSimpleName, UNKNOWNMESSAGE)

	def handleMessage(message: SetRatingMessage): Message = new ErrorReport(message.getClass.getSimpleName, UNKNOWNMESSAGE)

	/** Function to set the rating.
		*
		* @param rateid BigInt to set a Rating
		* @param rating Rating the Rating
		* @return a Message from the server **/
	def setRating(rateid: BigInt, rating: Rating): Message = this.sendMessage(new SetRatingMessage(rateid, rating))

	def handleMessage(message: ShutdownMessage): Message = new ErrorReport(message.getClass.getSimpleName, UNKNOWNMESSAGE)

  /** Function to delete a user.
    *
    * @param usr as AbstractUser to delete
    * @return Message from the server
    */
  def deleteUser(usr: AbstractUser): Message = {
    this.logger.writeinfo("Logger deleteUser:" + " signal to delete one user sending")
    val temp: DeleteUserMessage = new DeleteUserMessage(usr)
    temp.encryptme(this.cryp)
    this.sendMessage(temp)
  }

	@SuppressWarnings(Array("all")) def handleMessage(message: UpdateRecipesMessage): Message = {
		var toreturn: Message = null
		val updates: List[Recipe] = this.parser.parseRecipeIntoList(message.updaterecipes)
		val inserts: List[Recipe] = this.parser.parseRecipeIntoList(message.insertrecipes)
		try {
      val temp: List[BigInt] = List[BigInt]()
      import scala.collection.JavaConversions._
      for (rec <- updates) {
        temp.add(rec.recipeid)
      }
      this.connection.updateRecipes(temp, updates)
      try {
        this.connection.importDB(inserts)

      } catch {
        case e: IOException => {
          toreturn = new ErrorReport(e.getMessage, DBERROR)
        }
      }
      //toreturn = new Message("updatessuccessfully")

    }catch {
			case e: DBException => {
				toreturn = new ErrorReport(e.getMessage, DBERROR)
			}
		}
		toreturn
	}

	def handleMessage(message: UserListMessage): Message = {
		message.decryptme(this.cryp)
		message
	}

	@SuppressWarnings(Array("all")) def handleMessage(message: XMLIngredientListMessage): Message = new IngredientSetMessage(this.parser.parseIngredientList(message.ingredientsinXML))

	def handleMessage(message: XMLRatingMessage): Message = message

	@SuppressWarnings(Array("all")) def handleMessage(message: XMLRecipeMessage): Message = {
		var temp: Message = null
		if (message.backup) try {
      this.connection.importDB(this.parser.parseRecipeIntoList(message.getMessage))
      //temp = new Nothing("xmlimportsuccsessfully")

    }catch {
			case e: DBException => {
				this.logger.writeerror("Insert recipes from " + "server into local " + "database failed \n" + e.getMessage)
				temp = new ErrorReport( e.getMessage, DBERROR)
			}
			case e: IOException => {
				temp = new ErrorReport( e.getMessage, DBERROR)
			}
		}
		else if (message.islist) {
			this.logger.writeinfo("Before parsed: \n" + message.getMessage)
			temp = new RecipeListMessage(this.parser.parseRecipeEntry(message.getMessage))
			this.logger.writeinfo("Parsed XML RecipeListeMessage: \n" + temp.getMessage)
		}
		else {
			this.logger.writeinfo("Before Recipe parsed: \n" + message.getMessage)
			temp = this.parser.parseRecipe(message.getMessage)
			this.logger.writeinfo("Parsed XML Recipe: \n" + temp.getMessage)
		}
		temp
	}

	@SuppressWarnings(Array("all")) def handleMessage(message: XMLTimestampMessage): Message = {
		var toreturn: Message = null
		val versionsserver: Map[BigInt, BigInt] = this.parser.parseVersions(message.getMessage)
		try {
      val versionsclient: Map[BigInt, BigInt] = this.parser.parseVersions(this.connection.getVersions)
      val keys: Set[BigInt] = versionsserver.keySet
      val uplist: UpdateRecipesMessage = new UpdateRecipesMessage(List[BigInt](), List[BigInt]())
      import scala.collection.JavaConversions._
      for (recipeid <- keys) {
        this.logger.writeerror(recipeid.toString)
        if (versionsclient.containsKey(recipeid)) {
          //if (versionsserver.get(recipeid).compare(versionsclient.get(recipeid)) > 0) {
          //this.logger.writeerror("Want to update recipe")
          uplist.addupdate(recipeid)
        }
        else if (message.unknownto) {
          this.logger.writeerror("Want new recipe")
          uplist.insertrecipes.add(recipeid)
        }
      }
      toreturn = this.sendMessage(uplist)

    }catch {
			case e: DBException => {
				toreturn = new ErrorReport(e.getMessage, DBERROR)
			}
		}
		toreturn
	}


	def sendMessage(msg: Message): Message = {
    var recieved: Message = ErrorReport("Timeoutexpired", UNKNOWNMESSAGE)
    if (!this.status) {
      recieved = this.connection.visitmessage(msg)
    }
    else {
      try {
        this.output.writeObject(msg)
        recieved = this.input.readObject().asInstanceOf[Message]
      } catch {
        case e: SocketException => {
          this.logger.writeerror(e.getMessage)
          if (this.connections < MAXRECON) {
            this.connections += 1
            this.connect
            this.sendMessage(msg)
          }
          recieved = ErrorReport("Timeoutexpired",
            UNKNOWNMESSAGE)
        }
        case e: IOException => this.logger.writeerror(e.getMessage)
        case e: ClassNotFoundException => this.logger.writeerror(e.getMessage)
      }
    }
    recieved.clientHandleMessage(this)
  }

		/** To login with userdata.
			*
			* @param message as Message
			* @return Message
			*/
		def sendUser(message: Message): Message = {
			SWTLogger.logger.info("Logger sendUser:" + message.getMessage())

			message.asInstanceOf[Loginmessage].encryptme(this.cryp)
			val temp = this.sendMessage(message)

			if (temp.asInstanceOf[ErrorReport] == null) {
				this.loggedin = false
				SWTLogger.logger.info("Logger sendUser: User is not logged in")
			} else {
				this.usertype = temp.asInstanceOf[AbstractUser]
				this.loggedin = true
				SWTLogger.logger.info("Logger sendUser: User is logged in")
			}
			temp
		}

	/** Gets the address of the clients' localhost.
		*
		* @return the client address as String **/
	getLocalhostadress= this.getLocalhostadress

	/** Gets the port.
		*
		* @return the port id **/
 getPort = this.port

	/** Gets the address of the server.
		*
		* @return the server address as String
	 */
	getServeradress = this.getServeradress

	/** Gets the client socket.
		*
		* @return the socket
		*/
	getSock= this.getSock

	/** Gets the type of the user which is currently logged in.
		*
		* @return an object of a class extending AbstractUser.
		* */
	 getUsertype = this.getUsertype

	/** Sends a message to the server to get all recipes from the database.
		*
		* @return Message (Confirm or Error)
		*/
	override def importDB: Message = ???

	/** Return connectstatus.
		*
		* @return boolean connected true or false */
	override def isStatus: Boolean = ???

  def logOut: Message = {
    var temp: Message = null
    if (this.isStatus) {
      this.logger.writeinfo("Logger logut: Signal to logout user sending")
      this.usertype = new Guest("", "")
      temp = this.sendMessage(new LogoutMessage)
    }
    else temp = new ErrorReport("Youarenotloggedin", SERVERNOTFOUND)
    temp
  }

  /** Method to synchronise own Data to Server Data.
    * Send Message to server with flag to update own recipes to newest Version
    * and optional gets unknown recipes
    *
    * @param unknowntoo Boolean if unknown recipes will get too
    * @return ErrorReport or Confirmreport
    */
  override def synchronise(unknowntoo: Boolean): Message = ???

  /** Get a Recipe back by Name.
    *
    * @param msg String name of Recipe
    * @return Recipe*/
override def getRecipeByName(msg: String): Message = ???

  /** Make Server input a backup.
    *
    * @param number    0,1,2 the backups (0 is newest)
    * @param userecipe Boolean to decide if user oder recipe file will be used
    * @return Confirm or ErrorReport
    */
  override def serverinputbackup(number: Int, userecipe: Boolean): Message = ???
}