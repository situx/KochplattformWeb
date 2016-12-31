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

import scala.util.Properties
import java.sql.{Connection, DriverManager, ResultSet,Statement,SQLException}
import java.math.BigInteger
import java.security.{MessageDigest => JMessageDigest}


class UserManagement(state:Statement) extends Object with UserAPI {
	
	private def md5(password:String)={
		var md5password = new StringBuffer()
		val md5 = JMessageDigest.getInstance("MD5")
		md5.reset;
		md5.update(password.getBytes())
		md5.digest.foreach(arg =>md5password.append(Integer.toHexString(0xFF & arg)))
		md5password.toString()
	}
	
	override def deleteUser(id:BigInt)=this.state.executeUpdate("DELETE FROM \"user\" WHERE id='"+id+"'")>0
	
	override def forgotpassword(username:String)={
		val result=state.executeQuery("SELECT COUNT(name) FROM \"user\" WHERE name='"+username+"'")
		result.next
		if("0"==result.getString(1))
			false
		else{
			val pass=this.getHashByUser(username)
			println(pass.substring(0,pass.length()/2)+md5(username)+pass.substring(pass.length()/2))
			new SendMail("fishermensfriend@fish.fish",username,"<html>Bitte klicken! <a href=\"forgottenpassword.jsp?&hash="+pass.substring(0,pass.length()/2)+md5(username)+pass.substring(pass.length()/2))
			true
		}
	}
	
	override def getAllUsers()={
		val result=state.executeQuery("SELECT * FROM \"user\" ORDER BY name")
		val buffer=new StringBuffer()
		while(result.next){
			buffer.append("<tr><td id=\"result\">"+result.getString(2)+"</td><td>")
			buffer.append("""<form name="userform" type="post" action="usermanagement2.jsp"><input type="password" name="password"/><button name="passbutton" value=""""+result.getString(1)+"\">&Auml;ndern</button></form></td><td id=\"result\">")
			buffer.append(result.getString(1)+"</td><td id=\"result\">"+result.getString(4)+"</td><td><form name=\"userform\" type=\"post\" action=\"usermanagement2.jsp\"><button name=\"adminstatus\" value=\""+result.getString(1)+" "+result.getString(4)+"\">Adminstatus &auml;ndern</button></form></td><td><form name=\"userform\" type=\"post\" action=\"usermanagement2.jsp\"><button name=\"delete\" value=\""+result.getString(1)+"\">L&ouml;schen</button></form></td></tr>")
		}
		buffer.toString
	}
	
	private def getHashByUser(username:String)={
			val result=state.executeQuery("SELECT password FROM \"user\" WHERE name='"+username+"'")
			result.next()
			result.getString(1)
	}
	
	override def login(username:String, password:String)={
		val result = state.executeQuery("SELECT * FROM \"user\" WHERE name='"+username+"'")
		var array=Array.fill(2)(new BigInt(BigInteger.ZERO))
		result.next
		if(md5(password).replace(" ", "").equals(result.getString(3).replace(" ", ""))){
			if("t".equals(result.getString(4)))
				array(0)=2
			else
				array(0)=1
			array(1)=result.getString(1).toInt
		}
		array
	}
	
		
	override def logout()=true
	
	override def setAdminState(userid:BigInt, status:String)={
		println("UPDATE \"user\" SET admin='"+status+"' WHERE id='"+userid+"'");
		state.executeUpdate("UPDATE \"user\" SET admin='"+status+"' WHERE id='"+userid+"'")>0
	}
	
	override def setPassword(username:String,oldpassword:String,password:String)={
		val result=state.executeQuery("SELECT id,name FROM \"user\" WHERE password='"+oldpassword+"'")
		result.next
		print ("Username:"+username+" : "+result.getString(2)+"\nPassword: "+password+" ID: "+result.getString(1))
		result.getString(1)
		if(username==result.getString(2))
			this.updatePassword(password, BigInt(result.getString(1)))
		false
	}
		
	override def register(username:String, password:String)={
		val array = Array.fill(2)(new BigInt(BigInteger.ZERO))
		if (username.length()<3){
			throw new SQLException("Der Benutzername muss mindestens 3 Zeichen lang sein.")
		}
		if (password.length()<5){
			throw new SQLException("Das Passwort muss mindestens 5 Zeichen lang sein.")
		}
		if(state.executeUpdate("INSERT INTO \"user\" (name,password) VALUES ('"+username+"','"+md5(password)+"')")>0){
			val result=state.executeQuery("SELECT id FROM \"user\" WHERE name='"+username+"'")
			result.next
			array(0)=1
			array(1)=result.getString(1).toInt
		}
		array
	}
	
	override def updateUser(username:String, password:String, userid:BigInt)=state.executeUpdate("UPDATE \"user\" SET name='"+username+"',password='"+md5(password)+"' WHERE id='"+userid+"'")>0
	
	override def updatePassword(password:String, userid:BigInt)=state.executeUpdate("UPDATE \"user\" SET password='"+md5(password)+"' WHERE id='"+userid+"'")>0
}