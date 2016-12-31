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

import de.hsrm.cs.kochplattformweb.db.Ingredient
import de.hsrm.cs.kochplattformweb.db.Recipe
import de.hsrm.cs.kochplattformweb.utils.SWTLogger
import java.io.File
import java.io.FileWriter
import java.io.IOException
import javax.xml.transform.Transformer

import com.sun.scenario.effect.ImageData



/**
 * This class implements Parameterable to set parameter to print it.
 * @author aahri001
 * @version
 * @see
 * @since
 */
class PrintRecipe(recipe:Recipe) extends Parameterable {
	/**
	 * Height of picture.
	 */
	val HEIGHT = 200
	/**
	 * Width of picture.
	 */
	val WIDTH = 250

	@Override
	override def setParameters(transformer: Transformer, out:FileWriter) {
		transformer.setParameter("amount", Printer.formatInteger(this.recipe.ingredients.length))
		transformer.setParameter("personamount", this.recipe.zombies)
		transformer.setParameter("category", this.recipe.category)
		transformer.setParameter("difficult", this.recipe.difficulty)
		transformer.setParameter("name", this.recipe.name)
		transformer.setParameter("calories", Printer.formatDouble(this.recipe.nutrition.getCalories))
		transformer.setParameter("carbohydrates", Printer.formatDouble(this.recipe.nutrition.getCarbohydrates))
		transformer.setParameter("fat", Printer.formatDouble(this.recipe.nutrition.getFat))
		transformer.setParameter("protein", Printer.formatDouble(this.recipe.nutrition.getProtein))
		transformer.setParameter("preparation", this.recipe.preparation)
		transformer.setParameter("picture", "pic.jpg")
		transformer.setParameter("time", this.recipe.time)
		val res:StringBuffer = new StringBuffer(1024)
		res.append("<list>")
		for (i:Ingredient<-this.recipe.ingredients) {
			res.append("<item><name>")
			res.append(i.name)
			res.append("</name><set>")
			res.append(Printer.formatDouble(i.amount))
			res.append(' ')
			if(i.amount>1){
				res.append(i.amountType+"S")
			}
			else{
				res.append(i.amountType)
			}
			res.append("</set></item>")
		}
		try {
			out.append(res)
			out.append("</list></recipe>")
			out.close()
		} catch  {
			case e:IOException => SWTLogger.writeerror(e.getMessage)
		}
		var temp:File=null
		try {
			temp = File.createTempFile("recipe",
					".jpg")
			temp.deleteOnExit()
			if(this.recipe.picture.nonEmpty){
        /*val loader: ImageLoader = new ImageLoader()
				loader.data = new ImageData[] {
					this.recipe.picture.get(0).toImage().getImageData().scaledTo(WIDTH,HEIGHT)}
				loader.save(temp.getPath, SWT.IMAGE_JPEG)*/
				transformer.setParameter("picture",temp.getPath)
			}
		} catch {
			case e:IOException => SWTLogger.writeerror(e.getMessage)
		}
	}

}
