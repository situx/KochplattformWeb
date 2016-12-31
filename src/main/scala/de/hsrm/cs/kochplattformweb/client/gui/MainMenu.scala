package de.hsrm.cs.kochplattformweb.client.gui

import java.io.IOException
import java.util.ResourceBundle
import java.util.Set

import de.hsrm.cs.kochplattformweb.client.gui._
import de.hsrm.cs.kochplattformweb.client.Client
import de.hsrm.cs.kochplattformweb.client.gui._
import de.hsrm.cs.kochplattformweb.db.{Ingredient, Recipe}
import de.hsrm.cs.kochplattformweb.messages.{ConfirmReport, ErrorReport, IngredientSetMessage, Message}
import de.hsrm.cs.kochplattformweb.printer.Printer
import de.hsrm.cs.kochplattformweb.utils.SWTLogger
import org.eclipse.swt.SWT
import org.eclipse.swt.events.{SelectionAdapter, SelectionEvent, ShellAdapter, ShellEvent}
import org.eclipse.swt.graphics.Image
import org.eclipse.swt.widgets.{Display, Menu, MenuItem, Shell}

import scala.Predef.Set
import scala.collection.Set



/**Class for implementing the menu which can be seen in the Mainwindow.
 * @author Timo Homburg
 */
class MainMenu(window:Shell,client:Client,controller:Controller,
							 var loginwindow:Loginwindow,var mesgwindow:Messagewindow){
	/** menuBar as Menu for the Menu in the GUI. **/
	var menuBar: Menu=null
	/** This field is the MenuItem for opening the Aboutwindow. **/
	var menueabout: MenuItem=null
	/** This field is the MenuItem for opening the backup options . **/
	var menuebackup: MenuItem=null
	/** This field is the MenuItem for opening the Loginwindow . **/
	var menueconnect: MenuItem=null
	/** This field is the MenuItem for closing the Program. **/
	var menueclose: MenuItem=null
	/** This field is the MenuItem for closing the Serverconnection . **/
	var menueclosecon:MenuItem=null
	/** Selection Adapter for the menueconnect. */
	var menueconnectselect: SelectionAdapter=null
	/** This field is the MenuItem for creating a new Recipes . **/
	var menuecreaterec: MenuItem=null
	/** This field is the MenuItem for opening the Languagewindow. **/
	var menuelanguage: MenuItem=null
	/** This field is the MenuItem for opening the Languagewindow. **/
	var menueimport: MenuItem=null
	/** This field is the MenuItem for logging out the User . **/
	var menuelogout: MenuItem=null
	/** This field is the MenuItem for opening the Helpwindow . **/
	var menuemanual: MenuItem=null
	/** This field is the MenuItem for opening the Printingwindow . **/
	var menueprint: MenuItem=null
	/** This field is the MenuItem for displaying Userinfos . **/
	var menueuserinfo: MenuItem=null
	/** This field is the MenuItem for changing Userdata . **/
	var menueuserch: MenuItem=null
	/** This field is the MenuItem for shutting down the Server . **/
	var menueserversd: MenuItem=null
	/** This field contains the Submenue named Switchmode. **/
	var submenumode: Menu=null
	/** This field contains the Submenue named Recipe. **/
	var submenurecipe: Menu=null
	/** This field contains the Submenue named Program. **/
	var submenuprogram: Menu=null
	/** This field contains the Submenue named User. **/
	var submenuser: Menu=null
	/** This field contains the Submenue named Options. **/
	var submenuoptions: Menu=null
	/** This field contains the Submenue named Help. **/
	var submenuhelp: Menu=null
	/** Selection Adapter for the menueclose. */
	var menuecloseselect: SelectionAdapter=null
	/** Selection. */
	var menuelanguageselect: SelectionAdapter=null
	/** Selection Adapter for the menuelogout. */
	var menuelogoutselect: SelectionAdapter=null
	/** Selection Adapter for the menuebackup. */
	var menuebackupselect: SelectionAdapter=null
	/** Selection Adapter for the menueserversd. */
	var menueserversdselect: SelectionAdapter=null
	/** Selection Adapter for the menueclosecon. */
	var menuecloseconselect: SelectionAdapter=null
	/**The display used by this class.*/
	val display:Display=null

	var msg: Message=null
	/**The display used by this class.*/
	val resource:ResourceBundle=ResourceBundleStorage.
		getResourceBundle(ResourceBundleStorage.MENUE)
	/** Shell Adapter for the windowselect. */
	var windowselect: ShellAdapter=null
	/** menueimportselect as SelectionAdapter. **/
	var menueimportselect: SelectionAdapter=null

	this.buildMenue()
	
	/**
	 * This Method is for buildung the Menue with its content.
	 */
	def buildMenue() {
		this.menuBar=new Menu(this.window, SWT.BAR)
		val submenuItem1:MenuItem = new MenuItem(this.menuBar, SWT.CASCADE)

		this.submenuprogram = new Menu(submenuItem1)
		submenuItem1.setMenu(this.submenuprogram)
		submenuItem1.setText(this.resource.getString("program"))

		val submenuItem2:MenuItem = new MenuItem(this.menuBar, SWT.CASCADE)
		val submenuItem3:MenuItem = new MenuItem(this.menuBar, SWT.CASCADE)
		val submenuItem5:MenuItem = new MenuItem(this.submenuprogram,
				SWT.CASCADE)
		val submenuItem6:MenuItem = new MenuItem(this.menuBar, SWT.CASCADE)
		val submenuItem4:MenuItem = new MenuItem(this.menuBar, SWT.CASCADE)
		this.submenuser = new Menu(submenuItem1)

		submenuItem2.setText(this.resource.getString("user"))
		submenuItem2.setMenu(this.submenuser)

		this.submenuoptions = new Menu(submenuItem3)
		submenuItem3.setMenu(this.submenuoptions)
		submenuItem3.setText(this.resource.getString("options"))

		this.submenuhelp = new Menu(submenuItem4)
		submenuItem4.setMenu(this.submenuhelp)
		submenuItem4.setText(this.resource.getString("help"))

		this.submenumode = new Menu(submenuItem5)
		submenuItem5.setMenu(this.submenumode)
		submenuItem5.setText(this.resource.getString("switchmode"))

		this.submenurecipe = new Menu(submenuItem6)
		submenuItem6.setMenu(this.submenurecipe)
		submenuItem6.setText(this.resource.getString("recipe"))

		this.menueconnect = new MenuItem(this.submenuser, SWT.PUSH)
		this.menueconnect.setText(this.resource.getString("connect"))

		this.menueclose = new MenuItem(this.submenuprogram, SWT.PUSH)
		this.menueclose.setText(this.resource.getString("close"))

		this.menueabout = new MenuItem(this.submenuhelp, SWT.PUSH)
		this.menueabout.setText(this.resource.getString("about"))

		this.menuemanual = new MenuItem(this.submenuhelp, SWT.PUSH)
		this.menuemanual.setText(this.resource.getString("manual"))

		this.menueuserinfo = new MenuItem(this.submenuser, SWT.PUSH)
		this.menueuserinfo.setText(this.resource.getString("info"))

		this.menueuserch = new MenuItem(this.submenuser, SWT.PUSH)
		this.menueuserch.setText(this.resource.getString("edit"))

		this.menuelogout = new MenuItem(this.submenumode, SWT.PUSH)
		this.menuelogout.setText(this.resource.getString("logout"))

		this.menuelanguage = new MenuItem(this.submenuoptions, SWT.PUSH)
		this.menuelanguage.setText(this.resource.getString("language"))

		this.menueclosecon = new MenuItem(this.submenumode, SWT.PUSH)
		this.menueclosecon.setText(this.resource.getString("closeconnection"))

		this.menueserversd = new MenuItem(this.submenumode, SWT.PUSH)
		this.menueserversd.setText(this.resource.getString("servershutdown"))

		this.menuecreaterec = new MenuItem(this.submenurecipe, SWT.PUSH)
		this.menuecreaterec.setText(this.resource.getString("create"))

		this.menuebackup = new MenuItem(this.submenurecipe, SWT.PUSH)
		this.menuebackup.setText(this.resource.getString("backup"))

		this.menueimport = new MenuItem(this.submenurecipe, SWT.PUSH)
		this.menueimport.setText(this.resource.getString("import"))

		this.menueprint = new MenuItem(this.submenuoptions, SWT.PUSH)
		this.menueprint.setText(this.resource.getString("print"))

		/** In these lines are we adding the icons to the menu **/
		try {
			submenuItem1.setImage(new Image(this.display, this.getClass()
					.getResource("img/program.png").openStream()))
			submenuItem2.setImage(new Image(this.display, this.getClass()
					.getResource("img/user.png").openStream()))
			submenuItem3.setImage(new Image(this.display, this.getClass()
					.getResource("img/options.png").openStream()))
			submenuItem4.setImage(new Image(this.display, this.getClass()
					.getResource("img/help.png").openStream()))
			submenuItem5.setImage(new Image(this.display, this.getClass()
					.getResource("img/switch.png").openStream()))
			submenuItem6.setImage(new Image(this.display, this.getClass()
					.getResource("img/recipe.png").openStream()))
			this.menueconnect.setImage(new Image(this.display, this.getClass()
					.getResource("img/connect.png").openStream()))
			this.menueclose.setImage(new Image(this.display, this.getClass()
					.getResource("img/exit.png").openStream()))
			this.menueabout.setImage(new Image(this.display, this.getClass()
					.getResource("img/about.png").openStream()))
			this.menuemanual.setImage(new Image(this.display, this.getClass()
					.getResource("img/manual.png").openStream()))
			this.menueuserinfo.setImage(new Image(this.display, this.getClass()
					.getResource("img/info.png").openStream()))
			this.menuelogout.setImage(new Image(this.display, this.getClass()
					.getResource("img/logout.png").openStream()))
			this.menuecreaterec.setImage(new Image(this.display, this.getClass()
					.getResource("img/create.png").openStream()))
			this.menueprint.setImage(new Image(this.display, this.getClass()
					.getResource("img/print.png").openStream()))
			this.menueuserch.setImage(new Image(this.display, this.getClass()
					.getResource("img/edit.png").openStream()))
			this.menueserversd.setImage(new Image(this.display, this.getClass()
					.getResource("img/servershutdown.png").openStream()))
			this.menuelanguage.setImage(new Image(this.display, this.getClass()
					.getResource("img/language.png").openStream()))
			this.menueclosecon.setImage(new Image(this.display, this.getClass()
					.getResource("img/closecon.png").openStream()))
			this.menueimport.setImage(new Image(this.display, this.getClass()
					.getResource("img/import.png").openStream()))
			this.menuebackup.setImage(new Image(this.display, this.getClass()
					.getResource("img/backup.png").openStream()))
		} catch{
			case e:IOException => SWTLogger.writeerror(e.getMessage())
		}

		this.menueListener()
	}
	/**
	 * This Method contains the Listeners for the Menueitems.
	 */
	def menueListener(){
		this.windowselect = new ShellAdapter() {
			override def shellClosed(event:ShellEvent) {
				Mainwindow.getInstance().changeLanguage()
			}
		}
		this.window.addShellListener(this.windowselect)
		this.menueconnectselect = new Loginwindow(this.client,this.display,this.controller)
		this.menuebackupselect = new BackupWindow(this.display,this.client)
		this.menuebackup.addSelectionListener(this.menuebackupselect)
		this.menueconnect.addSelectionListener(this.menueconnectselect)

		this.menuecloseselect = new SelectionAdapter() {
			@Override
			override def widgetSelected(sevent:SelectionEvent) {
				MainMenu.this.client.closeConnection()
				MainMenu.this.display.close()
			}
		}
		this.menueclose.addSelectionListener(this.menuecloseselect)

		this.menueabout.addSelectionListener(new Aboutwindow(this.display))

		this.menuemanual.addSelectionListener(new Helpwindow(this.display))

		this.menueuserinfo.addSelectionListener(new Userwindow(this.display,this.client))

		this.menueuserch.addSelectionListener(new UserManagementWindow(
				this.display,this.client,MainMenu.this.mesgwindow))

		this.menueimportselect= new ImportWindow(this.display, this.client)

		this.menueimport.addSelectionListener(this.menueimportselect)
		this.menuelogoutselect = new SelectionAdapter() {
			@Override
			override def widgetSelected(sevent:SelectionEvent) {
				if (MainMenu.this.mesgwindow.getShell.isDisposed) {
					MainMenu.this.mesgwindow = new Messagewindow(
							"Statusmeldung",MainMenu.this.display)
				}
				MainMenu.this.msg = MainMenu.this.client.logOut

				if (MainMenu.this.msg.isInstanceOf[ConfirmReport]) {
					MainMenu.this.mesgwindow.setMessage(MainMenu.this.msg.getMessage(),true)
					Mainwindow.getInstance().modus(MainMenu.this.loginwindow.isStatus)
					MainMenu.this.mesgwindow.openwindow()
				}else if(MainMenu.this.msg.isInstanceOf[ErrorReport]) {
					MainMenu.this.mesgwindow.openwindow()
				}
//				if (this.loginwindow.getWindow().isDisposed()) {
//					this.loginwindow = new Loginwindow(
//							this.client,
//							this.display,
//							this.controller)
//					Mainwindow.getInstance().
//						modus(this.loginwindow.isStatus())
//				}
			}
		}
		this.menuelogout.addSelectionListener(this.menuelogoutselect)
		this.menuelanguageselect=new SelectionAdapter() {
			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.SelectionAdapter
			 * #widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			override def widgetSelected(event:SelectionEvent) {
				Mainwindow.setChangelang(true)
				Mainwindow.changeLanguage()
			}
		}
		this.menuelanguage.addSelectionListener(this.menuelanguageselect)
		this.menueserversdselect = new SelectionAdapter() {
			@Override
			override def widgetSelected(sevent:SelectionEvent) {
				if (MainMenu.this.mesgwindow.getShell.isDisposed()) {
					MainMenu.this.mesgwindow = new Messagewindow(
							"Statusmeldung",MainMenu.this.display)
				}
				MainMenu.this.mesgwindow.openwindow()
				MainMenu.this.mesgwindow
						.setMessage(MainMenu.this.client
								.shutdown().getMessage(),true)
			}
		}
		this.menueserversd.addSelectionListener(this.menueserversdselect)

		this.menuecloseconselect = new SelectionAdapter() {
			@Override
			override def widgetSelected(sevent:SelectionEvent) {
				if (MainMenu.this.mesgwindow.getShell.isDisposed) {
          MainMenu.this.mesgwindow = new Messagewindow("Statusmeldung",MainMenu.this.display)
				}
        MainMenu.this.mesgwindow.openwindow()
        MainMenu.this.mesgwindow.setMessage(MainMenu.this.client.closeConnection().getMessage,true)
				Mainwindow.getInstance().modus(MainMenu.this.loginwindow.isStatus)
				if (MainMenu.this.loginwindow.getWindow.isDisposed()) {
          MainMenu.this.loginwindow = new Loginwindow(
            MainMenu.this.client,
            MainMenu.this.display,
            MainMenu.this.controller)
					Mainwindow.getInstance().
						modus(MainMenu.this.loginwindow
								.isStatus)
				}
			}
		}
		this.menueclosecon.addSelectionListener(this.menuecloseconselect)

		this.menuecreaterec.addSelectionListener(
				new RecipeWindow(this.display, new FillOutRecipe() {
					@Override
					override def getIngredients():List[Ingredient]= {
						if (MainMenu.this.mesgwindow.getShell.isDisposed()) {
              MainMenu.this.mesgwindow = new Messagewindow(
									"Statusmeldung",MainMenu.this.display)
						}
						MainMenu.this.msg =
              MainMenu.this.client.getAllIngredients(
								ResourceBundleStorage.getLanguage.toString)
						if(MainMenu.this.msg.isInstanceOf[IngredientSetMessage]){
							val tempset:scala.collection.Set[Ingredient] = MainMenu.this.msg.asInstanceOf[IngredientSetMessage].ingredientSet
							val tempingrlist:List[Ingredient] = List[Ingredient]()
							for(inf:Ingredient<-tempset){
								tempingrlist:+ inf
							}
							return tempingrlist
						}
						if(MainMenu.this.msg.isInstanceOf[ErrorReport]){
							MainMenu.this.mesgwindow = new Messagewindow(
									msg.asInstanceOf[ErrorReport].message,
								MainMenu.this.display)
							MainMenu.this.mesgwindow.openwindow()
						}
						return List[Ingredient]()
					}
					@Override
					override def createRecipe(recipe:Recipe) {
						MainMenu.this.client.sendRecipes(recipe)
					}
				}))
		//this.menueprint.addSelectionListener(Printer)
	}

	/** getter for the Menu.
	 * @return a Menubar
	 */
	def getMenuBar()={
		this.menuBar
	}
}
