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

import de.hsrm.cs.kochplattformweb.client._
import de.hsrm.cs.kochplattformweb.db.Recipe
import de.hsrm.cs.kochplattformweb.db.RecipeEntry
import de.hsrm.cs.kochplattformweb.messages.GetRecipeMessage
import de.hsrm.cs.kochplattformweb.messages.Message
import de.hsrm.cs.kochplattformweb.utils.SWTLogger
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.io.InputStream
import java.math.BigInteger
import java.net.URI
import java.util
import javax.xml.transform.Result
import javax.xml.transform.Source
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerConfigurationException
import javax.xml.transform.TransformerException
import javax.xml.transform.TransformerFactory
import javax.xml.transform.sax.SAXResult
import javax.xml.transform.stream.StreamSource

import de.hsrm.cs.kochplattformweb.client.gui.ResourceBundleStorage
import org.apache.batik.gvt.event.SelectionAdapter
import org.apache.fop.apps.FOPException
import org.apache.fop.apps.FOUserAgent
import org.apache.fop.apps.Fop
import org.apache.fop.apps.FopFactory
import org.apache.fop.apps.MimeConstants

/**
 * This Class is the Printer Class to print documents.
 * @author aahri001
 * @version
 * @see
 * @since
 */
object Printer extends SelectionAdapter with Printable{
	/**
	 * String for directory above this package.{@value}
	 */
	val BASEDIR = "xml/"
	/**
	 * Constant for doubleFormat, number of numbers that are visible after ..
	 * {@value}
	 */
	val DOUBLEAFTERPOINT = 3
	/**
		* String for directory above this package.
		*/
	var parameter: Parameterable=null

	/**
	 * Format Double.
	 * @param value Double value to convert.
	 * @return String with converted double.
	 */
	def formatDouble(value:Double) {
		var result:String=""
		if(value==null) {
			result=""
		}
		else if((value%1)==0) {
			result=value.toString.substring(0, value.toString.indexOf("."))
		}
		else{
			if(value.toString.indexOf('.')<value.toString.length()-DOUBLEAFTERPOINT){
				result=value.toString.substring(0, value.toString.indexOf(".")+DOUBLEAFTERPOINT)
			}
			else{
				result=value.toString
			}
		}
		result
	}
	/**
	 * Format BigInteger.
	 * @param value Double value to convert.
	 * @return String with converted double.
	 */
	def formatBigInteger(value:BigInt) {
		var result:String=""
		if(value==null) {
			result=""
		}
		else{
			result=value.toString()
		}
		result
	}
	
	/**
	 * Format Integer.
	 * @param value Double value to convert.
	 * @return String with converted double.
	 */
	def formatInteger(value:Integer) {
		var result:String=""
		if(value==null) {
			result=""
		}
		else{
			result=value.toString
		}
		result
	}
	/**
	 * Handle to print a admin.
	 * @param admin the admin that will be printed.
	 */
	@Override
	override def printIt(admin:Admin) {
		this.parameter = new PrintAdmin(admin)
		this.doPrinting("admin", MimeConstants.MIME_FOP_AWT_PREVIEW, this.parameter)
	}
	/**
	 * Prints a Message.
	 * @param message Message to print.
	 */
	override def printIt(message:String) {
		this.parameter = new PrintMessage(message)
		this.doPrinting("message", MimeConstants.MIME_FOP_AWT_PREVIEW, this.parameter)
	}
	/**
	 * Handle to print a MainUser.
	 * @param user the MainUser that will be printed.
	 */
	@Override
	override def printIt(user:MainUser) {
		this.parameter = new PrintUser(user)
		this.doPrinting("user", MimeConstants.MIME_FOP_AWT_PREVIEW, this.parameter)
	}
	/**
	 * Handle to print a userlist.
	 * @param userlist the userlist that will be printed.
	 */
	@Override
	override def printIt(userlist:util.LinkedList[AbstractUser] ) {
		this.parameter = new PrintUserList(userlist)
		this.doPrinting("userlist", MimeConstants.MIME_FOP_AWT_PREVIEW, this.parameter)
	}
	/**
	 * Handle to print a recipeList.
	 * @param rlist
	 *            the ReceipeList that will be printed.
	 */
	@Override
	override def printIt(rlist:List[RecipeEntry]) {
		this.parameter = new PrintRecipeEntryList(rlist)
		this.doPrinting("recipeentry", MimeConstants.MIME_FOP_AWT_PREVIEW, this.parameter)
	}
	/**
	 * Handle to print a recipe.
	 * @param printRecipe
	 *            the receipe that will be printed.
	 */
	@Override
	override def printIt(printRecipe:Recipe) {
		this.parameter = new PrintRecipe(printRecipe)
		this.doPrinting("recipe", MimeConstants.MIME_FOP_AWT_PREVIEW, this.parameter)
	}
	/**
	 * General printing function.
	 * @param name XML Template name.
	 * @param mimeType Mimetype, default pdf.
	 * @param parameter Interface to set variables in xslt file.
	 * @return
	 * @see
	 * @since
	 */
	def doPrinting(name:String, mimeType:String,
			parameter:Parameterable) {
		val fopFactory:FopFactory = FopFactory.newInstance(new URI(""))

		val foUserAgent:FOUserAgent = fopFactory.newFOUserAgent()
		foUserAgent.setAuthor("Kochplattform")
		foUserAgent.setTitle("Kochplattform")
		try {
			var xsltfile:InputStream=null
			var xmlfile:InputStream=null
			xmlfile = this.getClass.getResource(BASEDIR +
					name + "_" + ResourceBundleStorage.getLanguage + ".xml").openStream()
			xsltfile = this.getClass.getResource(BASEDIR +
						name + ".xsl").openStream()
      val temp: File = File.createTempFile("userlist", ".xml")
			temp.deleteOnExit()
			val out:FileWriter = new FileWriter(temp)
			var character:Int=xmlfile.read()
			while (character != -1) {
				out.write(character)
				character=xmlfile.read()
			}
			xmlfile.close()
			val fop:Fop = fopFactory.newFop(mimeType, foUserAgent)
			val factory:TransformerFactory = TransformerFactory.newInstance()
			val transformer:Transformer = factory.newTransformer(new StreamSource(
					xsltfile))
			//Setting specific Parameters.
			parameter.setParameters(transformer, out)
			val src:Source = new StreamSource(temp)
			val res:Result = new SAXResult(fop.getDefaultHandler)
			transformer.transform(src, res)
		} catch { 
			case e:FOPException => SWTLogger.writeinfo(e.getMessage)
			case e:TransformerConfigurationException => SWTLogger.writeinfo(e.getMessage)
			case e:TransformerException => SWTLogger.writeinfo(e.getMessage)
			case e:IOException => SWTLogger.writeinfo(e.getMessage)
		}
	}
	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionAdapter
	 * #widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
/*	@Override
	override def widgetSelected(event:SelectionEvent) {
		val msg: Message = Mainwindow.getInstance().getClient()
			.getRecipe(new GetRecipeMessage(
				Mainwindow.getInstance().getRecipeIdList().
					get(Mainwindow.getInstance().getMainviewtab().
						getMainviewTable().
						getSelectionIndex())))
		this.printIt(msg.asInstanceOf[Recipe])
	}*/
	/*
	 * Main method.
	 * @param args command-line arguments
	 */
	/*public static void main(final String[] args) {
		File f = new File("/import/users/student/aahri001/doener.jpg")
		Image img = null
		try {
			img = new Image(null, new FileInputStream(f))
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace()
		}
		final List<Image> imagelist = new LinkedList<Image>()
		imagelist.add(img)
		final List<Ingredient> list = new LinkedList<Ingredient>()
		list.add(new Ingredient("Tomaten", 2.1234, de.hsrm.cs.kochplattform.db.EnumAmountType.KG))
		list.add(new Ingredient("Zitronen", 2.0, de.hsrm.cs.kochplattform.db.EnumAmountType.PIECE))
		list.add(new Ingredient("Kebab", 100000.0, de.hsrm.cs.kochplattform.db.EnumAmountType.CUP))
		final Recipe r = new Recipe("Döner", "türkisch-deutsch-USA-spanish-griechisch-undso", 2, "1:20",2, list, new Nutrition(5.327325672, null, 5.0, 5.0))
		r.setPicture(imagelist)
		r.setDifficulty("Schwer")
		r.setPreparation("Einfach Fleisch in Bort kippen.")
		r.setAmount(list.size())
		final Printer prin = new Printer()
		prin.printIt(r)
		List<RecipeEntry> rlist = new LinkedList<RecipeEntry>()
		RecipeEntry r0 = new RecipeEntry("Doener", "tuerkisch", 1, "1:30", list,2)
		r0.setRating(3)
		RecipeEntry r1 = new RecipeEntry("Gulasch", "ungarschig", 2, "10:30", list,2)
		r1.setRating(4)
		rlist.add(r0)
		for(int i=0i<100i++)
			rlist.add(r1)
		//prin.printIt(rlist)
		Admin a = new Admin("lala", "lala")
		a.setUserid(3)
		MainUser u = new MainUser("lulu", "lulu")
		u.setUserid(5)
		LinkedList<AbstractUser> userlist = new LinkedList<AbstractUser>()
		userlist.add(a)
		userlist.add(u)
		//prin.printIt("hallo")
	}*/
}
