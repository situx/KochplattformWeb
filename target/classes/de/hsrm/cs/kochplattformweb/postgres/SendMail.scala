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

package de.hsrm.cs.kochplattformweb.postgres

import javax.mail.{Authenticator,PasswordAuthentication}
import javax.mail.{Session => JSession,Transport => JTransport,Message => JMessage,
	Authenticator,PasswordAuthentication => JPasswordAuthentication}
import javax.mail.internet.{InternetAddress => JInternetAddress, MimeMessage => JMimeMessage}
import java.util.{Properties=> JProperties}
class SendMail(fromAddress:String,toAddress:String,text:String) {
	class MailAuthenticator(username:String,password:String) extends Authenticator{
		override def getPasswordAuthentication=new PasswordAuthentication(username, password)
	}
	
	def send(){
			val props = new JProperties();
			//SMTP adresse
			props.put("mail.smtp.host", "yenrai.de");
			//Verwendung der Authentifizierung
			props.put("mail.smtp.auth", "true");
			val message = new JMimeMessage(JSession.getDefaultInstance(props, new MailAuthenticator("Benutzername", "Passwort")))
			//Von welcher EmailAdresse
			message.setFrom(new JInternetAddress(fromAddress));
			//Zu welcher Adresse
			message.addRecipient(JMessage.RecipientType.TO, new JInternetAddress(toAddress));
			message.setSubject("Passwort wiederherstellen.");
			message.setText(text);
			JTransport.send(message);
	}

}