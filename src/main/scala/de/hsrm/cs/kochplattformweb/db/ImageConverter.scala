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

package de.hsrm.cs.kochplattformweb.db

import java.awt.Image

import de.hsrm.cs.kochplattformweb.utils.SWTLogger
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException

import com.sun.scenario.effect.ImageData
import org.apache.xmlgraphics.image.loader.spi.ImageLoader


/**
  * This class has two converter to method to convert Image to String and vice versa.
  *
  * @author aahri001
  * @version
  * @see
  * @since
  */
object ImageConverter {
  /**
    * Imagetype of image.
    */
  private val IMAGETYPE: Integer = 4

  /**
    * Convert a Image to String.
    *
    * @param img Image to convert.
    * @return Image as String.
    * @see
    * @since
    */
  /*def imageToBytes(img: Image): Array[Byte] = {
    val outs: ByteArrayOutputStream = new ByteArrayOutputStream
    val loader: ImageLoader = new ImageLoader
    loader.data = Array[ImageData](img.getImageData)
    loader.save(outs, ImageConverter.IMAGETYPE)
    try
      outs.flush()

    catch {
      case e: IOException => {
        SWTLogger.writeerror(e.getMessage)
      }
    }
    outs.toByteArray
  }

  /**
    * Convert byte array to Image.
    *
    * @param str the byte array.
    * @return the Image.
    * @see
    * @since
    */
  def bytesToImage(str: Array[Byte]): Image = {
    val ins: ByteArrayInputStream = new ByteArrayInputStream(str)
    new Image(null, ins)
  }
*/
}


