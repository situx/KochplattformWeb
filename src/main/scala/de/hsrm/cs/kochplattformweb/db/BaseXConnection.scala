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

package de.hsrm.cs.kochplattformweb.db
  import de.hsrm.cs.kochplattformweb.client.AbstractUser
  import de.hsrm.cs.kochplattformweb.parser.XMLparser
  import de.hsrm.cs.kochplattformweb.utils.SWTLogger
  import java.io.IOException
  import java.security.NoSuchAlgorithmException

  import com.sun.org.apache.xerces.internal.impl.dv.util.Base64
  import de.hsrm.cs.kochplattformweb.postgres.DBConnectionAPI
  import org.basex.api.client.ClientSession
  import org.basex.core.cmd.{Close, CreateDB, DropDB, Exit, Export, Open, XQuery}


  //mittelweerte der einzelratings je ze

  /**
    * Demonstration einer BaseX-Datenbank-Verbindung mit Java.
    * @author Dirk Preisner
    */
class BaseXConnection(var recipedbFile:String,var userdbFile:String) extends DBConnectionAPI{
    /**Instance for the Singleton implementation.*/
    var  instance:BaseXConnection=null
    /**The port for the DB-Connection: {@value}.**/
   val  DB_PORT = 1984
    /**username {@value}.**/
    val USER = "admin"
    /**password {@value}.**/
    val PASSWORD = "admin"
    /**The sleep value {@value} for letting the database start.*/
    val SLEEPVALUE = 5500
    /**The sleep value {@value} for letting the database start.*/
    val INTERSECT_QUERY = "declare namespace sep = 'http://www.panitz.name/web'"+" declare function sep:except($smallerList,$greaterList){ "+"for $a in $smallerList "+"let $c := $greaterList[. eq $a] "+"where (empty($c)) "+"return ($a)} "+"declare function sep:isSubset($smallerList,$greaterList){ "+"empty(sep:except($smallerList,$greaterList))} "
    /** The connected flag. */
    var connected: Boolean=false
    /**The clientsession for the database connection.**/
    var cli:ClientSession=null
    /**The second clientsession for the database connection.**/
    var cli2:ClientSession=null
    /** XML parser for the importDB method. **/
    var parser: XMLparser=null


    /** Constructor .
      * @param recipedbFile the xml file containing the recipes.
      * @param userdbFile the xml file containing the users.**/
    def BaseXConnection(recipedbFile:String,userdbFile:String){
      this.recipedbFile = recipedbFile
      this.userdbFile = userdbFile
      this.parser=new XMLparser()
      try {
        this.connect()
      } catch {
        case e:InterruptedException => SWTLogger.writeerror(e.getMessage)
       case  e:IOException => SWTLogger.writeerror(e.getMessage)
      }
    }
    /* (non-Javadoc)
     * @see de.hsrm.cs.kochplattform.db.DBAPI#backupDB()
     */
    @Override
    override def backupDB(exportpath:String,filename:String):Boolean={
      //if(this.connected && this.cli.execute(new Export(exportpath))){
      //  return true
      //}
      false
    }

    /* (non-Javadoc)
     * @see de.hsrm.cs.kochplattform.db.DBAPI#checkUserVote()
     */
    @Override
    override def checkUserVote(recipeid:BigInt, userid:BigInt):Boolean= {
      val anf: StringBuffer = new StringBuffer(160)
      anf.append(this.forquery(true))
      anf.append(" where $t/@id='")
      anf.append(recipeid)
      anf.append("' and $t/rating/@userid='")
      anf.append(userid)
      anf.append("' return $t")
      SWTLogger.writeinfo("CheckUserVote:\n"+anf.toString())
      val str: String = this.execute(anf.toString(), true)
      SWTLogger.writeinfo("Result: "+str)
      if("".equals(str)){
        return true
      }
      false
    }

    @Override
    def closeCon(){
      this.connected=false
      this.cli.execute(new Close())
      this.cli2.execute(new Close())
      this.cli.execute(new Exit())
      this.cli2.execute(new Exit())
      this.cli.close()
      this.cli2.close()
    }
    /**Method for connecting to the database.
      * @throws IOException on error
      * @throws InterruptedException */
   def connect() {
     val t: Thread = new Thread(new Runnable() {
       @Override
       def run() {
         org.basex.BaseXServer.main(new Array[String](0))
       }
     })
      t.start()
      //Runtime.getRuntime().exec("java -cp BaseX61.jar" +
      //	" org.basex.BaseXServer")
      Thread.sleep(SLEEPVALUE)
      this.cli = new ClientSession("localhost", DB_PORT, USER, PASSWORD)
      this.cli2 = new ClientSession("localhost", DB_PORT, USER, PASSWORD)
      if(this.cli.execute(new Open(this.recipedbFile.substring(0, this.recipedbFile.lastIndexOf('.'))))!=null && this.cli2.execute(new Open(this.userdbFile.substring(0, this.userdbFile.lastIndexOf('.'))))!=null){
        this.connected=true
      }
    }
    /* (non-Javadoc)
     * @see de.hsrm.cs.kochplattform.db.DBAPI#remove(int)
     */
    @Override
    override def deleteRecipe(recipeid:BigInt):Boolean= {
      val rem: StringBuffer = new StringBuffer(120)
      rem.append(this.forquery(true))
      rem.append(" where $t[@id='")
      rem.append(recipeid)
      rem.append("'] return delete node $t")
      SWTLogger.writeinfo("Remove Recipe:\n"+rem.toString)
      this.execute(rem.toString,true)
      true
    }
    /* (non-Javadoc)
     * @see de.hsrm.cs.kochplattform.db.DBAPI#
     * deleteuser(de.hsrm.cs.kochplattform.client.AbstractUser)
     */
    @Override
    override def deleteUser(user:BigInt):Boolean={
      val rem: StringBuffer = new StringBuffer(120)
      rem.append(this.forquery(false))
      rem.append(" where $t/@author_id='")
      rem.append(user)
      rem.append("' return delete nodes $t")
      SWTLogger.writeinfo("Delete User:\n"+rem.toString)
      this.execute(rem.toString,false)
      true
    }
    /**Returns the instance of BaseXConnection.
      * @param recipe the name of the recipe DB
      * @param user the name of the user DB
      * @return the instance of BaseXConnection
      * */
    def getInstance(recipe:String,user:String)={
      if(instance==null || !instance.connected){
        instance=new BaseXConnection(recipe,user)
      }
      instance
    }
    /* (non-Javadoc)
     * @see de.hsrm.cs.kochplattform.db.DBAPI#getMaxRecipeId()
     */
    @Override
    def getMaxRecipeId:BigInt= {
      val anf:StringBuffer= new StringBuffer(120)
      anf.append("max(for $t in doc('")
      anf.append(this.recipedbFile)
      anf.append("')/recipes/recipe return $t/@id)")
      SWTLogger.writeinfo("Get Max Recipe Id:\n"+anf.toString)
      BigInt(this.execute(anf.toString,true).toString.toInt)
    }

    /* (non-Javadoc)
     * @see de.hsrm.cs.kochplattform.db.DBAPI#getMaxUserId()
     */
    @Override
    def getMaxUserId:BigInt={
      val anf:StringBuffer= new StringBuffer(120)
      anf.append("max(for $t in doc('")
      anf.append(this.userdbFile)
      anf.append("')/users/user return $t/@author_id)")
      SWTLogger.writeinfo("Get Max User Id:\n"+anf.toString)
      BigInt(this.execute(anf.toString,true).toString.toInt)
    }

    /* (non-Javadoc)
     * @see de.hsrm.cs.kochplattform.db.DBAPI#getRating(int)
     */
    @Override
    override def getRating(recipeid:BigInt):String={
      val anf: StringBuffer = new StringBuffer(120)
      anf.append(this.forquery(true))
      anf.append(" where $t/@id='")
      anf.append(recipeid)
      anf.append("' return <ratings>{$t/@rating}{$t/rating}</ratings> ")
      SWTLogger.writeinfo("Get Rating:\n"+anf.toString())
      this.execute(anf.toString(),true)
    }

    /* (non-Javadoc)
     * @see de.hsrm.cs.kochplattform.db.DBAPI#getRecipe(int)
     */
    @Override
    override def getRecipe(recipeid:BigInt):String= {
      val anf: StringBuffer = new StringBuffer(160)
      anf.append(this.forquery(true))
      anf.append(" where $t/@id='")
      anf.append(recipeid)
      anf.append("' return $t")
      SWTLogger.writeinfo("Get Recipe by Id:\n"+anf.toString)
      this.execute(anf.toString,true)
    }

    /* (non-Javadoc)
     * @see de.hsrm.cs.kochplattform.db.DBAPI#selectuser(int)
     */
    @Override
    override def getVersions:String={
      val anf: StringBuffer = new StringBuffer(160)
      anf.append(this.forquery(true))
      anf.append(" return <status>{$t/@id}{$t/@version}</status>")
      SWTLogger.writeinfo("Get Versions:\n"+anf.toString)
      val str: String = this.execute(anf.toString, true)
      SWTLogger.writeinfo("Result: "+str)
      "<versions>"+str+"</versions>"
    }
    /* (non-Javadoc)
     * @see de.hsrm.cs.kochplattform.db.DBAPI#importBackup(boolean,String)
     */
    @Override
    override def importBackup(isrecipeDB:Boolean,
                              filename:String):Boolean={
      if(isrecipeDB){
        this.cli.execute(new DropDB(this.recipedbFile.substring(0,
          this.recipedbFile.lastIndexOf('.'))))
        this.cli.execute(new CreateDB(filename,this.recipedbFile.substring(0,
          this.recipedbFile.lastIndexOf('.'))))
      }
      else{
        this.cli.execute(new DropDB(this.userdbFile.substring(0,
          this.userdbFile.lastIndexOf('.'))))
        this.cli.execute(new CreateDB(filename,this.userdbFile.substring(0,
          this.userdbFile.lastIndexOf('.'))))
      }
      true
    }

    /* (non-Javadoc)
     * @see de.hsrm.cs.kochplattform.db.DBAPI#importDB(java.util.List)
     */
    @Override
    override def importDB(recipelist:List[Recipe]):Boolean= {
      val temp: StringBuffer = new StringBuffer(10000)
      temp.append(this.forquery(true))
      temp.append(" return <id>{string($t/@id)}</id>")
      SWTLogger.writeinfo("\nImport DB I:\n"+temp.toString)
      val idlist: List[BigInt] = this.parser.parseIds("<ids>" + this.execute(temp.toString, true) + "</ids>")
      SWTLogger.writeinfo("\nImport DB II :\n"+temp.toString)
      if(recipelist.isEmpty){
        return true
      }
      val iter: Iterator[Recipe] = recipelist.iterator

      while(iter.hasNext){
        if(idlist.contains(iter.next.recipeid)){
          //iter.remove()
        }
      }
      if(recipelist.isEmpty){
        return true
      }
      temp.delete(0, temp.length())
      for(recipe:Recipe<-recipelist){
        this.insertRecipe(recipe)
      }
      true
    }
    /* (non-Javadoc)
     * @see de.hsrm.cs.kochplattform.db.DBAPI#insertrecipe
     * (de.hsrm.cs.kochplattform.client.AbstractUser)
     */
    @Override
    override def insertRecipe(recipe:Recipe):Boolean={
      val ins: StringBuffer = new StringBuffer(400)
      ins.append("insert nodes ")
      ins.append(this.buildRecipeasXML(recipe,false))
      ins.append(" into doc('")
      ins.append(this.recipedbFile)
      ins.append("')/recipes")
      SWTLogger.writeinfo("\nInsert Recipe:\n"+ins.toString)
      this.execute(ins.toString,true)
      true
    }

    /* (non-Javadoc)
     * @see de.hsrm.cs.kochplattform.db.DBAPI#
     * insertuser(de.hsrm.cs.kochplattform.client.AbstractUser)
     */
    @Override
    override def insertUser(user:AbstractUser):Boolean={
      val ins: StringBuffer = new StringBuffer(130)
      ins.append("insert nodes ")
      ins.append(this.buildUserAsXML(user,false))
      ins.append(" into doc('")
      ins.append(this.userdbFile)
      ins.append("')/users")
      SWTLogger.writeinfo("Insert User:\n"+ins.toString)
      this.execute(ins.toString,false)
      true
    }

    /* (non-Javadoc)
     * @see de.hsrm.cs.kochplattform.db.DBAPI#selectAll(java.util.List)
     */
    @Override
    override def selectAll(ing:List[Ingredient]):String= {
      val anf: StringBuffer = new StringBuffer(160)
      val returnit: String = " order by $t/title ascending " +
        "return <tableentry><title>{$t/title/text()}</title>" +
        "<category>{string($t/@category)}</category>" +
        "<ingredients>{$t/ingredient}</ingredients>" +
        "<time>{string($t/@time)}</time>" +
        "<zombies>{string($t/@zombies)}</zombies>" +
        "<rating>{string($t/@rating)}</rating><id>{string($t/@id)}</id>" +
        "</tableentry>"
      SWTLogger.writeinfo("Select-Anfrage: ")
      if(ing.nonEmpty){
        anf.append(this.forquery(true))
        anf.append(" where $t/ingredient='")
        anf.append(ing(0)+"' ")

        for (ingg:Ingredient <- ing) {
          anf.append("and $t/ingredient='"+ ing + "' ")
        }
        anf.append(returnit)
        SWTLogger.writeinfo(anf.toString)
        "<table>%s</table>".format(this.execute(anf.toString, true))
      }
      SWTLogger.writeinfo("for $t in doc('" + this.recipedbFile +
        "')/recipes/recipe"+returnit)
       "<table>"+this.execute("for $t in doc('" + this.recipedbFile +
        "')/recipes/recipe"+returnit,true)+"</table>"
    }

    /* (non-Javadoc)
     * @see de.hsrm.cs.kochplattform.db.DBAPI#selectAllIngredients()
     */
    @Override
    override def selectAllIngredients(language:String):String= {
      val anf: StringBuffer = new StringBuffer(160)
      anf.append(this.forquery(true))
      anf.append(" where $t/@lang='")
      anf.append(language)
      anf.append("' return $t/ingredient")
      SWTLogger.writeinfo("Select All Ingredients: "+anf.toString)
      "<ingredients>"+this.execute(anf.toString,true)+"</ingredients>"
    }
    /* (non-Javadoc)
     * @see de.hsrm.cs.kochplattform.db.DBAPI#selectallusers()
     */
    @Override
    override def selectAllUsers():String={
      SWTLogger.writeinfo("Select All Users:\nfor $t in doc('"
        + this.userdbFile + "')/users/user order by $t/@author_name" +
        " return $t")
      "<users>"+this.execute(this.forquery(false)+" order by $t/@author_name return $t",
        false)+"</users>"
    }
    /* (non-Javadoc)
     * @see de.hsrm.cs.kochplattform.db.DBAPI#selectbyName(java.lang.String)
     */
    @Override
    override def selectByName(name:String):String= {
      val anf: StringBuffer = new StringBuffer(60)
      val returnit: String = " order by $t/title ascending " +
        "return <tableentry><title>{$t/title/text()}</title>" +
        "<category>{string($t/@category)}</category>" +
        "<ingredients>{$t/ingredient}</ingredients>" +
        "<time>{string($t/@time)}</time>" +
        "<zombies>{string($t/@zombies)}</zombies>" +
        "<rating>{string($t/@rating)}</rating><id>{string($t/@id)}</id>" +
        "</tableentry>"
      anf.append(this.forquery(true))
      anf.append(" where $t/title='")
      anf.append(name)
      anf.append('\'')
      anf.append(returnit)
      "<table>"+this.execute(anf.toString,true)+"</table>"
    }

    /* (non-Javadoc)
     * @see de.hsrm.cs.kochplattform.db.DBAPI#selectRecipeDB()
     */
    @Override
    override def selectRecipeDB():String={
      this.execute("doc('"+this.recipedbFile+"')", true)
    }
    /*
     * (non-Javadoc)
     * @see de.hsrm.cs.kochplattform.db.DBAPI#select
     * (de.hsrm.cs.kochplattform.client.AbstractUser)
     */
    @Override
    override def select(searchItems:List[Ingredient]):String= {
      val anf: StringBuffer = new StringBuffer(1000)
      SWTLogger.writeinfo("Select-Anfrage: ")
      anf.append(INTERSECT_QUERY)
      anf.append("let $a := <recipe>")
      for (ing:Ingredient <- searchItems) {
        anf.append("<ingredient>" + ing + "</ingredient>")
      }
      anf.append("</recipe> ")
      anf.append(this.forquery(true))
      anf.append("[sep:isSubset(./ingredient,$a/ingredient)] ")
      anf.append("order by $t/title ascending " +
        "return <tableentry><title>{$t/title/text()}</title>" +
        "<category>{string($t/@category)}</category>" +
        "<ingredients>{$t/ingredient}</ingredients>" +
        "<time>{string($t/@time)}</time>" +
        "<zombies>{string($t/@zombies)}</zombies>"+
        "<rating>{string($t/@rating)}</rating><id>{string($t/@id)}</id>" +
        "</tableentry>")
      SWTLogger.writeinfo(anf.toString())
      "<table>" + this.execute(anf.toString(),true) + "</table>"
    }

    /* (non-Javadoc)
     * @see de.hsrm.cs.kochplattform.db.DBAPI#selectuser(int)
     */
    @Override
    override def selectUser(username:String):String= {
      val anf: StringBuffer = new StringBuffer(100)
      anf.append(this.forquery(false))
      anf.append(" where $t/@author_name='")
      anf.append(username)
      anf.append("' return $t")
      SWTLogger.writeinfo("Select User:\n"+anf.toString)
      "<users>"+this.execute(anf.toString,false)+"</users>"
    }

    /* (non-Javadoc)
     * @see de.hsrm.cs.kochplattform.db.DBAPI#selectuser(int)
     */
    @Override
    override def selectRecipesByUser(userid:BigInt):String= {
      val anf: StringBuffer = new StringBuffer(100)
      anf.append(this.forquery(true))
      anf.append(" where $t/@author_id='")
      anf.append(userid)
      anf.append("' return $t")
      SWTLogger.writeinfo("Select User:\n"+anf.toString)
      "<recipes>"+this.execute(anf.toString,false)+"</recipes>"
    }

    /* (non-Javadoc)
     * @see de.hsrm.cs.kochplattform.db.DBAPI#selectuser(int)
     */
    @Override
    override def getRecipeUpdates(synchlist:List[BigInt]) :List[BigInt]={
      val anf: StringBuffer = new StringBuffer(100)
      if(synchlist.isEmpty){
        SWTLogger.writeinfo("List empty")
        return List[BigInt]()//"<recipes></recipes>"
      }
      for(intg:BigInt <- synchlist){
        anf.append(this.getRecipe(intg))
      }
      /*
      anf.append(this.forquery(true))
      anf.append(" where $t/@id='")
      anf.append(synchlist.get(0)+"' ")
      final Iterator<BigInteger> iter = synchlist.iterator()
      for (iter.next() iter.hasNext()) {
        anf.append("or $t/@id='"+iter.next() + "' ")
      }
      anf.append("return $t")*/
      SWTLogger.writeinfo("Get Recipe Updates:\n"+anf.toString)
      "<recipes>"+anf.toString+"</recipes>"
      null
    }

    /* (non-Javadoc)
     * @see de.hsrm.cs.kochplattform.db.DBAPI#setRating(int)
     */
    @Override
    override def setRating(recipeid:BigInt,rate:Rating):Boolean= {
      val ins: StringBuffer = new StringBuffer(300)
      ins.append(this.forquery(true))
      ins.append(" where $t[@id='")
      ins.append(recipeid)
      ins.append("'] return insert nodes <rating rate=\"")
      ins.append(rate.rating)
      ins.append("\" userid=\"")
      ins.append(rate.userid)
      ins.append("\">")
      ins.append(rate.comment)
      ins.append("</rating> into $t")
      this.execute(ins.toString,true)
      SWTLogger.writeinfo("Ratingeintrag:\n"+ins.toString)
      ins.delete(0, ins.length())
      ins.append(this.forquery(true))
      ins.append(" where $t[@id='")
      ins.append(recipeid)
      ins.append("'] return replace value of node $t/@rating with avg($t/rating/@rate)")
      SWTLogger.writeinfo(ins.toString)
      SWTLogger.writeinfo("Aktualisierung des Durchschnittsratings:\n"
        +ins.toString)
      this.execute(ins.toString,true)
      true
    }

    /* (non-Javadoc)
     * @see de.hsrm.cs.kochplattform.db.DBAPI#
     * updaterecipe(int, de.hsrm.cs.kochplattform.db.Recipe)
     */
    @Override
    override def updateRecipe(recipeid:BigInt, recipe:Recipe):Boolean={
      val upd: StringBuffer = new StringBuffer(400)
      upd.append(this.forquery(true))
      upd.append(" where $t[@id='")
      upd.append(recipeid)
      upd.append("'] return replace node $t with ")
      upd.append(this.buildRecipeasXML(recipe,true))
      SWTLogger.writeinfo("Update Recipe:\n"+upd.toString)
      this.execute(upd.toString,true)
      true
    }
    /* (non-Javadoc)
     * @see de.hsrm.cs.kochplattform.db.DBAPI#
     * updaterecipe(int, de.hsrm.cs.kochplattform.db.Recipe)
     */
    @Override
    override def updateRecipes(recipeidlist:List[BigInt], recipelist:List[Recipe]):Boolean= {
      var count=0
      for(recipe:Recipe<-recipelist){
        this.updateRecipe(recipeidlist(count), recipe)
        count+=1
      }
      SWTLogger.writeinfo("Update Recipes: ")
      true
    }

    /* (non-Javadoc)
     * @see de.hsrm.cs.kochplattform.db.DBAPI#
     * updateuser(de.hsrm.cs.kochplattform.client.AbstractUser)
     */
    @Override
   override def updateUser(user:AbstractUser):Boolean={
      val upd: StringBuffer = new StringBuffer(160)
      upd.append(this.forquery(false))
      upd.append(" where $t[@author_id='")
      upd.append(user.id)
      upd.append("'] return replace node $t with ")
      upd.append(this.buildUserAsXML(user,true))
      SWTLogger.writeinfo("Update User:\n"+upd.toString)
      this.execute(upd.toString,false)
      true
    }

    /**Creates the xml representation of a recipe as string.
      * @param recipe the recipe to convert
      * @param update indicates if the recipe should be updated
      * @return the converted recipe
      * @throws DBException on error*/
    def buildRecipeasXML(recipe:Recipe, update:Boolean){
      val buf: StringBuffer = new StringBuffer(380)
      val result=
        <recipe id='{recipe.recipeid}' category="{recipe.category}" difficulty="{recipe.difficulty}"></recipe>;
      buf.append("<recipe id='")
      if(update){
        buf.append(recipe.recipeid)
      }
      else{
        buf.append(this.getMaxRecipeId+(BigInt(1)))
      }
      buf.append("' category='")
      buf.append(recipe.category)
      buf.append("' difficulty='")
      buf.append(recipe.difficulty)
      buf.append("' ingredients='")
      buf.append(recipe.ingredients.length)
      buf.append("' time='")
      buf.append(recipe.time)
      buf.append("' lang='")
      buf.append(recipe.language)
      buf.append("' author_id='")
      buf.append(recipe.authorId)
      buf.append("' rating='-1' zombies='")
      buf.append(recipe.zombies)
      buf.append("' version='")
      if(update){
        buf.append((this.getVersion(recipe.recipeid).toString+BigInt(1)).toString)
      }else {
        buf.append(0)
      }
      buf.append("' ><title>")
      buf.append(recipe.name)
      buf.append("</title>")
      for (xs:Ingredient <- recipe.ingredients) {
        buf.append(xs.toXML.toString)
      }
      buf.append("<preparation>")
      buf.append(recipe.preparation)
      buf.append("</preparation>")
      buf.append(recipe.nutrition.toXML)
      /*if(update){
        for(Rating rating: recipe.getRatings()){
          buf.append("<rating rate='")
          buf.append(rating.getRatingnumber())
          buf.append("'>")
          buf.append(rating.getComment())
          buf.append("</rating>")
        }
      }*/
      for(img:ByteImage <- recipe.picture){
        buf.append("<image><![CDATA[")
        buf.append(Base64.encode(img.getBytes))
        buf.append("]]></image>")
      }
      for(url:String <- recipe.videos.keySet){
        buf.append("<video url='"+recipe.videos.get(url)+"'>"+url+"</video>")
      }
      buf.append("</recipe>")
      buf.toString()
    }

    /**Creates the XML representation of a user.
      * @param user the user to convert
      * @param update indicates if the user should be updated
      * @return the converted user
      * @throws DBException
      */
    def buildUserAsXML(user:AbstractUser,update:Boolean){
      val buffer: StringBuffer = new StringBuffer(100)
      buffer.append("<user author_name=\"")
      buffer.append(user.name)
      buffer.append("\" password=\"")
      buffer.append(user.password)
      buffer.append("\" admin=\"")
      buffer.append(user.isAdmin)
      buffer.append("\" author_id=\"")
      if(update){
        buffer.append(user.id)
      }
      else{
        buffer.append(BigInt(this.getMaxUserId.toString.toInt)+(BigInt.int2bigInt(1)))
      }
      buffer.append("\"/>")
      buffer.toString
    }

    /**Creates a searchquery for the two databases.
      * @param recorusr indicates which database is used.
      * @return the query as string
      */
    def forquery(recorusr:Boolean){
      val buf: StringBuffer = new StringBuffer(100)
      buf.append("for $t in doc('")
      if(recorusr){
        buf.append(this.recipedbFile)
        buf.append("')/recipes/recipe")
      }
      else{
        buf.append(this.userdbFile)
        buf.append("')/users/user")
      }
      buf.toString()
    }
    /**Private execution methode for every database query.
      * @param query the query string.
      * @param choose indicates on which database the query is executed
      * @return String.
      * @throws DBException **/
    def execute(query:String, choose:Boolean):String={
      var result:String=null
      try {
        if(choose){
          result=this.cli.execute(query)
          if(result!=null){
            throw new DBException(this.cli.info())
          }
        }
        else{
          result=this.cli2.execute(new XQuery(query))
          if(result!=null){
            throw new DBException(this.cli2.info())
          }
        }
      }catch {
        case e:IOException => throw new DBException(e.getMessage)
      }
      result
    }
    /**Private function to return the version of a recipe.
      * @param recipeid the recipeid to find out
      * @return the current version of the recipe
      * @throws DBException on error*/
    def getVersion(recipeid:BigInt) {
      val anf: StringBuffer = new StringBuffer(160)
      anf.append(this.forquery(true))
      anf.append(" where $t/@id='")
      anf.append(recipeid)
      anf.append("' return string($t/@version)")
      SWTLogger.writeinfo("Get Recipe Version:\n"+anf.toString)
      BigInt(this.execute(anf.toString,true))
    }

    /**Setter for connected.
      * @param connected .*/
    def setConnected(connected:Boolean){
      this.connected=connected
    }

    /** Gets the ingredients from the database.
      *
      * @return a formatted html string containing the ingredients
      */
    override def getAllIngredients: String = ???

    /** Gets a recipe by its id from the database.
      *
      * @param recipeid the recipeid of the recipe to get
      * @param edit     indicates the creation of an editform
      * @return a formatted html string containing the information
      */
    override def getRecipe(recipeid: BigInt, edit: Boolean): String = ???

    /** Gets a recipe by its name from the database.*/
override def getRecipeByName(name: String, edit: Boolean): String = ???

    /** Inserts a recipe in the database.
      *
      * @return
      */
override def selectraw(ings: List[String]): String = ???

    /** Sets a rating for the relating recipe.*/
override def setRating(rating: Double, text: String, recipeid: BigInt, userid: BigInt): Boolean = ???
override def forgotpassword(username: String): Boolean = ???

    /** Gets all users from the database.
      *
      * @return a formatted html string containing the users
      */
override def getAllUsers(): String = ???

    /** Method for logging in a user.
      *
      * @param username the username
      * @param password the password
      * @return an Array[Integer] containing the users status and its id
      */
override def login(username: String, password: String): Array[BigInt] = ???
override def logout(): Boolean = ???

    /** Adds a user to the database.
      *
      * @throws NoSuchAlgorithmException
      * @param username the username to add
      * @param the      password in plaintext
      * @return an Array[Integer] containing the users status and its id
	 */
    override def register(username: String, password: String): Array[BigInt] = ???

    /** Sets the status of the given user to Admin or MainUser.
      *
      * @param userid the userid of the givn user
      * @param status the status indicator
      * @return a success indicator
	 */
    override def setAdminState(userid: BigInt, status: String): Boolean = ???

    /** Sets the password of a user if he chooses the forgotten password option.*/
    override def setPassword(username: String, oldpassword: String, password: String): Boolean = ???

    /** Updates the password of a user.
      *
      * @param password the new password to set
      * @param userid   the userid
      * @return a success indicator
	 */
    override def updatePassword(password: String, userid: BigInt): Boolean = ???

    /** Updates a user in the database.
      *
      * @throws NoSuchAlgorithmException*/
    override def updateUser(username: String, password: String, userid: BigInt): Boolean = ???
}

