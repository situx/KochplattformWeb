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


package de.hsrm.cs.kochplattformweb.safety

class Cryptor extends CryptorAPI{
	/**Constant representing the length of the used key.**/
	private val KEYLENGTH:Int = 52

	/**The highest int for the Key {@value}.*/
	private val MAXKEY:Int = 51

	/**Constant representing the highest Number of an char plus one.*/
	private val MAXNUMBER:Int = 256

	/**String zur verschlüsselung.*/
	private val key="EdKhQxGaAmNvRwFcLoVkZeBzHrfSiJyMWnTtObXqCuIgPjDlUpYs"
		
	override def decrypt(start:Int,crypt:String)={
		val plain = new StringBuffer()
		var counter = start
		var tmp=0
		for(i <- 0 to crypt.length){
			counter+=1
			counter = counter%KEYLENGTH
			if(i==0){
				plain.append(((
					crypt.charAt(i)-this.key.charAt(counter)
					)%MAXNUMBER).asInstanceOf[Char])
			}
			else{
				tmp = (crypt.charAt(i)-this.key.charAt(counter)-plain.charAt(i-1))%MAXNUMBER
				while(tmp<0){
					tmp = tmp+MAXNUMBER
				}
				plain.append(tmp.asInstanceOf[Char])
			}
		}
		plain.toString
	}


	def encrypt(start: Int, plaintext: String): String = {
		val crypt: StringBuffer = new StringBuffer
		var kcount: Int = start
		var i: Int = 0
		while (i < plaintext.length) {
			{
				kcount = kcount % KEYLENGTH
				if (i == 0) crypt.append(((plaintext.charAt(i) + this.key.charAt(kcount)) % MAXNUMBER).toChar)
				else crypt.append(((plaintext.charAt(i) + plaintext.charAt(i - 1) + this.key.charAt(kcount)) % MAXNUMBER).toChar)
			}
			{
				i += 1; i - 1
			}
			{
				kcount += 1; kcount - 1
			}
		}
		crypt.toString
	}

	def getKey(): Int = (Math.random * MAXKEY).round.toInt
}