package de.hsrm.cs.kochplattformweb.client.gui

import java.util.regex.Pattern

import de.hsrm.cs.kochplattformweb.db.EnumAmountType.EnumAmountType
import de.hsrm.cs.kochplattformweb.db.{EnumAmountType, Ingredient, Recipe}
import org.eclipse.swt.SWT
import org.eclipse.swt.events.SelectionAdapter
import org.eclipse.swt.events.SelectionEvent
import org.eclipse.swt.widgets.{Button, Composite, Display, Label, List, Shell, TabFolder, Text}

/**
 * This Class is to display the Ingredient Composite.
 * @author alex */
object RecipeWindowIng {
	/**
		* Composite to display.
		*/
	var composite: Composite=new Composite(new Shell(),SWT.BORDER)

	var display:Display=new Display()
	/** Height of the CreateButton. **/
	val DESC_H = 22
	/** Width of the CreateButton. **/
	val DESC_W = 766
	/** X-Position of the CreateButton. **/
	val DESC_X = 22
	/** Y-Position of the CreateButton. **/
	val DESC_Y = 22
	/** Height of the CreateButton. **/
	val CHOOSEN_H = 20
	/** Width of the CreateButton. **/
	val CHOOSEN_W = 250
	/** X-Position of the CreateButton. **/
	val CHOOSEN_X = 22
	/** Y-Position of the CreateButton. **/
	val CHOOSEN_Y = 60
	/** Height of the CreateButton. **/
	val AMOUNT_H = 20
	/** Width of the CreateButton. **/
	val AMOUNT_W = 130
	/** X-Position of the CreateButton. **/
	val AMOUNT_X = 280
	/** Y-Position of the CreateButton. **/
	val AMOUNT_Y = 60
	/** Height of the CreateButton. **/
	val CHOOSE_H = 20
	/** Width of the CreateButton. **/
	val CHOOSE_W = 250
	/** X-Position of the CreateButton. **/
	val CHOOSE_X = 524
	/** Y-Position of the CreateButton. **/
	val CHOOSE_Y = 60
	/** Height of the CreateButton. **/
	val RIGHTBUTTON_H = 35
	/** Width of the CreateButton. **/
	val RIGHTBUTTON_W = 80
	/** X-Position of the CreateButton. **/
	val RIGHTBUTTON_X = 430
	/** Y-Position of the CreateButton. **/
	val RIGHTBUTTON_Y = 300
	/** Height of the CreateButton. **/
	val LEFTBUTTON_H = 35
	/** Width of the CreateButton. **/
	val LEFTBUTTON_W = 80
	/** X-Position of the CreateButton. **/
	val LEFTBUTTON_X = 430
	/** Y-Position of the CreateButton. **/
	val LEFTBUTTON_Y = 345
	/** Height of the CreateButton. **/
	val INGREDIENTBUTTON_H = 28
	/** Width of the CreateButton. **/
	val INGREDIENTBUTTON_W = 250
	/** X-Position of the CreateButton. **/
	val INGREDIENTBUTTON_X = 22
	/** Y-Position of the CreateButton. **/
	val INGREDIENTBUTTON_Y = 80
	/** Height of the CreateButton. **/
	val ADDINGREDIENTBUTTON_H = 28
	/** Width of the CreateButton. **/
	val ADDINGREDIENTBUTTON_W = 250
	/** X-Position of the CreateButton. **/
	val ADDINGREDIENTBUTTON_X = 22
	/** Y-Position of the CreateButton. **/
	val ADDINGREDIENTBUTTON_Y = 115
	/** Height of the CreateButton. **/
	val INGREDIENTLEFTLIST_H = 470
	/** Width of the CreateButton. **/
	val INGREDIENTLEFTLIST_W = 250
	/** X-Position of the CreateButton. **/
	val INGREDIENTLEFTLIST_X = 22
	/** Y-Position of the CreateButton. **/
	val INGREDIENTLEFTLIST_Y = 150
	/** Height of the CreateButton. **/
	val AMOUNTTEXT_H = 28
	/** Width of the CreateButton. **/
	val AMOUNTTEXT_W = 130
	/** X-Position of the CreateButton. **/
	val AMOUNTTEXT_X = 280
	/** Y-Position of the CreateButton. **/
	val AMOUNTTEXT_Y = 80
	/** Height of the CreateButton. **/
	val INGREDIENTRIGHTLIST_H = 540
	/** Width of the CreateButton. **/
	val INGREDIENTRIGHTLIST_W = 250
	/** X-Position of the CreateButton. **/
	val INGREDIENTRIGHTLIST_X = 524
	/** Y-Position of the CreateButton. **/
	val INGREDIENTRIGHTLIST_Y = 80
	/** Height of the CreateButton. **/
	val ENUMAMOUNTLIST_H = 505
	/** Width of the CreateButton. **/
	val ENUMAMOUNTLIST_W = 130
	/** X-Position of the CreateButton. **/
	val ENUMAMOUNTLIST_X = 280
	/** Y-Position of the CreateButton. **/
	val ENUMAMOUNTLIST_Y = 115

  /** List which has all choosen ingredients.	 */
  var ingredientchoose: scala.List[Ingredient]=scala.List[Ingredient]()

	val leftButton:Button = new Button(composite, SWT.PUSH)
	leftButton.setBounds(LEFTBUTTON_X, LEFTBUTTON_Y, LEFTBUTTON_W,
		LEFTBUTTON_H)

	val amountText:Text = new Text(composite, SWT.BORDER)
	amountText.setBounds(AMOUNTTEXT_X, AMOUNTTEXT_Y,
		AMOUNTTEXT_W, AMOUNTTEXT_H)

	val ingredientRightList:org.eclipse.swt.widgets.List = new org.eclipse.swt.widgets.List(composite, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL)
	/**
	 * addIngredientientButton Listener, when you click on the button.
	 */


  val ingredientText:Text=new Text(composite, SWT.BORDER)
  ingredientText.setBounds(INGREDIENTBUTTON_X, INGREDIENTBUTTON_Y,
    INGREDIENTBUTTON_W, INGREDIENTBUTTON_H)

  val addingredientButton:Button = new Button(composite, SWT.PUSH | SWT.CENTER)
  addingredientButton.setBounds(ADDINGREDIENTBUTTON_X, ADDINGREDIENTBUTTON_Y,
    ADDINGREDIENTBUTTON_W, ADDINGREDIENTBUTTON_H)

	val ingredientLeftList:org.eclipse.swt.widgets.List = new org.eclipse.swt.widgets.List(composite, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL)

	/**
	 * Returns the specifig composite.
	 * @param folder Tabfolder.
	 * @param window Parent window.
	 * @param display Display to display.
	 * @param ingredientlist list with all ingredients in the database.
	 * @return a Composite with the gui.
	 * @see
	 * @since
	 */
	def getControl(folder:TabFolder,
			window:Shell , display:Display,
			 ingredientlist:scala.List[Ingredient]):Composite={
		if (composite == null || composite.isDisposed) {
			buildTabGui(folder, window, display, ingredientlist)
		}
		composite
	}
	/**
	 * Fills both lists new and sort it.
	 * @param intlist2 Ingredientlist on the right side.
	 * @param ingredientLeftList nameList on the left side.
	 * @param ingredientRightList nameList on the right side.
	 * @return
	 */
	def fillList(intlist2:scala.List[Ingredient], ingredientLeftList:org.eclipse.swt.widgets.List, ingredientRightList:org.eclipse.swt.widgets.List) {
		var intlist=intlist2.sorted
		ingredientLeftList.removeAll()
		ingredientRightList.removeAll()
		ingredientchoose=ingredientchoose.sorted
		for (i:Ingredient<-intlist) {
			ingredientLeftList.add(i.name)
		}
		for (i:Ingredient<-ingredientchoose) {
			ingredientRightList.add(i.amount + " " +
					GUIHelper.enumAmountToString(i.amountType.toString) + " " + i.name)
		}
	}
	/**
	 * Method to build the composite.
	 * @param folder tabfolder.
	 * @param window specific window
	 * @param display display to display
	 * @param ingredientlist the ingredientlist from db.
	 * @return
	 * @see
	 * @since
	 */
	def buildTabGui(folder:TabFolder, window:Shell, display:Display, ingredientlist:scala.List[Ingredient]) {
		composite = new Composite(folder, SWT.BORDER)
		ingredientchoose = scala.List[Ingredient]()
		composite.setSize(folder.getSize)
		val ingdescLabel:Label = new Label(composite, SWT.NONE)
		ingdescLabel.setText(ResourceBundleStorage.getResourceBundle("RecipeWindow").getString("ingdesc"))
		ingdescLabel.setSize(DESC_W, DESC_H)
		ingdescLabel.setLocation(DESC_X, DESC_Y)
		val choosenLabel:Label = new Label(composite, SWT.NONE)
		choosenLabel.setText(ResourceBundleStorage.getResourceBundle("RecipeWindow").getString("chooseIngredient"))
		choosenLabel.setBounds(CHOOSEN_X, CHOOSEN_Y, CHOOSEN_W,
				CHOOSEN_H)
		val amountLabel:Label = new Label(composite, SWT.NONE)
		amountLabel.setText(ResourceBundleStorage.getResourceBundle("RecipeWindow").getString("amount"))
		amountLabel.setBounds(AMOUNT_X, AMOUNT_Y, AMOUNT_W,
				AMOUNT_H)
		val chooseLabel:Label = new Label(composite, SWT.NONE)
		chooseLabel.setText(ResourceBundleStorage.getResourceBundle("RecipeWindow").getString("choose"))
		chooseLabel.setBounds(CHOOSE_X, CHOOSE_Y, CHOOSE_W,
				CHOOSE_H)
		val rightButton:Button = new Button(composite, SWT.PUSH)
		rightButton.setBounds(RIGHTBUTTON_X, RIGHTBUTTON_Y, RIGHTBUTTON_W,
				RIGHTBUTTON_H)
		rightButton.setText(">>")

		leftButton.setText("<<")

		addingredientButton.setText(ResourceBundleStorage.getResourceBundle("RecipeWindow").getString("addIngredient"))

		ingredientLeftList.setBounds(INGREDIENTLEFTLIST_X, INGREDIENTLEFTLIST_Y,
				INGREDIENTLEFTLIST_W, INGREDIENTLEFTLIST_H)
		for (i:Ingredient<-ingredientlist) {
			ingredientLeftList.add(i.name, ingredientlist.indexOf(i))
		}


		amountText.setText("1")
		ingredientRightList.setBounds(INGREDIENTRIGHTLIST_X, INGREDIENTRIGHTLIST_Y,
				INGREDIENTRIGHTLIST_W, INGREDIENTRIGHTLIST_H)
		val enumamounttypeList:org.eclipse.swt.widgets.List = new org.eclipse.swt.widgets.List(composite, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL)
		enumamounttypeList.setBounds(ENUMAMOUNTLIST_X, ENUMAMOUNTLIST_Y,
				ENUMAMOUNTLIST_W, ENUMAMOUNTLIST_H)
    var intlist: scala.List[Ingredient] = scala.List[Ingredient]()
		for (i:Ingredient<-ingredientlist) {
			intlist:+(i)
		}
		val addingredientButtonListener = new SelectionAdapter() {
			@Override
			override def widgetSelected(arg0:SelectionEvent) {
				if ("".equals(ingredientText.getText().toUpperCase())) {
					new Messagewindow("wrongIng", display).openwindow()
					return
				}
				if (Pattern.matches("[0-9]", ingredientText.getText().toUpperCase().substring(0, 1))) {
					new Messagewindow("wrongIng", display).openwindow()
					return
				}
				for (i:Ingredient<- intlist) {
					if (i.name.toUpperCase().equals(ingredientText.getText().toUpperCase())) {
						new Messagewindow("doubleIng", display).openwindow()
						return
					}
				}
				for (i:Ingredient<- ingredientchoose) {
					if (i.name.toUpperCase().equals(ingredientText.getText().toUpperCase())) {
						new Messagewindow("doubleIng", display).openwindow()
						return
					}
				}
				val ingre:Ingredient = new Ingredient( -1.0,ingredientText.getText(), EnumAmountType.NULL.toString)
				intlist:+ ingre
				ingredientLeftList.removeAll
        intlist=intlist.sorted
				for (i:Ingredient<-intlist) {
					ingredientLeftList.add(i.name)
				}
			}
		}
		addingredientButton.addSelectionListener(addingredientButtonListener)
		val ingredientLeftListListener = new SelectionAdapter() {
			@Override
			override def widgetSelected(arg0:SelectionEvent) {
				enumamounttypeList.removeAll()
				if (ingredientLeftList.getSelectionIndex != -1) {
					/*for (i:EnumAmountType<-EnumAmountType.withName(intlist(ingredientLeftList.getSelectionIndex).amountType)) {
						enumamounttypeList.add(GUIHelper.enumAmountToString(i.toString))
					}*/
				}
			}
		}
		ingredientLeftList.addSelectionListener(ingredientLeftListListener)
		val rightButtonListener = new SelectionAdapter() {
			@Override
			override def widgetSelected(arg0:SelectionEvent) {
				if (ingredientLeftList.getSelectionIndex != -1
						&& enumamounttypeList.getSelectionIndex != -1) {
					try {
						ingredientchoose:+ new Ingredient(intlist(ingredientLeftList.getSelectionIndex).name.replace("<", "&lt").replace(">", "&gt").toDouble,
              Math.abs(amountText.getText().toDouble).toString,null)
              //EnumAmountType.withName(intlist(ingredientLeftList.getSelectionIndex).amountType)(enumamounttypeList.getSelectionIndex))
						intlist.patch(ingredientLeftList.getSelectionIndex,Nil,1)
					} catch {
						case e:NumberFormatException => new Messagewindow("wrongAmount", display).openwindow()
					}
				}
				fillList(intlist, ingredientLeftList, ingredientRightList)
			}
		}
		rightButton.addSelectionListener(rightButtonListener)
		val leftButtonListener = new SelectionAdapter() {
			@Override
			override def widgetSelected(arg0:SelectionEvent) {
				if (ingredientRightList.getSelectionIndex != -1) {
					intlist:+ ingredientchoose(ingredientRightList.getSelectionIndex)
					ingredientchoose.patch(ingredientRightList.getSelectionIndex,Nil,1)
					ingredientRightList.remove(ingredientRightList.getSelectionIndex)
				}
				fillList(intlist, ingredientLeftList, ingredientRightList)
			}
		}
		leftButton.addSelectionListener(leftButtonListener)
	}

	/**
	 * Fills the Recipe with gui components.
	 * @see de.hsrm.cs.kochplattform.gui.FillOutRecipe#createRecipe(de.hsrm.cs.kochplattform.db.Recipe)
	 * @param recipe to fill.
	 */
	def fillRecipe(recipe:Recipe) {
		recipe.ingredients= scala.List[Ingredient]()
    //recipe.ingredients=recipe.ingredients+:ingredientchoose
	}
}
