/*
 * This file is part of KochplattformWeb.
 *
 *   KochplattformWeb is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *    KochplattformWeb is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with KochplattformWeb.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

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
