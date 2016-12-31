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
 * Class for converting the recipes' units.
 * @author alex
 * @version
 * @see
 * @since
 */
class UnitConverter(amounttype:EnumAmountType.EnumAmountType, amount:Double) extends Convertable {

	/** Unique Serialization-number for identification of Object {@value} . */
	val serialVersionUID = 6186091791240822092L
	/** BOTTLE-value in KG for volumes. */
	val BOTTLECON:Double = 0.7
	/** CUP-value in KG for masses. */
	val CUPCON:Double = 0.2
	/** EL-value in KG for masses. */
	val EL_MASS:Double = 0.040
	/** EL-value in KG for volumes. */
	val EL_VOL:Double = 0.015
	/** Exponent 10+E1. */
	val EXP1:Double = 10
	/** Exponent 10+E2. */
	val EXP2:Double = 100
	/** Exponent 10+E3. */
	val EXP3:Double = 1000
	/** Exponent 10+E6. */
	val EXP6:Double = 1000000
	/** JAR-value in KG for masses. */
	val JARCON:Double = 1.0
	/** ounce-value in KG. */
	val OUNCECON:Double = 0.0283495231
	/** PINCH-value in KG for volumes. */
	val PINCHCON:Double = 0.003
	/** pound-value in KG. */
	val POUNDCON:Double = 0.45359237
	/** TL-value in KG for masses. */
	val TL_MASS:Double = 0.015
	/** TL-value in KG for volumes. */
	val TL_VOL:Double = 0.005

	override def toCentiLiter:Double = this.toLiter * EXP2

	override def toCup:Double=this.toLiter * CUPCON


	/*
	 * (non-Javadoc)
	 * @see de.hsrm.cs.kochplattform.db.Convertable#toDeziLiter()
	 */
	@Override
	override def  toDeziLiter:Double =this.toLiter * EXP1


	/*
	 * (non-Javadoc)
	 * @see de.hsrm.cs.kochplattform.db.Convertable#toGram()
	 */
	@Override
	override def toGram:Double =this.toKiloGram * EXP3


	/**
	 * Converts the unit to kilogram.
	 * @return the converted unit value
	 * @throws ConvertException
	 *             when a unit cannot convert.
	 */
	def toKiloGram:Double =this.amounttype match {
		    case EnumAmountType.MG => this.amount / EXP6
		    case EnumAmountType.G => this.amount / EXP3
		    case EnumAmountType.KG => this.amount
		    case EnumAmountType.OUNCE => this.amount * OUNCECON
		    case EnumAmountType.POUND => this.amount * POUNDCON
		    case EnumAmountType.ML => this.amount / EXP3
		    case EnumAmountType.CL => this.amount / EXP2
		    case EnumAmountType.DL => this.amount / EXP1
		    case EnumAmountType.L => this.amount
		    case EnumAmountType.EL => this.amount * EL_MASS
		    case EnumAmountType.TL => this.amount * TL_MASS
		    case EnumAmountType.CUP => this.amount * CUPCON
		    case EnumAmountType.BOTTLE => this.amount * BOTTLECON
		    case EnumAmountType.JAR => this.amount * JARCON
		    case EnumAmountType.PINCH => this.amount * PINCHCON
				// No match to convert this Unit with other Unit
			    throw new ConvertException()
	}

	/*
	 * (non-Javadoc)
	 * @see de.hsrm.cs.kochplattform.db.Convertable#toLiter()
	 */

	override def toLiter: Double = this.amounttype match {
			case EnumAmountType.ML => this.amount / EXP3
			case EnumAmountType.CL => this.amount / EXP2
			case EnumAmountType.DL => this.amount / EXP1
			case EnumAmountType.L => this.amount
			case EnumAmountType.EL => this.amount * EL_VOL
			case EnumAmountType.TL => this.amount * TL_VOL
			case EnumAmountType.CUP => this.amount * CUPCON
			case EnumAmountType.BOTTLE => this.amount * BOTTLECON
			case EnumAmountType.JAR => this.amount
			case _ => throw new ConvertException()
			// No match to convert this Unit with other Unit
	}

	/*
	 * (non-Javadoc)
	 * @see de.hsrm.cs.kochplattform.db.Convertable#toMilliGram()
	 */
	@Override
	override def toMilliGram:Double =this.toKiloGram * EXP3

	/*
	 * (non-Javadoc)
	 * @see de.hsrm.cs.kochplattform.db.Convertable#toMilliLiter()
	 */
	@Override
	override def toMilliLiter:Double =this.toLiter * EXP3


	/*
	 * (non-Javadoc)
	 * @see de.hsrm.cs.kochplattform.db.Convertable#toOunce()
	 */
	@Override
	override def toOunce:Double =this.toKiloGram * OUNCECON

	/* (non-Javadoc)
	 * @see de.hsrm.cs.kochplattform.db.Convertable#toPinch()
	 */
	@Override
	override def  toPinch:Double= this.toLiter * PINCHCON

	/*
	 * (non-Javadoc)
	 * @see de.hsrm.cs.kochplattform.db.Convertable#toPound()
	 */
	@Override
	override def  toPound:Double=this.toKiloGram * POUNDCON

	/*
	 * (non-Javadoc)
	 * @see de.hsrm.cs.kochplattform.db.Convertable#toTableSpooon()
	 */
	override def toTableSpooon:Double = this.amounttype match {
		    case EnumAmountType.MG =>  this.amount / EXP6 * EL_MASS
		    case EnumAmountType.G => this.amount / EXP3 * EL_MASS
		    case EnumAmountType.KG => this.amount * EL_MASS
		    case EnumAmountType.OUNCE => this.amount * OUNCECON / EL_MASS
		    case EnumAmountType.POUND => this.amount * POUNDCON / EL_MASS
		    case EnumAmountType.ML => this.amount / EXP3 * EL_MASS
		    case EnumAmountType.CL => this.amount / EXP2 * EL_MASS
		    case EnumAmountType.DL => this.amount / EXP1 * EL_MASS
		    case EnumAmountType.L => this.amount * EL_MASS
		    case EnumAmountType.TL => this.amount / TL_MASS * EL_MASS
		    case EnumAmountType.CUP => this.amount / CUPCON * EL_MASS
		    case EnumAmountType.BOTTLE => this.amount / BOTTLECON * EL_MASS
		    case EnumAmountType.JAR => this.amount / EL_MASS
				case _ =>throw new ConvertException()
		// No match to convert this Unit with other Unit
	}

	/*
	 * (non-Javadoc)
	 * @see de.hsrm.cs.kochplattform.db.Convertable#toTeaSpoon()
	 */
	@Override
	override def  toTeaSpoon: Double={
		0.0
	}

	/**
	 * Gets the amount of the UnitConverter.
	 * @return the amount
	 */
	def getAmount =this.amount

	/**
	 * Gets the AmountType of the UnitConverter.
	 * @return the amounttype
	 */
	def getAmounttype= this.amounttype

}
