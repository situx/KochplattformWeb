package de.hsrm.cs.kochplattformweb.client.gui

import de.hsrm.cs.kochplattformweb.client.Client
import de.hsrm.cs.kochplattformweb.messages.IngredientMessage
import de.hsrm.cs.kochplattformweb.messages.Message
import de.hsrm.cs.kochplattformweb.messages.RecipeListMessage
import java.io.IOException

/**
 * Interface zum weiterreichen der Änderungen.
 * @author john
 * @version
 * @see
 * @since
 */
trait ControllerInterface  {
	/**
	 * This Method is for binding the Client to the Server.
	 * @param server to connect to
	 * @param localhost to connect to
	 * @param port Port to connect to
	 * @throws IOException throws an IOExeption
	 * @return Client
	 */
	def bind(server:String,localhost:String, port:Int):Client
	/**
	 * This Method is for Logging in.
	 * @param name to login with
	 * @param password to login with
	 * @param servercon to connect to
	 * @return Message A Message is returned.
	 */
	def login(name:String,password:String, servercon:Client):Message
	/**
	 * This Method is for registering a new User.
	 * @param name to register with
	 * @param password to register with
	 */
	def register(name:String, password:String)
	/**
	 * This Method is for searching Recipes by an excact List of Ingredients.
	 * @param ingm a Message containing the Ingredients for searching
	 * @return RecipeListMessage
	 */
	def search(ingm:IngredientMessage):RecipeListMessage
	/**
	 * This Method is for logging out an User.
	 */
	def logout()
}
