package de.hsrm.cs.kochplattformweb.client.gui

import java.io.IOException
import java.net.URI
import java.util.ResourceBundle

import de.hsrm.cs.kochplattformweb.client.Client
import de.hsrm.cs.kochplattformweb.db.EnumAmountType.EnumAmountType
import de.hsrm.cs.kochplattformweb.db.{EnumAmountType, _}
import de.hsrm.cs.kochplattformweb.messages.{ChangeRecipeMessage, ConfirmReport, ErrorReport, Message}
import de.hsrm.cs.kochplattformweb.utils.SWTLogger
import org.eclipse.swt.SWT
import org.eclipse.swt.SWTException
import org.eclipse.swt.custom.ScrolledComposite
import org.eclipse.swt.events.MouseAdapter
import org.eclipse.swt.events.MouseEvent
import org.eclipse.swt.events.SelectionAdapter
import org.eclipse.swt.events.SelectionEvent
import org.eclipse.swt.events.SelectionListener
import org.eclipse.swt.graphics.Image
import org.eclipse.swt.layout.FormAttachment
import org.eclipse.swt.layout.FormData
import org.eclipse.swt.layout.FormLayout
import org.eclipse.swt.widgets.Button
import org.eclipse.swt.widgets.Combo
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.FileDialog
import org.eclipse.swt.widgets.Label
import org.eclipse.swt.widgets.Table
import org.eclipse.swt.widgets.TableColumn
import org.eclipse.swt.widgets.TableItem
import org.eclipse.swt.widgets.Text


/**
 * This class contains the Composite with the 
 * @author Daniel Torpus
 */
class Changetab(controller:Controller, display:Display) {


	/** Width of the TABLE_COLUMS_WIDTH{@value} . **/
	val TABLE_COLUMS_WIDTH = 190
	/** Width of the columns of the Mainviewtable {@value} . **/
	val TABLE_COL_WIDTH = 70

	/** Constant to for spacing SPACE5 {@value} . **/
	val SPACE5 = 5
	/** Constant to for spacing SPACE10 {@value} . **/
	val SPACE10 = 10
	/** Constant to for spacing SPACE15 {@value} . **/
	val SPACE15 = 15
	/** Constant to for spacing SPACE15 {@value} . **/
	val SPACE20 = 20
	/** Constant to for spacing SPACE200 {@value} . **/
	val SPACE30 = 30
	/** Constant to for spacing the SPACE50 {@value} . **/
	val SPACE50 = 50
	/** Constant to for spacing the SPACE60 {@value} . **/
	val SPACE60 = 60
	/** Constant to for spacing the SPACE80 {@value} . **/
	val SPACE80 = 80
	/** Constant to for spacing SPACE100 {@value} . **/
	val SPACE100 = 100
	/** Constant to for spacing SPACE150 {@value} . **/
	val SPACE150 = 150
	/** Constant to for spacing the SPACE160 {@value} . **/
	val SPACE160 = 160
	/** Constant to for spacing the SPACE180 {@value} . **/
	val SPACE180 = 180
	/** Constant to for spacing SPACE200 {@value} . **/
	val SPACE200 = 200
	/** Constant to for spacing the SPACE214 {@value} . **/
	val SPACE214 = 214
	/** Constant to for spacing the SPACE250 {@value} . **/
	val SPACE250 = 250
	/** Constant to for spacing the SPACE330 {@value} . **/
	val SPACE330 = 330
	/** Constant to for spacing the SPACE475 {@value} . **/
	val SPACE475 = 475
	/**Width of the Left Labels in this Composite.**/
	val LEFT_FIELDS = 130


	/** This field is for adding Ingredients to the Searchlist. **/
	var addbutton: Button=null
	/** Selection Adapter for the add button. */
	var addbuttonselect: SelectionAdapter=null
	/** Selection Listener for the changebox Combo. */
	val changeboxselect:SelectionListener=null
	/**
		* This field is the Composite that contains the Widgets for changing
		* Recipes.
		**/
	var changecomp: Composite=null
	/** This field is for submitting the changed Recipeinformation . **/
	var changerecipe: Button=null
	/** Selection Adapter for the changerecipe Button. */
	var changerecselect:SelectionAdapter=null
	/**
		* This field is the Table that contains the Ingredientlist that is used for
		* searching Recipes.
		**/
	var ingredientstable: Table=null
	/** Table urltable to see the url-list. **/
	var urltable: Table=null
	/** Map for the uri table. */
	var urlmap: scala.collection.immutable.Map[String, URI]=null
	/** Selection Adapter for the Ingredienttable. */
	var ingtablesel: SelectionAdapter=null
	/** This field is a Label for the Picture of the Selected Recipe. **/
	var logolabel: Label=null
	/** This field is for choosing a new Picture. **/
	var picturebutton: Button=null
	/** This field is for changing of the recipecategory. **/
	var recipecategorych: Text=null
	/** This field is for changing the Recipedifficulty. **/
	var recipedifficulty: Text=null
	/** This field is for changing the Ingredientname. **/
	var recipeingchange: Text=null
	/** This field is for changing of the recipename. **/
	var recipenamech: Text=null
	/** This field is for changing of the recipetime. **/
	var recipetimech: Text=null
	/** This field is for changing the Ingredientamount. **/
	var recipeingamountch: Text=null
	/** This field is for changing the Nutrition-Calorie-Amount. **/
	var recipenutritioncalch: Text=null
  /** This field is for changing the Nutrition-Carbohydrate-Amount. **/
  var recipenutritioncarch: Text=null
  /** This field is for changing the Nutrition-Fat-Amount. **/
  var recipenutritionfatch: Text=null
	/** This field is for changing the Nutrition-Protein-Amount. **/
	var recipenutritionprotch: Text=null
	/** This field is for changing the Recipepreperation. **/
	var recipepreparationch: Text=null
	/** This field is for changing the Recipepersamount. **/
	var recipepersamount: Text=null
	/** This field is for changing the Recipeimage. **/
	//private transient Text recipeimagech
	/** This field is for changing the Recipevideo. **/
	var recipevideoch:Text=null
	/** This field is for removing Ingredients from the Searchlist. **/
	var removebutton: Button=null
	/** This field is for removing Ingredients from the Searchlist. **/
	var addURL: Button=null
  /** addurlbuttonselect. */
  var addurlbuttonselect: SelectionAdapter=null
	/** deleteurlbuttonselect. */
	var deleteurlbuttonselect:SelectionAdapter=null
	/** This field is for removing Ingredients from the Searchlist. **/
	var deleteURL: Button=null
	/** Resource Bundle for this class. */
	val resource:ResourceBundle=ResourceBundleStorage.getResourceBundle(
		ResourceBundleStorage.TABS)
	/** Selection Adapter for the remove button. */
	var removebuttonselect: SelectionAdapter=null
	/** Selection Adapter for the update button. */
	var updatebuttonselect: SelectionAdapter=null
	/**SelectionListener for the URL table.*/
	var urltabselect:MouseAdapter=null
	/** This field is for storing the returned Messages of Request. **/
	var msg:Message=null

	var client: Client=null
	/**
		* This field contains an Object of the Messagewindowclass for Displaying
		* Messages.
		**/
	var msgwindow: Messagewindow=null
	/** Window for this Class. **/

  /** This field is an Instance of the Class Controller. **/
  var recipe: Recipe=null
  /**
    * This field is the Composite that contains the Widgets for changing
    * Recipes.
    **/
  var scrollchangecomp: ScrolledComposite=null
	/**
		* This field is a Combo Box for displaying the Ingredients of a single.
		* Recipe
		**/
	var textamounttype: Combo=null
	/** This field is for updating the Ingredientlist. **/
	var updatebutton: Button=null
	/** Text field is for the raiting. */
	var ratingtextbox: Text=null
	/** Button for adding a rating to the selected recipe. */
	var addRatingButton: Button=null
	/** SelectionAdapter for the rating button. */
	var addRatingButtonselect: SelectionAdapter=null


	this.buildGUI()


	/**
	 * Build up the GUI.
	 */
	def buildGUI(){
		this.scrollchangecomp = new ScrolledComposite(Mainwindow.getTabfolder(), SWT.BORDER
				| SWT.V_SCROLL | SWT.H_SCROLL)

		this.changecomp = new Composite(this.scrollchangecomp,SWT.LEFT)
		this.changecomp.setSize(Mainwindow.getTabfolder().getSize())
		this.changecomp.setLayout(new FormLayout())

		this.msgwindow = new Messagewindow("Statusmeldung", this.display)

/*-----------------Recipename---------------*/
		val labename:Label = new Label(this.changecomp,SWT.LEFT)
		labename.setText("* "+this.resource.getString("name") + ":")
		var formd: FormData = new FormData(LEFT_FIELDS, SWT.DEFAULT)
		formd.top = new FormAttachment(SPACE5, 0)
		formd.left = new FormAttachment(0, SPACE10)
		formd.right = new FormAttachment(SPACE15, 0)
		labename.setLayoutData(formd)

		this.recipenamech = new Text(this.changecomp, SWT.LEFT | SWT.BORDER | SWT.SINGLE)
		formd = new FormData(SPACE200, SWT.DEFAULT)
		formd.top = new FormAttachment(labename,SPACE5, SWT.CENTER)
		formd.left = new FormAttachment(labename, SPACE10)
		this.recipenamech.setLayoutData(formd)

/*-----------------Peopleamount---------------*/

		val labpeopleamount:Label = new Label(this.changecomp,SWT.LEFT)
		labpeopleamount.setText("* "+this.resource.getString("numberofpeople") + ":")
		formd = new FormData(LEFT_FIELDS, SWT.DEFAULT)
		formd.top = new FormAttachment(labename, SPACE20, 0)
		formd.left = new FormAttachment(0, SPACE10)
		formd.right = new FormAttachment(SPACE15, 0)
		labpeopleamount.setLayoutData(formd)

		this.recipepersamount = new Text(this.changecomp, SWT.BORDER | SWT.SINGLE)
		formd = new FormData(SPACE200, SWT.DEFAULT)
		formd.top = new FormAttachment(labpeopleamount, 0, SWT.CENTER)
		formd.left = new FormAttachment(labpeopleamount, SPACE10)
		this.recipepersamount.setLayoutData(formd)

/*-----------------Difficulty---------------*/

		val labdifficulty:Label = new Label(this.changecomp,SWT.LEFT)
		labdifficulty.setText("  "+this.resource.getString("difficulty") + ":")
		formd = new FormData(LEFT_FIELDS, SWT.DEFAULT)
		formd.top = new FormAttachment(labpeopleamount,SPACE20, 0)
		formd.left = new FormAttachment(0, SPACE10)
		formd.right = new FormAttachment(SPACE15, 0)
		labdifficulty.setLayoutData(formd)

		this.recipedifficulty = new Text(this.changecomp, SWT.BORDER | SWT.SINGLE)
		formd = new FormData(SPACE200, SWT.DEFAULT)
		formd.top = new FormAttachment(labdifficulty, 0, SWT.CENTER)
		formd.left = new FormAttachment(labdifficulty, SPACE10)
		this.recipedifficulty.setLayoutData(formd)

/*-----------------Category----------------*/

		val labcategory:Label = new Label(this.changecomp,SWT.LEFT)
		labcategory.setText("* "+this.resource.getString("category") + ":")
		formd = new FormData(LEFT_FIELDS, SWT.DEFAULT)
		formd.top = new FormAttachment(labdifficulty, SPACE20, 0)
		formd.left = new FormAttachment(0, SPACE10)
		formd.right = new FormAttachment(SPACE15, 0)
		labcategory.setLayoutData(formd)

		this.recipecategorych = new Text(this.changecomp, SWT.BORDER | SWT.SINGLE)
		formd = new FormData(SPACE200, SWT.DEFAULT)
		formd.top = new FormAttachment(labcategory, 0, SWT.CENTER)
		formd.left = new FormAttachment(labcategory, SPACE10)
		this.recipecategorych.setLayoutData(formd)

/*-----------------Preparationtime--------------*/

		val labpreparationtime:Label = new Label(this.changecomp, SWT.LEFT)
		labpreparationtime.setText("* "+this.resource.getString("preparationtime") + ":")
		formd = new FormData(LEFT_FIELDS, SWT.DEFAULT)
		formd.top = new FormAttachment(labcategory, SPACE20, 0)
		formd.left = new FormAttachment(0, SPACE10)
		formd.right = new FormAttachment(SPACE15, 0)
		labpreparationtime.setLayoutData(formd)

		this.recipetimech = new Text(this.changecomp, SWT.BORDER | SWT.SINGLE)
		formd = new FormData(SPACE200, SWT.DEFAULT)
		formd.top = new FormAttachment(labpreparationtime, 0, SWT.CENTER)
		formd.left = new FormAttachment(labpreparationtime, SPACE10)
		this.recipetimech.setLayoutData(formd)

		/*-----------------Nutrition----------------*/

		val labfat:Label = new Label(this.changecomp, SWT.LEFT)
		labfat.setText("  "+this.resource.getString("fat") + ":")
		formd = new FormData(LEFT_FIELDS, SWT.DEFAULT)
		formd.top = new FormAttachment(labpreparationtime, SPACE20, 0)
		formd.left = new FormAttachment(0, SPACE10)
		formd.right = new FormAttachment(SPACE15, 0)
		labfat.setLayoutData(formd)

		this.recipenutritionfatch = new Text(this.changecomp, SWT.BORDER
				| SWT.SINGLE)
		formd = new FormData(SPACE200, SWT.DEFAULT)
		formd.top = new FormAttachment(labfat, 0, SWT.CENTER)
		formd.left = new FormAttachment(labfat, SPACE10)
		this.recipenutritionfatch.setLayoutData(formd)

		val labcalories:Label = new Label(this.changecomp, SWT.LEFT)
		labcalories.setText("  "+this.resource.getString("calories") + ":")
		formd = new FormData(LEFT_FIELDS, SWT.DEFAULT)
		formd.top = new FormAttachment(labfat, SPACE20, 0)
		formd.left = new FormAttachment(0, SPACE10)
		formd.right = new FormAttachment(SPACE15, 0)
		labcalories.setLayoutData(formd)

		this.recipenutritioncalch = new Text(this.changecomp, SWT.BORDER
				| SWT.SINGLE)
		formd = new FormData(SPACE200, SWT.DEFAULT)
		formd.top = new FormAttachment(labcalories, 0, SWT.CENTER)
		formd.left = new FormAttachment(labcalories, SPACE10)
		this.recipenutritioncalch.setLayoutData(formd)

		val labcarbohydrates:Label = new Label(this.changecomp, SWT.LEFT)
		labcarbohydrates.setText("  "+this.resource.getString("carbohydrate") + ":")
		formd = new FormData(LEFT_FIELDS, SWT.DEFAULT)
		formd.top = new FormAttachment(labcalories,SPACE20, 0)
		formd.left = new FormAttachment(0, SPACE10)
		formd.right = new FormAttachment(SPACE15, 0)
		labcarbohydrates.setLayoutData(formd)

		this.recipenutritioncarch = new Text(this.changecomp, SWT.BORDER
				| SWT.SINGLE)
		formd = new FormData(SPACE200, SWT.DEFAULT)
		formd.top = new FormAttachment(labcarbohydrates, 0, SWT.CENTER)
		formd.left = new FormAttachment(labcarbohydrates, SPACE10)
		this.recipenutritioncarch.setLayoutData(formd)

		val labprotein:Label = new Label(this.changecomp, SWT.LEFT)
		labprotein.setText("  "+this.resource.getString("protein") + ":")
		formd = new FormData(LEFT_FIELDS, SWT.DEFAULT)
		formd.top = new FormAttachment(labcarbohydrates, SPACE20, 0)
		formd.left = new FormAttachment(0, SPACE10)
		formd.right = new FormAttachment(SPACE15, 0)
		labprotein.setLayoutData(formd)

		this.recipenutritionprotch = new Text(this.changecomp, SWT.BORDER
				| SWT.SINGLE)
		formd = new FormData(SPACE200, SWT.DEFAULT)
		formd.top = new FormAttachment(labprotein, 0, SWT.CENTER)
		formd.left = new FormAttachment(labprotein, SPACE10)
		this.recipenutritionprotch.setLayoutData(formd)

/*-----------------Ingredient--------------*/

		val labingredient:Label = new Label(this.changecomp, SWT.LEFT)
		labingredient.setText("* "+this.resource.getString("ingredient"))
		formd = new FormData(LEFT_FIELDS, SWT.DEFAULT)
		formd.top = new FormAttachment(labprotein, SPACE30, 0)
		formd.left = new FormAttachment(0, SPACE10)
		formd.right = new FormAttachment(SPACE15, 0)
		labingredient.setLayoutData(formd)

		this.recipeingchange = new Text(this.changecomp, SWT.BORDER | SWT.SINGLE)
		formd = new FormData(SPACE200, SWT.DEFAULT)
		formd.top = new FormAttachment(labingredient, 0, SWT.CENTER)
		formd.left = new FormAttachment(labingredient, SPACE10,SWT.RIGHT)
		this.recipeingchange.setLayoutData(formd)

/*-----------------Amount---------------*/
		val labingamount:Label = new Label(this.changecomp, SWT.LEFT)
		labingamount.setText("* "+this.resource.getString("amount") + ":")
		formd = new FormData(LEFT_FIELDS, SWT.DEFAULT)
		formd.top = new FormAttachment(labingredient, SPACE20, 0)
		formd.left = new FormAttachment(0, SPACE10)
		formd.right = new FormAttachment(SPACE15, 0)
		labingamount.setLayoutData(formd)

		this.recipeingamountch = new Text(this.changecomp, SWT.BORDER
				| SWT.SINGLE)
		formd = new FormData(SPACE200, SWT.DEFAULT)
		formd.top = new FormAttachment(labingamount, 0, SWT.CENTER)
		formd.left = new FormAttachment(labingamount, SPACE10,SWT.RIGHT)
		this.recipeingamountch.setLayoutData(formd)

/*-----------------Amounttyp---------------*/

		val labingamounttype:Label = new Label(this.changecomp,SWT.LEFT)
		labingamounttype.setText("* "+this.resource.getString("amounttype") + ":")
		formd = new FormData(LEFT_FIELDS, SWT.DEFAULT)
		formd.top = new FormAttachment(labingamount, SPACE20, 0)
		formd.left = new FormAttachment(0, SPACE10)
		formd.right = new FormAttachment(SPACE15, 0)
		labingamounttype.setLayoutData(formd)
		this.textamounttype = new Combo(this.changecomp, SWT.BORDER | SWT.SINGLE | SWT.READ_ONLY)
		formd = new FormData(SPACE214, SWT.DEFAULT)
		formd.top = new FormAttachment(labingamounttype, 0, SWT.CENTER)
		formd.left = new FormAttachment(labingamounttype, SPACE10,SWT.RIGHT)
		this.textamounttype.setVisibleItemCount(SPACE100)
		this.textamounttype.setLayoutData(formd)

/*-----------------Ingredientstable----------------*/

		this.ingredientstable = new Table(this.changecomp, SWT.MULTI
				| SWT.BORDER | SWT.FULL_SELECTION)
		this.ingredientstable.setHeaderVisible(true)
		this.ingredientstable.setLinesVisible(true)

		val tableColumn:TableColumn = new TableColumn(this.ingredientstable,
				SWT.BORDER |SWT.VIRTUAL)
		tableColumn.setWidth(TABLE_COLUMS_WIDTH)
		tableColumn.setText(this.resource.getString("name"))
		val tableColumn1:TableColumn = new TableColumn(this.ingredientstable,
				SWT.VIRTUAL)
		tableColumn1.setWidth(TABLE_COL_WIDTH)
		tableColumn1.setText(this.resource.getString("amount"))
		val tableColumn2:TableColumn = new TableColumn(this.ingredientstable,
				SWT.VIRTUAL)
		tableColumn2.setWidth(TABLE_COL_WIDTH)
		tableColumn2.setText(this.resource.getString("amounttype"))

		formd = new FormData(SPACE330, SPACE100)
		formd.top = new FormAttachment(labingamounttype, SPACE30,SWT.TOP)
		formd.left = new FormAttachment(labingamounttype, 0, SWT.LEFT)
		this.ingredientstable.setLayoutData(formd)

/*-----------------Buttons ADD REMOVE UPDATE----------------*/
		this.addbutton = new Button(this.changecomp, SWT.PUSH)
		this.addbutton.setText(this.resource.getString("add"))
		formd = new FormData(SPACE100, SWT.DEFAULT)
		formd.top = new FormAttachment(this.ingredientstable, SPACE10, SWT.BOTTOM)
		formd.left = new FormAttachment(this.ingredientstable, 0, SWT.LEFT)
		this.addbutton.setLayoutData(formd)

		this.updatebutton = new Button(this.changecomp, SWT.PUSH)
		this.updatebutton.setText(this.resource.getString("update"))
		formd = new FormData(SPACE150, SWT.DEFAULT)
		formd.top = new FormAttachment(this.addbutton, 0, SWT.TOP)
		formd.left = new FormAttachment(this.addbutton, SPACE10, SWT.RIGHT)
		this.updatebutton.setLayoutData(formd)

		this.removebutton = new Button(this.changecomp, SWT.PUSH)
		this.removebutton.setText(this.resource.getString("remove"))
		formd = new FormData(SPACE100, SWT.DEFAULT)
		formd.top = new FormAttachment(this.updatebutton, 0, SWT.TOP)
		formd.left = new FormAttachment(this.updatebutton, SPACE10, SWT.RIGHT)
		this.removebutton.setLayoutData(formd)

/*-----------------Picture----------------*/

		val labpicture:Label = new Label(this.changecomp, SWT.LEFT)
		labpicture.setText("  "+this.resource.getString("picture") + ":")
		formd = new FormData(SPACE60, SWT.DEFAULT)
		formd.top = new FormAttachment(SPACE5, 0)
		formd.left = new FormAttachment(this.recipenamech, SPACE50)
		labpicture.setLayoutData(formd)

		this.logolabel = new Label(this.changecomp, SWT.CENTER | SWT.BORDER)
		formd = new FormData(SWT.DEFAULT, SWT.DEFAULT)
		formd.top = new FormAttachment(labpicture, 0,SWT.TOP)
		formd.left = new FormAttachment(labpicture, SPACE10)
		this.logolabel.setLayoutData(formd)

		this.picturebutton = new Button(this.changecomp, SWT.BORDER |SWT.PUSH)
		this.picturebutton.setText(this.resource.getString("open"))
		formd = new FormData(SPACE60, SWT.DEFAULT)
		formd.top = new FormAttachment(labpicture,0)
		formd.left = new FormAttachment(this.recipepersamount, SPACE50)
		this.picturebutton.setLayoutData(formd)

/*-----------------Submit Button----------------*/
		this.changerecipe = new Button(this.changecomp, SWT.BORDER |SWT.PUSH)
		this.changerecipe.setText(this.resource.getString("submit"))
		formd = new FormData(SPACE100, SWT.DEFAULT)
		formd.top = new FormAttachment(this.logolabel,  0 ,SWT.TOP)
		formd.left = new FormAttachment(this.logolabel,SPACE20)
		this.changerecipe.setLayoutData(formd)

/*-----------------url----------------*/
		this.urltable = new Table(this.changecomp, SWT.MULTI
				|SWT.V_SCROLL | SWT.H_SCROLL
				| SWT.BORDER | SWT.FULL_SELECTION)
		this.urltable.setHeaderVisible(true)
		this.urltable.setLinesVisible(true)
		val urlname:TableColumn = new TableColumn(this.urltable,
				SWT.BORDER |SWT.VIRTUAL)
		urlname.setWidth(SPACE180)
		urlname.setText(this.resource.getString("urltableentry"))
		formd = new FormData(SPACE200, SPACE100)
		formd.top = new FormAttachment(this.changerecipe, SPACE20 ,SWT.BOTTOM)
		formd.left = new FormAttachment(this.logolabel,SPACE20)
		this.urltable.setLayoutData(formd)

/*-----------------Delete and Add URI Button----------------*/
		this.addURL = new Button(this.changecomp, SWT.MULTI)
		this.addURL.setText(this.resource.getString("addURL"))
		formd = new FormData(SPACE100, SWT.DEFAULT)
		formd.top = new FormAttachment(this.urltable,  SPACE10 ,SWT.BOTTOM)
		formd.left = new FormAttachment(this.logolabel, SPACE20)
		this.addURL.setLayoutData(formd)

		this.deleteURL = new Button(this.changecomp, SWT.MULTI)
		this.deleteURL.setText(this.resource.getString("deleteURL"))
		formd = new FormData(SPACE100, SWT.DEFAULT)
		formd.top = new FormAttachment(this.addURL, 0,SWT.TOP)
		formd.left = new FormAttachment(this.addURL, SPACE5,SWT.RIGHT)
		this.deleteURL.setLayoutData(formd)

/*-----------------Preparation----------------*/
		val labpreparation:Label = new Label(this.changecomp, SWT.LEFT)
		labpreparation.setText("* "+this.resource.getString("preparation") + ":")
		formd = new FormData(SPACE100, SWT.DEFAULT)
		formd.top = new FormAttachment(this.recipenutritioncalch, 0,SWT.TOP)
		formd.left = new FormAttachment(this.recipenutritioncalch, SPACE20)
		labpreparation.setLayoutData(formd)

		this.recipepreparationch = new Text(this.changecomp, SWT.MULTI
				| SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER)
		formd = new FormData(SPACE475, SPACE160)
		formd.top = new FormAttachment(labpreparation, 0,SWT.TOP)
		formd.left = new FormAttachment(labpreparation, 0)
		this.recipepreparationch.setLayoutData(formd)

/*-----------------Rating----------------*/
		val labreciperating:Label = new Label(this.changecomp, SWT.LEFT)
		labreciperating .setText("* "+this.resource.getString("Rating") + ":")
		formd = new FormData(SPACE80, SWT.DEFAULT)
		formd.top = new FormAttachment(this.textamounttype, 0,SWT.TOP)
		formd.left = new FormAttachment(this.textamounttype, SPACE20)
		labreciperating .setLayoutData(formd)

		this.ratingtextbox = new Text(this.changecomp, SWT.MULTI
				| SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER)
		formd = new FormData(SPACE475, SPACE160)
		formd.top = new FormAttachment(labreciperating, 0,SWT.TOP)
		formd.left = new FormAttachment(labreciperating, SPACE15)
		this.ratingtextbox.setLayoutData(formd)
		this.ratingtextbox.setEditable(false)

		this.addRatingButton = new Button(this.changecomp, SWT.BORDER |SWT.PUSH)
		this.addRatingButton.setText(this.resource.getString("addRating"))
		formd = new FormData(SPACE100, SWT.DEFAULT)
		formd.top = new FormAttachment(labreciperating,0)
		formd.left = new FormAttachment(this.ingredientstable, SPACE20)
		this.addRatingButton.setLayoutData(formd)

		this.buttonlisteners()
		Mainwindow.gettabFolderItem3().setControl(this.scrollchangecomp)

		this.scrollchangecomp.setContent(this.changecomp)
		this.changecomp.setSize(this.changecomp.computeSize(SWT.DEFAULT, SWT.DEFAULT))

	}
	/**Converts the Amountypes from Ingredienttable to EnumAmounttypes.
	 * @return enumat
	 * @param index **/
	def translateAmounttype(index:Int):EnumAmountType={
		val enumat:EnumAmountType=EnumAmountType.NULL
		var count: Int = 0
		val enumlist:List[EnumAmountType] = List[EnumAmountType]()

		/*for (i:EnumAmountType<-EnumAmountType.withName(EnumAmountType.NULL.toString)){
			enumlist:+(i)
		}*/
		while(!this.ingredientstable.getItem(index).getText(2)
				.equals(this.textamounttype.getItem(count))){
			count+=1
		}
		enumlist(count)
	}

	/**This Method contains the Listener of this Class.**/
	def buttonlisteners() {
		this.ingtablesel = new SelectionAdapter() {
			@Override
			override def widgetSelected(sevent:SelectionEvent) {
				val sel:Int = Changetab.this.ingredientstable
						.getSelectionIndex
				if (sel != -1) {
					Changetab.this.recipeingchange
							.setText(Changetab.this.
									ingredientstable.getItem(
									sel).getText(0))
					Changetab.this.recipeingamountch
							.setText(Changetab.this.
									ingredientstable.getItem(
									sel).getText(1))
					Changetab.this.textamounttype
							.setText(Changetab.this.
									ingredientstable.getItem(
									sel).getText(2))
				}
			}
		}
		this.ingredientstable.addSelectionListener(this.ingtablesel)

		this.changerecselect = new SelectionAdapter() {
			@Override
			override def widgetSelected(sevent:SelectionEvent) {
				if (Changetab.this.msgwindow.getShell.isDisposed) {
					Changetab.this.msgwindow = new Messagewindow(
							"Statusmeldung", Changetab.this.display)
				}
				if (Changetab.this.ingredientstable.getItemCount > 0){
					if(Changetab.this.checkallFields()){

						val templist:List[Ingredient]  = List[Ingredient]()
						val tempimagelist:List[ByteImage] = List[ByteImage]()
						//tempimagelist:+ new ByteImage(Changetab.this.logolabel.getImage)


						for (i <- 0 until Changetab.this.ingredientstable.getItemCount) {
							val tempingr:Ingredient = Ingredient(
								Changetab.this.ingredientstable.getItem(i).getText(0).toDouble,
									Changetab.this.ingredientstable.getItem(i).getText(1)
									,Changetab.this.translateAmounttype(i).toString)
									/*EnumAmountType.valueOf(this.ingredientstable.
											getItem(i).getText(2))*/
							templist:+ tempingr

						}
						Changetab.this.msg = Changetab.this.client.changeRecipe(ChangeRecipeMessage(
								new Recipe(
									Changetab.this.recipecategorych.getText(),
                  templist,
                  Changetab.this.recipenamech.getText(),
                  Changetab.this.recipe.recipeid,
                  Changetab.this.recipe.rating,
									Changetab.this.recipetimech.getText(),

												Changetab
														.this.recipepersamount
														.getText().toInt)))

						/**
							* new Nutrition(
												Changetab.this.recipenutritioncalch.getText.toDouble,
												Changetab
														.this.recipenutritionfatch
														.getText.toDouble,
												Changetab
														.this.recipenutritioncarch
														.getText.toDouble,
												Changetab
														.this.recipenutritionprotch
														.getText.toDouble),
												tempimagelist,
									Changetab.this.urlmap,
									Changetab.this.recipepreparationch
														.getText(),"en", Changetab
														.this.recipedifficulty.getText(),
							*/
						Changetab.this.msgwindow.openwindow()
						if (Changetab.this.msg.isInstanceOf[ConfirmReport]) {
							Changetab.this.msgwindow
									.setMessage(Changetab.this.msg.asInstanceOf[ConfirmReport])
						} else if (Changetab.this.msg.isInstanceOf[ErrorReport]) {
							Changetab.this.msgwindow
							.setMessage(Changetab.this.msg.asInstanceOf[ErrorReport])
						}
					}else{
						Changetab.this.msgwindow
						.setMessage("ChangeTab_CheckAllFlields", true)
						Changetab.this.msgwindow.openwindow()
					}
				}else{
					Changetab.this.msgwindow
					.setMessage("ChangeTab_emptyList", true)
					Changetab.this.msgwindow.openwindow()
				}
			}
		}
		this.changerecipe.addSelectionListener(this.changerecselect)

		this.addurlbuttonselect = new SelectionAdapter() {
			@Override
			override def widgetSelected(env:SelectionEvent) {
				if (Changetab.this.msgwindow.getShell.isDisposed) {
					Changetab.this.msgwindow = new Messagewindow(
							"Statusmeldung",Changetab.this.display)
				}
				if (Changetab.this.ingredientstable.getItemCount > 0){
					new AddMovieWindow(Changetab.this.display, Changetab.this.recipe.recipeid).openwindow()
				}else{
					Changetab.this.msgwindow
					.setMessage("ChangeTab_emptyList", true)
					Changetab.this.msgwindow.openwindow()
				}
			}
		}
		this.addURL.addSelectionListener(this.addurlbuttonselect)
		this.deleteurlbuttonselect = new SelectionAdapter() {
			@Override
			override def widgetSelected(env:SelectionEvent) {
				if (Changetab.this.msgwindow.getShell.isDisposed) {
					Changetab.this.msgwindow = new Messagewindow(
							"Statusmeldung",Changetab.this.display)
				}
				if (Changetab.this.ingredientstable.getItemCount > 0){
					if(Changetab.this.urltable.getSelectionIndex!=(-1)){
						val temp:String = Changetab.this.urltable.getSelection.toString
						val iter:Iterator[String] = Changetab.this.recipe.videos.keySet.iterator
						val itertemp:String=null
						Changetab.this.urltable.remove(
							Changetab.this.urltable.getSelectionIndex)
						Changetab.this.recipe.videos.keySet.filter(i=> i.equals(temp))
						/*for (itertemp:String <- Changetab.this.recipe.videos.keySet){
							if(temp.equals(itertemp)){
								iter.remove()
							}
						}*/
					}else{
						Changetab.this.msgwindow
						.setMessage("SelctaURL", true)
						Changetab.this.msgwindow.openwindow()
					}
				}else{
					Changetab.this.msgwindow
					.setMessage("ChangeTab_emptyList", true)
					Changetab.this.msgwindow.openwindow()
				}
			}
		}
		this.deleteURL.addSelectionListener(this.deleteurlbuttonselect)

		this.addRatingButtonselect = new SelectionAdapter() {
			@Override
			override def widgetSelected(arg0:SelectionEvent) {
				if (Changetab.this.msgwindow.getShell.isDisposed) {
					Changetab.this.msgwindow = new Messagewindow(
							"Statusmeldung",Changetab.this.display)
				}
				if (Changetab.this.ingredientstable.getItemCount > 0){
					val rate:RatingWindow  = new RatingWindow(Changetab.this.client
							,Changetab.this.recipe.recipeid
							,Languagewindow.getDisplay)
					rate.getWindow.open()
				}else{
					Changetab.this.msgwindow
					.setMessage("ChangeTab_emptyList", true)
					Changetab.this.msgwindow.openwindow()
				}
			}
		}
		this.addRatingButton.addSelectionListener(this.addRatingButtonselect)

		this.addbuttonselect = new SelectionAdapter() {
			@Override
			override def widgetSelected(sevent:SelectionEvent) {
        var item5: TableItem=null
				if (Changetab.this.msgwindow.getShell.isDisposed) {
					Changetab.this.msgwindow = new Messagewindow(
							"Statusmeldung",Changetab.this.display)
				}
				if (Changetab.this.ingredientstable.getItemCount > 0){
					if(Changetab.this.checkIngrediant()){
						item5 = new TableItem(Changetab.this.ingredientstable, SWT.NONE)
						item5.setText(0, Changetab.this.recipeingchange.getText())
						item5.setText(1, Changetab.this.recipeingamountch.getText())
						item5.setText(2, Changetab.this.textamounttype.getText)
					}
					else{
						Changetab.this.msgwindow
						.setMessage("ChangeTab_CheckAllIngrediants", true)
						Changetab.this.msgwindow.openwindow()
					}
				}else {
					Changetab.this.msgwindow
					.setMessage("ChangeTab_emptyList", true)
					Changetab.this.msgwindow.openwindow()
				}
			}
		}
		this.addbutton.addSelectionListener(this.addbuttonselect)

		this.updatebuttonselect = new SelectionAdapter() {
			@Override
			override def widgetSelected(sevent:SelectionEvent) {
				val sel = Changetab.this.ingredientstable
				.getSelectionIndex
				if (Changetab.this.msgwindow.getShell.isDisposed) {
					Changetab.this.msgwindow = new Messagewindow(
							"Statusmeldung",Changetab.this.display)
				}
				if (Changetab.this.ingredientstable.getItemCount > 0){
					if(sel!=(-1)){
						if(Changetab.this.checkIngrediant()){
							val tempname:String = Changetab.this.ingredientstable.getItem(sel).getText(0)
							var indi = 0
							val item5:TableItem = new TableItem(
								Changetab.this.ingredientstable, SWT.NONE)
							while (indi < Changetab.this.ingredientstable.getItemCount
									&& (!Changetab.this.ingredientstable
											.getItem(indi)
											.getText(0)
											.equals(tempname))) {
								indi+=1
							}
							Changetab.this.ingredientstable.remove(indi)
							item5.setText(0, Changetab.this.recipeingchange
											.getText())
							item5.setText(1, Changetab.this
											.recipeingamountch
											.getText())
							item5.setText(2, Changetab.this.textamounttype.getText)
						}
						else{
							Changetab.this.msgwindow
							.setMessage("ChangeTab_NeedAllIngrediants", true)
							Changetab.this.msgwindow.openwindow()
						}
					}
					else{
						Changetab.this.msgwindow.setMessage(
								"NoIngredientcheck", true)
						Changetab.this.msgwindow.openwindow()
					}
				}else{
					Changetab.this.msgwindow
						.setMessage("ChangeTab_emptyList", true)
					Changetab.this.msgwindow.openwindow()
				}
			}
		}
		this.updatebutton.addSelectionListener(this.updatebuttonselect)

		this.removebuttonselect = new SelectionAdapter() {
			@Override
			override def widgetSelected(sevent:SelectionEvent) {
				val sel = Changetab.this.ingredientstable
						.getSelectionIndex
				if (Changetab.this.msgwindow.getShell.isDisposed) {
					Changetab.this.msgwindow = new Messagewindow(
							"Statusmeldung",Changetab.this.display)
				}
				if (Changetab.this.ingredientstable.getItemCount > 0){
					if(sel==(-1)){
						Changetab.this.msgwindow
						.setMessage("noingselected", true)
						Changetab.this.msgwindow.openwindow()
					}
					else{
						Changetab.this.ingredientstable.remove(sel)
					}
				}else{
					Changetab.this.msgwindow
					.setMessage("ChangeTab_emptyList", true)
					Changetab.this.msgwindow.openwindow()
				}
			}
		}
		this.removebutton.addSelectionListener(this.removebuttonselect)

		var logo: Image=null
		try {
			logo = new Image(this.display, this.getClass.getResource(
					"img/nopic.png").openStream())
			this.logolabel.setImage(logo)
			this.picturebutton.addSelectionListener(
					new org.eclipse.swt.events.SelectionAdapter() {
						@Override
						override def widgetSelected(arg0:SelectionEvent) {
							if (Changetab.this.msgwindow.getShell.isDisposed) {
								Changetab.this.msgwindow = new Messagewindow(
										"Statusmeldung",Changetab.this.display)
							}
							if (Changetab.this.ingredientstable.getItemCount > 0){
								val dialog:FileDialog = new FileDialog(
									Changetab.this.changecomp.getParent
												.getShell, SWT.OPEN)
								val filterExt:Array[String] = Array[String]("*.png", "*.jpg", "*.jpeg","*.gif" )
								dialog.setFilterExtensions(filterExt)
								val path:String = dialog.open()
								var img: Image = null
								if (path != null) {
									try {
										img = new Image(Changetab.this.display, path)
										img.getImageData.scaledTo(SPACE250, SPACE200)
										img = new Image(Changetab.this.display,img.getImageData
												.scaledTo(SPACE250, SPACE200))
										Changetab.this.logolabel.setImage(img)
									} catch {
										case event:SWTException =>
										new Messagewindow("bild",Changetab.this.display)
												.openwindow()
									}
								}
							}else{
								Changetab.this.msgwindow
								.setMessage("ChangeTab_emptyList", true)
								Changetab.this.msgwindow.openwindow()
							}
						}
					})

			this.urltabselect=new MouseAdapter() {
				@Override
				override def mouseDoubleClick(mevent:MouseEvent){
					if (Changetab.this.msgwindow.getShell.isDisposed) {
						Changetab.this.msgwindow = new Messagewindow(
								"Statusmeldung",Changetab.this.display)
					}
					if(Changetab.this.urltable.getSelectionIndex==(-1)){
						Changetab.this.msgwindow
						.setMessage("SelctaURL", true)
						Changetab.this.msgwindow.openwindow()
					}else{
						try {
							/*java.awt.Desktop.getDesktop.browse(
								Changetab.this.urlmap.get(
									Changetab.this.urltable.getSelection()(0)
											 .getText(0))(0))*/
						} catch {case  ioe:IOException =>
							SWTLogger.writeerror(ioe.getMessage)
						}

					}

				}
			}
			this.urltable.addMouseListener(this.urltabselect)
		}catch{
      case e:IOException => 			SWTLogger.writeerror(e.getMessage)
    }
	}

/**Checkt recipeingchange, recipeingamountch, textamounttyp.
 * @return true by  all filled and false by one isn't filled*/
	def checkIngrediant():Boolean={
		if("".equals(this.recipeingchange.getText())
				|| "".equals(this.recipeingamountch.getText())
				|| "".equals(this.textamounttype.getText)){
			return false
		}
		true
	}

/**Checkt if recipeingchange, recipeingamountch, textamounttype is filled.
 * @return true by  all filled and false by one isn't filled*/
	def checkallFields():Boolean={
		if("".equals(this.recipenamech.getText())
				|| "".equals(this.recipepersamount.getText())
				|| "".equals(this.recipecategorych.getText())
				|| "".equals(this.recipedifficulty.getText())
				|| "".equals(this.recipepreparationch.getText())
				|| "".equals(this.recipetimech.getText())){
			return false
		}
		 true
	}

		/**
	 * This Method shows Detail of a Recipe.
	 * @param recipes Recipe that is supposed to be displayed in detail.
	 * @param clients This is an Instance of the Class Client
	 * **/
	def filldetailtab(recipes:Recipe,clients:Client) {
		this.recipe = recipes
		this.client = clients

		this.recipecategorych.setText(this.recipe.category)
		this.recipenamech.setText(this.recipe.name)
		this.recipetimech.setText(this.recipe.time)

		this.recipepersamount.setText(this.recipe.zombies.toString)
		this.recipedifficulty.setText(this.recipe.difficulty)
		this.urltable.removeAll()
		this.ingredientstable.removeAll()

		for (ingredient:Ingredient <- this.recipe.ingredients) {
			val item5:TableItem = new TableItem(
					this.ingredientstable, SWT.NONE)
			item5.setText(0, ingredient.name
					.toString)
			item5.setText(1, ingredient.amount.toString)
			item5.setText(2, ResourceBundleStorage.getResourceBundle("EnumAmountType")
					.getString(ingredient.amountType.toString))
		}
		if(this.recipe.picture.nonEmpty){
			//this.logolabel.setImage(this.recipe.picture(0).toImage)
		}
		else{
			try {
				this.logolabel.setImage(new Image(this.display,
						this.getClass.getResource("img/nopic.png").openStream()))
			} catch {
        case e:IOException => SWTLogger.writeerror(e.getMessage)
			}
		}
		this.urlmap=this.recipe.videos
		if(this.recipe.videos.nonEmpty){
			var urlvid: TableItem=null
			this.urltable.setEnabled(true)
			this.urltable.removeAll()
			this.urlmap=this.recipe.videos
			for (url:String <- this.recipe.videos.keySet){
				urlvid = new TableItem(this.urltable, SWT.NONE)
				urlvid.setText(0, url.toString)
			}
		}else{
			this.urltable.setEnabled(false)
		}

		for (i:EnumAmountType<-EnumAmountType.values){
			this.textamounttype.add(GUIHelper.enumAmountToString(i.toString))
		}

		this.recipenutritioncalch.setText(this.recipe.nutrition.getCalories.toString)
		this.recipenutritionfatch.setText(this.recipe.nutrition.getFat
				.toString)
		this.recipenutritioncarch.setText(this.recipe.nutrition
				.getCarbohydrates.toString)
		this.recipenutritionprotch.setText(this.recipe.nutrition.getProtein
				.toString)
		this.recipepreparationch.setText(this.recipe.preparation)


		SWTLogger.writeinfo("Ratings: " + this.recipe.ratings.size)
		val buffer:StringBuffer= new StringBuffer(500)

		buffer.append(this.resource.getString("Avg"))
		buffer.append(": ")
		if(this.recipe.rating==(-1)){
			buffer.append(this.resource.getString("notrated"))
		}
		else{
			buffer.append(this.recipe.rating)
		}
		buffer.append("\n\n")


		for(rate:Rating <-this.recipe.ratings){
			buffer.append(this.resource.getString("Rating"))
			buffer.append(": ")
			buffer.append(rate.rating)
			buffer.append('\n')
			buffer.append(rate.comment)
			buffer.append("\n\n")
		}
		this.ratingtextbox.setText(buffer.toString)
	}



	/**
	 * Gets the SelectionAdapters of this class.
	 * @return a list of SelectionAdapters.
	 */
	def getSelecttionListener= {
		this.changeboxselect
	}

	/**
	 * Gets the SelectionAdapters of this class.
	 * @return the SelectionAdapters.
	 */
	def getSelecttionAdapter= {
		this.changerecselect
	}

	/**
	 * This method returns the SelectionListener changeboxselect.
	 * @return the changeboxselect
	 */
	def getChangeBoxSelect= {
		 this.changeboxselect
	}

	/**
	 * This method returns the SelectionAdapter changerecipeselect.
	 * @return the changerecipeselect
	 */
	def getChangeRecipeSelect= {
		 this.changerecselect
	}

	/**
	 * This method returns the Text recipenamech.
	 * @return the recipenamech
	 */
	def getRecipenamech= {
		 this.recipenamech
	}

	/**
	 * This method returns the Text recipecategorych.
	 * @return the recipecategorych
	 */
	def getRecipecategorych()= {
		 this.recipecategorych
	}

	/**
	 * This method returns the Text recipetimech.
	 * @return the recipetimech
	 */
	def getRecipetimech= {
		 this.recipetimech
	}

	/**
	 * This method returns the Text recipeingredientsch.
	 * @return the recipeingredientsch
	 */
	def getRecipeingredientsch= {
		 this.recipeingchange
	}

	/**
	 * This method returns the Text recipeingredientsamch.
	 * @return the recipeingredientsamch
	 */
	def getRecipeingredientsamch= {
		 this.recipeingamountch
	}

	/**
	 * This method returns the Text recipenutritioncalch.
	 * @return the recipenutritioncalch
	 */
	def getRecipenutritioncalch= {
		 this.recipenutritioncalch
	}

	/**
	 * This method returns the Text recipenutritionfatch.
	 * @return the recipenutritionfatch
	 */
	def getRecipenutritionfatch= {
		 this.recipenutritionfatch
	}

	/**
	 * This method returns the Text recipenutritioncarch.
	 * @return the recipenutritioncarch
	 */
	def getRecipenutritioncarch= {
		 this.recipenutritioncarch
	}

	/**
	 * This method returns the Text recipenutritionprotch.
	 * @return the recipenutritionprotch
	 */
	def getRecipenutritionprotch= {
		 this.recipenutritionprotch
	}

	/**
	 * This method returns the Text recipevideoch.
	 * @return the recipevideoch
	 */
	def getRecipevideoch= {
		 this.recipevideoch
	}


	/**
	 * This method returns the Composite changecomp.
	 * @return the changecomp
	 */
	def getChangecomp= {
		 this.changecomp
	}

	/**
	 * This method returns the Button changerecipe.
	 * @return the changerecipe
	 */
	def getChangerecipe= {
		this.changerecipe
	}
	/**
	 * This method returns the Button changerecipe.
	 * @return the changerecipe
	 */
	def getRatingText= {
		 this.ratingtextbox
	}
	/**
	 * This method returns the Button changerecipe.
	 * @return the changerecipe
	 */
	def getURLTable= {
		this.urltable
	}
	/**
	 * This method returns the Button changerecipe.
	 * @return the changerecipe
	 */
	def getURLmap= {
		 this.urlmap
	}
}
