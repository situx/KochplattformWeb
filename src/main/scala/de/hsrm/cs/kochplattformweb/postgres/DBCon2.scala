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

/*
 * This file is part of ${PROJECT_NAME}.
 *
 *   ${PROJECT_NAME} is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *    ${PROJECT_NAME} is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with ${PROJECT_NAME}.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package de.hsrm.cs.kochplattformweb.postgres

import de.hsrm.cs.kochplattformweb.db.Recipe

import scala.collection.mutable.ListBuffer
import java.sql.{DriverManager, ResultSet}
import java.util

import de.hsrm.cs.kochplattformweb.client.AbstractUser

class DBCon2 extends  DBConnectionAPI {
	connection="jdbc:postgresql://localhost:5432/Rezepte?user=postgres&password=post"
	classOf[org.postgresql.Driver]
	val user:UserAPI=new UserManagement(state)
	
	override def deleteRecipe(recipeid:BigInt)=state.executeUpdate("DELETE * FROM \"recipe\" WHERE id="+recipeid)>0
	
	override def deleteUser(id:BigInt)=user.deleteUser(id)
	
	override def forgotpassword(username:String)=user.forgotpassword(username)
	
	override def getAllUsers()=user.getAllUsers
	
		override def getRecipe(recipeid:BigInt,edit:Boolean)={

	<table width="100%">
	{query("""SELECT DISTINCT * FROM "recipe","ingredient" WHERE recipe.id=ingredient.recipeid AND id='"""+recipeid.toString+"'"){
		result =>
	<tr><th colspan="2">{result.getString(2)}</th></tr>
	<tr><td>Kategorie:</td><td>{result.getString(5)}</td></tr>
	<tr><td>Schwierigkeit:</td><td>{result.getString(4)}</td></tr>
	<tr><td>Kochzeit:</td><td>{result.getString(3)}</td></tr>
	<tr><td>Personenanzahl:</td><td>{result.getString(8)}</td></tr>
	<tr><td>Zubereitung:</td><td>{result.getString(7)}</td></tr>
	<tr><td>Zutaten:</td><td><ul>
	{queryEach(result){result => <li>result.getString(12) result.getString(13) result.getString(10)</li>}}
	</ul></td></tr>}
	}</table>.toString
	}
	
	override def insertRecipe(recipe:Recipe)=true
	
	override def login(username:String, password:String)=user.login(username,password)
	
	override def logout()=true
	
	
	
	override protected def query[B](sql:String)(process: ResultSet => B):B={
		process(state.executeQuery(sql))
	}
	
	override protected def queryEach[T](sql: String)(process: ResultSet => T): List[T] ={
		query(sql){result =>
	      bmap(result.next) {
	        process(result)
	      }
		}
	 }
	
	override protected def queryEach[T](result: ResultSet)(process: ResultSet => T): List[T] ={
	      bmap(result.next) {
	        process(result)
	      }
	}
	override protected def bmap[T](test: => Boolean)(block: => T): List[T] = {
	    val ret = new ListBuffer[T]
	    while(test) ret += block
	    ret.toList
	}
	
	override def register(username:String, password:String)=user.register(username,password)
	
	override def setAdminState(userid:BigInt, status:String)=user.setAdminState(userid,status)
	
	override def setPassword(username:String,oldpassword:String,password:String)=user.setPassword(username, oldpassword, password)
	
	override def setRating(rating:Double,text:String,recipeid:BigInt,userid:BigInt)=state.executeUpdate("INSERT INTO rating (userid,recipeid,rating,ratingtext) VALUES ('"+userid+"','"+recipeid+"','"+rating+"','"+text+"')")>0
	
	override def updatePassword(password:String,userid:BigInt)=user.updatePassword(password,userid)
	
	override def updateUser(username:String,password:String,userid:BigInt)=user.updateUser(username,password,userid)

	/** Gets the ingredients from the database.
		*
		* @return a formatted html string containing the ingredients
		*/
	override def getAllIngredients(): String = ???

	/** Gets a recipe by its name from the database. */
	override def getRecipeByName(name: String, edit: Boolean): String = ???

	/** Inserts a recipe in the database.
		*
		* @return
		*/
	def selectraw(ings: util.List[String]): String = ???

	/** Inserts a recipe in the database.
		*
		* @return
		*/
	override def selectraw(ings: List[String]): String = ???

	override def updateUser(userr: AbstractUser): Boolean = ???

	override def getVersions: String = ???
}