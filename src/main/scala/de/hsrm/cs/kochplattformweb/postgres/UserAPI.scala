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

import scala.BigInt;
trait UserAPI {
	/**Deletes a user from the database.
	 * @param userid the userid of the user to delete
	 * @return a success indicator
	 */
	def deleteUser(userid:BigInt):Boolean
	
	def forgotpassword(username:String):Boolean
	/**Gets all users from the database.
	 * 
	 * @return a formatted html string containing the users
	 */
	def getAllUsers():String
	/**Method for logging in a user.
	 * @param username the username
	 * @param password the password
	 * @return an Array[Integer] containing the users status and its id
	 */
	def login(username:String, password:String):Array[BigInt]
	def logout():Boolean
	/**Adds a user to the database.
	 * @throws NoSuchAlgorithmException
	 * @param username the username to add
	 * @param the password in plaintext
	 * @return an Array[Integer] containing the users status and its id
	 */
	def register(username:String, password:String):Array[BigInt]
	
	/**Sets the status of the given user to Admin or MainUser.
	 * 
	 * @param userid the userid of the givn user
	 * @param status the status indicator
	 * @return a success indicator
	 */
	def setAdminState(userid:BigInt,status:String):Boolean
	/**Sets the password of a user if he chooses the forgotten password option.*/
	def setPassword(username:String,oldpassword:String,password:String):Boolean
	/**Updates the password of a user.
	 * 
	 * @param password the new password to set
	 * @param userid the userid
	 * @return a success indicator
	 */
	def updatePassword(password:String,userid:BigInt):Boolean
	/**Updates a user in the database.
	 * @throws NoSuchAlgorithmException */
	def updateUser(username:String, password:String, userid:BigInt):Boolean}