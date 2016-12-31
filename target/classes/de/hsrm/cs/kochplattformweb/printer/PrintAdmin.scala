package de.hsrm.cs.kochplattformweb.printer

import java.io.FileWriter
import java.io.IOException
import javax.xml.transform.Transformer

import de.hsrm.cs.kochplattformweb.client.Admin
import de.hsrm.cs.kochplattformweb.utils.SWTLogger

/**
 * This class sets parameter to print out a admin.
 * @author aahri001
 * @version
 * @see
 * @since
 */
class PrintAdmin(admin:Admin) extends Parameterable {

	@Override
	override def setParameters(transformer: Transformer,out:FileWriter) {
		transformer.setParameter("name", this.admin.name)
		transformer.setParameter("password", this.admin.password)
		transformer.setParameter("id", Printer.formatBigInteger(this.admin.id))
		try {
			out.append("</admin>")
			out.close()
		} catch{
			case e:IOException => SWTLogger.writeerror(e.getMessage)
		} 
	}
}
