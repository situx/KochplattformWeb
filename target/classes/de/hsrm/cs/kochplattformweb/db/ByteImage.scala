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

/**
    * @author alex
    * @version
    * @see
    * @since
    */
  @SerialVersionUID(-7968896807253252813L)
  class ByteImage extends Serializable {
    private[db] var imagesBytes: Array[Byte] = null

    def this(image: Image) {
      this()
      //this.imagesBytes = ImageConverter.imageToBytes(image)
    }

    def this(image: Array[Byte]) {
      this()
      this.imagesBytes = image
    }

    def toImage: Image = {
      //val img: Image = ImageConverter.bytesToImage(this.imagesBytes)
      //img
      null
    }

    def getBytes: Array[Byte] = {
      val imagesBytes2: Array[Byte] = this.imagesBytes
      imagesBytes2
    }


}
