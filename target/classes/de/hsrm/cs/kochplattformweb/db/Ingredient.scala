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

case class Ingredient(amount:Double,name:String,amountType:String) extends Comparable[Ingredient]{

  def this(name:String)=this(0.0,name,EnumAmountType.KG.toString)

  def toXML={
    <ingredient amount={amount.toString} unit={amountType.toString}>{name.toString}</ingredient>
  }

  override def compareTo(o: Ingredient): Int ={name.compareTo(o.name)
  }
}
