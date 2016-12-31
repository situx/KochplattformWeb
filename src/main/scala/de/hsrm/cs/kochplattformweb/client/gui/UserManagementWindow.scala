package de.hsrm.cs.kochplattformweb.client.gui

import java.io.IOException
import java.util.ResourceBundle

import org.eclipse.swt.SWT
import org.eclipse.swt.events.SelectionAdapter
import org.eclipse.swt.events.SelectionEvent
import org.eclipse.swt.graphics.Image
import org.eclipse.swt.graphics.Rectangle
import org.eclipse.swt.widgets.Button
import org.eclipse.swt.widgets.Combo
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.Label
import org.eclipse.swt.widgets.Monitor
import org.eclipse.swt.widgets.Shell
import org.eclipse.swt.widgets.Table
import org.eclipse.swt.widgets.TableColumn
import org.eclipse.swt.widgets.TableItem
import org.eclipse.swt.widgets.Text
import de.hsrm.cs.kochplattformweb.client._
import de.hsrm.cs.kochplattformweb.client.AbstractUser
import de.hsrm.cs.kochplattformweb.client.Admin
import de.hsrm.cs.kochplattformweb.messages._
import de.hsrm.cs.kochplattformweb.printer.Printer
import de.hsrm.cs.kochplattformweb.utils.SWTLogger

/**UserManagement Window for the management of the user data.
 * @author Timo Homburg / Philipp Macho
 */
class UserManagementWindow(display:Display,client:Client, var messagewindow:Messagewindow) extends SelectionAdapter{
	/**A constant Number {@value}.*/
	val  CONSTNUMBER3= 3
	/**A constant Number {@value}.*/
	val  CONSTNUMBER4= 4
	/**A constant Number {@value}.*/
	val  CONSTNUMBER5= 5
	/**A constant Number {@value}.*/
	val  CONSTNUMBER8= 8
	/**A constant Number {@value}.*/
	val  CONSTNUMBER15= 15
	/**A constant Number {@value}.*/
	val  CONSTNUMBER20= 20
	/**A constant Number {@value}.*/
	val  CONSTNUMBER60= 60
	/**A constant Number {@value}.*/
	val  CONSTNUMBER70= 70
	/**A constant Number {@value}.*/
	val  CONSTNUMBER218= 218
	/**A constant Number {@value}.*/
	val  CONSTNUMBER335= 335
	/**A constant Number {@value}.*/
	val  CONSTNUMBER490= 490

	/**A constant Number {@value}.*/
	val  DISPLAYRESX= 640
	/**A constant Number {@value}.*/
	val  DISPLAYRESY= 480



	/**Position of the apply button {@value}.*/
	val  APPLY_POS=200
	/**Button height with value {@value}.*/
	val  BUTTON_HEIGHT=28
	/**Button width with value {@value}.*/
	val  BUTTON_WIDTH=108
	/**Combo Box width.*/
	val  COMBO_WIDTH = 136
	/**XPosition of the Combobox {@value}.*/
	val  COMBO_XPOS=400
	/**Table column width with value {@value}.*/
	val  TABLE_COL_WIDTH=300
	/**Button height with value {@value}.*/
	val  TEXTFIELD_WIDTH=300
	/**The label height.*/
	val  LABEL_HEIGHT = 16
	/**List position with value {@value}.*/
	val  LIST_POS=144
	/**Password Label Position with value {@value}.*/
	val  PASS_POS=72
	/**Password field position with value {@value}.*/
	val  PASSTEXT_POS=90
	/**Upper Label X-Position {@value}.*/
	val  UPLABXPOS=10
	/**Regular Expression for validating usernames.*/
	val MAIL_REGEX="^[-_.\\w]+@([0-9a-zA-Z][-\\w]*[0-9a-zA-Z]\\.){1,300}[a-zA-Z]{2,9}$"
	/** Button for changing user data. */
	var applybutton: Button=null
	/** Button to confirm all changes. */
	var confirmbutton: Button=null
	/** Button to confirm all changes. */
	var deletebutton: Button=null
	/** The Label for the name field. */
	var namelabel: Label=null
	/** The Label for the password field. */
	var passlabel: Label=null
	/** Text field for the user password. */
	var passphrase: Text=null
	/** Button to confirm all changes. */
	var printlistbutton: Button=null
	/** Button to confirm all changes. */
	var printbutton: Button=null
	/** The ResourceBundleStorage object for the languages. */
	var resource: ResourceBundle=null
	/** The Label for the status combobox. */
	var statuslabel: Label=null
	/** The Tableitem. */
	var tableitem: TableItem=null
	/** Button to confirm all changes. */
	var updatebutton: Button=null
	/**
		* This field is a Combo Box for displaying the Ingredients of a single.
		* Recipe
		**/
	var usergroupbox: Combo=null
	/** The userlist for displaying users. */
	var userlist: List[AbstractUser]=null
	/** Text field for the username. */
	var username: Text=null
  /** The Usertable. */
  var usertable: Table=null
  /** The userlist for displaying users. */
  var window: Shell = new Shell(display, SWT.CLOSE | SWT.TITLE | SWT.MIN | SWT.MAX)
  /** The SelectionAdapter selectapply . */
  var selectapply: SelectionAdapter=null
  /** The SelectionAdapter selectdelete . */
  var selectdelete: SelectionAdapter=null
	/** The inner class of the selectlistentry. */
	var selectlistentry: SelectionAdapter=null
  /** The inner class of the selectlistlist. */
  var selectprintlist: SelectionAdapter=null
  /** The inner class of the selectprint. */
  var selectprint: SelectionAdapter=null
  /** The inner class of the applybutton. */
  var selectupdate: SelectionAdapter=null
  /** A Composite . */
  var composite: Composite=null
  /** The SelectionAdapter selectok. */
  var selectok: SelectionAdapter=null

	this.buildGUI()


	/**The build adapters function for creating the adapters.*/
	def buildAdapters(){
		this.selectapply=new SelectionAdapter(){
			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.SelectionAdapter
			 * #widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			override def widgetSelected(event:SelectionEvent) {
				var olduser: AbstractUser = UserManagementWindow.this.userlist(UserManagementWindow.this.usertable.getSelectionIndex)
				val olduserid:BigInt=olduser.id
				if("Admin".equals(
          UserManagementWindow.this.usergroupbox.getText)){
					olduser=new Admin(olduser.name,olduser.password,0)
				}
				else{
					olduser=new MainUser(olduser.name,
							olduser.password,0)
				}
				olduser.id= olduserid
				if(UserManagementWindow.this.username.getText()
						.matches(MAIL_REGEX)){
					val message:Message=
            UserManagementWindow.this.client.sendChangeData(new ChangeData(olduser,
            UserManagementWindow.this.username.getText(),
            UserManagementWindow.this.passphrase.getText()
							.replace("<", "&lt").replace(">", "&gt")))
					if(message.isInstanceOf[ErrorReport]){
						val msgwin:Messagewindow=new Messagewindow(
								message.asInstanceOf[ErrorReport].message,

              UserManagementWindow.this.window.getDisplay)
            UserManagementWindow.this.messagewindow.openwindow()
					}
					else if(message.isInstanceOf[AbstractUser]){
						val tableadapter:TableItem=UserManagementWindow
						.this.usertable
						.getItem(UserManagementWindow.this.usertable
								.getSelectionIndex)
						tableadapter.setText(0, UserManagementWindow
								.this.username.getText())
						tableadapter.setText(1, UserManagementWindow
								.this.passphrase.getText())
						tableadapter.setText(2, UserManagementWindow
								.this.usergroupbox.getText)
					}
				}
				else{
					val msgwin:Messagewindow=new Messagewindow(
							"regex",

            UserManagementWindow.this.window.getDisplay)
					msgwin.openwindow()
				}
			}
		}

		this.selectupdate=new SelectionAdapter(){
			override def widgetSelected(event:SelectionEvent) {
        UserManagementWindow.this.getAllUsers
			}
		}
		this.selectprintlist=new SelectionAdapter() {
			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			override def widgetSelected(event:SelectionEvent) {
        UserManagementWindow.this.getAllUsers
				Printer.printIt(UserManagementWindow.this.userlist.toString())
			}
		}
		this.selectdelete= new SelectionAdapter(){
			override def widgetSelected(event:SelectionEvent) {
				if(UserManagementWindow.this.usertable.getSelectionIndex==(-1)){
          UserManagementWindow.this.messagewindow=new Messagewindow(
							"notselected",
							UserManagementWindow
							.this.window.getDisplay)
          UserManagementWindow.this.messagewindow.openwindow()
					return
				}
				if (UserManagementWindow.this.messagewindow
						.getShell.isDisposed) {
          UserManagementWindow.this.messagewindow
					.setShell(new Shell
					(UserManagementWindow.this.window.getDisplay
							,SWT.CLOSE | SWT.TITLE
						| SWT.MIN | SWT.MAX))
          UserManagementWindow.this.messagewindow
					.buildGUI("Statusmeldung",
            UserManagementWindow.this.window
							.getDisplay)
				}
        var abs: AbstractUser = null
				val  tableit:Array[TableItem]=
          UserManagementWindow.this.usertable.getSelection
				if("Admin".equals(tableit(0).getText(2))){
					abs= new Admin(tableit(0).getText(0),tableit(0).getText(1),0)
				}
				else{
					abs= new MainUser(tableit(0).getText(0)
							,tableit(0).getText(1),0)
				}
				abs.id= BigInt(tableit(0).getText(CONSTNUMBER3))
				val msg:Message=UserManagementWindow.this.client.deleteUser(abs)
				if(msg.isInstanceOf[ErrorReport]){
          UserManagementWindow.this.messagewindow
					.setMessage(msg.asInstanceOf[ErrorReport])
				}
				else if(msg.isInstanceOf[ConfirmReport]){
          UserManagementWindow.this.messagewindow
					.setMessage(msg.asInstanceOf[ConfirmReport])
          UserManagementWindow.this.usertable
					.remove(UserManagementWindow.this.usertable.getSelectionIndex)
          UserManagementWindow.this.getAllUsers
				}
        UserManagementWindow.this.messagewindow.openwindow()
			}
		}
		this.selectprint=new SelectionAdapter(){
			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.SelectionAdapter
			 * #widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			override def widgetSelected(event:SelectionEvent) {
				if(UserManagementWindow.this.usertable.getSelectionIndex==(-1)){
          UserManagementWindow.this.messagewindow=new Messagewindow(
							"notselected",
							UserManagementWindow
							.this.window.getDisplay)
          UserManagementWindow.this.messagewindow.openwindow()
					return
				}

				val  tableit:Array[TableItem]=
				UserManagementWindow.this.usertable.getSelection
				if("Admin".equals(tableit(0).getText(2))){
					val admin:Admin=new Admin(tableit(0).getText(0)
							,tableit(0).getText(1),0)
					admin.id= BigInt(tableit(0).getText(CONSTNUMBER3))
					Printer.printIt(admin)
				}
				else{
					val mainuser:MainUser=new MainUser(tableit(0).getText(0)
							,tableit(0).getText(1),0)
					mainuser.id= BigInt(tableit(0).getText(CONSTNUMBER3))
					Printer.printIt(mainuser)
				}
			}
		}
		this.selectlistentry=new SelectionAdapter() {
			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.SelectionAdapter
			 * #widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			override def widgetSelected(event:SelectionEvent) {
				val  item:Array[TableItem]= UserManagementWindow.this.usertable.getSelection
        UserManagementWindow.this.username.setText(item(0).getText(0))
        UserManagementWindow.this.passphrase.setText(item(0).getText(1))
				if("Admin".equals(item(0).getText(2))){
          UserManagementWindow.this.usergroupbox.select(0)
				}
				else{
          UserManagementWindow.this.usergroupbox.select(1)
				}
			}
		}
		this.selectok=new SelectionAdapter() {
				/* (non-Javadoc)
				 * @see org.eclipse.swt.events.SelectionAdapter
				 * #widgetSelected(org.eclipse.swt.events.SelectionEvent)
				 */
				@Override
				override def widgetSelected(event:SelectionEvent) {
          UserManagementWindow.this.window.close()
				}
			}
		this.usertable.addSelectionListener(this.selectlistentry)
		this.applybutton.addSelectionListener(this.selectapply)
		this.updatebutton.addSelectionListener(this.selectupdate)
		this.printbutton.addSelectionListener(this.selectprint)
		this.printlistbutton.addSelectionListener(this.selectprintlist)
		this.deletebutton.addSelectionListener(this.selectdelete)
		this.confirmbutton.addSelectionListener(this.selectok)
	}


	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionAdapter#
	 * widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	override def widgetSelected(event:SelectionEvent) {
		if (this.window.isDisposed) {
			this.window = new Shell (Languagewindow.getDisplay, SWT.CLOSE |
					SWT.TITLE | SWT.MIN | SWT.MAX)
			this.buildGUI()
		}
		this.window.open()
	}

	/**Builds the GUI.*/
	def buildGUI(){
		this.window.setSize(DISPLAYRESX, DISPLAYRESY)
		val primary:Monitor = Languagewindow.getDisplay.getPrimaryMonitor
		val bounds:Rectangle = primary.getBounds
		val rect:Rectangle = this.window.getBounds
		val xint = bounds.x+(bounds.width - rect.width)/2
		val yint = bounds.y+(bounds.height - rect.height)/2
		this.window.setLocation(xint, yint)
		this.resource=ResourceBundleStorage
			.getResourceBundle(ResourceBundleStorage.USERMANAGEMENT)
		this.composite = new Composite(this.window, SWT.BORDER)
		this.composite.setBounds(CONSTNUMBER5, CONSTNUMBER5
				,this.window.getSize.x-CONSTNUMBER15,
				this.window.getSize.y-CONSTNUMBER70)
		this.usertable=new Table(this.composite,
				SWT.MULTI| SWT.BORDER | SWT.FULL_SELECTION)
		this.usertable.setBounds(0, LIST_POS,
				this.window.getSize.x-CONSTNUMBER20,
				this.window.getSize.y-CONSTNUMBER218)
		this.usertable.setHeaderVisible(true)
		this.usertable.setLinesVisible(true)
		val tableColumn:TableColumn = new TableColumn(this.usertable,
				SWT.VIRTUAL)
		tableColumn.setWidth(TABLE_COL_WIDTH)
		tableColumn.setText(this.resource.getString("tabname"))
		val tableColumn1:TableColumn = new TableColumn(this.usertable,
				SWT.VIRTUAL)
		tableColumn1.setWidth(TABLE_COL_WIDTH/2)
		tableColumn1.setText(this.resource.getString("tabpass"))
		val tableColumn2:TableColumn = new TableColumn(this.usertable,
				SWT.VIRTUAL)
		tableColumn2.setWidth(TABLE_COL_WIDTH/CONSTNUMBER3)
		tableColumn2.setText(this.resource.getString("tabstatus"))
		val tableColumn3:TableColumn = new TableColumn(this.usertable,
				SWT.VIRTUAL)
		tableColumn3.setWidth(TABLE_COL_WIDTH/CONSTNUMBER5)
		tableColumn3.setText(this.resource.getString("tabid"))
		this.window.setText(this.resource.getString("UserManagementWindow"))
		this.applybutton=new Button(this.window, SWT.NONE)
		this.applybutton.setBounds(APPLY_POS,
				APPLY_POS, BUTTON_WIDTH,
				BUTTON_HEIGHT)
		this.applybutton.setText(this.resource.getString("Apply"))
		this.confirmbutton=new Button(this.window,SWT.NONE)
		this.confirmbutton.setText(this.resource.getString("Confirm"))
		this.confirmbutton.setBounds(this.window.getSize.
				x-BUTTON_WIDTH*2-CONSTNUMBER8,
				this.window.getSize.y-CONSTNUMBER60,BUTTON_WIDTH,
				BUTTON_HEIGHT)
		this.applybutton.setBounds(this.window.getSize.
				x-BUTTON_WIDTH-CONSTNUMBER8,
				this.window.getSize.y-CONSTNUMBER60, BUTTON_WIDTH,
				BUTTON_HEIGHT)
		this.deletebutton=new Button(this.window,SWT.NONE)
		this.deletebutton.setText(this.resource.getString("delete"))
		this.deletebutton.setBounds(this.window.getSize.
				x-BUTTON_WIDTH*CONSTNUMBER4-CONSTNUMBER8,
				this.window.getSize.y-CONSTNUMBER60, BUTTON_WIDTH,
				BUTTON_HEIGHT)
		this.updatebutton=new Button(this.window,SWT.NONE)
		this.updatebutton.setText(this.resource.getString("Update"))
		this.updatebutton.setBounds(this.window.getSize.
				x-BUTTON_WIDTH*CONSTNUMBER3-CONSTNUMBER8,
				this.window.getSize.y-CONSTNUMBER60, BUTTON_WIDTH,
				BUTTON_HEIGHT)
		this.printlistbutton=new Button(this.composite,SWT.NONE)
		this.printlistbutton.setText(this.resource.getString("printlist"))
		this.printlistbutton.setBounds(CONSTNUMBER490,
				PASSTEXT_POS,
				BUTTON_WIDTH+CONSTNUMBER20,
				BUTTON_HEIGHT)
		this.printbutton=new Button(this.composite,SWT.NONE)
		this.printbutton.setText(this.resource.getString("print"))
		this.printbutton.setBounds(CONSTNUMBER335,
				PASSTEXT_POS,
				BUTTON_WIDTH+CONSTNUMBER20,
				BUTTON_HEIGHT)
		this.username= new Text(this.composite, SWT.BORDER)
		this.username.setBounds(0,
				BUTTON_HEIGHT,
				TEXTFIELD_WIDTH,
				BUTTON_HEIGHT)
		this.passphrase= new Text(this.composite, SWT.BORDER)
		this.passphrase.setBounds(0,
				PASSTEXT_POS,
				TEXTFIELD_WIDTH,
				BUTTON_HEIGHT)
		this.namelabel= new Label(this.composite, SWT.NONE)
		this.namelabel.setText(this.resource.getString("namelabel"))
		this.namelabel.setBounds(0, UPLABXPOS,
				BUTTON_WIDTH*2,
				LABEL_HEIGHT)
		this.passlabel= new Label(this.composite, SWT.NONE)
		this.passlabel.setText(this.resource.getString("passlabel"))
		this.passlabel.setBounds(0,
				PASS_POS, BUTTON_WIDTH*2,
				LABEL_HEIGHT)
		this.statuslabel=new Label(this.composite,SWT.NONE)
		this.statuslabel.setText(this.resource.getString("combo"))
		this.statuslabel.setBounds(COMBO_XPOS,
				UPLABXPOS,
				COMBO_WIDTH
				,LABEL_HEIGHT)
		this.usergroupbox=new Combo(this.composite, SWT.BORDER)
		this.usergroupbox.setBounds(COMBO_XPOS,
				BUTTON_HEIGHT,
				COMBO_WIDTH
				, BUTTON_HEIGHT)
		this.usergroupbox.add("Admin")
		this.usergroupbox.add("MainUser")
		this.applybutton.setEnabled(false)
		this.printbutton.setEnabled(false)
		this.printlistbutton.setEnabled(false)
		this.deletebutton.setEnabled(false)
		if(!this.client.getUsertype.isAdmin){
			this.usergroupbox.setEnabled(false)
		}
		try {
			this.window.setImage(new Image(this.window.getDisplay,
					this.getClass.getResource("img/edit.png").openStream()))
		} catch{
      case e:IOException => SWTLogger.writeerror(e.getMessage)
    }
		this.buildAdapters()
	}

	/**Gets the userlist.*/
	def getAllUsers= {
		this.usertable.removeAll()
		val message:Message=this.client.getAllUsers
		if(message.isInstanceOf[ErrorReport]){
			this.messagewindow=new Messagewindow(
					message.asInstanceOf[ErrorReport].message,
					this.window.getDisplay)
			this.messagewindow.openwindow()
		}
		else{
			this.userlist= message.asInstanceOf[UserListMessage].userlist
			if(this.userlist.isEmpty){
				this.messagewindow=new Messagewindow(
						this
						.resource.getString("empty"),
						this.window.getDisplay)
				this.messagewindow.openwindow()
				this.printbutton.setEnabled(false)
				this.printlistbutton.setEnabled(false)
				this.applybutton.setEnabled(false)
				this.deletebutton.setEnabled(false)
			}
			else{
				for(abstractuser:AbstractUser <-this.userlist){
					this.tableitem=
						new TableItem(this.usertable, SWT.BORDER)
					this.tableitem
					.setText(0, abstractuser.name)
					this.tableitem
					.setText(1, abstractuser.password)
					if(abstractuser.isAdmin){
						this.tableitem
						.setText(2, "Admin")
					}
					else{
						this.tableitem
						.setText(2, "MainUser")
					}
					this.tableitem
					.setText(CONSTNUMBER3, abstractuser.id.toString())
				}
				this.printbutton.setEnabled(true)
				this.printlistbutton.setEnabled(true)
				this.applybutton.setEnabled(true)
				this.deletebutton.setEnabled(true)
			}
		}
	}
}
