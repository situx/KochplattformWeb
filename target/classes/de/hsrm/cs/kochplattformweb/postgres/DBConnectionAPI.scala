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


package de.hsrm.cs.kochplattformweb.postgres

import java.sql.{DriverManager, ResultSet}

import de.hsrm.cs.kochplattformweb.client.{AbstractUser, MainUser}
import de.hsrm.cs.kochplattformweb.db.{Ingredient, Rating, Recipe}
import de.hsrm.cs.kochplattformweb.messages.Message

import scala.collection.mutable.ListBuffer

trait DBConnectionAPI extends UserAPI{
	def updateRecipes(temp: List[BigInt], updates: List[Recipe]):Boolean = ???

	def importDB(inserts: List[Recipe]):Boolean = ???

  def setRating(recipeid:BigInt,rate:Rating):Boolean = ???

  def selectRecipesByUser(userid:BigInt):String = ???

  def selectAllUsers():String = ???

  def getRecipe(recipeid:BigInt):String= ???

  def backupDB(exportpath:String,filename:String):Boolean = ???

	def insertUser(user:AbstractUser):Boolean= ???

	def selectAll(ingredients: List[Ingredient]): String = ???

	def select(ingredients: List[Ingredient]): String = ???

	def checkUserVote(recipeid: BigInt, id: BigInt): Boolean = ???

  def selectByName(s: String):String = ???
  def getRating(recipeid: BigInt): String = ???

  def selectAllIngredients(language: String): String = ???

  def insertUser(temp: MainUser): Boolean = ???

  def selectUser(name: String):String = ???

  def visitmessage(msg: Message): Message = ???

  def getRecipeUpdates(getUpdaterecipes: List[BigInt]): List[BigInt] = ???

  def selectRecipeDB(): String = ???

  def updateRecipe(recipeid: BigInt, recipe: Recipe):Boolean = ???

  def importBackup(b: Boolean, s: String):Boolean = ???

  var connection:String=""
  val con = DriverManager.getConnection(connection)
  val state=con.createStatement()

	/**Inserts a recipe in the database.
	 * @param recipeid the recipeid of the recipe to delete
	 * @return a success indicator 
	 */
	def deleteRecipe(recipeid:BigInt):Boolean
	/**Gets the ingredients from the database.
	 * 
	 * @return a formatted html string containing the ingredients
	 */
	def getAllIngredients:String
	/**Gets a recipe by its id from the database.
	 * @param recipeid the recipeid of the recipe to get
	 * @param edit indicates the creation of an editform
	 * @return a formatted html string containing the information
	 */
	def getRecipe(recipeid:BigInt,edit:Boolean):String
	/**Gets a recipe by its name from the database.*/
	def getRecipeByName(name:String,edit:Boolean):String

	def insertRecipe(recipe:Recipe):Boolean
	/**Inserts a recipe in the database.
	 *
	 * @return
	 */
	def selectraw(ings:List[String]):String
	/**Sets a rating for the relating recipe.*/
	def setRating(rating:Double,text:String,recipeid:BigInt,userid:BigInt):Boolean
	def updateUser(userr:AbstractUser):Boolean

	protected def query[B](sql:String)(process: ResultSet => B):B={
		process(state.executeQuery(sql))
	}

	protected def queryEach[T](sql: String)(process: ResultSet => T): List[T] ={
		query(sql){result =>
			bmap(result.next) {
				process(result)
			}
		}
	}

	protected def queryEach[T](result: ResultSet)(process: ResultSet => T): List[T] ={
		bmap(result.next) {
			process(result)
		}
	}
	protected def bmap[T](test: => Boolean)(block: => T): List[T] = {
		val ret = new ListBuffer[T]
		while(test) ret += block
		ret.toList
	}

	def getVersions:String

}