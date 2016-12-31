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



package de.hsrm.cs.kochplattformweb.messages

import de.hsrm.cs.kochplattformweb.client.{Client, ClientAPI}
import de.hsrm.cs.kochplattformweb.server.{UserConnection, UserConnectionAPI}
import java.io.Serializable

/**Class Message for all types of Messages betweed Client/Server.
 * @author Lehel Eses
 * @version $Date$
 */
trait Message extends Serializable {
	var message:String=""
	/**Gets the string of the message.
	 * @return the string of the message*/
	def getMessage()=message

	/** Handles the Message on the server.
	 * This Method calls the Method in the given UserConnection which
	 * knows how to handle this kind of Message-type
	 * @param usercon References to the sending User-Connection
	 * @return An Object implementing Interface Message will be returned
	 * @see
	 * @since	11.06.2010
	 */
	def serverHandleMessage(usercon:UserConnection)={
		usercon.handleMessage(this)
	}

	/** Handles the Message on the client.
	 * @param clientcon as Client
	 * @return Object implementing Message
	 */
	def clientHandleMessage(clientcon:ClientAPI):Message={
		clientcon.handleMessage(this)
	}
}