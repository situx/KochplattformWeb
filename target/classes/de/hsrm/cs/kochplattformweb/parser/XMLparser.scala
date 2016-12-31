package de.hsrm.cs.kochplattformweb.parser


import java.net.URI
import java.math.BigInteger

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64
import de.hsrm.cs.kochplattformweb.client.{AbstractUser, Admin, MainUser}
import de.hsrm.cs.kochplattformweb.db._

import scala.xml._
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import scala.collection.immutable.TreeSet
import scala.collection.mutable

/**Scala XML parser
  * @author Timo Homburg/Lehel Eses
  * @version $Date$ **/
class XMLparser() {
  def parseRecipeIntoList(updaterecipes: List[BigInt]): List[Recipe] = ???

  var result:String = ""

  def parseIds(xml:String):List[BigInt]={
    println(xml)
    val xmlInput = XML.loadString(xml)
    var list = List[BigInt]()

    for(test <- xmlInput \\ "id"){
      list.add(BigInt(test.text))
    }

    list
  }

  /*private def parseRecipe(n: Node): Recipe = n.map(x => Recipe(
    name = (x \ "title").text,
    category = (x \ "@category").text,
    recipeId = new BigInteger((x \ "@id").headOption.getOrElse("0").toString),
    time = (x \ "@time").text,
    rating = (x \ "@rating").headOption.getOrElse("-1").toString.toDouble,
    ingredients = (x \ "ingredient").map(parseIngredient(_)),
    zombies = (x \ "@zombies").headOption.getOrElse("-1").toString.toInt,
    language = (x \ "@lang").text,
    authorId = new BigInteger((x \ "@author_id").text),
    preparation = (x \ "preparation").text,
    difficulty = (x \ "@difficulty").text,
    nutrition = Nutrition((x \ "@calories").text.toDouble, (x \ "@fat").text.toDouble, (x \ "@carbohydrates").text.toDouble, (x \ "@protein").text.toDouble),
    ratings = (x \ "rating").map(x2 => Rating((x2 \ "@rate").text.toDouble, x2.text, new BigInteger((x2 \ "@userid").text))),
    picture = (x \ "image").map(x2 => new ByteImage(x2.text)), // Decoder-Aufruf/Base64 muesste ergaenzt werden
    videos = (x \ "video").map(x2 => (x2.text -> new URI((x2 \ "@url").text))).toMap[String, URI])).head

  def parseRecipeEntry(xml: String): JList[RecipeEntry] = (xml \\ "tableentry").map(x => Recipe(
    name = (x \ "title").text,
    category = (x \ "@category").text,
    recipeId = new BigInteger((x \ "@id").headOption.getOrElse("0").toString),
    time = (x \ "@time").text,
    rating = (x \ "@rating").headOption.getOrElse("-1").toString.toDouble,
    ingredients = (x \ "ingredients" \ "ingredient").map(x2 => Ingredient(x2.text)),
    zombies = (x \ "@zombies").headOption.getOrElse("-1").toString.toInt))
    */
  /** Parses ALL Ingredients
    *
    * @param xml as Ingredient string
    * @return Set of Strings with Ingredients
    */
  def parseIngredientList(xml:String):Set[Ingredient]={
    val xmlInput = XML.loadString(xml)
    val ingredientset: Set[Ingredient] = Set[Ingredient]()
    var temp=1.0
    for (test <- xmlInput \\ "ingredient"){
      ingredientset.add(Ingredient(java.lang.Double.valueOf(test.text), (test \ "@amount").text, EnumAmountType.withName((test \ "@unit").text.toUpperCase).toString))
    }
    ingredientset
  }

  /** Parses the Ratings **/
  def parseRating(xml:String):List[Rating]= {

    val xmlInput = XML.loadString(xml)
    val list: List[Rating] = List[Rating]()

    for (test <- xmlInput \\ "ratings") {
      list.add(Rating(java.lang.Double.valueOf((test \\ "@rating").text), "", new BigInteger("-1")))
      for (test2 <- test \\ "rating") {
        list.add(Rating(java.lang.Double.valueOf((test2 \\ "@rate").text), test2.text, new BigInteger((test2 \\ "@userid").text)))
      }
    }
    list
  }

  /**Parse a recipe xml string and put it into recipe.
    * @param xml string recipe
    * @return a list with recipes
    */
  def parseRecipe(xml:String):Recipe= {
    println(xml)
    val xmlInput = XML.loadString(xml)
    val recipe = new Recipe()
    var ingredientlist:List[Ingredient] = List[Ingredient]()
    val imagelist: List[ByteImage] = List[ByteImage]()
    val videomap = Map[String, URI]()
    val ratinglist: List[Rating] = List[Rating]()

    /**Parsing every tableentry and add to the object Recipe.java.**/
    for (test <- xmlInput \\ "recipe") {
      recipe.name=(test \ "title").text
      recipe.category=(test \ "@category").text
      if(ingredientlist.nonEmpty)
        ingredientlist=List[Ingredient]()
      for (test2 <- test \ "ingredient"){
        ingredientlist.add(Ingredient(java.lang.Double.valueOf(test2.text), (test2 \\ "@amount").text, EnumAmountType.withName((test2 \\ "@unit").text.toUpperCase).toString))
      }
      recipe.ingredients=ingredientlist
      if ("" != (test \ "@id").text)
        recipe.recipeid=new BigInteger((test \ "@id").text)
      else
        recipe.recipeid=BigInteger.valueOf(0L)
      recipe.time=(test \ "@time").text
      if ("" != (test\"@rating").text)
        recipe.rating= java.lang.Double.valueOf((test \ "@rating").text)
      else
        recipe.rating=(-1)
      recipe.language=(test \ "@lang").text
      recipe.authorId=new BigInteger((test \ "@author_id").text)
      recipe.preparation=(test \ "preparation").text
      recipe.nutrition=new Nutrition(java.lang.Double.valueOf((test \\ "@calories").text),java.lang.Double.valueOf((test \\ "@fat").text),java.lang.Double.valueOf((test \\ "@carbohydrates").text),java.lang.Double.valueOf((test \\ "@protein").text))
      recipe.difficulty=(test \ "@difficulty").text
      for(test6 <- test.child){
        if(test6.label=="rating"){
          ratinglist.add(Rating(java.lang.Double.valueOf((test6 \ "@rate").text), test6.text, new BigInteger((test6 \ "@userid").text)))
        }
      }
      recipe.ratings= ratinglist
      for (test3 <- test \ "image"){
        //imagelist.add(new ByteImage(JImageConverter.bytesToImage(Base64.decode(test3.text))))
      }
      recipe.picture=imagelist
      for (test4 <- test \ "video"){
        videomap.put(test4.text,new URI((test4 \"@url").text))
      }
      recipe.videos= videomap
      if ("" != (test \ "@zombies").text)
        recipe.zombies= Integer.valueOf((test \ "@zombies").text)
      else
        recipe.zombies=(-1)
    }
    recipe
  }

  /**Parse a recipeentry xml string and put it into a list.
    * @param xml string recipe
    * @return a list of recipy entrys
    */
  def parseRecipeEntry(xml:String) :List[RecipeEntry]= {
    val xmlInput = XML.loadString(xml)
    val recipeentrylist = List[RecipeEntry]()
    var recipeentry = new RecipeEntry()
    var ingredientlist:List[Ingredient]= List[Ingredient]()

    /**Parsing every tableentry and add to the object RecipeEntry.java.**/
    for (test <- xmlInput \\ "tableentry") {
      recipeentry.name=(test \ "title").text
      recipeentry.category=(test \ "category").text
      if(ingredientlist.nonEmpty) {
        ingredientlist=List[Ingredient]()
      }
      for (test2 <- test \ "ingredients" \ "ingredient"){
        ingredientlist.add(new Ingredient(test2.text))
      }
      recipeentry.ingredients= ingredientlist
      if ("" != (test\"rating").text)
        recipeentry.rating=java.lang.Double.valueOf((test \ "rating").text)
      else
        recipeentry.rating=(-1)
      recipeentry.time= (test \ "time").text
      if ("" != (test\"id").text)
        recipeentry.recipeid= new BigInteger((test \ "id").text)
      else
        recipeentry.recipeid= new BigInteger("-1")

      if ("" != (test\"zombies").text)
        recipeentry.zombies= Integer.valueOf((test \ "zombies").text)
      else
        recipeentry.zombies=(-1)
      recipeentrylist.add(recipeentry)
      recipeentry = new RecipeEntry()
      result+= (test \ "title").text+(test \ "category").text+(test \ "time").text+(test \ "id").text
    }
    recipeentrylist
  }


  private def parseUser(n: Node): AbstractUser = {
    val id = new BigInteger((n \ "@author_id").text)
    val name = (n \ "@author_name").text
    val pw = (n \ "@password").text
    if ((n \ "@admin").text == "true") new Admin(name, pw, id) else new MainUser(name, pw, id)
  }
  def parseUser(xml: String): AbstractUser={
    var xmlInput = XML.loadString(xml)
    Admin("test", "test", BigInteger.ZERO, true)
    /*(xmlInput \\ "users").map(x => parseUser(x)).head*/
  }
  /**Parses a User from the database.
    * @return The abstractUser being parsed*/
  /*def parseUser(xml:String):JAbstractUser={
    var xmlInput = XML.loadString(xml)
    var result:JAbstractUser=null

    for(test <- (xmlInput \\ "user")){
      if((test\\"@admin").text=="true"){
        result=new Admin((test\\"@author_name").text,(test\\"@password").text)
        result.setUserid(new BigInteger((test\\"@author_id").text))
      }
      else{
        result=new MainUser((test\\"@author_name").text,(test\\"@password").text)
        result.setUserid(new BigInteger((test\\"@author_id").text))
      }
    }
    result
  }*/

  /**Parses ALL Users from the database.
    * @param xml user string
    * @return List of all Users*/
  def parseUsers(xml:String):List[AbstractUser] ={
    val xmlInput = XML.loadString(xml)
    val userList = List[AbstractUser]()

    for(test <- xmlInput \\ "user") {
      if((test\\"@admin").text=="true"){
        userList.add(Admin((test \\ "@author_name").text, (test \\ "@password").text, BigInteger.ZERO, true))
        userList.get(userList.size-1).id=new BigInteger((test\\"@author_id").text)
      }
      if((test\\"@admin").text=="false"){
        userList.add(MainUser((test \\ "@author_name").text, (test \\ "@password").text, BigInteger.ZERO, false))
        userList.get(userList.size-1).id=new BigInteger((test\\"@author_id").text)
      }
    }
    userList
  }

  /**Parse a recipe xml string and put it into a list.
    * @param xml string recipe
    * @return a list with recipes
    */
  def parseRecipeIntoList(xml:String):List[Recipe]= {
    val xmlInput = XML.loadString(xml)
    var recipe = new Recipe()
    var ingredientlist:List[Ingredient] = List[Ingredient]()
    val imagelist: List[ByteImage] = List[ByteImage]()
    val videomap = Map[String, URI]()
    val ratinglist: List[Rating] =List[Rating]()
    var temp = ""
    val recipeList: List[Recipe] = List[Recipe]()

    /**Parsing every tableentry and add to the object Recipe.java.**/
    for (test <- xmlInput \\ "recipe") {
      recipe.name=(test \ "title").text
      recipe.category=(test \ "@category").text
      if(ingredientlist.nonEmpty)
        ingredientlist=List[Ingredient]()
      for (test2 <- test \ "ingredient"){
        temp = (test2 \\ "@unit").text.toUpperCase
        ingredientlist.add(Ingredient(java.lang.Double.valueOf(test2.text), (test2 \\ "@amount").text, EnumAmountType.withName(temp).toString))
      }
      recipe.ingredients= ingredientlist
      if ("" != (test \ "@id").text)
        recipe.recipeid=new BigInteger((test \ "@id").text)
      else
        recipe.recipeid=BigInteger.valueOf(0L)
      recipe.time=(test \ "@time").text
      if ("" != (test\"@rating").text)
        recipe.rating=java.lang.Double.valueOf((test \ "@rating").text)
      else
        recipe.rating=(-1)
      recipe.language=(test \ "@lang").text
      recipe.authorId=new BigInteger((test \ "@author_id").text)
      recipe.preparation=(test \ "preparation").text
      recipe.nutrition=new Nutrition(java.lang.Double.valueOf((test \\ "@calories").text),java.lang.Double.valueOf((test \\ "@fat").text),java.lang.Double.valueOf((test \\ "@carbohydrates").text),java.lang.Double.valueOf((test \\ "@protein").text))
      recipe.difficulty=(test \ "@difficulty").text
      for(test6 <- test.child){
        if(test6.label=="rating"){
          ratinglist.add(Rating(java.lang.Double.valueOf((test6 \ "@rate").text), test6.text, new BigInteger((test6 \ "@userid").text)))
        }
      }
      recipe.ratings= ratinglist
      for (test3 <- test \ "image"){
        imagelist.add(new ByteImage(Base64.decode(test3.text)))
      }
      recipe.picture=imagelist
      for (test4 <- test \ "video")
        videomap.put(test4.text,new URI((test4 \"@url").text))
      recipe.videos= videomap
      if ("" != (test \ "@zombies").text)
        recipe.zombies=Integer.valueOf((test \ "@zombies").text)
      else
        recipe.zombies=(-1)
      recipeList.add(recipe)
      recipe = new Recipe()
    }
    recipeList
  }

  def parseVersions(xml:String)={
    println(xml)
    val xmlInput = XML.loadString(xml)
    val map = Map[BigInt, BigInt]()
    for(test <- xmlInput \\ "status"){
      //var ident = new BigInteger((test\"@id").text)
      //var vers = new BigInteger((test\"@version").text)
      map.put(BigInt((test\"@id").text), BigInt(Integer.parseInt((test\"@version").text)))
    }
    map
  }


}