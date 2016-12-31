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

package de.hsrm.cs.kochplattformweb.utils
import java.util.Date

import org.apache.log4j.{ConsoleAppender, FileAppender, Level, Logger, SimpleLayout}

object SWTLogger{
 val logger=Logger.getRootLogger()
 /**Prime for hashCode Methods {@value}.*/
 val PRIME = 31;
 this.logger.addAppender(new ConsoleAppender(new SimpleLayout()))
 this.logger.addAppender(new FileAppender(new SimpleLayout(),"Logged.log"));
 this.logger.setLevel(Level.ERROR)

 /** Writes Infos into a file to log with a timestemp.
   *
   * @param towrite String to write to log
   */
 def writeinfo(towrite: String) {
  this.logger.info(new Date().toString + " : " + towrite)
 }

 /** Writes Errors into a file to log with a timestemp.
   *
   * @param towrite String to write to log
   */
 def writeerror(towrite: String) {
  this.logger.info(new Date().toString + " : " + towrite)
 }
}