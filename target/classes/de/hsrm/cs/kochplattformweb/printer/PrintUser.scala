package de.hsrm.cs.kochplattformweb.printer

import java.io.FileWriter
import java.io.IOException
import javax.xml.transform.Transformer

import de.hsrm.cs.kochplattformweb.client.MainUser
import de.hsrm.cs.kochplattformweb.utils.SWTLogger

/**
 * This class sets parameter to print out a MainUser.
 * @author aahri001
 * @version
 * @see
 * @since
 */
class PrintUser(user:MainUser) extends Parameterable {

	@Override
	override def setParameters(transformer:Transformer, out:FileWriter) {
		transformer.setParameter("name", this.user.name)
		transformer.setParameter("password", this.user.password)
		transformer.setParameter("id", Printer.formatBigInteger(this.user.id))
		try {
			out.append("</user>")
			out.close()
		} catch{
			case e:IOException => SWTLogger.writeerror(e.getMessage)
		}
	}
}
