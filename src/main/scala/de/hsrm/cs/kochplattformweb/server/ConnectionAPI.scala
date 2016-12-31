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

package de.hsrm.cs.kochplattformweb.server

import de.hsrm.cs.kochplattformweb.messages.FinishMessage
import de.hsrm.cs.kochplattformweb.messages.Message
import de.hsrm.cs.kochplattformweb.messages.ShutdownMessage;

/** API for Connection.
  * @author Daniel
  * @version $Date$
  * @since 19.07.2010
  */
trait ConnectionAPI {

  /** Method to close Socket.
    * @since 19.07.2010
    */
  def closeconnection();

  /** Closes Connection, removes all Object.
    * @param message Object from FinishMessage
    * @return Confirmreport
    * @since 3.08.2010
    */
  def handleFinish(message:FinishMessage);

  /** Shuts down the server.
    * Checks if this Connection belongs to an Administrator.
    * If true, boolean working flag of server will be set to false.
    * Else an ErrorReport will be send.
    * @param message Object of ShutdownMessage
    * @return An Object implementing Interface Message
    * @since
    */
  def handleshutdown(message:ShutdownMessage);

  /** Method to handle incomming Messages from Socket.
    * @since 19.07.2010
    */
  def process();

  /** Method to send Object implementing Interface Message to Client.
    * @since 19.07.2010
    */
  def sendmessage();


  /** Getter for local field UserConnection.
    * @return instance of UserConnection
    * @since 3.08.2010
    */
  def getUcon();

  /** Setter for Connection-Flag.
    * @param processing Flag if this connection will be processed
    * @since 19.07.2010
    */
  def setprocessing(processing:Boolean);


}

