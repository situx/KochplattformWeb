package de.hsrm.cs.kochplattformweb.client.gui

import java.io.IOException
import java.util.ResourceBundle

import de.hsrm.cs.kochplattformweb.client.Client
import de.hsrm.cs.kochplattformweb.messages.{ConfirmReport, ErrorReport}
import de.hsrm.cs.kochplattformweb.utils.SWTLogger
import org.eclipse.swt.SWT
import org.eclipse.swt.events.SelectionAdapter
import org.eclipse.swt.events.SelectionEvent
import org.eclipse.swt.graphics.Image
import org.eclipse.swt.widgets.Button
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.Shell


/**Importwindow including the synchronisation options for the database.
 * @author Timo Homburg
 */
class ImportWindow(display:Display,client:Client) extends SelectionAdapter{

	/**A constant Number CONSTNUMBER32 {@value}.*/
	val  CONSTNUMBER32=32
	/**A constant Number {@value}.*/
	val  CONSTNUMBER165=165
	/**A constant Number {@value}.*/
	val  CONSTNUMBER170=170
	/**Button height with value {@value}.*/
	val  BUTTON_HEIGHT=28
	/**Button position with value {@value}.*/
	val  BUTTON_POS=70
	/**Button width with value {@value}.*/
	val  BUTTON_WIDTH=150
	/**Window height with value {@value}.*/
	val  WINDOW_HEIGHT=140
	/**Window with value {@value}.*/
	val  WINDOW_WIDTH=320
	/** The apply button for the */
	var apply: Button=null
	/** Selection Adapter for the apply button. */
	var applyselect: SelectionAdapter=null
  /** SelectionAdapter for the import radio button. */
  var importselect: SelectionAdapter=null
	/** The ok button for the */
	var okbutton: Button=null
  /** SelectionAdapter for the ok button. */
  var okbuttonselect: SelectionAdapter=null
	/** Checks if the refresh or import option is selected. */
	var refreshorimport: Boolean=false
	/** The ResourceBundle to use. */
	var resource: ResourceBundle=null
	/** Choose Button for the user backup. */
	var radioimport: Button=null
	/** Choose Button for the recipe backup. */
	var radiorefresh: Button=null
	/** SelectionAdapter for the refresh radio button. */
	var refreshselect: SelectionAdapter=null
	/** The shell used for the import window. */
	var window: Shell=new Shell(display, SWT.CLOSE | SWT.TITLE
		| SWT.MIN | SWT.MAX)
	this.buildGUI()


	/**Builds the gui components.*/
	def  buildGUI(){
		this.window.setBounds(0, 0, WINDOW_WIDTH,
				WINDOW_HEIGHT)
		GUIHelper.makeMeCenter(this.display, this.window)
		this.resource=ResourceBundleStorage.getResourceBundle("Import")
		this.window.setText(this.resource.getString("import"))
		this.apply = new Button(this.window,SWT.BORDER)
		this.apply.setText(this.resource.getString("apply"))
		this.apply.setBounds(this.window.getSize().x-CONSTNUMBER165, BUTTON_POS,
				BUTTON_WIDTH, BUTTON_HEIGHT)
		this.okbutton=new Button(this.window,SWT.BORDER)
		this.okbutton.setText(this.resource.getString("ok"))
		this.okbutton.setBounds(this.window.getSize().x-CONSTNUMBER170-BUTTON_WIDTH,
				BUTTON_POS,
				BUTTON_WIDTH,BUTTON_HEIGHT)
		this.radiorefresh=new Button(this.window,SWT.RADIO)
		this.radiorefresh.setBounds(0, 0, BUTTON_WIDTH*2,
				BUTTON_HEIGHT)
		this.radiorefresh.setText(this.resource.getString("refresh"))
		this.radioimport=new Button(this.window,SWT.RADIO)
		this.radioimport.setText(this.resource.getString("import"))
		this.radioimport.setBounds(0, CONSTNUMBER32, BUTTON_WIDTH*2,
				BUTTON_HEIGHT)
		this.radiorefresh.setSelection(true)
		try {
			this.window.setImage(new Image(this.display,
					this.getClass().getResource("img/import.png").openStream()))
		} catch{
			case e:IOException => SWTLogger.writeerror(e.getMessage())
		}
		this.buttonListener()
	}

	/**Implements the buttonListeners of this class.*/
	def  buttonListener(){
		this.refreshselect=new SelectionAdapter(){
			override def  widgetSelected(event:SelectionEvent) {
				ImportWindow.this.refreshorimport=false
			}
		}
		this.radiorefresh.addSelectionListener(this.refreshselect)
		this.importselect=new SelectionAdapter(){
			override def  widgetSelected(event:SelectionEvent) {
				ImportWindow.this.refreshorimport=true
			}
		}
		this.radioimport.addSelectionListener(this.importselect)
		this.okbuttonselect=new SelectionAdapter(){
			override def  widgetSelected(event:SelectionEvent) {
				ImportWindow.this.window.close()
			}
		}
		this.okbutton.addSelectionListener(this.okbuttonselect)
		this.applyselect=new SelectionAdapter(){
			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			override def  widgetSelected(event:SelectionEvent) {
        val message = ImportWindow.this.client.synchronise(ImportWindow.this.refreshorimport)
				//message = this.client.importDB()
				if(message.isInstanceOf[ErrorReport]){
					new Messagewindow(
							message.asInstanceOf[ErrorReport].message,ImportWindow.this.display).openwindow()
				}
				else if(message.isInstanceOf[ConfirmReport]){
					new Messagewindow(
							message.asInstanceOf[ConfirmReport].message,
						ImportWindow.this.display).openwindow()
				}
			}
		}
		this.apply.addSelectionListener(this.applyselect)
	}
	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	override def  widgetSelected(event:SelectionEvent) {
		if (this.window.isDisposed) {
			this.window = new Shell(this.display,
					SWT.CLOSE | SWT.TITLE | SWT.MIN | SWT.MAX)
			this.buildGUI()
		}
		this.window.open()
	}
}
