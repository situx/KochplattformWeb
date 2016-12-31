package de.hsrm.cs.kochplattformweb.client.gui

import java.io.IOException

import de.hsrm.cs.kochplattformweb.db.Recipe
import de.hsrm.cs.kochplattformweb.utils.SWTLogger
import org.eclipse.swt.SWT
import org.eclipse.swt.events.SelectionAdapter
import org.eclipse.swt.events.SelectionEvent
import org.eclipse.swt.graphics.Image
import org.eclipse.swt.widgets.Button
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.Shell
import org.eclipse.swt.widgets.TabFolder
import org.eclipse.swt.widgets.TabItem

/**
 * This class is to display RecipeWindow to create a recipe.
 * @author alex
 */
class RecipeWindow(display:Display,fillout:FillOutRecipe) extends SelectionAdapter {
	/** Height of the Window. **/
	val WINDOW_H = 760
	/** Width of the Window. **/
	val WINDOW_W = 820
	/** Height of the Tabfolder. **/
	val TAB_H = 680
	/** Width of the Tabfolder. **/
	val TAB_W = 810
	/** Height of the CreateButton. **/
	val CREATE_H = 28
	/** Width of the CreateButton. **/
	val CREATE_W = 150
	/** X-Position of the CreateButton. **/
	val CREATE_X = 650
	/** Y-Position of the CreateButton. **/
	val CREATE_Y = 694
	/** Height of the ResetButton. **/
	val RESET_H = 28
	/** Width of the ResetButton. **/
	val RESET_W = 150
	/** X-Position of the ResetButton. **/
	val RESET_X = 470
	/** Y-Position of the ResetButton. **/
	val RESET_Y = 694
	/**
		* Recipe to create.
		*/
	var recipe: Recipe=null
  /**
    * Tabfolder for tabview.
    */
  var tabFolder: TabFolder=null
  /**
    * Window to create.
    */
  var window: Shell=null

	/**
	 * Method to built the gui.
	 * @return
	 * @see
	 * @since
	 */
	def buildGui() {
		try {
			this.window.setImage(new Image(this.display, this.getClass
					.getResource("img/create.png").openStream()))
		} catch{
			case e:IOException => SWTLogger.writeerror(e.getMessage)
		}
		this.recipe = new Recipe()
		this.window.setText(ResourceBundleStorage
				.getResourceBundle("RecipeWindow").getString("title"))
		this.window.setSize(WINDOW_W, WINDOW_H)
		this.tabFolder.setSize(TAB_W, TAB_H)
		val item:TabItem = new TabItem(this.tabFolder, SWT.NONE)
		item.setText(ResourceBundleStorage
				.getResourceBundle("RecipeWindow").getString("properties"))
		item.setControl(RecipeWindowProp.getControl(this.tabFolder,
				this.window, this.display))
		val item2:TabItem = new TabItem(this.tabFolder, SWT.NONE)
		item2.setText(ResourceBundleStorage
				.getResourceBundle("RecipeWindow").getString("ingredient"))
		item2.setControl(RecipeWindowIng.getControl(this.tabFolder,
				this.window, this.display, this.fillout.getIngredients()))
		val item3:TabItem = new TabItem(this.tabFolder, SWT.NONE)
		item3.setText(ResourceBundleStorage
				.getResourceBundle("RecipeWindow").getString("preperation"))
		item3.setControl(RecipeWindowDesc
				.getControl(this.tabFolder, this.window, this.display))
		val create:Button = new Button(this.window, SWT.PUSH)
		create.setText(ResourceBundleStorage
				.getResourceBundle("RecipeWindow").getString("create"))
		create.setBounds(CREATE_X, CREATE_Y, CREATE_W,
				CREATE_H)
		create.addSelectionListener(new SelectionAdapter() {
			@Override
			override def widgetSelected(arg0:SelectionEvent) {
				try {
					RecipeWindowIng.fillRecipe(RecipeWindow.this.recipe)
					RecipeWindowProp.fillRecipe(RecipeWindow.this.display,
						RecipeWindow.this.recipe)
					RecipeWindowDesc.fillRecipe(RecipeWindow.this.recipe,
						RecipeWindow.this.display)
					RecipeWindow.this.recipe.language= ResourceBundleStorage.getLanguage.toString
					Mainwindow.getInstance().getTabfolder().setSelection(0)
					RecipeWindow.this.window.close()
					RecipeWindow.this.fillout.createRecipe(RecipeWindow.this.recipe)
				} catch{
					case e:RecipeWrongInputException => new Messagewindow(e.getMessage(), RecipeWindow.this.display)
						.openwindow()
				}
			}
		})
		val reset:Button = new Button(this.window, SWT.PUSH)
		reset.setText(ResourceBundleStorage
				.getResourceBundle("RecipeWindow").getString("close"))
		reset.setBounds(RESET_X, RESET_Y,
				RESET_W, RESET_H)
		reset.addSelectionListener(new SelectionAdapter() {
			@Override
			override def widgetSelected(arg0:SelectionEvent) {
				RecipeWindow.this.window.close()
			}
		})
		GUIHelper.makeMeCenter(this.display, this.window)
		try {
			item.setImage(new Image(this.display,
					this.getClass.getResource("img/tab4.png").openStream()))
			item2.setImage(new Image(this.display,
					this.getClass.getResource("img/tab4.png").openStream()))
			item3.setImage(new Image(this.display,
					this.getClass.getResource("img/tab4.png").openStream()))
		} catch {case e:IOException =>
			SWTLogger.writeerror(e.getMessage)
		}
	}

	/** (non-Javadoc)
	 * Method when you clicked on menu.
	 * @param arg0 parameter for clickevent.
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	override def widgetSelected(arg0:SelectionEvent) {
		if (this.window == null) {
			this.window = new Shell(this.display,
					SWT.CLOSE | SWT.TITLE | SWT.MIN | SWT.MAX)
			this.tabFolder = new TabFolder(this.window, SWT.BORDER)
			this.buildGui()
		}
		if (this.window.isDisposed) {
			this.window = new Shell(this.display,
					SWT.CLOSE | SWT.TITLE | SWT.MIN | SWT.MAX)
			this.tabFolder = new TabFolder(this.window, SWT.BORDER)
			this.buildGui()
		}
		this.window.open
	}

}
