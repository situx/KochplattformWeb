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

import java.io.Serializable


/**Representation of the nutritions of the recipe.
 * @author Timo
 */
class Nutrition(var calories:Double, var fat:Double, var carbohydrates:Double, var protein:Double) extends Serializable{
  def setProtein(d: Double){
    this.protein=d
  }

  def setCarbohydrates(d: Double): Unit = {
    this.carbohydrates=d
  }

  def setFat(d: Double){
    this.fat=d
  }

  def setCalories(d: Double)  {
		this.calories=d
	}

  def getCalories=calories

  def getFat=fat

  def getCarbohydrates=carbohydrates

  def getProtein=protein

	def toXML={
		<nutrition calories={calories.toString} fat={fat.toString} carbohydrates={carbohydrates.toString} protein={protein.toString}/>
	}
}
