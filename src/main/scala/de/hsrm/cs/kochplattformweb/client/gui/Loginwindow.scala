package de.hsrm.cs.kochplattformweb.client.gui

import java.io.IOException
import java.util.{ResourceBundle}


import de.hsrm.cs.kochplattformweb.client.{AbstractUser, Client}
import de.hsrm.cs.kochplattformweb.messages.{ConfirmReport, ErrorReport, Message, Registration}
import de.hsrm.cs.kochplattformweb.utils.SWTLogger
import org.eclipse.swt.SWT
import org.eclipse.swt.custom.CLabel
import org.eclipse.swt.events.SelectionAdapter
import org.eclipse.swt.events.SelectionEvent
import org.eclipse.swt.graphics.Image
import org.eclipse.swt.graphics.Point
import org.eclipse.swt.layout.FillLayout
import org.eclipse.swt.layout.GridData
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.widgets.Button
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.Group
import org.eclipse.swt.widgets.Shell
import org.eclipse.swt.widgets.Text

/**
 * This class generates a Window where the User is able to Test the Connection,Login or Register.
 * @author Daniel Torpus
 */
class Loginwindow(client:Client,display:Display,controller:Controller) extends SelectionAdapter{

	/**A constant Number {@value}.*/
	val CONSTNUMBER100=100
	/**A constant Number {@value}.*/
	val CONSTNUMBER400= 400
	/**A constant Number {@value}.*/
	val CONSTNUMBER170= 170
	/**A constant Number {@value}.*/
	val CONSTNUMBER50= 50
	/**A constant Number {@value}.*/
	val CONSTNUMBER130= 130
	/**A constant Number {@value}.*/
	val CONSTNUMBER80= 80
	/**A constant Number {@value}.*/
	val CONSTNUMBER200= 200
	/**A constant Number {@value}.*/
	val PORT= 42006

	/** Width of the Languagewindow{@value}. **/
	val HWINDOW_HEIGHT = 150
	/** Height of the Languagewindow{@value}. **/
	val HWINDOW_WIDTH = 450
	/**Regular Expression for validating usernames.*/
	val MAIL_REGEX=
		"^[-_.\\w]+@([0-9a-zA-Z][-\\w]*[0-9a-zA-Z]\\.){1,300}[a-zA-Z]{2,9}$"
	/** Window for this Class. **/
	var window: Shell=new Shell(SWT.CLOSE | SWT.TITLE | SWT.MIN | SWT.MAX)
	/** This field is for typing in the Loginname. **/
	var textloginname: Text=null
	/** This field is for typing in the Password. **/
	var textpassword: Text=null
	/** This field is for describing the Loginname-Label. **/
	var labellgname: CLabel=null
	/** This field is for describing the Password-Label. **/
	var labelpw: CLabel=null
	///** This field is for describing the Serveradress-Label.. **/
	//private transient CLabel labelserveradress
	/** This field is for typing in the Serveradress. **/
	var textserver: Text=null
	/** This field ist for Logging in with the Data
		* typed in the fields textlonginname and textpassword. **/
	var loginbutton: Button=null
	/** This field ist for Logging in with the Data typed in the field textserver. **/
	var testconbutton: Button=null
	/** This field ist for opening the Registerwindow. **/
	var registerbutton: Button=null
	/** This field is for storing the returned Messages of Request. **/
	var msg: Message=null
	/** This field contains an Object of the Messagewindowclass for Displaying Messages. **/
	var msgwindow: Messagewindow=null
	/**ResourceBundle for */
	val resource:ResourceBundle=ResourceBundleStorage.
		getResourceBundle(ResourceBundleStorage.LOGIN)
	//private transient static ResourceBundle loginpropfile
	/**Selection Adapter for the register Button.*/
	var registerselect:SelectionAdapter=null
	/**Selection Adapter for the testcon Button.*/
	var testconselect:SelectionAdapter=null
	/** Selection Adapter for the login Button. */
	var loginselect: SelectionAdapter=new SelectionAdapter() {
		@Override
		override def widgetSelected(sevent:SelectionEvent) {
			Loginwindow.this.msg = Loginwindow.this.controller.login(
				Loginwindow.this.textloginname.getText()
				,Loginwindow.this.textpassword.getText()
					.replace("<", "&lt").replace(">", "&gt")
				,Loginwindow.this.client)
			if(Loginwindow.this.msgwindow.getShell.isDisposed()){
				Loginwindow.this.msgwindow =
					new Messagewindow("Statusmeldung",
						Loginwindow.this.display)

			}
			Loginwindow.this.msgwindow.openwindow()
			if (Loginwindow.this.msg.isInstanceOf[AbstractUser]) {
				Mainwindow.getInstance().modus(Loginwindow.this.isStatus)
				Loginwindow.this.msgwindow.setMessage(Loginwindow.this.
					msg.getMessage()+" "+
					Loginwindow.this.resource
						.getString("loggedin"),false)
				/*Mainwindow.getInstance()
          .setUsername(((AbstractUser)
            this.msg).getName())
        Mainwindow.getInstance()
          .setUserpassword(((AbstractUser)
            this.msg).getPassword())*/
				Loginwindow.this.window.close()
			} else if (Loginwindow.this.msg.isInstanceOf[ErrorReport]) {
				Loginwindow.this.msgwindow.setMessage(
					Loginwindow.this.msg.getMessage(),true)

			}

		}

	}
	this.buildGUI()


	/**This Method returns the Serverconnection.
	 * @return servercon Serverconnection
	 **/
	def getSvc:Client= {
		this.client
	}

	/**This Method builds the Widgets of this Class.**/
	def  buildGUI() {

		this.window = new Shell(SWT.CLOSE | SWT.TITLE | SWT.MIN | SWT.MAX)
		this.window.setLayout(new GridLayout())
		this.window.setText(this.resource.getString("login"))
		this.window.setSize(HWINDOW_WIDTH,
				HWINDOW_HEIGHT)
		this.msgwindow = new Messagewindow("Statusmeldung",this.display)

		val group:Group = new Group(this.window, SWT.TOP)
		group.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING))
		group.setSize(CONSTNUMBER400,CONSTNUMBER100)
		val labelserveradress:CLabel = new CLabel(group, SWT.NONE)

		labelserveradress.setText(this.resource.getString("serveraddress"))

		labelserveradress.setSize(CONSTNUMBER170, CONSTNUMBER50)
		this.textserver = new Text(group, SWT.NONE)
		this.textserver.setSize(CONSTNUMBER170, CONSTNUMBER50)

		this.testconbutton = new Button(group, SWT.NONE)
		this.testconbutton.setSize(CONSTNUMBER130, CONSTNUMBER50)

		this.testconbutton.setText(this.resource.getString("testconnection"))

		val group2:Group = new Group(this.window,SWT.CENTER)
		group2.setSize(CONSTNUMBER400,CONSTNUMBER100)

		this.labellgname = new CLabel(group2, SWT.NONE)
		this.labellgname.setText(this.resource.getString("loginname"))

		this.labellgname.setSize(new Point(CONSTNUMBER80, CONSTNUMBER50))

		this.textloginname = new Text(group2, SWT.BORDER)
		this.textloginname.setSize(CONSTNUMBER80, CONSTNUMBER50)

		this.labelpw = new CLabel(group2, SWT.NONE)
		this.labelpw.setText(this.resource.getString("password"))
		//this.labelpw.setText(this.loginpropfile.getString("password")+":")
		this.labelpw.setSize(CONSTNUMBER80, CONSTNUMBER50)

		this.textpassword = new Text(group2, SWT.BORDER)
		this.textpassword.setSize(CONSTNUMBER80, CONSTNUMBER50)
		this.textpassword.setEchoChar('*')


		val group3:Group = new Group(this.window, SWT.DOWN)
		group3.setSize(CONSTNUMBER400,CONSTNUMBER100)

		this.loginbutton = new Button(group3, SWT.NONE)
		this.loginbutton.setSize(CONSTNUMBER200, CONSTNUMBER50)
		//this.loginbutton.setBounds(new Rectangle(183, 137, 108, 29))

		this.loginbutton.setText(this.resource.getString("login"))

		this.registerbutton = new Button(group3, SWT.NONE)
		this.registerbutton.setSize(CONSTNUMBER200, CONSTNUMBER50)
		this.registerbutton.setText(this.resource.getString("register"))

		if(!this.isStatus){
			this.textloginname.setVisible(false)
			this.textpassword.setVisible(false)
			this.labellgname.setVisible(false)
			this.labelpw.setVisible(false)
			this.testconbutton.setEnabled(true)
			this.textserver.setEnabled(true)
		}
		else{
			this.testconbutton.setEnabled(false)
			this.textserver.setEnabled(false)
		}
		this.buttonListeners()

		group.setLayout(new FillLayout())
		group2.setLayout(new FillLayout())
		group3.setLayout(new FillLayout())
		try {
			this.window.setImage(new Image(this.display,
					this.getClass().getResource("img/connect.png").openStream()))
		} catch{
			case e:IOException => 			SWTLogger.writeerror(e.getMessage());
		}
		GUIHelper.makeMeCenter(this.display, this.window)
	}

	/**This Method contains the ButtonListeners for this Class.**/
	def  buttonListeners(){


		this.loginbutton.addSelectionListener(this.loginselect)

		this.testconselect = new SelectionAdapter() {
			@Override
			override def widgetSelected(sevent:SelectionEvent) {
				if(Loginwindow.this.msgwindow.getShell.isDisposed()){
					Loginwindow.this.msgwindow =
						new Messagewindow("Statusmeldung",
							Loginwindow.this.display)
				}
				Loginwindow.this.client.getServeradress=Loginwindow.this.textserver.getText().toString
				Loginwindow.this.client.port=PORT
				Loginwindow.this.client.getLocalhostadress="localhost"
				Loginwindow.this.msg=Loginwindow.this.client.connect
//					this.servercon = new Serverconnection(
//
//					this.servercon = new Client(
//							this.textserver.getText(),
//							"localhost",42006)
				Loginwindow.this.msgwindow.setMessage(
					Loginwindow.this.msg.getMessage(),true)
				if (Loginwindow.this.msg.isInstanceOf[ConfirmReport]) {
					Loginwindow.this.textloginname.setVisible(true)
					Loginwindow.this.textpassword.setVisible(true)
					Loginwindow.this.labellgname.setVisible(true)
					Loginwindow.this.labelpw.setVisible(true)
					Loginwindow.this.testconbutton.setEnabled(false)
					Loginwindow.this.textserver.setEnabled(false)
					Loginwindow.this.msgwindow.openwindow()
					Mainwindow.getInstance().modus(Loginwindow.this.isStatus)
				} else if(Loginwindow.this.msg.isInstanceOf[ErrorReport]) {
					Loginwindow.this.textloginname.setVisible(false)
					Loginwindow.this.textpassword.setVisible(false)
					Loginwindow.this.labellgname.setVisible(false)
					Loginwindow.this.labelpw.setVisible(false)
					Loginwindow.this.testconbutton.setEnabled(true)
					Loginwindow.this.textserver.setEnabled(true)
					Loginwindow.this.msgwindow.openwindow()
				}
			}
		}
		this.testconbutton.addSelectionListener(this.testconselect)

		this.registerselect = new SelectionAdapter() {
				@Override
				override def widgetSelected(sevent:SelectionEvent) {
					if (Loginwindow.this.msgwindow.getShell.isDisposed()) {
						Loginwindow.this.msgwindow.setShell(new Shell
						(Loginwindow.this.display,SWT.CLOSE | SWT.TITLE
							| SWT.MIN | SWT.MAX))
						Loginwindow.this.msgwindow.buildGUI("Statusmeldung",
							Loginwindow.this.display)
					}
					if(Loginwindow.this.textloginname.getText()
							.matches(MAIL_REGEX)){
						Loginwindow.this.msg = Loginwindow.this.client.sendRegistration(new Registration(
							Loginwindow.this.textloginname.getText(),
							Loginwindow.this.textpassword.getText().replace("<", "&lt")
							.replace(">", "&gt")))
						if (Loginwindow.this.msg.isInstanceOf[ConfirmReport]) {
							Loginwindow.this.msgwindow.setMessage(
								Loginwindow.this.msg.asInstanceOf[ConfirmReport])
						}
						else if (Loginwindow.this.msg.isInstanceOf[ErrorReport]) {
							Loginwindow.this.msgwindow.setMessage(
								Loginwindow.this.msg.asInstanceOf[ErrorReport])
						}
					}
					else{
						Loginwindow.this.msgwindow.setMessage("regex",true)
						Loginwindow.this.textloginname.selectAll()
						Loginwindow.this.textpassword.setText("")
					}
					Loginwindow.this.msgwindow.openwindow()
				}
			}
		this.registerbutton.addSelectionListener(this.registerselect)
	}

	/**
	 *
	 * This method sets Widgets visible if the Server is available.
	 *
	 */
	def serverOn(){
		this.textloginname.setVisible(true)
		this.textpassword.setVisible(true)
		this.labellgname.setVisible(true)
		this.labelpw.setVisible(true)
	}
	/**
	 *
	 * This method hides Widgets  if the Server is not available.
	 */
	def serverOff(){
		this.textloginname.setVisible(false)
		this.textpassword.setVisible(false)
		this.labellgname.setVisible(false)
		this.labelpw.setVisible(false)
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#
	 * widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	override def widgetSelected(arg0:SelectionEvent) {
		if (this.window.isDisposed()) {
			this.window = new Shell(this.display,
					SWT.CLOSE | SWT.TITLE | SWT.MIN | SWT.MAX)
			this.buildGUI()
		}
		this.window.open()
	}


	/** This method is for opening the  **/
	def openWindow() {
		this.window.open()
	}

	/**
	 * This Method returns the  SelectionAdapters registerselect of this class.
	 * @return the registerselect
	 */
	def getRegisterselect()= {
		this.registerselect
	}

	/**
	 * This Method returns the  SelectionAdapters testconselect of this class.
	 * @return the testconselect
	 */
	def getTestconselect()= {
		this.testconselect
	}

	/**
	 * This Method returns the  SelectionAdapters loginselect of this class.
	 * @return the loginselect
	 */
	def getLoginselect:SelectionAdapter= {
		this.loginselect
	}


	/**Gets the SelectionAdapters of this class.
	 * @return a list of SelectionAdapters.*/
	def getSelect:List[SelectionAdapter]={
		val result:List[SelectionAdapter]=List[SelectionAdapter]()
		result:+(this.registerselect)
		result:+(this.testconselect)
		result:+(this.loginselect)
		result
	}

	/**
	 * This method returns the Serverstatus.
	 * @return boolean Serverstatus
	 * **/
	def isStatus:Boolean= {
		this.client.isStatus
	}

	/**
	 * This method returns the Text-Loginname-Widget of this Class.
	 * @return the textloginname
	 */
	def getTextloginname:Text= {
		this.textloginname
	}

	/**
	 * This method returns the Text-Password-Widget of this Class.
	 * @return the textpassword
	 */
	def getTextpassword:Text= {
		this.textpassword
	}

	/**
	 * This method returns the CLabel-Loginname-Widget of this Class.
	 * @return the labellgname
	 */
	def getLabellgname:CLabel= {
		this.labellgname
	}

	/**
	 * This method returns the CLabel-Password-Widget of this Class.
	 * @return the labelpw
	 */
	def getLabelpw:CLabel= {
		this.labelpw
	}

	/**
	 * This method returns the Text-Server-Widget of this Class.
	 * @return the textserver
	 */
	def getTextserver:Text= {
		this.textserver
	}

	/**
	 * This method returns the Button-Login-Widget of this Class.
	 * @return the loginbutton
	 */
	def getLoginbutton:Button= {
		this.loginbutton
	}

	/**
	 * This method returns the Button-Test-Connection-Widget of this Class.
	 * @return the testconbutton
	 */
	def getTestconbutton:Button= {
		this.testconbutton
	}

	/**
	 * This method returns the Button-Register-Widget of this Class.
	 * @return the registerbutton
	 */
	def getRegisterbutton:Button= {
		 this.registerbutton
	}

	/**
	 * This method returns the Client Instance of this Class.
	 * @return the servercon
	 */
	def getServercon:Client= {
		this.client
	}

	/**
	 * This method returns the Message Instance of this Class.
	 * @return the msg
	 */
	def getMsg:Message= {
		 this.msg
	}

	/**
	 * This method returns the Messagwindow Instance of this Class.
	 * @return the msgwindow
	 */
	def getMsgwindow:Messagewindow= {
		this.msgwindow
	}




	/**
	 * This method returns the Display of this Class.
	 * @return the display
	 */
	def getDisplay:Display= {
		 this.display
	}

	/**
	 * This method returns the Controller Instance of this Class.
	 * @return the controller
	 */
	def getController:Controller= {
		this.controller
	}

	/**
	 * This method returns the Shell of this Class.
	 * @return the window
	 */
	def getWindow:Shell= {
		this.window
	}


	/**This Method return the testconbutton of this Class.
	 * @return testconbutton Button to Test the Serverconnection**/
	def getconButton:Button= {
		this.testconbutton
	}
}
