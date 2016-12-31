package de.hsrm.cs.kochplattformweb.client.gui

import de.hsrm.cs.kochplattformweb.db.Recipe
import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.Label
import org.eclipse.swt.widgets.Shell
import org.eclipse.swt.widgets.TabFolder
import org.eclipse.swt.widgets.Text

/**
 * RecipeWindowDesc to display preperation.
 * @author alex
 * @version
 * @see
 * @since
 */
object RecipeWindowDesc {
	/** Height of the CreateButton. **/
	val PREPLABEL_H = 22
	/** Width of the CreateButton. **/
	val PREPLABEL_W = 766
	/** X-Position of the CreateButton. **/
	val PREPLABEL_X = 22
	/** Y-Position of the CreateButton. **/
	val PREPLABEL_Y = 22
	/** Height of the CreateButton. **/
	val PREPTEXT_H = 570
	/** Width of the CreateButton. **/
	val PREPTEXT_W = 758
	/** X-Position of the CreateButton. **/
	val PREPTEXT_X = 22
	/** Y-Position of the CreateButton. **/
	val PREPTEXT_Y = 46
	/**
		* Composite to generate.
		*/
	var composite: Composite=new Composite(new Shell(), SWT.BORDER)
	/**
		* Preperation inputText.
		*/
	var preperationtext: Text=new Text(composite,
		SWT.MULTI | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL)

	/**
	 * getControl to get Composite.
	 * @param folder the tabfolder.
	 * @param window the parent window.
	 * @param display the display.
	 * @return the generated Composite.
	 * @see
	 * @since
	 */
	def getControl(folder:TabFolder,
			window:Shell, display:Display):Composite= {
		if (composite == null || composite.isDisposed()) {
			buildTabGui(folder)
		}
		composite
	}
	/**
	 * Method to build the gui.
	 * @param folder the tab folder.
	 * @return
	 * @see
	 * @since
	 */
	def buildTabGui(folder:TabFolder) {
		composite = new Composite(folder, SWT.BORDER)
		composite.setSize(folder.getSize())
		val preperationLabel:Label = new Label(composite, SWT.NONE)
		preperationLabel.setText(ResourceBundleStorage.getResourceBundle("RecipeWindow")
				.getString("preperation"))
		preperationLabel.setBounds(PREPLABEL_X, PREPLABEL_Y,
				PREPLABEL_W, PREPLABEL_H)
		preperationtext = new Text(composite,
				SWT.MULTI | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL)
		preperationtext.setBounds(PREPTEXT_X, PREPTEXT_Y,
				PREPTEXT_W, PREPTEXT_H)
	}

	/**
	 * Fill out Recipe with textinput.
	 * @param recipe to fill out.
	 * @param display Display to display.
	 */
	def fillRecipe(recipe:Recipe,
			display:Display) {
		if (preperationtext.getText().length() == 0) {
			throw new RecipeWrongInputException("wrongPreperation")
		}
		recipe.preparation= preperationtext.getText()
			.replace("<", "&lt").replace(">", "&gt")
	}
}
