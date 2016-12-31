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
