package de.hsrm.cs.kochplattformweb.printer

import java.io.FileWriter
import java.io.IOException
import javax.xml.transform.Transformer

import de.hsrm.cs.kochplattformweb.db.{Ingredient, RecipeEntry}
import de.hsrm.cs.kochplattformweb.utils.SWTLogger

/**
 * This class implements Parameterable to print a list of recipeenetrys.
 * @author aahri001
 * @version
 * @see
 * @since
 */
class PrintRecipeEntryList(rlist:List[RecipeEntry]) extends Parameterable {
	/**The count variable for the transformer.*/
	var count:Int=0

	@Override
	override def setParameters(transformer:Transformer, out:FileWriter) {
		val res: StringBuffer = new StringBuffer(1024)
		res.append("<list>")
		for (i:RecipeEntry<-this.rlist) {
			res.append("<item><name>")
			res.append(i.name)
			res.append("</name><category>")
			res.append(i.category)
			res.append("</category><time>")
			res.append(i.time)
			res.append("</time><rating>")
			res.append(Printer.formatDouble(i.rating))
			res.append("</rating><ingredient>")
			this.count = 0
			for (intgre:Ingredient <- i.ingredients) {
				if(this.count == i.ingredients
						.size-1) {
					res.append(intgre
							.name)
				}
				else {
					res.append(intgre
							.name)
					res.append(", ")
				}
				this.count+=1
			}
			res.append("</ingredient></item>")
		}
		try {
			out.append(res)
			out.append("</list></recipeentry>")
			out.close()
		} catch { case e:IOException =>	SWTLogger.writeerror(e.getMessage)
		}
	}

}
