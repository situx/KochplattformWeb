package de.hsrm.cs.kochplattformweb.client.gui


import java.util.ResourceBundle

import de.hsrm.cs.kochplattformweb.client.Client
import de.hsrm.cs.kochplattformweb.db.{Ingredient, RecipeEntry}
import de.hsrm.cs.kochplattformweb.messages._
import org.eclipse.swt.SWT
import org.eclipse.swt.events.{SelectionAdapter, SelectionEvent}
import org.eclipse.swt.graphics.Rectangle
import org.eclipse.swt.widgets.Button
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.Label
import org.eclipse.swt.widgets.Table
import org.eclipse.swt.widgets.TableColumn
import org.eclipse.swt.widgets.TableItem
import org.eclipse.swt.widgets.Text


/**
 * This class contains the Composite with the 
 * @author Daniel Torpus
 */
class Searchtab(client:Client,mainviewtab:Mainviewtab,display:Display) {

	/**A constant Number {@value}.*/
	val CONSTNUMBER20=20
	/**A constant Number {@value}.*/
	val CONSTNUMBER22= 22
	/**A constant Number {@value}.*/
	val CONSTNUMBER28= 28
	/**A constant Number {@value}.*/
	val CONSTNUMBER35= 35
	/**A constant Number {@value}.*/
	val CONSTNUMBER60= 60
	/**A constant Number {@value}.*/
	val CONSTNUMBER80= 80
	/**A constant Number {@value}.*/
	val CONSTNUMBER115= 115
	/**A constant Number {@value}.*/
	val CONSTNUMBER155= 155
	/**A constant Number {@value}.*/
	val CONSTNUMBER190= 190
	/**A constant Number {@value}.*/
	val CONSTNUMBER225= 225
	/**A constant Number {@value}.*/
	val CONSTNUMBER250= 250
	/**A constant Number {@value}.*/
	val CONSTNUMBER260= 260
	/**A constant Number {@value}.*/
	val CONSTNUMBER290= 290
	/**A constant Number {@value}.*/
	val CONSTNUMBER300= 300
	/**A constant Number {@value}.*/
	val CONSTNUMBER325= 325
	/**A constant Number {@value}.*/
	val CONSTNUMBER345= 345
	/**A constant Number {@value}.*/
	val CONSTNUMBER380= 380
	/**A constant Number {@value}.*/
	val CONSTNUMBER672= 672
	/**A constant Number {@value}.*/
	val CONSTNUMBER766= 766

	/** Width of the columns of the Ingredienttable{@value}. **/
	val TABLE2_COL_WIDTH = 60
	/** Height of the Ingredienttable{@value}. **/
	val TABLE1_HEIGHT = 520
	/** Width of the Ingredienttable2{@value}. **/
	val TABLE2_HEIGHT = 520
	/** Width of the Ingredienttable{@value}. **/
	val TABLE1_WIDTH = 250
	/** Width of the Ingredienttable2{@value}. **/
	val TABLE2_WIDTH = 250
	/** X-Position of the Ingredienttable{@value}. **/
	val TABLE1_X = 22
	/** X-Position of the Ingredienttable2{@value}. **/
	val TABLE2_X = 380
	/** Y-Position of the Ingredienttable{@value}. **/
	val TABLE1_Y = 80
	/** Y-Position of the Ingredienttable2{@value}. **/
	val TABLE2_Y = 80
	/** This field is for adding Ingredients to the Searchlist. **/
	var addbutton: Button=new Button(this.searchcomp, SWT.PUSH)
	/**Selection Adapter for the add button.*/
	val addbuttonselect:SelectionAdapter=new SelectionAdapter() {
		@Override
		override def widgetSelected(arg0:SelectionEvent) {
			if (Searchtab.this.ingredientstable.getSelectionIndex != -1) {
				Searchtab.this.ingredientlist2:+ Searchtab.this.
					ingredientlist(Searchtab.this.
						ingredientstable.
						getSelectionIndex).
					replace("<", "&lt").
					replace(">", "&gt")
				Searchtab.this.ingredientlist.patch(Searchtab.this.ingredientstable.getSelectionIndex,Nil,1)
			}
			Searchtab.this.ingredientstable.removeAll()
			Searchtab.this.ingredientstable2.removeAll()
			Searchtab.this.ingredientlist=Searchtab.this.ingredientlist.sorted
			Searchtab.this.ingredientlist2=Searchtab.this.ingredientlist2.sorted
			for (ing:String <- Searchtab.this.ingredientlist) {
				val item6:TableItem = new TableItem(
					Searchtab.this.ingredientstable,
					SWT.NONE)
				item6.setText(ing)
			}
			for (ing:String <- Searchtab.this.ingredientlist2) {
				val item7:TableItem = new TableItem(
					Searchtab.this.ingredientstable2,
					SWT.NONE)
				item7.setText(ing)
			}
		}
	}
	/** Textfield to add Ingredients. **/
	var ingredient: Text=null
	/** Ingredientlist to temp store the Ingredientstrings. **/
	var ingredientlist: scala.List[String] = scala.List[String]()
	/** Ingredientlist2 to temp store the Ingredientstrings. **/
	var ingredientlist2: scala.List[String] = scala.List[String]()
	/** This field is the Table that contains the
		* Ingredientlist that is used for searching Recipes. **/
	var ingredientstable: Table=null
	/** This field is the Table that contains the
		* Ingredientlist that is used for searching Recipes. **/
	var ingredientstable2: Table=null
	/** This field contains a IngredientMessage to search for Recipes. **/
	var ingrmess: IngredientMessage=null
//	/**This field is set of Ingredients that is recived by calling getallingredients Method.**/
//	private List<Ingredient> ingredientset
	/** This field is for storing the returned Messages of Request. **/
	var msg: Message=null
	/** This field is the Messagewindow, where Infomessages are displayed. **/
	var msgwindow: Messagewindow = new Messagewindow("Statusmeldung", display)
	/** This field is to search for Ingredients by name. **/
	var namesearchbutton: Button=new Button(this.searchcomp, SWT.PUSH | SWT.CENTER)
	/** Selection Adapter for the namesearchbutton. */
	var namesearchbuttonselect: SelectionAdapter=null
	/** This field is for removing Ingredients from the Searchlist. **/
	var removebutton: Button=null
	/**Selection Adapter for the remove button.*/
	val removebuttonselect:SelectionAdapter=new SelectionAdapter() {
		@Override
		override def widgetSelected(arg0:SelectionEvent) {
			if (Searchtab.this.ingredientstable2.getSelectionIndex != -1) {
				Searchtab.this.ingredientlist:+Searchtab.this.ingredientlist2(
					Searchtab.this.
						ingredientstable2.
						getSelectionIndex)
				Searchtab.this.ingredientlist2.patch(Searchtab.this.ingredientstable2.
					getSelectionIndex,Nil,1)
				Searchtab.this.ingredientstable2.remove(
					Searchtab.this.ingredientstable2.
						getSelectionIndex)
			}
			Searchtab.this.ingredientstable.removeAll()
			Searchtab.this.ingredientstable2.removeAll()
			Searchtab.this.ingredientlist=Searchtab.this.ingredientlist.sorted
			Searchtab.this.ingredientlist2=Searchtab.this.ingredientlist2.sorted
			for (ing:String <- Searchtab.this.ingredientlist) {
				val item3:TableItem = new TableItem(
					Searchtab.this.ingredientstable,
					SWT.NONE)
				item3.setText(ing)
			}
			for (ing:String <- Searchtab.this.ingredientlist2) {
				val item4:TableItem = new TableItem(
					Searchtab.this.ingredientstable2,
					SWT.NONE)
				item4.setText(ing)
			}
		}
	}
	/**Resourcebundle for this class.*/
	val resource:ResourceBundle=ResourceBundleStorage
    .getResourceBundle(ResourceBundleStorage.TABS)
	/** This field is for searching Recipes containing the Ingredients from the Searchlist. **/
	var searchbutton: Button=null
	/** Selection Adapter for the search button. */
	var searchbuttonselect: SelectionAdapter=new SelectionAdapter() {
		@Override
		override def widgetSelected(sevent:SelectionEvent) {
			mainviewtab.getMainviewTable.removeAll()
			var temp:List[RecipeEntry]=null
			if (Searchtab.this.msgwindow.getShell.isDisposed) {
				Searchtab.this.msgwindow = new Messagewindow(
					"Statusmeldung",display)
			}
			for(i<- 0 until Searchtab.this.ingredientstable2.
				getItemCount){
				Searchtab.this.ingrmess.addIngredient(
					new Ingredient(Searchtab.this.
						ingredientstable2.
						getItem(i).
						getText()))
			}

			Searchtab.this.ingrmess.requestType= true
			Searchtab.this.msg = Searchtab.this.client.sendIngredients(Searchtab.this.ingrmess)
			Searchtab.this.ingrmess = new IngredientMessage(true)
			if (Searchtab.this.msg.isInstanceOf[RecipeListMessage]) {
				temp = Searchtab.this.msg.asInstanceOf[RecipeListMessage].recipelist
				Mainwindow.fillTable(temp)
				Searchtab.this.msgwindow.setMessage(temp.size
					+ " Einträge gefunden",false)
				Searchtab.this.ingredientstable.removeAll()
				if(temp.nonEmpty){
					Mainwindow.getTabfolder().setSelection(1)
				}
			} else {
				if (Searchtab.this.msg.isInstanceOf[ErrorReport]) {
					Searchtab.this.msgwindow.
						setMessage(Searchtab.this.msg.asInstanceOf[ErrorReport])
				}
			}
			Searchtab.this.msgwindow.openwindow()
		}
	}
	/** This field is for searching Recipes containing at least
		* the Ingredients from the Searchlist plus several other Ingredients. **/
	var searchbutton2: Button=null
	/** Selection Adapter for the search2 button. */
	var searchbutton2select: SelectionAdapter= new SelectionAdapter() {
		@Override
		override def widgetSelected(sevent:SelectionEvent) {
			mainviewtab.getMainviewTable.removeAll
			var temp: List[RecipeEntry]=null
			if (Searchtab.this.msgwindow.getShell.isDisposed) {
				Searchtab.this.msgwindow = new Messagewindow(
					"Statusmeldung",display)
			}
			for(i<- 0 until Searchtab.this.ingredientstable2.getItemCount){
				Searchtab.this.ingrmess.addIngredient(
					new Ingredient(Searchtab.this.
						ingredientstable2.
						getItem(i).getText()))
			}
			Searchtab.this.ingrmess.requestType=(false)
			Searchtab.this.msg = Searchtab.this.client.sendIngredients(Searchtab.this.ingrmess)
			Searchtab.this.ingrmess = new IngredientMessage(false)
			if (Searchtab.this.msg.isInstanceOf[RecipeListMessage]) {
				temp = Searchtab.this.msg.asInstanceOf[RecipeListMessage].recipelist
				Mainwindow.fillTable(temp)
				Searchtab.this.msgwindow.setMessage(temp.size + " Einträge gefunden",false)
				if(temp.nonEmpty){
					Mainwindow.getTabfolder().setSelection(1)
				}
				Searchtab.this.ingredientstable.removeAll()
			} else {
				if (Searchtab.this.msg.isInstanceOf[ErrorReport]) {
					Searchtab.this.msgwindow.
						setMessage(Searchtab.this.msg.asInstanceOf[ErrorReport])
				}
			}
			Searchtab.this.msgwindow.openwindow()
		}
	}
	this.searchbutton2.addSelectionListener(this.searchbutton2select)

	/** This field is the Composite that contains the Widgets for searching Recipes. **/
	var searchcomp: Composite=null
	/** This field is for updating the  Ingredientlist. **/
	var updatebutton: Button=null
	/** Selection Adapter for the update button. */
	var updatebuttonselect:SelectionAdapter=new SelectionAdapter() {
	@Override
	override def widgetSelected(sevent:SelectionEvent) {
	mainviewtab.getMainviewTable.removeAll
	Searchtab.this.ingredientstable.removeAll()
	Searchtab.this.msg = Searchtab.this.client.getAllIngredients(
	ResourceBundleStorage.getLanguage.toString)

	if (Searchtab.this.msgwindow.getShell.isDisposed) {
	Searchtab.this.msgwindow = new Messagewindow(
	"Statusmeldung",display)
}

	if (Searchtab.this.msg.isInstanceOf[IngredientSetMessage]) {
	val tempset:Set[Ingredient] = Searchtab.this.msg.asInstanceOf[IngredientSetMessage].ingredientSet


	Searchtab.this.ingredientlist=List[String]()
	Searchtab.this.ingredientlist2=List[String]()
	Searchtab.this.ingredientstable2.removeAll()
	for (ing:Ingredient <- tempset) {
	val item5:TableItem = new TableItem(
	Searchtab.this.ingredientstable,
	SWT.NONE)
	item5.setText(ing.name)
	Searchtab.this.ingredientlist:+ ing.name

}
} else {
	if (Searchtab.this.msg.isInstanceOf[ErrorReport]) {
	Searchtab.this.msgwindow.
	setMessage(Searchtab.this.msg.asInstanceOf[ErrorReport])
	Searchtab.this.msgwindow.openwindow()
}
}
}}


	this.tabfoldercontent2(mainviewtab,display)




	/**
	 * This Method contains the Listeners for the Widgets,
	 * that are placed in the SearchTabfolder.
	 * @param mainviewtab This is a Instance of the Class Mainviewtab
	 * @param display This is a reference to the field Display in the Class Mainwindow
	 **/
	def searchlistener(mainviewtab:Mainviewtab,display:Display) {
		this.ingrmess = new IngredientMessage(true)

		this.namesearchbuttonselect = new SelectionAdapter() {
			@Override
			override def widgetSelected(arg0: SelectionEvent) {
				mainviewtab.getMainviewTable.removeAll()
				var temp: List[RecipeEntry] = null
				if (Searchtab.this.msgwindow.getShell.isDisposed) {
					Searchtab.this.msgwindow = new Messagewindow(
						"Statusmeldung", display)
				}
				Searchtab.this.msg = Searchtab.this.client.
					getRecipeByName(Searchtab.this.ingredient.getText())

				if (Searchtab.this.msg.isInstanceOf[RecipeListMessage]) {
					temp = Searchtab.this.msg.asInstanceOf[RecipeListMessage]
						.recipelist
					Mainwindow.fillTable(temp)
					Searchtab.this.msgwindow.setMessage(temp.size
						+ " Einträge gefunden", false)
					Searchtab.this.ingredient.setText("")
					if (temp.nonEmpty) {
						Mainwindow.getTabfolder().setSelection(1)
					}
				} else {
					if (Searchtab.this.msg.isInstanceOf[ErrorReport]) {
						Searchtab.this.msgwindow.
							setMessage(Searchtab.this.msg.asInstanceOf[ErrorReport])
					}
				}
				Searchtab.this.msgwindow.openwindow()
			}
		}
		this.namesearchbutton.addSelectionListener(this.namesearchbuttonselect)

		this.updatebutton.addSelectionListener(this.updatebuttonselect)

		this.removebutton.addSelectionListener(this.removebuttonselect)

		this.addbutton.addSelectionListener(this.addbuttonselect)



		this.searchbutton.addSelectionListener(this.searchbuttonselect)

	}
//	/**
//	 * This Method sets the Set of Ingredients of this Class and cleans the Tables.**/
//	def setIngredientlist(final Set<Ingredient> iset) {
//		this.ingredientstable.removeAll()
//		this.ingredientstable2.removeAll()
//		this.ingredientlist.clear()
//		this.ingredientlist.clear()
//		this.ingredientset = iset
//		for (Ingredient ing : iset) {
//			final TableItem item5 = new TableItem(
//			this.ingredientstable,SWT.NONE)
//			item5.setText(ing.getName())
//			this.ingredientlist.add(ing.getName())
//		}
//	}

	/**
	 * This Method is for preparing the content in the Tabfolder2(searching for
	 * Recipes).
	 * @param mainviewtab This is a Instance of the Class Mainviewtab
	 * @param display This is a reference to the field Display in the Class Mainwindow
	 * **/
	def tabfoldercontent2(mainviewtab:Mainviewtab,display:Display) {
		this.searchcomp = new Composite(Mainwindow.getTabfolder(), SWT.BORDER)
		val label:Label = new Label(this.searchcomp, SWT.NONE)
		label.setText(this.resource.getString("ingdesc"))
		label.setSize(CONSTNUMBER766, CONSTNUMBER22)
		label.setLocation(CONSTNUMBER22, CONSTNUMBER22)

		val label1:Label = new Label(this.searchcomp, SWT.NONE)
		label1.setText(this.resource.getString("recipe"))
		label1.setBounds(CONSTNUMBER672, CONSTNUMBER60
				,CONSTNUMBER250, CONSTNUMBER20)
		this.ingredient = new Text(this.searchcomp, SWT.BORDER)
		this.ingredient.setBounds(CONSTNUMBER672, CONSTNUMBER80
				,CONSTNUMBER250, CONSTNUMBER28)
		this.namesearchbutton = new Button(this.searchcomp, SWT.PUSH | SWT.CENTER)
		this.namesearchbutton.setBounds(CONSTNUMBER672, CONSTNUMBER115
				,CONSTNUMBER250, CONSTNUMBER28)
		this.namesearchbutton.setText(this.resource.getString("search"))

		val label3:Label = new Label(this.searchcomp, SWT.NONE)
		label3.setText(this.resource.getString("choose"))
		label3.setBounds(CONSTNUMBER380, CONSTNUMBER60
				,CONSTNUMBER250, CONSTNUMBER20)

		val label4:Label = new Label(this.searchcomp, SWT.NONE)
		label4.setText(ResourceBundleStorage.
				getResourceBundle("RecipeWindow").getString("chooseIngredient"))
		label4.setBounds(CONSTNUMBER22, CONSTNUMBER60
				,CONSTNUMBER250, CONSTNUMBER20)

		this.addbutton = new Button(this.searchcomp, SWT.PUSH)
		this.addbutton.setBounds(CONSTNUMBER290, CONSTNUMBER300
				,CONSTNUMBER80, CONSTNUMBER35)
		this.addbutton.setText(">>")
		this.removebutton = new Button(this.searchcomp, SWT.PUSH)
		this.removebutton.setBounds(CONSTNUMBER290, CONSTNUMBER345
				,CONSTNUMBER80, CONSTNUMBER35)
		this.removebutton.setText("<<")

		this.updatebutton = new Button(this.searchcomp, SWT.NONE)
		this.updatebutton.setBounds(new Rectangle(CONSTNUMBER672, CONSTNUMBER325
				,CONSTNUMBER250, CONSTNUMBER28))
		this.updatebutton.setText(this.resource.getString("update"))

		val label6:Label = new Label(this.searchcomp, SWT.WRAP | SWT.CENTER)
		label6.setText(this.resource.getString("exactsearch"))
		label6.setBounds(CONSTNUMBER672, CONSTNUMBER155
				,CONSTNUMBER250, CONSTNUMBER35)
		this.searchbutton = new Button(this.searchcomp, SWT.NONE)
		this.searchbutton.setBounds(new Rectangle(CONSTNUMBER672, CONSTNUMBER190
				,CONSTNUMBER250, CONSTNUMBER28))
		this.searchbutton.setText(this.resource.getString("exactsearch_button"))

		val label7:Label = new Label(this.searchcomp, SWT.WRAP | SWT.CENTER)
		label7.setText(this.resource.getString("rawsearch"))
		label7.setBounds(CONSTNUMBER672, CONSTNUMBER225
				,CONSTNUMBER250, CONSTNUMBER35)
		this.searchbutton2 = new Button(this.searchcomp, SWT.NONE)
		this.searchbutton2.setBounds(new Rectangle(CONSTNUMBER672, CONSTNUMBER260
				,CONSTNUMBER250, CONSTNUMBER28))
		this.searchbutton2.setText(this.resource.getString("rawsearch_button"))

		this.ingredientstable = new Table(this.searchcomp, SWT.MULTI
				| SWT.BORDER | SWT.FULL_SELECTION)
		this.ingredientstable.setHeaderVisible(true)
		this.ingredientstable.setLinesVisible(true)
		this.ingredientstable.setBounds(new Rectangle(TABLE1_X,
				TABLE1_Y, TABLE1_WIDTH,
				TABLE1_HEIGHT))
		val tableColumn4:TableColumn = new TableColumn(this.ingredientstable,
				SWT.VIRTUAL)
		tableColumn4.setWidth(TABLE2_COL_WIDTH)

		this.ingredientstable2 = new Table(this.searchcomp, SWT.MULTI
				| SWT.BORDER | SWT.FULL_SELECTION)
		this.ingredientstable2.setHeaderVisible(true)
		this.ingredientstable2.setLinesVisible(true)
		this.ingredientstable2.setBounds(new Rectangle(TABLE2_X,
				TABLE2_Y, TABLE2_WIDTH,
				TABLE2_HEIGHT))
		val tableColumn5:TableColumn = new TableColumn(this.ingredientstable2,
				SWT.VIRTUAL)
		tableColumn5.setWidth(TABLE2_COL_WIDTH)

		Mainwindow.gettabFolderItem2().setControl(this.searchcomp)

		this.searchlistener(mainviewtab,display)
	}

	/**
	 * This Method returns the Button-Widget addbutton of this Class.
	 * @return the addbutton
	 */
	def getAddbutton():Button= {
		this.addbutton
	}

	/**
	 * This Method returns the SelectonAdapter addbuttonselect of this Class.
	 * @return the addbuttonselect
	 */
	def getAddbuttonselect:SelectionAdapter= {
		this.addbuttonselect
	}

	/**
	 * This Method returns the Table-Widget ingredientstable of this Class.
	 * @return the ingredientstable
	 */
	def getIngredientstable:Table= {
		this.ingredientstable
	}

	/**
	 * This Method returns the IngredientMessage Instance of this Class.
	 * @return the ingrmess
	 */
	def getIngrmess:IngredientMessage= {
		this.ingrmess
	}

	/**
	 * This Method returns the Messagewindow Instance of this Class.
	 * @return the msgwindow
	 */
	def getMsgwindow:Messagewindow= {
		this.msgwindow
	}

	/**
	 * This Method returns the Button-Widget removebutton of this Class.
	 * @return the removebutton
	 */
	def getRemovebutton:Button= {
		this.removebutton
	}

	/**
	 * This Method returns the SelectonAdapter removebuttonselect of this Class.
	 * @return the removebuttonselect
	 */
	def getRemovebuttonselect():SelectionAdapter= {
		this.removebuttonselect
	}

	/**
	 * This Method returns the Button-Widget searchbutton of this Class.
	 * @return the searchbutton
	 */
	def getSearchbutton():Button= {
		this.searchbutton
	}

	/**
	 * This Method returns the Button-Widget searchbutton2 of this Class.
	 * @return the searchbutton2
	 */
	def getSearchbutton2:Button= {
		 this.searchbutton2
	}

	/**
	 * This Method returns the SelectonAdapter searchbuttonselect of this Class.
	 * @return the searchbuttonselect
	 */
	def getSearchbuttonselect:SelectionAdapter= {
		 this.searchbuttonselect
	}

	/**
	 * This Method returns the SelectonAdapter searchbutton2select of this Class.
	 * @return the searchbutton2select
	 */
	def getSearchbutton2select:SelectionAdapter= {
		 this.searchbutton2select
	}

	/**
	 * This Method returns the Composite-Widget searchcomp of this Class.
	 * @return the searchcomp
	 */
	def getSearchcomp:Composite= {
		 this.searchcomp
	}

	/**Gets the SelectionAdapters of this class.
	 * @return a list of SelectionAdapters.*/
	def getSelect:List[SelectionAdapter]={
		val result:List[SelectionAdapter]=List[SelectionAdapter]()
		result:+ this.addbuttonselect
		result:+ this.removebuttonselect
		result:+ this.searchbutton2select
		result:+ this.searchbuttonselect
		result:+ this.updatebuttonselect
    result
	}

	/**
	 * This Method returns the Button-Widget updatebutton of this Class.
	 * @return the updatebutton
	 */
	def getUpdatebutton:Button= {
		this.updatebutton
	}

	/**
	 * This Method returns the SelectonAdapter updatebuttonselect of this Class.
	 * @return the updatebuttonselect
	 */
	def getUpdatebuttonselect:SelectionAdapter= {
		 this.updatebuttonselect
	}

}

