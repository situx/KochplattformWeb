package de.hsrm.cs.kochplattformweb.client.gui

import java.io.IOException
import de.hsrm.cs.kochplattformweb.db.{ByteImage, Nutrition, Recipe}
import de.hsrm.cs.kochplattformweb.utils.SWTLogger
import org.eclipse.swt.SWT
import org.eclipse.swt.SWTException
import org.eclipse.swt.events.SelectionEvent
import org.eclipse.swt.events.SelectionListener
import org.eclipse.swt.graphics.Image
import org.eclipse.swt.layout.FormAttachment
import org.eclipse.swt.layout.FormData
import org.eclipse.swt.layout.FormLayout
import org.eclipse.swt.widgets.Button
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.FileDialog
import org.eclipse.swt.widgets.Label
import org.eclipse.swt.widgets.Shell
import org.eclipse.swt.widgets.TabFolder
import org.eclipse.swt.widgets.Text

/**
 * This class is to show RecipeWindowProperties.
 * @author alex
 * @version
 * @see
 * @since
 */
object RecipeWindowProp {
	/**
		* Composite to display.
		*/
	var composite: Composite=new Composite(new Shell(), SWT.BORDER)
	/**
		* Textname input field.
		*/
	var nameText: Text=new Text(composite, SWT.BORDER | SWT.SINGLE)
	/**
		* Personamount inputfield.
		*/
	var personamountText: Text=new Text(composite, SWT.BORDER | SWT.SINGLE)
	/**
		* Difficult inputfield.
		*/
	var difficultText: Text=new Text(composite, SWT.BORDER | SWT.SINGLE)
	/**
		* Category inputfield.
		*/
	var categoryText: Text=new Text(composite, SWT.BORDER | SWT.SINGLE)
	/**
		* Time inputfield.
		*/
	var timeText: Text=new Text(composite, SWT.BORDER | SWT.SINGLE)
	/**
		* Fat inputfield.
		*/
	var fatText: Text=new Text(composite, SWT.BORDER | SWT.SINGLE)
	/**
		* calorien inputfield.
		*/
	var calorienText: Text=new Text(composite, SWT.BORDER | SWT.SINGLE)
	/**
		* carbohydrates inputfield.
		*/
	var carbohydratesText: Text=new Text(composite, SWT.BORDER | SWT.SINGLE)
	/**
		* protein inputfield.
		*/
	var proteinText: Text=new Text(composite, SWT.BORDER | SWT.SINGLE)
	/**
		* Picture Label to show picture.
		*/
	var pictureLabel: Label=new Label(composite, SWT.CENTER | SWT.BORDER)
  /**
    * Listener for addPrictureButton.
    */
  var addPictureButtonListener: SelectionListener=null

	/**A constant Number {@value}.*/
	val CONSTNUMBER10=10
	/**A constant Number {@value}.*/
	val CONSTNUMBER16= 16
	/**A constant Number {@value}.*/
	val CONSTNUMBER18= 18
	/**A constant Number {@value}.*/
	val CONSTNUMBER20= 20
	/**A constant Number {@value}.*/
	val CONSTNUMBER200= 200
	/**A constant Number {@value}.*/
	val CONSTNUMBER250= 250
	/**A constant Number {@value}.*/
	val CONSTNUMBER300= 300
	/**A constant Number {@value}.*/
	val CONSTNUMBER302= 302

	/**
	 * Get composite with gui.
	 * @param folder specific TabFolder
	 * @param window the window
	 * @param display the display.
	 * @return returns the filles Composite.
	 */
	def getControl(folder:TabFolder,window:Shell, display:Display):Composite={
		if (composite == null || composite.isDisposed) {
			addPictureButtonListener = new SelectionListener() {
				@Override
				def widgetSelected(arg0:SelectionEvent) {
					val dialog:FileDialog = new FileDialog(window, SWT.OPEN)
					dialog.setText(ResourceBundleStorage.getResourceBundle("RecipeWindow").getString("addPic"))
					val filterExt:Array[String] = Array[String]("*.png", "*.jpg", "*.jpeg", "*.gif")
					dialog.setFilterExtensions(filterExt)
					val path:String = dialog.open()
          var img: Image = null
					if (path != null) {
						try {
							img = new Image(display, path)
							img.getImageData.scaledTo(CONSTNUMBER250, CONSTNUMBER200)
							img = new Image(display, img.getImageData
									.scaledTo(CONSTNUMBER250, CONSTNUMBER200))
							pictureLabel.setImage(img)
						} catch {case e:SWTException =>
							new Messagewindow("nopic", display).openwindow()
						}
					}
				}
				@Override
				def widgetDefaultSelected(arg0:SelectionEvent) {
				}
			}
			buildTabGui(folder, window, display)
		}
		composite
	}
	/**
	 * build the GUI in composite.
	 * @param folder the Tabfolder.
	 * @param window the window.
	 * @param display the display.
	 * @return
	 * @see
	 * @since
	 */
	def buildTabGui(folder:TabFolder,window:Shell, display:Display) {
		composite = new Composite(folder, SWT.BORDER)
		composite.setSize(folder.getSize)
		composite.setLayout(new FormLayout())
		val nameLabel:Label = new Label(composite, SWT.RIGHT)
		nameLabel.setText(ResourceBundleStorage.getResourceBundle("RecipeWindow").getString("name"))
		var fd: FormData = new FormData(CONSTNUMBER300, SWT.DEFAULT)
		fd.top = new FormAttachment(CONSTNUMBER10, 0)
		fd.left = new FormAttachment(0, CONSTNUMBER10)
		fd.right = new FormAttachment(CONSTNUMBER16, 0)
		nameLabel.setLayoutData(fd)
		val personamountLabel:Label = new Label(composite, SWT.RIGHT)
		personamountLabel.setText(ResourceBundleStorage.getResourceBundle("RecipeWindow").getString("personamount"))
		fd = new FormData(CONSTNUMBER300, SWT.DEFAULT)
		fd.top = new FormAttachment(nameLabel, CONSTNUMBER20, 0)
		fd.left = new FormAttachment(0, CONSTNUMBER10)
		fd.right = new FormAttachment(CONSTNUMBER16, 0)
		personamountLabel.setLayoutData(fd)
		val difficultLabel:Label = new Label(composite, SWT.RIGHT)
		difficultLabel.setText(ResourceBundleStorage.getResourceBundle("RecipeWindow").getString("difficult"))
		fd = new FormData(CONSTNUMBER300, SWT.DEFAULT)
		fd.top = new FormAttachment(personamountLabel, CONSTNUMBER20, 0)
		fd.left = new FormAttachment(0, CONSTNUMBER10)
		fd.right = new FormAttachment(CONSTNUMBER16, 0)
		difficultLabel.setLayoutData(fd)
		val categoryLabel:Label = new Label(composite, SWT.RIGHT)
		categoryLabel.setText(ResourceBundleStorage.getResourceBundle("RecipeWindow").getString("category"))
		fd = new FormData(CONSTNUMBER300, SWT.DEFAULT)
		fd.top = new FormAttachment(difficultLabel, CONSTNUMBER20, 0)
		fd.left = new FormAttachment(0, CONSTNUMBER10)
		fd.right = new FormAttachment(CONSTNUMBER16, 0)
		categoryLabel.setLayoutData(fd)
		val timeLabel:Label = new Label(composite, SWT.RIGHT)
		timeLabel.setText(ResourceBundleStorage.getResourceBundle("RecipeWindow").getString("time"))
		fd = new FormData(CONSTNUMBER300, SWT.DEFAULT)
		fd.top = new FormAttachment(categoryLabel, CONSTNUMBER20, 0)
		fd.left = new FormAttachment(0, CONSTNUMBER10)
		fd.right = new FormAttachment(CONSTNUMBER16, 0)
		timeLabel.setLayoutData(fd)
		val nutritionLabel:Label = new Label(composite, SWT.RIGHT)
		nutritionLabel.setText(ResourceBundleStorage.getResourceBundle("RecipeWindow").getString("nutrition"))
		fd = new FormData(CONSTNUMBER300, SWT.DEFAULT)
		fd.top = new FormAttachment(timeLabel, CONSTNUMBER20, 0)
		fd.left = new FormAttachment(0, CONSTNUMBER10)
		fd.right = new FormAttachment(CONSTNUMBER20, 0)
		nutritionLabel.setLayoutData(fd)
		val fatLabel:Label = new Label(composite, SWT.RIGHT)
		fatLabel.setText(ResourceBundleStorage.getResourceBundle("RecipeWindow").getString("fat"))
		fd = new FormData(CONSTNUMBER300, SWT.DEFAULT)
		fd.top = new FormAttachment(nutritionLabel, CONSTNUMBER20, 0)
		fd.left = new FormAttachment(0, CONSTNUMBER10)
		fd.right = new FormAttachment(CONSTNUMBER16, 0)
		fatLabel.setLayoutData(fd)
		val calorienLabel:Label = new Label(composite, SWT.RIGHT)
		calorienLabel.setText(ResourceBundleStorage.getResourceBundle("RecipeWindow").getString("calorien"))
		fd = new FormData(CONSTNUMBER300, SWT.DEFAULT)
		fd.top = new FormAttachment(fatLabel, CONSTNUMBER20, 0)
		fd.left = new FormAttachment(0, CONSTNUMBER10)
		fd.right = new FormAttachment(CONSTNUMBER16, 0)
		calorienLabel.setLayoutData(fd)
		val carbohydratesLabel:Label = new Label(composite, SWT.RIGHT)
		carbohydratesLabel.setText(ResourceBundleStorage.getResourceBundle("RecipeWindow").getString("carbohydrates"))
		fd = new FormData(CONSTNUMBER300, SWT.DEFAULT)
		fd.top = new FormAttachment(calorienLabel, CONSTNUMBER20, 0)
		fd.left = new FormAttachment(0, CONSTNUMBER10)
		fd.right = new FormAttachment(CONSTNUMBER16, 0)
		carbohydratesLabel.setLayoutData(fd)
		val proteinLabel:Label = new Label(composite, SWT.RIGHT)
		proteinLabel.setText(ResourceBundleStorage.getResourceBundle("RecipeWindow").getString("protein"))
		fd = new FormData(CONSTNUMBER300, SWT.DEFAULT)
		fd.top = new FormAttachment(carbohydratesLabel, CONSTNUMBER20, 0)
		fd.left = new FormAttachment(0,CONSTNUMBER10)
		fd.right = new FormAttachment(CONSTNUMBER16, 0)
		proteinLabel.setLayoutData(fd)
		nameText = new Text(composite, SWT.BORDER | SWT.SINGLE)
		fd = new FormData(CONSTNUMBER200, SWT.DEFAULT)
		fd.top = new FormAttachment(nameLabel, 0, SWT.CENTER)
		fd.left = new FormAttachment(nameLabel, CONSTNUMBER10)
		nameText.setLayoutData(fd)
		personamountText = new Text(composite, SWT.BORDER | SWT.SINGLE)
		fd = new FormData(CONSTNUMBER200, SWT.DEFAULT)
		fd.top = new FormAttachment(personamountLabel, 0, SWT.CENTER)
		fd.left = new FormAttachment(personamountLabel, CONSTNUMBER10)
		personamountText.setLayoutData(fd)
		difficultText = new Text(composite, SWT.BORDER | SWT.SINGLE)
		fd = new FormData(CONSTNUMBER200, SWT.DEFAULT)
		fd.top = new FormAttachment(difficultLabel, 0, SWT.CENTER)
		fd.left = new FormAttachment(difficultLabel, CONSTNUMBER10)
		difficultText.setLayoutData(fd)
		categoryText = new Text(composite, SWT.BORDER | SWT.SINGLE)
		fd = new FormData(CONSTNUMBER200, SWT.DEFAULT)
		fd.top = new FormAttachment(categoryLabel, 0, SWT.CENTER)
		fd.left = new FormAttachment(categoryLabel, CONSTNUMBER10)
		categoryText.setLayoutData(fd)
		timeText = new Text(composite, SWT.BORDER | SWT.SINGLE)
		fd = new FormData(CONSTNUMBER200, SWT.DEFAULT)
		fd.top = new FormAttachment(timeLabel, 0, SWT.CENTER)
		fd.left = new FormAttachment(timeLabel, CONSTNUMBER10)
		timeText.setLayoutData(fd)
		fatText = new Text(composite, SWT.BORDER | SWT.SINGLE)
		fd = new FormData(CONSTNUMBER200, SWT.DEFAULT)
		fd.top = new FormAttachment(fatLabel, 0, SWT.CENTER)
		fd.left = new FormAttachment(fatLabel, CONSTNUMBER10)
		fatText.setLayoutData(fd)
		calorienText = new Text(composite, SWT.BORDER | SWT.SINGLE)
		fd = new FormData(CONSTNUMBER200, SWT.DEFAULT)
		fd.top = new FormAttachment(calorienLabel, 0, SWT.CENTER)
		fd.left = new FormAttachment(calorienLabel, CONSTNUMBER10)
		calorienText.setLayoutData(fd)
		carbohydratesText = new Text(composite, SWT.BORDER | SWT.SINGLE)
		fd = new FormData(CONSTNUMBER200, SWT.DEFAULT)
		fd.top = new FormAttachment(carbohydratesLabel, 0, SWT.CENTER)
		fd.left = new FormAttachment(carbohydratesLabel, CONSTNUMBER10)
		carbohydratesText.setLayoutData(fd)
		proteinText = new Text(composite, SWT.BORDER | SWT.SINGLE)
		fd = new FormData(CONSTNUMBER200, SWT.DEFAULT)
		fd.top = new FormAttachment(proteinLabel, 0, SWT.CENTER)
		fd.left = new FormAttachment(proteinLabel, CONSTNUMBER10)
		proteinText.setLayoutData(fd)
		pictureLabel = new Label(composite, SWT.CENTER | SWT.BORDER)
		fd = new FormData(CONSTNUMBER300, SWT.DEFAULT)
		fd.top = new FormAttachment(nameLabel, CONSTNUMBER20, 0)
		fd.left = new FormAttachment(nameLabel, CONSTNUMBER300, 0)
		pictureLabel.setLayoutData(fd)
		var logo:Image=null
		try {
			logo = new Image(display, this.getClass.getResource("img/nopic.png").openStream())
			pictureLabel.setImage(logo)
			val addPictureButton:Button = new Button(composite, SWT.PUSH)
			addPictureButton.setText(ResourceBundleStorage.getResourceBundle("RecipeWindow").getString("addPic"))
			addPictureButton.addSelectionListener(addPictureButtonListener)
			fd = new FormData(CONSTNUMBER300, SWT.DEFAULT)
			fd.top = new FormAttachment(pictureLabel, CONSTNUMBER18, 0)
			fd.right = new FormAttachment(pictureLabel, CONSTNUMBER302, 0)
			addPictureButton.setLayoutData(fd)
		} catch{
			case e:IOException => 			SWTLogger.writeerror(e.getMessage)
		}
	}
	/**
	 * This method fills the recipe.
	 * @param display the display to display.
	 * @param recipe the recipe to fill items in.
	 */
	def fillRecipe(display:Display, recipe:Recipe) {
		recipe.picture= List[ByteImage]()
		//recipe.picture:+ new ByteImage(pictureLabel.getImage)
		if (recipe.ingredients.isEmpty) {
			throw new RecipeWrongInputException("wrongIngAmount")
		}
		if (categoryText.getText().length()==0) {
			throw new RecipeWrongInputException("wrongCategory")
		}
		recipe.category= categoryText.getText().replace("<", "&lt").replace(">", "&gt")
		if (difficultText.getText().length()==0) {
			throw new RecipeWrongInputException("wrongDifficult")
		}
		recipe.difficulty= difficultText.getText().replace("<", "&lt").replace(">", "&gt")
		if (nameText.getText().length()==0) {
			throw new RecipeWrongInputException("wrongName")
		}
		recipe.name= nameText.getText().replace("<", "&lt").replace(">", "&gt")
		if (timeText.getText().length()==0) {
			throw new RecipeWrongInputException("wrongTime")
		}
		recipe.time= timeText.getText().replace("<", "&lt").replace(">", "&gt")
		try {
			recipe.zombies= new Integer(personamountText.getText())
		} catch{
			case e:NumberFormatException =>			throw new RecipeWrongInputException("wrongPersonamount")
		}
		try {
			recipe.nutrition= new Nutrition(Math.abs(calorienText.getText().toDouble),
        Math.abs(fatText.getText().toDouble),
        Math.abs(carbohydratesText.getText().toDouble),
        Math.abs(proteinText.getText().toDouble))
		} catch {case e:NumberFormatException =>
			if(calorienText.getText().length() != 0 || fatText.getText().length() != 0
					|| carbohydratesText.getText().length() != 0
					|| proteinText.getText().length() != 0) {
				throw new RecipeWrongInputException("wrongNutrition")
			}
			recipe.nutrition.setCalories(-1.0)
			recipe.nutrition.setFat(-1.0)
			recipe.nutrition.setCarbohydrates(-1.0)
			recipe.nutrition.setProtein(-1.0)
		}
	}
}
