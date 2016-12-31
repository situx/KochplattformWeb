package de.hsrm.cs.kochplattformweb.printer

import java.io.FileWriter

import javax.xml.transform.Transformer

/**
 * This Interface is to set parameter to the xsl file.
 * @author aahri001
 * @version
 * @see
 * @since
 */
trait Parameterable {
	/**
	 * This method is to set Parameters to xsl file and extend xml file.
	 * @param transformer xslt transformer.
	 * @param out FileWriter for xml file.
	 * @return
	 * @see
	 * @since
	 */
	def setParameters(transformer:Transformer, out:FileWriter)
}
