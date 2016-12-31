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

package de.hsrm.cs.kochplattformweb.db

/**
 * Enum for all Units that a ingredient can have.
 * @author alex
 * @version
 * @see
 * @since
 */
object EnumAmountType extends Enumeration {

	type EnumAmountType = Value;
	/** None. */
	val NULL,

	/** KiloGram unit. */
	KG,

	/** Gram unit. */
	G,

	/** Milligram unit. */
	MG,
	// Volumes
	/** Milliliter unit. */
	ML,

	/** Liter unit. */
	L,

	/** Deziliter unit. */
	DL,

	/** Centiliter unit. */
	CL,

	/** Tablespoon. */
	EL,

	/** Teaspoon. */
	TL,

	/** Pinch unit. */
	// PINCH = PRISE
	PINCH,

	/** A bunch. */
	BUNCH,

	/** Ounce unit. */
	OUNCE,

	/** Cube unit. */
	CUBE,

	/** Pound unit. */
	POUND,

	/** A cup. */
	CUP,

	/** A can. */
	CAN,

	/** A bottle. */
	BOTTLE,

	/** A slice (Scheibe). */
	SLICE,

	/** A piece. */
	PIECE,

	/** A jar. */
	JAR,

	/** A packet. */
	PACKET,

	/** A package. */
	PACKAGE,

	/** A soup bowl. */
	SOUPBOWL,

	/** A clove (Zehe Knoblauch). */
	CLOVE,

	/** A leaf. */
	LEAF,

	/** A rib. */
	RIB =Value

	/*
	 * Gets the mass or volume type for the ingredient.
	 * @param value
	 *            the value type.
	 * @return the EnumAmountType Array to use.
	 */
	/*def EnumAmountType[] getEnum(value:EnumAmountType) {
		mass:EnumAmountType[] = {KG, G, MG, EL, TL, OUNCE, POUND, PINCH,
				BUNCH, CUBE, CUP, CAN, BOTTLE, SLICE, PIECE, JAR, PACKET,
				PACKAGE, SOUPBOWL, CLOVE, RIB, LEAF};
		final EnumAmountType[] volumen = {ML, L, DL, CL, EL, TL, BUNCH,
				CUBE, CUP, CAN, BOTTLE, JAR, PACKET, PACKAGE,
				SOUPBOWL, CLOVE, RIB, LEAF};
		final EnumAmountType[] all = {KG, G, MG, ML, L, DL, CL, EL, TL, PINCH,
				BUNCH, OUNCE, CUBE, POUND, CUP, CAN, BOTTLE, SLICE, PIECE, JAR,
				PACKET, PACKAGE, SOUPBOWL, CLOVE, RIB, LEAF};
		EnumAmountType[] result;
		value match  {
			case KG=> result = mass;
			case G: // fallthrough
			case MG:
			case OUNCE:
			case POUND:
			case PINCH:
			case SLICE:
				result = mass;
				break;
			case ML:// fallthrough
			case L:
			case DL:
			case CL:
				result = volumen;
				break;
			default:
				result = all;
				break;
		}
		return result;
	}*/
}
