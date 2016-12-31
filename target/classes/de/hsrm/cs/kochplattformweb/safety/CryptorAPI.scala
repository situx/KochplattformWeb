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

package de.hsrm.cs.kochplattformweb.safety

trait CryptorAPI {

	/**Method to decrypt a String.
	 * @param start Integer representing the position in String to start
	 * @param crypt a crypted String
	 * @return encrypted String
	 * @since 21.07.2010
	 */
	def decrypt(start:Int, crypt:String):String

	/** Method to decrypt a String.
	 * @param start Integer representing prosition in key-String to start
	 * @param plaintext a String
	 * @return decrypted String
	 * @see
	 * @since 21.07.2010
	 */
	def encrypt(start:Int, plaintext:String):String

	/**Generates a random IntegerValue.
	 * @return Random Number between 0 and 51
	 * @see
	 * @since 20.07.2010
	 */
	def getKey():Int
}