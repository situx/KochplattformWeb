package de.hsrm.cs.kochplattformweb.client.gui

import java.io.IOException
import java.util.ResourceBundle

import de.hsrm.cs.kochplattformweb.client.Client
import de.hsrm.cs.kochplattformweb.client.gui._
import de.hsrm.cs.kochplattformweb.db.{Ingredient, Recipe, RecipeEntry}
import de.hsrm.cs.kochplattformweb.messages._
import de.hsrm.cs.kochplattformweb.printer.Printer
import de.hsrm.cs.kochplattformweb.utils.SWTLogger
import org.eclipse.swt.SWT
import org.eclipse.swt.events.SelectionAdapter
import org.eclipse.swt.events.SelectionEvent
import org.eclipse.swt.graphics.Image
import org.eclipse.swt.graphics.Point
import org.eclipse.swt.graphics.Rectangle
import org.eclipse.swt.widgets.Button
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.Label
import org.eclipse.swt.widgets.Shell
import org.eclipse.swt.widgets.TabFolder
import org.eclipse.swt.widgets.TabItem
import org.eclipse.swt.widgets.TableItem

import scala.List


/**
 * This is the Mainguiclass for displaying all the important Widgets to control
 * the Program.
 * @author Daniel Torpus
 */
object Mainwindow {

  /**A constant Number {@value}.*/
	val CONSTNUMBER3=3
	/**A constant Number {@value}.*/
	val CONSTNUMBER4 = 4
	/**A constant Number {@value}.*/
	val CONSTNUMBER7=7
	/**A constant Number {@value}.*/
	val CONSTNUMBER8=8
	/**A constant Number {@value}.*/
	val CONSTNUMBER35=35
	/**A constant Number {@value}.*/
	val CONSTNUMBER70=70
	/**A constant Number {@value}.*/
	val CONSTNUMBER150=150
	/**A constant Number {@value}.*/
	val CONSTNUMBER300=300
	/**A constant Number {@value}.*/
	val CONSTNUMBER530=530

	/** Height of the Online/Offline Label{@value}. **/
	val LABEL_HEIGHT = 21
	/** Width of the Online/Offline Label{@value}. **/
	val LABEL_WIDTH = 195
	/** X-Position of the Online/Offline Label {@value}. **/
	val LABEL_X = 780
	/** Y-Position of the Online/Offline Label{@value}. **/
	val LABEL_Y = 4
	/** Height of the Username/Usertype Label{@value}. **/
	val LABEL2_HEIGHT = 21
	/** Width of the Username/Usertype Label{@value}. **/
	val LABEL2_WIDTH = 230
	/** X-Position of the Username/Usertype Label {@value}. **/
	val LABEL2_X = 500
	/** Y-Position of the Username/Usertype Label{@value}. **/
	val LABEL2_Y = 4
	/** Height of the Printrecipelist Button{@value}. **/
	val PRINTRECLIST_HEIGHT = 29
	/** Width of the Printrecipelist Button{@value}. **/
	val PRINTRECIPELIST_WIDTH = 170
	/** X-Position of the Printrecipelist Button {@value}. **/
	val PRINTRECIPELIST_X = 500
	/** Y-Position of the Printrecipelist Button {@value}. **/
	val PRINTRECIPELIST_Y = 570
	/** Height of the Recipedetailbutton{@value}. **/
	val RECBUTTON_HEIGHT = 29
	/** Width of the Recipedetailbutton{@value}. **/
	val RECBUTTON_WIDTH = 150
	/** X-Position of the Recipedetailbutton {@value}. **/
	val RECIPEBUTTON_X = 295
	/** Y-Position of the Recipedetailbutton {@value}. **/
	val RECIPEBUTTON_Y = 570
	/** Height of the Recipedetailbutton{@value}. **/
	val REMOVERECBUTTON_HEIGHT = 29
	/** Width of the Recipedetailbutton{@value}. **/
	val REMOVERECBUTTON_WIDTH = 150
	/** X-Position of the Recipedetailbutton {@value}. **/
	val REMOVERECBUTTON_X = 140
	/** Y-Position of the Recipedetailbutton {@value}. **/
	val REMOVERECBUTTON_Y = 570
	/** Height of the Tabfolder{@value}. **/
	val TAB_F_HEIGHT = 700
	/** Width of the Tabfolder{@value}. **/
	val TAB_F_WIDTH = 1000
	/** Height of the Mainwindow{@value}. **/
	val WINDOW_HEIGHT = 730
	/** Width of the Mainwindow{@value}. **/
	val WINDOW_WIDTH = 1000

	/** Instance of the Class Mainwindow for realising this Class as Singleton. **/
	//var instance:Mainwindow
  /** This field is for displaying detailled Information about a Recipe. **/
  var buttonrecipedetail: Button=new Button(this.mainviewtab.getMainviewComp,SWT.PUSH)
  /** Selection Adapter for the recipedetail button. */
  var buttonrecipedetailselect: SelectionAdapter=new SelectionAdapter() {
    @Override
    override def widgetSelected(sevent:SelectionEvent) {
    val sel = Mainwindow.this.mainviewtab.getMainviewTable
    .getSelectionIndex()
    if(sel!=(-1)){
    Mainwindow.this.msg = Mainwindow.this.client
    .getRecipe(new GetRecipeMessage(
    Mainwindow.this.recipeidlist(sel)))
  }
    if (Mainwindow.this.msgwindow.getShell.isDisposed()) {
    Mainwindow.this.msgwindow = new Messagewindow(
    "Statusmeldung", Mainwindow.this.display)
  }
    if (Mainwindow.this.msg.isInstanceOf[Recipe]) {
    Mainwindow.this.changetab.filldetailtab(
    Mainwindow.this.msg.asInstanceOf[Recipe],
    Mainwindow.this.client)
    Mainwindow.this.tabFolder.setSelection(2)
  } else {
    if (Mainwindow.this.msg.isInstanceOf[ErrorReport]) {
    Mainwindow.this.msgwindow.setMessage(
    Mainwindow.this.msg.getMessage(),
    true)
  }
  }
  }
  }
  /** This field is for removing the selected Recipe. **/
  var buttonremoverecipe: Button=new Button(this.mainviewtab.getMainviewComp,SWT.PUSH)
	/** Selection Adapter for the buttonremoverecipe button. */
	var buttonremoverecipeselect: SelectionAdapter=new SelectionAdapter() {
    @Override
    override def widgetSelected(sevent:SelectionEvent) {
      var temp:List[RecipeEntry]=null
      if (Mainwindow.this.msgwindow.getShell.isDisposed) {
        Mainwindow.this.msgwindow = new Messagewindow(
          "Statusmeldung", Mainwindow.this.display)
      }

      val sel = Mainwindow.this.mainviewtab.getMainviewTable.getSelectionIndex
      if(sel!=(-1)){

        val tempmess:IngredientMessage = new IngredientMessage(false)
        Mainwindow.this.msg = Mainwindow.this.client.sendIngredients(tempmess)
        temp = Mainwindow.this.msg.asInstanceOf[RecipeListMessage].recipelist
        Mainwindow.this.mainviewtab.getMainviewTable.removeAll()
        Mainwindow.this.fillTable(temp)

        Mainwindow.this.msg=Mainwindow.this.client.getRecipe(new GetRecipeMessage(
          Mainwindow.this.recipeidlist(sel)))

        //if (this.msg instanceof Recipe) {
        Mainwindow.this.msg = Mainwindow.this.client.deleteRecipe(Mainwindow.this.msg.asInstanceOf[Recipe])
      }
      if (Mainwindow.this.msg.isInstanceOf[ConfirmReport]) {
        Mainwindow.this.mainviewtab.getMainviewTable.remove(sel)
        Mainwindow.this.msgwindow.setMessage(Mainwindow.this.msg
          .getMessage(), true)
        Mainwindow.this.msgwindow.openwindow()
      } else {
        if (Mainwindow.this.msg.isInstanceOf[ErrorReport]) {
          Mainwindow.this.msgwindow
            .setMessage(Mainwindow.this.msg.asInstanceOf[ErrorReport])
          Mainwindow.this.msgwindow.openwindow()
        }
      }
      //				} else {
      //					if (this.msg instanceof Errorreport) {
      //						this.msgwindow.setMessage(
      //								(Errorreport)this.msg)
      //						this.msgwindow.openwindow()
      //					}
      //				}

    }}
	/** Boolean which indicates if the language should be changed. */
	var changelang: Boolean=false
  /** Instance of the Class Changetab. **/
  var changetab: Changetab=new Changetab(this.controller,this.display)

	/** Instance of the Class Controller. **/
	val controller:Controller=new Controller()

	/** Instance of the Class Loginwindow. **/
	var loginwindow: Loginwindow=new Loginwindow(this.client, this.display, this.controller)
	/** Instance of the Class MainMenu. **/
	var menu: MainMenu=new MainMenu(this.window, this.client, this.controller, this.loginwindow,this.msgwindow)
	/** Instance of the Class Messagewindow to display Messages. **/
	var msgwindow: Messagewindow=new Messagewindow("Statusmeldung", this.display)
  /** Instance of the Class Mainviewtab. **/
  var mainviewtab: Mainviewtab=new Mainviewtab()
  /** Instance of the Class Message. **/
  var msg: Message=null
	/** This field is for displaying the On/Offlinestatus. **/
	var onofflabel: Label=new Label(this.window, SWT.NONE)
  /** This field is for printing the Recipelist. **/
  var printrecipes: Button=new Button(this.mainviewtab.getMainviewComp,SWT.PUSH)
  /** Selection Adapter for the printrecipes button. */
  var printrecipesselect: SelectionAdapter=new SelectionAdapter() {
    @Override
    override def widgetSelected(sevent:SelectionEvent) {
      Printer.printIt(Mainwindow.this.recipelist)
    }
  }
	/** This field is stores the Ids of the Recipes in the Mainviewtable. **/
	var recipeidlist: List[BigInt]=List[BigInt]()
  /** This field is stores the RecipeEntrylist of the Mainviewtable. **/
  var recipelist: List[RecipeEntry]=List[RecipeEntry]()
	/**Instance of the Class ResourceBundleStorage.*/
	val resource:ResourceBundle=ResourceBundleStorage.
    getResourceBundle(ResourceBundleStorage.TABS)
  /** Instance of the Class Searchtab. **/
  var searchtab: Searchtab=new Searchtab(this.client, this.mainviewtab,this.display)
	/** Sutdown indicator. */
	var shutdown: Boolean=false
	/** tabFolder as TabFolder for the Tabs. **/
	var tabFolder: TabFolder=new TabFolder(this.window, SWT.BORDER)
	/** tabgolderItem as TabItem for the Items on the tabs. **/
	var tabfolderItem: TabItem=new TabItem(this.tabFolder, SWT.PUSH)
	/** tabgolderItem as TabItem for the Items on the tabs. **/
	var tabfolderItem2: TabItem=new TabItem(this.tabFolder, SWT.PUSH)
	/** tabgolderItem as TabItem for the Items on the tabs. **/
	var tabfolderItem3: TabItem=new TabItem(this.tabFolder, SWT.PUSH)
  /** String of the Username. */
  var username: String=""
	/** This field is for displaying the Logged in User. **/
	var usernamelabel: Label=new Label(this.window,SWT.NONE)
  /** String of the Userpassword. */
  var userpassword: String=""
	/** Window for this Class. **/
	val window:Shell=new Shell(this.display)

	var client: Client=new Client("",0)

	var display: Display=new Display()

  this.buildGUI()
  this.modus(this.client.isStatus)
  this.window.setSize(new Point(WINDOW_WIDTH,
    WINDOW_HEIGHT))
  this.window.setText(this.resource.getString("cookinglibary"))
  GUIHelper.makeMeCenter(this.display, this.window)
  this.window.open()
  while (!this.window.isDisposed) {
    if (!this.display.readAndDispatch()) {
      this.display.sleep()
    }
  }
  this.display.dispose()

def getInstance()=this

	/**
	 * This method returns the Instance of 
	 * @return instance Instance of Mainwindow
	 **/
	def getInstance(display:Display,client:Client)= {

		this.display=display
		this.client=client
		this

	}

	/**
	 * This method returns the Instance of 
	 * @return instance Instance of Mainwindow
	 **/
	/*def getNewInstance:Mainwindow= {
		new Mainwindow(Languagewindow
				.getDisplay,Languagewindow.
				getClient)
		instance
	}*/

	/**
	 * This method is for building the GUI-Components.
	 */
	def buildGUI() {
		this.loginwindow=new Loginwindow(this.client, this.display, this.controller)
		this.msgwindow=new Messagewindow("Statusmeldung", this.display)
		this.menu=new MainMenu(this.window,
				this.client, this.controller,
				this.loginwindow,this.msgwindow)
		this.onofflabel = new Label(this.window, SWT.NONE)
		this.onofflabel.setText("Offlinemodus")
		this.onofflabel.setBounds(new Rectangle(LABEL_X,
				LABEL_Y, LABEL_WIDTH,
				LABEL_HEIGHT))

		this.usernamelabel = new Label(this.window,SWT.NONE)
		this.usernamelabel.setText("Usernamedummy")
		this.usernamelabel.setBounds(new Rectangle(LABEL2_X,
				LABEL2_Y, LABEL2_WIDTH,
				LABEL2_HEIGHT))
		this.modus(false)

		this.window.setMenuBar(this.menu.getMenuBar())

		this.createTabfolder()
		this.mainviewtab = new Mainviewtab()
		this.searchtab = new Searchtab(this.client,
				this.mainviewtab,this.display)
		this.changetab = new Changetab(this.controller,this.display)
		try {
			this.window.setImage(new Image(this.display,
					this.getClass.getResource("img/icon.png").openStream()))
		} catch{
			case event:IOException => 			SWTLogger.writeerror(event.getMessage)
		}

		/*falls noch Zeit ist fixen , funktionell müsste
		 * es gehen aber Listener geht nicht,
		 * falls gefixed in Searchtab updatebutton und
		 * dessen listener entfernen und aus kommentierte
		 * setIngredientlist wieder verwenden*/
//		this.tabfolderItem2.addListener(SWT.Selection, new Listener() {
//
//			@Override
//			def handleEvent(final Event arg0) {
//
//				this.msg = this.client.getAllIngredients(
//						ResourceBundleStorage.getInstance().getLanguage())
//				this.logger.info("Tabfolderlister"+msg.getMessage())
//				if (this.msgwindow.getShell().isDisposed()) {
//					this.msgwindow = new Messagewindow(
//							"Statusmeldung",this.display)
//				}
//
//				if (this.msg instanceof IngredientSetMessage) {
//					final Set<Ingredient> tempset = ((IngredientSetMessage)
//							this.msg).
//					getIngredientsSet()
//					this.searchtab.setIngredientlist(tempset)
//				} else {
//					if (this.msg instanceof Errorreport) {
//						this.msgwindow.
//						setMessage(this.msg.getMessage(),true)
//						this.msgwindow.openwindow()
//					}
//				}
//			}
//		})

	}


	/**This Method changes the Language of the Program.**/
	def changeLanguage(){
		if(this.changelang){
			SWTLogger.writeinfo("Changelanguage")
			Languagewindow.getInstance(this.client,this.display)
			this.changelang=false
			this.shutdown=true
			this.window.close()
			this.window.dispose()
			Languagewindow.window.open()
			while (Languagewindow.getWindow.isDisposed()) {
				if (Languagewindow.getDisplay.readAndDispatch()) {
					Languagewindow.getDisplay.sleep()
				}
			}
		}
		if(this.shutdown){
			this.shutdown=false
		}else {
			this.client.closeConnection
		}
	}

	/**
	 * This method initializes tabFolder.
	 */
	def createTabfolder() {
		this.tabFolder = new TabFolder(this.window, SWT.BORDER)
		this.tabFolder.setSize(TAB_F_WIDTH, TAB_F_HEIGHT)
		this.tabfolderItem2 = new TabItem(this.tabFolder, SWT.PUSH)
		this.tabfolderItem2.setText(this.resource.getString("searchrequest"))
		this.tabfolderItem = new TabItem(this.tabFolder, SWT.NULL)
		this.tabfolderItem.setText(this.resource.getString("mainview"))
		this.tabfolderItem3 = new TabItem(this.tabFolder, SWT.NULL)
		this.tabfolderItem3.setText(this.resource.getString("detailview"))

		/** Put Icons into Tab **/
		try {
			this.tabfolderItem.setImage(new Image(this.display,
					this.getClass.getResource("img/tab4.png").openStream()))
			this.tabfolderItem2.setImage(new Image(this.display,
					this.getClass.getResource("img/tab4.png").openStream()))
			this.tabfolderItem3.setImage(new Image(this.display,
					this.getClass.getResource("img/tab4.png").openStream()))
		} catch{
			case e:IOException =>SWTLogger.writeerror(e.getMessage)
		}

	}

	

	/**
	 * This Method is for receiving Recipes that matches the typed in Ingredients.
	 * @param recipes List of RecipeEntrys that should be displayed in the Table.
	**/
	def fillTable(recipes:List[RecipeEntry]) {
		this.recipelist = recipes
		val ingbuffer:StringBuffer = new StringBuffer(60)
    var temp: TableItem=null
		var width0 = 0
    var width1 = 0
		var width2=0
		var width3=0
    var width4 = 0
		this.recipeidlist = List[BigInt]()

		for (rec:RecipeEntry <- recipes) {
			this.recipeidlist:+ rec.recipeid
			temp = new TableItem(this.mainviewtab.getMainviewTable, SWT.NONE)
			width0=java.lang.Math.max(rec.name.getBytes().length,width0)
			temp.setText(0, rec.name)

			width1=java.lang.Math.max(rec.category.length(),width1)
			temp.setText(1, rec.category)

			width2=java.lang.Math.max(100/CONSTNUMBER8,width2)
			temp.setText(2, rec.rating.toString)

			width3=java.lang.Math.max(100/CONSTNUMBER8,width3)
			temp.setText(CONSTNUMBER3, rec.zombies.toString)

			for (ing:Ingredient <- rec.ingredients) {
				ingbuffer.append(ing.name)
				ingbuffer.append(" | ")
			}
			width4=java.lang.Math.max(ingbuffer.length(),width4)
			temp.setText(CONSTNUMBER4,"{"+ingbuffer.toString+"}")

			ingbuffer.delete(0, ingbuffer.length())
		}
		/*Problem es wird die Max-Anzahl der Zeichen pro Spalte
		 * ermittelt aber damit weiß man noch nicht die
		 * Pixelbreite pro Zeichen und somit ist das vielfache des Max wertes nur grob*/
		//this.logger.info("Spalte0:"+width0)
		this.mainviewtab.getMainviewTable.getColumn(0).setWidth(width0*CONSTNUMBER7)
		//this.logger.info("Spalte1:"+width1)
		//width1=width1*CONSTNUMBER8
		this.mainviewtab.getMainviewTable.getColumn(1).setWidth(CONSTNUMBER70/*width1*/)
		//this.logger.info("Spalte2:"+width2)
		//width2=width2*7
		this.mainviewtab.getMainviewTable.getColumn(2).setWidth(CONSTNUMBER70/*width2*/)
		//this.logger.info("Spalte3:"+width3)
		//width3=width3*CONSTNUMBER7
		this.mainviewtab.getMainviewTable.getColumn(CONSTNUMBER3).setWidth(CONSTNUMBER70/*width3*/)
		//this.logger.info("Spalte4:"+width4)
		width4=width4*CONSTNUMBER7
		this.mainviewtab.getMainviewTable.getColumn(CONSTNUMBER4).setWidth(width4)

		this.buttonremoverecipe = new Button(this.mainviewtab.getMainviewComp,SWT.PUSH)
		this.buttonremoverecipe.setBounds(REMOVERECBUTTON_X,
				REMOVERECBUTTON_Y,
				REMOVERECBUTTON_WIDTH,
				REMOVERECBUTTON_HEIGHT)
		this.buttonremoverecipe.setText(this.resource.getString("remove"))

		val label1:Label = new Label(this.mainviewtab.getMainviewComp,
				SWT.WRAP | SWT.CENTER)
		label1.setText(this.resource.getString("detailview"))
		label1.setBounds(CONSTNUMBER300, CONSTNUMBER530, CONSTNUMBER150, CONSTNUMBER35)
		this.buttonrecipedetail = new Button(this.mainviewtab.getMainviewComp,SWT.PUSH)
		this.buttonrecipedetail.setBounds(RECIPEBUTTON_X,
				RECIPEBUTTON_Y,
				RECBUTTON_WIDTH,
				RECBUTTON_HEIGHT)
		this.buttonrecipedetail.setText(this.resource.getString("show"))

		this.buttonrecipedetailselect = new SelectionAdapter() {
			@Override
			override def widgetSelected(sevent:SelectionEvent) {
				val sel = Mainwindow.this.mainviewtab.getMainviewTable
						.getSelectionIndex()
				if(sel!=(-1)){
          Mainwindow.this.msg = Mainwindow.this.client
							.getRecipe(new GetRecipeMessage(
                Mainwindow.this.recipeidlist(sel)))
				}
				if (Mainwindow.this.msgwindow.getShell.isDisposed()) {
          Mainwindow.this.msgwindow = new Messagewindow(
							"Statusmeldung", Mainwindow.this.display)
				}
				if (Mainwindow.this.msg.isInstanceOf[Recipe]) {
          Mainwindow.this.changetab.filldetailtab(
							Mainwindow.this.msg.asInstanceOf[Recipe],
							Mainwindow.this.client)
          Mainwindow.this.tabFolder.setSelection(2)
				} else {
					if (Mainwindow.this.msg.isInstanceOf[ErrorReport]) {
            Mainwindow.this.msgwindow.setMessage(
              Mainwindow.this.msg.getMessage(),
								true)
					}
				}
			}
		}
		this.buttonrecipedetail.addSelectionListener(this.buttonrecipedetailselect)


		this.buttonremoverecipe.addSelectionListener(this.buttonremoverecipeselect)

		this.printrecipes = new Button(this.mainviewtab.getMainviewComp,SWT.PUSH)
		this.printrecipes.setBounds(PRINTRECIPELIST_X,
				PRINTRECIPELIST_Y,
				PRINTRECIPELIST_WIDTH,
				PRINTRECLIST_HEIGHT)
		this.printrecipes.setText(this.resource.getString("printlist"))
		this.printrecipes.addSelectionListener(this.printrecipesselect)

	}

	

	/**
	 * This method is for changing the content of the Statuslabel.
	 * @param loggedin Servermode Offline or Online
	 */
	def modus(loggedin:Boolean) {

		if (loggedin) {
			this.loginwindow.getconButton.setEnabled(false)
			this.loginwindow.serverOn()
			this.onofflabel.setText(this.resource.getString("onlinemode"))
			if(this.client.getUsertype.isAdmin){
				this.usernamelabel.setText("Admin:"+this.client.getUsertype.name)
			}
			if(this.client.getUsertype.isGuest){
				this.usernamelabel.setText(this.resource.getString("guest")+
						this.client.getUsertype.name)
			}
			if(this.client.getUsertype.isStandardUser){
				this.usernamelabel.setText("Standard User:"+
						this.client.getUsertype.name)
			}
		}
		if (!loggedin) {
			this.loginwindow.serverOff()
			this.loginwindow.getconButton.setEnabled(true)
			this.onofflabel.setText(this.resource.getString("offlinemode"))
			this.usernamelabel.setText("N/A")
		}
	}

	/**
	 * Sets the field changelang.
	 * @param changelang the changelang to set
	 */
	def setChangelang(changelang:Boolean) {
		this.changelang = changelang
	}

	/**This Method returns the display of this Class.
	 * @return display
	 * **/
	def getDisplay() ={
		this.display
	}

	/**
	 * This method returns the tabFolder.
	 * @return tabFolder
	 */
	def getTabfolder()={
		this.tabFolder
	}

	/**
	 * This method returns the tabFolderItem.
	 * @return tabFolderItem
	 */
	def gettabFolderItem()={
		this.tabfolderItem
	}

	/**
	 * This method returns the tabFolder2.
	 * @return tabFolderItem2
	 */
	def gettabFolderItem2()
  ={
		this.tabfolderItem2
	}

	/**
	 * This method returns the tabFolder3.
	 * @return tabFolderItem3
	 */
	def gettabFolderItem3()={
		this.tabfolderItem3
	}

	/**
	 * This Method returns the Client Instance of this Class.
	 * @return the servercon
	 */
	def getServercon()= {
		this.client
	}

	/**
	 * This Method returns the Mainviewtab Instance of this Class.
	 * @return the mainviewtab
	 */
	def getMainviewtab()= {
		this.mainviewtab
	}

	/**
	 * This Method returns the Searchtab Instance of this Class.
	 * @return the searchtab
	 */
	def getSearchtab()= {
		 this.searchtab
	}

	/**
	 * This Method returns the Changetab Instance of this Class.
	 * @return the changetab
	 */
	def getChangetab()= {
		 this.changetab
	}

	/**
	 * This Method returns the Button-Widget buttonrecipedetail of this Class.
	 * @return the buttonrecipedetail
	 */
	def getButtonrecipedetail()= {
		 this.buttonrecipedetail
	}

	/**
	 * This Method returns the Label-Widget onofflabel of this Class.
	 * submenurecipe
	 * @return the onofflabel
	 */
	def getOnofflabel()= {
    this.onofflabel
	}

	/**
	 * This Method returns the Shell of this Class.
	 * @return the window
	 */
	def getWindow()= {
		 this.window
	}

	/**
	 * This Method returns the SelectionAdapter buttonrecipedetailselect.
	 * @return the buttonrecipedetailselect
	 */
	def getButtonrecipedetailselect()= {
		 this.buttonrecipedetailselect
	}

	/**
	 * This Method returns the Message Instance of this Class.
	 * @return the msg
	 */
	def getMsg() ={
		this.msg
	}

	/**
	 * This Method returns the Controller Instance of thi Class.
	 * @return the controller
	 */
	def getController()= {
		this.controller
	}

	/**
	 * Returns the Username.
	 * @return the username
	 */
	def getUsername()= {
		 this.username
	}

	/**
	 * Returns the Userpassword.
	 * @return the userpassword
	 */
	def getUserpassword()= {
		 this.userpassword
	}

	/**
	 * Gets the Client.
	 * @return the client
	 */
	def getClient()= {
		this.client
	}

	/**
	 * returns the Recipelist.
	 * @return recipeidlist
	 */
	def getMessagewindow()={
		this.msgwindow
	}

	/**
	 * returns the Recipelist.
	 * @return recipeidlist
	 */
	def getRecipeIdList()={
		 this.recipeidlist
	}

	/**
	 * Sets the Client.
	 * @param client the client to set
	 */
	def setClient(client:Client)= {
		this.client=client
		this.buildGUI()
	}

	/**
	 * Returns the Username.
	 * @param uname the username
	 */
	def setUsername(uname:String) {
		this.username=uname
	}

	/**
	 * Returns the Userpassword.
	 * @param upa the userpassword
	 */
	def setUserpassword(upa:String) {
		this.userpassword=upa
	}

	/**
	 * Sets the field schutdown.
	 * @param shutdown the shutdown to set
	 */
	def setShutdown(shutdown:Boolean) {
		this.shutdown = shutdown
	}

	/**
	 * Returns if the Language is changed.
	 * @return the changelang
	 */
	def isChangelang()= {
		this.changelang
	}

	/**
	 * Returns the field shutdown.
	 * @return the shutdown
	 */
	def isShutdown()= {
		 this.shutdown
	}
	
	/**getter for the Loginwindow.
	 * @return the loginwindow	 */
	def getLoginwindow()= {
		 this.loginwindow
	}

	/** setter for the logginwindow.
	 * @param loginwindow the loginwindow to set */
	def setLoginwindow(loginwindow:Loginwindow) ={
		this.loginwindow = loginwindow
	}

	/** setter for the Display.
	 * @param display the display to set */
	def setDisplay(display:Display) ={
		this.display = display
	}
}
