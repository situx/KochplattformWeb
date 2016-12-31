package de.hsrm.cs.kochplattformweb.printer

import java.io.FileWriter
import java.io.IOException
import javax.xml.transform.Transformer

import de.hsrm.cs.kochplattformweb.utils.SWTLogger

/**
 * This class print out a Messagestring.
 * @author aahri001
 * @version
 * @see
 * @since
 */
class PrintMessage(message:String) extends Parameterable {

	@Override
	def setParameters(transformer:Transformer, out:FileWriter) {
		transformer.setParameter("text", this.message)
		try {
			out.append("</message>")
			out.close();
		} catch {
			case e:IOException =>SWTLogger.writeerror(e.getMessage)
		}
	}
}
