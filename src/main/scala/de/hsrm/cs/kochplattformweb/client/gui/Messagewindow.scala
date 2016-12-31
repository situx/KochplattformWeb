package de.hsrm.cs.kochplattformweb.client.gui

import java.io.IOException
import java.util.ResourceBundle
import de.hsrm.cs.kochplattformweb.messages.{ConfirmReport, ErrorReport}
import de.hsrm.cs.kochplattformweb.utils.SWTLogger
import org.eclipse.swt.SWT
import org.eclipse.swt.events.SelectionAdapter
import org.eclipse.swt.events.SelectionEvent
import org.eclipse.swt.graphics.Image
import org.eclipse.swt.graphics.Point
import org.eclipse.swt.graphics.Rectangle
import org.eclipse.swt.layout.RowLayout
import org.eclipse.swt.widgets.Button
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.Group
import org.eclipse.swt.widgets.Label
import org.eclipse.swt.widgets.Monitor
import org.eclipse.swt.widgets.Shell




/**
 * This class generates a Window where the Infomessages for the Programm are displayed.
 * @author Daniel Torpus
 */
class Messagewindow(message:String,locale:Boolean,display:Display) extends SelectionAdapter{

	/** Height of the Messagewindow{@value}. **/
	val AWINDOW_WIDTH = 400
	/** Width of the Messagewindow{@value}. **/
	val AWINDOW_HEIGHT = 150
	/** Group X-Value of the third grouping{@value}. **/
	val GROUP_XSIZE = 400
	/** Group  Y-Value of the third grouping{@value}. **/
	val GROUP_YSIZE = 50
	/** Group1 Y-Value of the third grouping{@value}. **/
	val GROUP1_YSIZE = 150
	///** X-Position of the Infolabel. **/
	//val LABEL_X = 5
	///** Y-Position of the Infolabel. **/
	//val LABEL_Y = 5
	/** Width of the Infolabel{@value}. **/
	val LABEL_WIDTH = 300
	/** Height of the Infolabel{@value}. **/
	val LABEL_HEIGHT = 150
	/**The name of the ResourceBundle.*/
	val MESSAGES="Messages"

	/** Window for this Class. **/
	var window: Shell=new Shell(/*SWT.CLOSE | SWT.TITLE | SWT.MIN | SWT.MAX*/)
	/** Label for displaying the Infomessages. **/
	var labelinfo: Label=null
	/** Button to confirm the Infomessage and close the Window. **/
	var okbutton: Button=null
  /** Selection Adapter for the ok button. */
  var okbuttonselect: SelectionAdapter=null
  /** The ResourceBundle for all message types. */
  var resource: ResourceBundle=ResourceBundleStorage.getResourceBundle("Messages")


	/**
	 * This method initializes sShell.
	 * @param message Messagetext that should be displayed.
	 * @param display is an Instance of the Mainwindow
	 */
	def this(message:String,display:Display)=this(message,true,display)

	/**
	 * This method initializes sShell.
	 * @param message Messagetext that should be displayed.
	 * @param locale indicates if the string should also be localized
	 * @param display is an Instance of the Mainwindow
	 */
	def Messagewindow(message:String,locale:Boolean,display:Display) {
		var msg:String=message
		this.resource=ResourceBundleStorage
			.getResourceBundle(MESSAGES)
		this.window = new Shell(/*SWT.CLOSE | SWT.TITLE | SWT.MIN | SWT.MAX*/)
		if(locale){
			msg=this.resource.getString(message)
		}
		this.buildGUI(msg,display)
	}

	/**Second constructor for Messagewindow including an error report.
	 * @param error the Errorreport to display
	 * @param display the display needed by Messagewindow*/
	def Messagewindow(error:ErrorReport,display:Display){
		this.resource=ResourceBundleStorage.getResourceBundle("Messages")
		this.window = new Shell(/*SWT.CLOSE | SWT.TITLE | SWT.MIN | SWT.MAX*/)
		this.buildGUI(error.code+" : "+ResourceBundleStorage
				.getResourceBundle(MESSAGES)
				.getString(error.getMessage())+"\n"+error.message, display)
	}

	/*Third constructor for Messagewindow including a Confirmreport.
	 * @param confirm the Confirmreport to display
	 * @param display the display needed by Messagewindow*/
	/*def Messagewindow(confirm:ConfirmReport,display:Display){
		this(ResourceBundleStorage.
				getResourceBundle(MESSAGES).
				getString(confirm.getMessage()),display)
	}*/
	/**Builds the GUI of the 
	 * @param message the message to be displayed.
	 * @param display the display to be used*/
	def buildGUI(message:String,display:Display){
		var img: Image = null
		try {
			img = new Image(display,this.getClass()
					.getResource("error.png").openStream())
		} catch {
			case exept:IOException => 	SWTLogger.writeinfo("Bild konnte nicht geladen werden"+exept)
		}
		this.window.setLayout(new RowLayout())
		this.window.setText(this.resource.getString("Messagewindow"))
		this.window.setSize(new Point(AWINDOW_WIDTH,
				AWINDOW_HEIGHT))

		val primary:Monitor = Languagewindow
			.getDisplay.getPrimaryMonitor()
		val bounds:Rectangle = primary.getBounds()
		val rect:Rectangle = this.window.getBounds()
		val xint:Int = bounds.x+(bounds.width - rect.width)/2
		val yint:Int = bounds.y+(bounds.height - rect.height)/2
		SWTLogger.writeinfo("Xint: "+xint+" YInt:"+yint)
		this.window.setLocation(xint, yint)


	/*	this.sShell.addShellListener(new ShellAdapter() {
			def shellClosed(ShellEvent e){
				if (runner.isAlive()) {
					e.doit = false
				}

			}
		})*/
		val group3:Group = new Group(this.window, SWT.LEFT)
		group3.setSize(GROUP_XSIZE,GROUP_YSIZE)
		val label:Label = new Label(group3,SWT.NONE)
		label.setSize(GROUP_YSIZE,GROUP_YSIZE)
		label.setImage(img)

		val group:Group = new Group(this.window, SWT.CENTER)
		group.setSize(GROUP_XSIZE,GROUP1_YSIZE)
		this.labelinfo = new Label(group, SWT.WRAP | SWT.NONE)
		this.labelinfo.setSize(new Point(LABEL_WIDTH,
				LABEL_HEIGHT))
		this.labelinfo.setText(message)


		val group2:Group = new Group(this.window, SWT.RIGHT)
		group2.setSize(GROUP_XSIZE,GROUP_YSIZE)
		this.okbutton = new Button(group2, SWT.NONE)
		this.okbutton.setSize(GROUP_YSIZE*2,GROUP_YSIZE)
		this.okbutton.setText("Ok")

		this.okbuttonselect = new SelectionAdapter() {
			@Override
			override def widgetSelected(sevent:SelectionEvent) {
				Messagewindow.this.window.close()
			}
		}
		this.okbutton.addSelectionListener(this.okbuttonselect)
		//group.setLayout(new FillLayout())
		//group2.setLayout(new RowLayout())
		this.window.pack()
		//this.labelinfo.setImage(img)
		//this.errorimg.drawImage(img, 0, 0)
	}

	/**
	 * This Method returns the Ok-Button of this Class.
	 * @return logger
	 */
	def getOkButton:Button= {
		this.okbutton
	}


	/**
	 * This Method sets the text that should be displayed.
	 * @param message Messagetext that should be displayed.
	 * @param locale indicates if the string should be localized
	 */
	def setMessage(message:String,locale:Boolean) {
		if(locale){
			this.labelinfo.setText(this.resource.getString(message))
		}
		else{
			this.labelinfo.setText(message)
		}
	}

	/**
	 * This Method sets the text that should be displayed.
	 * @param error the error report being displayed
	 */
	def setMessage(error:ErrorReport) {
		this.labelinfo.setText(error.code+" : "+
				this.resource.getString(error.getMessage())+
				"\n"+error.message)
	}

	/**
	 * This Method sets the text that should be displayed.
	 * @param confirm the confirm report being displayed
	 */
	def setMessage(confirm:ConfirmReport) {
		this.labelinfo.setText(this.resource.getString(confirm.getMessage()))
	}

	/**
	 * This Method gets the text that should be displayed.
	 * @return the displayed message.
	 */
	def getLabelInfo:Label= {
		this.labelinfo
	}

	/**
	 * This Method gets the text that should be displayed.
	 * @return the displayed message.
	 */
	def getMessage:String= {
		 this.labelinfo.getText()
	}

	/** This method is for opening the Languagewindow. **/
	def openwindow(){
		this.window.open()
	}
	/**This Method return the Shell of this Class.
	 * @return window Shell of this Class**/
	def getShell:Shell={
		 this.window
	}

	/**This Method sets the Shell of this Class.
	 * @param shell Shell of this Class**/
	def setShell(shell:Shell){
		this.window=shell
	}
	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionAdapter#
	 * widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	override def widgetSelected(event:SelectionEvent) {
		if (this.window.isDisposed) {
			this.window = new Shell(this.window.getDisplay,
					SWT.CLOSE | SWT.TITLE | SWT.MIN | SWT.MAX)
		}
		this.window.open()
	}
}
