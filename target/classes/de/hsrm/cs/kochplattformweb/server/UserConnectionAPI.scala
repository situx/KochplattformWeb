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

package de.hsrm.cs.kochplattformweb.server;

import de.hsrm.cs.kochplattformweb.client.AbstractUser
import de.hsrm.cs.kochplattformweb.db.{Recipe,RecipeEntry}
import de.hsrm.cs.kochplattformweb.messages._

/**API for the user connection.
 * @author Timo Homburg
 */
trait UserConnectionAPI {

	/** Checks if an user is already logged in.
	 * @param username Name of the user
	 * @return true if user is logged in
	 * @since 3.08.2010
	 */
	def checkforuser(username:String):Boolean

	/** Handles AbstractUser.
	 * AbstactUsers an users extending it should never be send to the Server
	 * @param message Object from AbstractUser or an Object extending it
	 * @return an ErrorReport
	 * @since 28.07.2010
	 */
	def handlemessage(message:AbstractUser):Message

	/**This Method will change user data in the database.
	 * Checks if actual userdata match to send one. if they are equal
	 * data in Database will be changed an a Confirmmessage will be send,
	 * else an Errormessage
	 * @param message Object of ChangeData
	 * @return An Object implementing Interface Message
	 * @since 18.06.2010
	 */
	def handlemessage(message:ChangeData):Message

	/** Changes the Recipe in DB with ID equal to send one.
	 * Checks if the Recipe belongs to the user (check by Autorid)
	 * or if he is an Administrator. if true Method of BaseXConnection will be
	 * called, else an ErrorReport will be send.
	 * @param message Object of ChangeRecipeMessage within the Recipe to change
	 * @return An Object implementing Interface Message
	 * @since 28.06.2010
	 */
	def handlemessage(message:ChangeRecipeMessage):Message

	/**Sends an ErrorMessage back.
	 * Sends an ErrorReport to the Client, because a Confirmreport should
	 * never be send to the Server.
	 * @param message Object of ConfirmMessage
	 * @return An Object implementing Interface Message
	 * @since 17.06.2010
	 */
	def handlemessage(message:ConfirmReport):Message

	/** Deletes a Recipe with equal integernumber.
	 * Checks if user has permission to delete the recipe belonging to the
	 * recipeid. if no permission an ErrorReport will be send.
	 * @param message Object of DeleteRecipeMessage
	 * @return An Object implementing Interface Message
	 * @since 28.06.2010
	 */
	def handlemessage(message:DeleteRecipeMessage):Message

	/** Handles DeleteUserMessage.
	 * Admins can delte all Users, MainUsers only herself
	 * @param message Object from DeleteUserMessage within an User
	 * @return Confirmreport or ErrorReport
	 * @since 20.07.2010
	 */
	def handlemessage(message:DeleteUserMessage):Message

	/**Sends an ErrorMessage back.
	 * Sends an ErrorReport to the Client, because an ErrorReport should
	 * never be send to the Server.
	 * @param message Object of ErrorReport
	 * @return An Object implementing Interface Message
	 * @since 18.06.2010
	 */
	def handlemessage(message:ErrorReport):Message

	/** Close Socket and sets working-flag for Thread to false.
	 * @param message Object of FinishMessage
	 * @return An Object implementing Interface Message
	 * @since 16.06.2010
	 */
	def handlemessage(message:FinishMessage):Message

	/**Handles GetAllIngrediens.
	 * Asks Database about all used Ingrediens and sends them back to the Server.
	 * @param message Object from GetAllIngrediens
	 * @return an ErrorReport or a XMLIngredienslistmessage
	 * @since 25.07.2010
	 */
	def handlemessage(message:GetAllIngredients):Message

	/**Handles GetAllUsersMessage.
	 * Can only be used by Admins, gets all Users in Database
	 * @param message Object from GetAllUsersMessage
	 * @return Returns a UsersListMessage or an ErrorReport
	 * @since 20.07.2010
	 */
	def handlemessage(message:GetAllUsersMessage):Message

	/**Handles GetRecipeByNameMessage.
	 * This Method will call the Method selectbyName from Database
	 * with the String given by GetRecipeByNameMessage.
	 * The result from Database (String within Recipes in XML format)
	 * will be returned
	 * @param message Object from GetRecipeByNameMessage
	 * @return Returns XMPRecipeListMessage or an ErrorReport
	 * @since 21.07.2010
	 */
	def handlemessage(message:GetRecipeByNameMessage):Message

	/** Searches after an recipe by its ID.
	 * This Method will search in the Database after a recipe identified by its
	 * unique Identification-number written in den GetRecipeMessage
	 * @param message Object of GetRecipeMessage
	 * @return An Object implementing Interface Message
	 * @since 18.06.2010
	 */
	def handlemessage(message:GetRecipeMessage):Message

	/** Calls Method from Database which searches Recipes by here Ingredients.
	 * @param message Object of IngredientMessage
	 * @return An Object implementing Interface Message
	 * @since 17.06.2010
	 */
	def handlemessage(message:IngredientMessage):Message

	/** Handles IngredientSetMessage.
	 * This Messagetyp should never be send to the Server
	 * @param message Object from IngredientSetMessage
	 * @return an ErrorReport
	 * @since 20.07.2010
	 */
	def handlemessage(message:IngredientSetMessage):Message

	/** Search in DB after the user and check login Data.
	 * This Method calls Method from BaseXConnection to get Users Data
	 * and checks if they are matching. If they match user is logged and
	 * an AbstactUserObject is send to Client.
	 * Else an ErrorReport is send to Client
	 * @param message Object of LoginMessage
	 * @return An Object implementing Interface Message
	 * @since 16.06.2010
	 */
	def handlemessage(message:Loginmessage):Message

	/**This Method will logout user but Thread will move on.
	 * Cleans the field of the class which contains the logged in User and
	 * sets the authentification flag to false.
	 * Sends Confirmreport or ErrorMessage.
	 * @param message Object of LogoutMessage
	 * @return An Object implementing Interface Message
	 * @since 18.06.2010
	 */
	def handlemessage(message:LogoutMessage):Message

	/** Inserts the given recipe into the Database.
	 * This Method will insert the given recipe into the Database.
	 * The used database Method is insertrecipe
	 * @param message Object of Recipe
	 * @return An Object implementing Interface Message
	 * @since 20.06.2010
	 */
	def handlemessage(message:Recipe):Message

	/** Handles RecipeEntry.
	 * RecipeEntry should never be send to the Server.
	 * @param message Object from RecipeEntry
	 * @return an ErrorReport
	 * @since 29.07.2010
	 */
	def handlemessage(message:RecipeEntry):Message

	/**Handles RecipeListMessage.
	 * RecipeListMessage should never be send to the Server
	 * @param message Object from RecipeListMessage
	 * @return an Object implementing Interface Message
	 * @since 19.07.2010
	 */
	def handlemessage(message:RecipeListMessage):Message

	/** Registers unregistered Users.
	 * Checks if User is registered. If not he will be registered
	 * an his Data send to Client. Else an ErrorReport will be send.
	 * @param message Object of FinishMessage
	 * @return An Object implementing Interface Message
	 * @since 16.06.2010
	 */
	def handlemessage(message:Registration):Message

	/** Handles SetRatingMessage.
	 * Methods from BaseX-connection will be called to check if this user has
	 * already ratet for this recipe. if not rating will be stored.
	 * @param message Object from SetRatingMessage
	 * @return Confirmreport or ErrorReport
	 * @since 26.07.2010
	 */
	def handlemessage(message:SetRatingMessage):Message

	/** This Message can only be given to UserConnection in Client.
	 * This should never be happen.
	 * @param message Object of ShutdownMessage
	 * @return ErrorReport
	 * @since 17.06.2010
	 */
	def handlemessage(message:ShutdownMessage):Message

	/** This Method will get a List of recipes from Database by there IDs.
	 * @param message Object from UpdateRecipesMessage
	 * @return ErrorReport or XMLRecipeMessage
	 * @since 06.08.2010
	 */
	def handlemessage(message:UpdateRecipesMessage):Message

	/** Handles UserListMessage.
	 * This Messagetyp should never be send to the Server
	 * @param message Object from UserListMessage
	 * @return Returns an ErrorReport
	 * @since 20.07.2010
	 */
	def handlemessage(message:UserListMessage):Message

	/**Handles XMLIngrediensListMessage.
	 * This kind of Message should never be send to the Server
	 * @param message Object from XMLIngrediensListMessage
	 * @return an ErrorReport
	 * @since 25.07.2010
	 */
	def handlemessage(message:XMLIngredientListMessage):Message

	/** Handles XMLRatingMessage
	 * A XMLRatingMessage should never be send to the Server. It's only used to
	 * send a rating from the Server to the Client.
	 * @param message Object from XMLRatingMessage
	 * @return ErrorReport
	 * @since 26.07.2010
	 */
	def handlemessage(message:XMLRatingMessage):Message

	/** Handles XMLRecipeMessage on Server.
	 * XMLRecipeMessage should never be send to the Server
	 * @param message Object from XMLRecipeListMessage
	 * @return an Object implementing Interface Message
	 * @since 19.07.2010
	 */
	def handlemessage(message:XMLRecipeMessage):Message

	/**Gets Data about all Recipes / her Timestamps.
	 * @param message Object from XMLTimestampMessage
	 * @return ErrorReport or XMLRecipeMessage
	 */
	def handlemessage(message:XMLTimestampMessage):Message

	/** Method for Visitor-Pattern.
	 * @param message Object implementing Interface Message
	 * @return Object implementing Interface Message
	 * @since 3.08.2010
	 */
	def visitmessage(message:Message):Message

	/** returns the User-Information belonging to the Connection.
	 * @return Object from AbstractUser oder an other User extending AbstractUser
	 * @since 10.06.2010
	 */
	def getuser():AbstractUser

	/**Getter authentificated.
	 * @return if user is authentificated or not
	 * @since 11.06.2010
	 */
	def isauthentification():Boolean

	/** Setter for local field authentificated.
	 * @param auth boolean set to the local field
	 * @since 4.08.2010
	 */
	def setAuthentificated(auth:Boolean)

	/** sets the given AbstactUser to the field of class.
	 * @param user an Object extending AbstactUser
	 * @since 10.06.2010
	 */
	def setuser(user:AbstractUser)
}