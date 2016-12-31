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

import de.hsrm.cs.kochplattformweb.safety.{Cryptable, CryptorAPI}

import de.hsrm.cs.kochplattformweb.messages.Message

class AbstractUser(var name:String,var password:String,var id:BigInt,var isAdmin:Boolean)extends Cryptable with Message{
	val isGuest=false
	val isStandardUser=false
	override def decryptme(cry:CryptorAPI){
		if(this.keyValue>=0){
			this.name = cry.decrypt(this.keyValue, this.name)
			this.password = cry.decrypt(this.keyValue, this.password)
			this.keyValue = -1
		}
	}
	override def encryptme(cry:CryptorAPI){
		if(this.keyValue<0){
			this.keyValue = cry.getKey()
			this.name = cry.encrypt(this.keyValue, this.name)
			this.password = cry.encrypt(this.keyValue, this.password)
		}
	}

	def toXML ={
		<user author_id={id.toString} author_name={name.toString} password={password.toString} admin={isAdmin.toString} isGuest={false.toString} isStandardUser={false.toString}/>
	}
}
case class MainUser(name0:String, password0:String, id0:BigInt, isAdmin0:Boolean=false, override val  isGuest:Boolean=false, override val  isStandardUser:Boolean=true) extends AbstractUser(name0,password0,id0,isAdmin0){
	def this(name0:String,password0:String,id0:BigInt) = this(name0,password0,id0,false,false,true)
  def this(admin:MainUser) = this(admin.name,admin.password,admin.id,admin.isAdmin,admin.isGuest,admin.isStandardUser)
  isAdmin=isAdmin0
  id=id0
  name=name0
  password=password0
  override def toXML ={
      <user author_id={id.toString} author_name={name.toString} password={password.toString} admin={isAdmin.toString} isGuest={isGuest.toString} isStandardUser={isStandardUser.toString}/>
  }
}
case class Admin(name0:String, password0:String, id0:BigInt, isAdmin0:Boolean=true, override val  isGuest:Boolean=false, override val isStandardUser:Boolean=false)extends AbstractUser(name0,password0,id0,isAdmin0){
	def this(name:String,password:String,id:BigInt) = this(name,password,id,false,false,true)
	def this(admin:Admin) = this(admin.name,admin.password,admin.id,admin.isAdmin,admin.isGuest,admin.isStandardUser)
  isAdmin=isAdmin0
  id=id0
  name=name0
  password=password0
  override def toXML ={
      <user author_id={id.toString} author_name={name.toString} password={password.toString} admin={isAdmin.toString} isGuest={isGuest.toString} isStandardUser={isStandardUser.toString}/>
  }
}
case class Guest(name0:String, password0:String, isAdmin0:Boolean=false, override val isGuest:Boolean=true, override val isStandardUser:Boolean=false) extends AbstractUser(name0,password0,BigInt(0),isAdmin0) {
  isAdmin=isAdmin0
	name=name0
	password=password0
	//def this(admin: Guest) = this(admin.name, admin.password, admin.id, admin.isAdmin, admin.isGuest, admin.isStandardUser)
  override def toXML ={
      <user author_id={id.toString} author_name={name.toString} password={password.toString} admin={isAdmin.toString} isGuest={isGuest.toString} isStandardUser={isStandardUser.toString}/>
  }
}