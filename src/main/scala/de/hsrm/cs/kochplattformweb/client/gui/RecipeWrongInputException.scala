package de.hsrm.cs.kochplattformweb.client.gui

/**
 * WrongInputException for Message.
 * @author aahri001
 * @version
 * @see
 * @since
 */
class RecipeWrongInputException(var message:String) extends RuntimeException {

	/**
	 * Getter for the Exceptions message.
	 * @return Message.
	 */
	override def getMessage =message

	/**
	 * Setter for the Exceptions message.
	 * @param message the exception message to set
	 */
	def setMessage(message:String) {
		this.message=message
	}
}
