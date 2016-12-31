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


package de.hsrm.cs.kochplattformweb.client

import de.hsrm.cs.kochplattformweb.messages._
import java.net.Socket

import de.hsrm.cs.kochplattformweb.db.RecipeEntry
/**API for the client.
 * @author Timo Homburg/Lehel Eses **/
trait ClientAPI {
  def handleMessage(message: Message): Message = ???

  protected val MAXRECON: Integer = 5
	/**Client will start the connection to the server.
	*@return Confirmreport (Message) if the connection with the server was successful
	*or Errorrerport (Message) if the connection failed. */
	def connect:Message=null

	/**Gets the address of the clients' localhost.
	 * @return the client address as String**/
	var getLocalhostadress:String=null

	/**Gets the port.
	 * @return the port id**/
	var getPort:Int=0

	/**Gets the address of the server.
	 * @return the server address as String
	 */
	var getServeradress:String=null

	/**Gets the client socket.
	 * @return the socket
	 */
	var getSock:Socket=null

	/**Gets the type of the user which is currently logged in.
	 * @return an object of a class extending AbstractUser.
	 **/
	var getUsertype:AbstractUser=null

	/** Sends a message to the server to get all recipes from the database.
	 * @return Message (Confirm or Error)
	 */
	def importDB:Message=null
	
	/**Return connectstatus.
	 * @return boolean connected true or false*/
	def isStatus:Boolean=false

	/** Handles AbstractUser Message.
	 * Return AbstractUser and Decrypt it
	 * @param message Object from AbstractUser
	 * @return AbstractUser
	 * @since 27.07.2010
	 */
	def handleMessage(message:AbstractUser):Message

	/** Handles ChangeData Message.
	 * This Message should never be send from the Server to the Client.
	 * @param message Object from ChangeData
	 * @return Retruns an ErrorReport
	 * @since 27.07.2010
	 */
	def handleMessage(message:ChangeData):Message

	/** Handles ChangeRecipeMessage Message.
	 * This Message should never be send from the Server to the Client.
	 * @param message Object from ChangeRecipeMessage
	 * @return Retruns an ErrorReport
	 * @since 27.07.2010
	 */
	def handleMessage(message:ChangeRecipeMessage):Message

	/** Handles Confirmreport Message.
	 * @param message Object from Confirmreport
	 * @return Returns an Confirmreport
	 */
	def handleMessage(message:ConfirmReport):Message

	/** Handles DeleteRecipeMessage Message.
	 * This Message should never be send from the Server to the Client.
	 * @param message Object from DeleteRecipeMessage
	 * @return Retruns an ErrorReport
	 * @since 27.07.2010
	 */
	def handleMessage(message:DeleteRecipeMessage):Message

	/** Handles DeleteUserMessage Message.
	 * This Message should never be send from the Server to the Client.
	 * @param message Object from DeleteUserMessage
	 * @return Retruns an ErrorReport
	 * @since 27.07.2010
	 */
	def handleMessage(message:DeleteUserMessage):Message

	/** Handles ErrorReport Message.
	 * Will be send from Server to Client if an error occur
	 * @param message Object from ErrorReport
	 * @return Retruns an ErrorReport
	 * @since 27.07.2010
	 */
	def handleMessage(message:ErrorReport):Message

	/** Handles FinishMessage Message.
	 * This Message should never be send from the Server to the Client.
	 * @param message Object from FinishMessage
	 * @return Retruns an ErrorReport
	 * @since 27.07.2010
	 */
	def handleMessage(message:FinishMessage):Message

	/** Handles GetAllIngredients Message.
	 * This Message should never be send from the Server to the Client.
	 * @param message Object from GetAllIngredients
	 * @return Retruns an ErrorReport
	 * @since 27.07.2010
	 */
	def handleMessage(message:GetAllIngredients):Message

	/** Handles GetAllUsersMessage Message.
	 * This Message should never be send from the Server to the Client.
	 * @param message Object from GetAllUsersMessage
	 * @return Retruns an ErrorReport
	 * @since 27.07.2010
	 */
	def handleMessage(message:GetAllUsersMessage):Message

	/** Handles GetRecipeByNameMessage Message.
	 * This Message should never be send from the Server to the Client.
	 * @param message Object from GetRecipeByNameMessage
	 * @return Retruns an ErrorReport
	 * @since 27.07.2010
	 */
	def handleMessage(message:GetRecipeByNameMessage):Message

	/** Handles GetRecipeMessage Message.
	 * This Message should never be send from the Server to the Client.
	 * @param message Object from GetRecipeMessage
	 * @return Retruns an ErrorReport
	 * @since 27.07.2010
	 */
	def handleMessage(message:GetRecipeMessage):Message

	/** Handles IngredientMessage Message.
	 * This Message should never be send from the Server to the Client.
	 * @param message Object from IngredientMessage
	 * @return Retruns an ErrorReport
	 * @since 27.07.2010
	 */
	def handleMessage(message:IngredientMessage):Message

	/** Handles IngredientSetMessage.
	 * This Message should never be send from the Server to the Client
	 * @param message Object from IngredientSetMessage
	 * @return ErrorReport
	 * @since 28.07.2010
	 */
	def handleMessage(message:IngredientSetMessage):Message

	/** Handles Loginmessage Message.
	 * This Message should never be send from the Server to the Client.
	 * @param message Object from Loginmessage
	 * @return Retruns an ErrorReport
	 * @since 27.07.2010
	 */
	def handleMessage(message:Loginmessage):Message

	/** Handles LogoutMessage Message.
	 * This Message should never be send from the Server to the Client.
	 * @param message Object from LogoutMessage
	 * @return Retruns an ErrorReport
	 * @since 27.07.2010
	 */
	def handleMessage(message:LogoutMessage):Message

	/** Handles RecipeEntry Message.
	 * This Message should never be send from the Server to the Client
	 * @param message Object from AbstractUser
	 * @return RecipeEntry
	 * @since 27.07.2010
	 */
	def handleMessage(message:RecipeEntry):Message

	/** Handles RecipeListMessage Message.
	 * This Message should never be send from the Server to the Client.
	 * @param message Object from RecipeListMessage
	 * @return Retruns an ErrorReport
	 * @since 27.07.2010
	 */
	def handleMessage(message:RecipeListMessage):Message

	/** Handles Registration Message.
	 * This Message should never be send from the Server to the Client.
	 * @param message Object from Registration
	 * @return Retruns an ErrorReport
	 * @since 27.07.2010
	 */
	def handleMessage(message:Registration):Message

	/** Handles SetRatingMessage Message.
	 * This Message should never be send from the Server to the Client.
	 * @param message Object from SetRatingMessage
	 * @return Retruns an ErrorReport
	 * @since 27.07.2010
	 */
	def handleMessage(message:SetRatingMessage):Message

	/** Handles ShutdownMessage Message.
	 * This Message should never be send from the Server to the Client.
	 * @param message Object from ShutdownMessage
	 * @return Retruns an ErrorReport
	 * @since 27.07.2010
	 */
	def handleMessage(message:ShutdownMessage):Message

	/** Handles UpdateRecipesMessage.
	 * Parses recipes an insert / updates it in the local Database
	 * @param message Object from UpdateRecipesMessage
	 * @return ErrorReport or Confirmreport
	 * @since 06.08.2010
	 */
	def handleMessage(message:UpdateRecipesMessage):Message

	/** Handles UserListMessage Message.
	 * The message will be decrypted and and then return as message
	 * @param message Object from UserListMessage
	 * @return Retruns a UserListMessage
	 * @since 27.07.2010
	 */
	def handleMessage(message:UserListMessage):Message

	/** Handles XMLIngredientListMessage Message.
	 * The XML String will be parsed and send as message
	 * @param message Object from UserListMessage
	 * @return IngredientSetMessage
	 * @since 27.07.2010
	 */
	def handleMessage(message:XMLIngredientListMessage):Message

	/** Handles XMLRatingMessage Message.
	 * The XML String will be parsed and send as message
	 * @param message Object from XMLRatingMessage
	 * @return XMLRatingMessage
	 * @since 27.07.2010
	 */
	def handleMessage(message:XMLRatingMessage):Message

	/** Handles XMLRecipeMessage Message.
	 * The XML String will be parsed and send as message
	 * @param message Object from XMLRecipeMessage
	 * @return IngredientSetMessage
	 * @since 27.07.2010
	 */
	def handleMessage(message:XMLRecipeMessage):Message

	/** Handles XMLTimestampMessage.
	 *  Checks versions of Recipes and sends a message to server to get newer
	 *  Versions and if set unknown recipes.
	 * @param message Object from XMLTimestampMessage
	 * @return a Message
	 */
	def handleMessage(message:XMLTimestampMessage):Message

	/**Method to synchronise own Data to Server Data.
	 * Send Message to server with flag to update own recipes to newest Version
	 * and optional gets unknown recipes
	 * @param unknowntoo Boolean if unknown recipes will get too
	 * @return ErrorReport or Confirmreport
	 */
	def synchronise(unknowntoo:Boolean):Message

	/** Get a Recipe back by Name.
	 * @param msg String name of Recipe
	 * @return Recipe*/
	def getRecipeByName(msg:String):Message

	/** Make Server input a backup.
	 * @param number 0,1,2 the backups (0 is newest)
	 * @param userecipe Boolean to decide if user oder recipe file will be used
	 * @return Confirm or ErrorReport
	 */
	def serverinputbackup(number:Int, userecipe:Boolean):Message

}
