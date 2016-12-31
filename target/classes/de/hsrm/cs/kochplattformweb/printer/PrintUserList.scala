package de.hsrm.cs.kochplattformweb.printer

import de.hsrm.cs.kochplattformweb.client.AbstractUser
import de.hsrm.cs.kochplattformweb.utils.SWTLogger
import java.io.FileWriter
import java.io.IOException
import java.util
import javax.xml.transform.Transformer

/**
 * This class implements Parameterable to set parameter if you print a userlist.
 * @author aahri001
 * @version
 * @see
 * @since
 */
class PrintUserList(userlist:util.LinkedList[AbstractUser]) extends Parameterable {

	/**
	 * Retruns a String which position has an user.
	 * @param user User which will his position returned.
	 * @return UserString my toString() for this class.
	 * @see
	 * @since
	 */
	def userToString(user:AbstractUser):String= {
		if(user.isAdmin) return "Admin"
		"MainUser"
	}
	@Override
	def setParameters(transformer: Transformer, out:FileWriter) {
		val res:StringBuffer = new StringBuffer(1024)
		res.append("<list>")
		val iter:java.util.Iterator[AbstractUser]=this.userlist.iterator()
		while (iter.hasNext) {
			var i:AbstractUser=iter.next()
			res.append("<item><name>")
			res.append(i.name)
			res.append("</name><position>")
			res.append(this.userToString(i))
			res.append("</position><password>")
			res.append(i.password)
			res.append("</password><id>")
			res.append(Printer.formatBigInteger(i.id))
			res.append("</id></item>")
		}
		res.append("</list>")
		try {
			out.append(res)
			out.append("</userlist>")
			out.close()
		} catch {
			case e:IOException =>SWTLogger.writeerror(e.getMessage)
		}
	}
}
