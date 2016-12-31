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

package de.hsrm.cs.kochplattformweb.db;

import java.io.Serializable

/**
 * Interface to Calculate Units in another Unit.
 * @author alex
 * @version
 * @see
 * @since
 */
trait Convertable extends Serializable {
	/**
	 * Convert to centiliter.
	 * @return Converted Value.
	 * @throws ConvertException when a unit not match with another unit.
	 */
	def toCentiLiter:Double;

	/**
	 * Convert to cup.
	 * @return Converted Value.
	 * @throws ConvertException when a unit not match with another unit.
	 */
	def toCup:Double;

	/**
	 * Convert to deziliter.
	 * @return Converted Value.
	 * @throws ConvertException when a unit not match with another unit.
	 */
	def toDeziLiter:Double;

	/**
	 * Convert to gram.
	 * @return Converted Value.
	 * @throws ConvertException when a unit not match with another unit.
	 */
	def toGram:Double;

	/**
	 * Convert to kilogram.
	 * @return Converted Value.
	 * @throws ConvertException when a unit not match with another unit.
	 */
	def toKiloGram:Double;


	/**
	 * Convert to liter.
	 * @return Converted Value.
	 * @throws ConvertException when a unit not match with another unit.
	 */
	def toLiter:Double;

	/**
	 * Convert to milligram.
	 * @return Converted Value.
	 * @throws ConvertException when a unit not match with another unit.
	 */
	def toMilliGram:Double;

	/**
	 * Convert to milliliter.
	 * @return Converted Value.
	 * @throws ConvertException when a unit not match with another unit.
	 */
	def toMilliLiter:Double;

	/**
	 * Convert to ounce.
	 * @return Converted Value.
	 * @throws ConvertException when a unit not match with another unit.
	 */
	def toOunce:Double;

	/**
	 * Convert to pinch.
	 * @return Converted Value.
	 * @throws ConvertException when a unit not match with another unit.
	 */
	def toPinch:Double;

	/**
	 * Convert to pound.
	 * @return Converted Value.
	 * @throws ConvertException when a unit not match with another unit.
	 */
	def toPound:Double;




	/**
	 * Convert to tablespoon.
	 * @return Converted Value.
	 * @throws ConvertException when a unit not match with another unit.
	 */
	def toTableSpooon:Double;
	/**
	 * Convert to teaspoon.
	 * @return Converted Value.
	 * @throws ConvertException when a unit not match with another unit.
	 */
	def toTeaSpoon:Double;
//	/**
//	 * Convert to bunch.
//	 * @return Converted Value.
//	 * @throws ConvertException when a unit not match with another unit.
//	 */
//	Double toBunch() throws ConvertException;


//	/**
//	 * Convert to can.
//	 * @return Converted Value.
//	 * @throws ConvertException when a unit not match with another unit.
//	 */
//	Double toCan() throws ConvertException;
//	/**
//	 * Convert to bottle.
//	 * @return Converted Value.
//	 * @throws ConvertException when a unit not match with another unit.
//	 */
//	Double toBottle() throws ConvertException;
//	/**
//	 * Convert to slice.
//	 * @return Converted Value.
//	 * @throws ConvertException when a unit not match with another unit.
//	 */
//	Double toSlice() throws ConvertException;
//	/**
//	 * Convert to piece.
//	 * @return Converted Value.
//	 * @throws ConvertException when a unit not match with another unit.
//	 */
//	Double toPiece() throws ConvertException;
//	/**
//	 * Convert to jar.
//	 * @return Converted Value.
//	 * @throws ConvertException when a unit not match with another unit.
//	 */
//	Double toJar() throws ConvertException;
//	/**
//	 * Convert to packet.
//	 * @return Converted Value.
//	 * @throws ConvertException when a unit not match with another unit.
//	 */
//	Double toPacket() throws ConvertException;
//	/**
//	 * Convert to soupbowl.
//	 * @return Converted Value.
//	 * @throws ConvertException when a unit not match with another unit.
//	 */
//	Double toSoupBowl() throws ConvertException;
}
