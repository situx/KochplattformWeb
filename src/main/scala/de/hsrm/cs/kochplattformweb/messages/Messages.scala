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


package de.hsrm.cs.kochplattformweb.messages

import de.hsrm.cs.kochplattformweb.safety.{Cryptable, CryptorAPI}
import de.hsrm.cs.kochplattformweb.client.{AbstractUser, Client}
import de.hsrm.cs.kochplattformweb.db.{Ingredient, Rating, Recipe, RecipeEntry}
import de.hsrm.cs.kochplattformweb.server.UserConnectionAPI

case class ChangeData(var user:AbstractUser,var newname:String,var password:String)extends Message with Cryptable{
	override def decryptme(cry:CryptorAPI){
		this.user.decryptme(cry)
		if(this.keyValue>(-1)){
			this.newname = cry.decrypt(this.keyValue, this.newname)
			this.password = cry.decrypt(this.keyValue, this.password)
			this.keyValue = -1
		}
	}

	override def encryptme(cry:CryptorAPI){
		this.user.encryptme(cry)
		if(this.keyValue<0){
			this.keyValue = cry.getKey()
			this.newname = cry.encrypt(this.keyValue, this.newname)
			this.password = cry.encrypt(this.keyValue, this.password)
		}
	}
}
case class ChangeRecipeMessage(recipe:Recipe)extends Message{
  override def getMessage: String = "Will be changed :" + this.recipe
}
case class ConfirmReport(message0:String)extends Message{
  message=message0
}
case class DeleteRecipeMessage(recipe:Recipe) extends Message{
	override def getMessage="Number or Recipe to delete:"+this.recipe.name
	def getRecipe=this.recipe
}
case class DeleteUserMessage(user:AbstractUser)extends Message with Cryptable{
	override def decryptme(cry:CryptorAPI)=this.user.decryptme(cry)
	override def encryptme(cry:CryptorAPI)=this.user.encryptme(cry)
}
case class ErrorReport(message0:String,code:Integer)extends Message{
	/** Information about Databaseerror:. */
	private
	var messageDB: String = null

  message=message0

	override def getMessage: String = this.message

  def clientHandleMessage(clientcon: Client): Message = clientcon.handleMessage(this)

  def serverHandleMessage(usercon: UserConnectionAPI): Message = usercon.handlemessage(this)
}
case class FinishMessage(message0:String) extends Message{
  message=message0
}
case class GetAllIngredients(language:String,message0:String="You will get all Ingredients")extends Message{
  message=message0
}
case class GetAllUsersMessage(message0:String="AdminsareallowedtousethisMessage")extends Message{
  message=message0
}
case class GetRecipeMessage(recipeid:BigInt,message0:String="Recipenumber ")extends Message{
  message=message0
}
case class GetRecipeByNameMessage(message0:String)extends Message{
	message=message0
}
case class IngredientMessage(var requestType:Boolean,ingredients:List[Ingredient]=List[Ingredient]()) extends Message{
  def addIngredient(ingredient: Ingredient) = ingredients:+ingredient

  def this(requestType:Boolean)=this(requestType,List[Ingredient]())
}
case class IngredientSetMessage(ingredientSet:Set[Ingredient])extends Message
case class Loginmessage(var name:String,var passw:String)extends Message with Cryptable{
	message=this.name
	override def decryptme(cry:CryptorAPI){
		if(this.keyValue>(-1)){
			this.name = cry.decrypt(this.keyValue, this.name)
			this.passw = cry.decrypt(this.keyValue, this.passw)
			this.keyValue = -1
		}
	}

	override def encryptme(cry:CryptorAPI){
		if(this.keyValue<0){
			this.keyValue = cry.getKey()
			this.name = cry.encrypt(this.keyValue, this.name)
			this.passw = cry.encrypt(this.keyValue, this.passw)
		}
	}
}
case class LogoutMessage() extends Message{
	message="Logoutmessage has no special Message"
}
case class RecipeListMessage(recipelist:List[RecipeEntry])extends Message
case class Registration(message0:String,var passw:String)extends Message with Cryptable{
  message=message0
	override def decryptme(cry:CryptorAPI){
		if(keyValue>(-1)){
			this.message = cry.decrypt(this.keyValue, this.message)
			this.passw = cry.decrypt(this.keyValue, this.passw)
			this.keyValue = -1
		}
	}

	override def encryptme(cry:CryptorAPI){
		if(this.keyValue<0){
			this.keyValue = cry.getKey()
			this.message = cry.encrypt(this.keyValue, this.message)
			this.passw = cry.encrypt(this.keyValue, this.passw)
		}
	}
}
case class SetRatingMessage(recipeid:BigInt,reciperating:Rating)extends Message
case class ShutdownMessage(message0:String,timetoshutdown:Int) extends Message{
  message=message0
}
case class UpdateRecipesMessage(updaterecipes: List[BigInt], insertrecipes: List[BigInt]) extends Message{
	def addupdate(recipeid: BigInt) = ???

	/** List in XML format within recipes to insert. */
  private var recipestoinsert: String = null
  /** List in XML format within recipes to update. */
  private var recipestoupdate: String = null
}
case class UserListMessage(userlist:List[AbstractUser],key:Int=(-1))extends Message with Cryptable{
	override def decryptme(cry:CryptorAPI){
		if(this.keyValue>(-1)){
			this.userlist.foreach{usr =>usr.decryptme(cry)}
			this.keyValue = -1
		}
	}
	override def encryptme(cry:CryptorAPI){
		if(this.keyValue<0){
			this.keyValue = cry.getKey()
			this.userlist.foreach{usr =>usr.encryptme(cry)}
		}
	}
}
case class XMLIngredientListMessage(message0:String) extends Message{
  message=message0
	var ingredientsinXML=message0
}
case class XMLRatingMessage(message0:String) extends Message{
  message=message0
}
case class XMLRecipeMessage(message0:String,islist:Boolean,backup:Boolean=false,var number:Int=(-1),userecipe:Boolean=true)extends Message{
  message=message0
	number=if(number<(-1)) -1 else if(number>2) 2 else number
}
case class XMLTimestampMessage(message0:String,unknownto:Boolean)extends Message{
  message=message0
}