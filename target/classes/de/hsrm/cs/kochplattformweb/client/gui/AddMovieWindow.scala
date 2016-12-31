package de.hsrm.cs.kochplattformweb.client.gui

import java.net.URI
import java.net.URISyntaxException
import java.util.ResourceBundle

import de.hsrm.cs.kochplattformweb.client.Client
import de.hsrm.cs.kochplattformweb.utils.SWTLogger
import org.eclipse.swt.SWT
import org.eclipse.swt.events.SelectionAdapter
import org.eclipse.swt.events.SelectionEvent
import org.eclipse.swt.widgets.Button
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.Label
import org.eclipse.swt.widgets.Shell
import org.eclipse.swt.widgets.TableItem
import org.eclipse.swt.widgets.Text




class AddMovieWindow(display:Display,recipeid:BigInt) extends SelectionAdapter{
	/**A constant Number CONSTNUMBER32 {@value}.*/
	val CONSTNUMBER32=32
	/**A constant Number {@value}.*/
	val CONSTNUMBER165=165
	/**A constant Number {@value}.*/
	val CONSTNUMBER170=170
	/**Button height with value {@value}.*/
	val BUTTON_HEIGHT=28
	/**Button position with value {@value}.*/
	val BUTTON_POS=70
	/**Button width with value {@value}.*/
	val BUTTON_WIDTH=150
	/**Window height with value {@value}.*/
	val WINDOW_HEIGHT=140
	/**Window with value {@value}.*/
	val WINDOW_WIDTH=320
	
	val URL_REGEX ="^http[s]?://[-a-zA-Z0-9_.:]+[-a-zA-Z0-9_:@&?=+,.!/~*'%$]*$"

	/** The apply button for the ImportWindow. */
	var apply: Button=new Button(this.window,SWT.BORDER)
	/**Selection Adapter for the apply button.*/
	var applyselect:SelectionAdapter=null
	/**SelectionAdapter for the import radio button.*/
	var importselect:SelectionAdapter=null
	/** The ok button for the ImportWindow. */
	var okbutton: Button=new Button(this.window,SWT.BORDER)
	/** SelectionAdapter for the ok button. */
	var okbuttonselect: SelectionAdapter=null
	/**Checks if the refresh or import option is selected.*/
	val refreshorimport:Boolean=false
	/** The ResourceBundle to use. */
	var resource: ResourceBundle=null
	/**SelectionAdapter for the refresh radio button.*/
	val refreshselect:SelectionAdapter=null
	/** The shell used for the import window. */
	var window: Shell = new Shell(display, SWT.CLOSE | SWT.TITLE
		| SWT.MIN | SWT.MAX)
	/**The shell used for the import window.*/
	/** Choose Button for the user backup. */
	var textcomment: Text=null
	/** Choose Button for the recipe backup. */
	var texturl: Text=null
	/** Serverconnection to Connect to the Server. **/
	val client:Client=null
	var msgwindow: Messagewindow=null
	this.buildGUI()


	def openwindow() {
		this.window.open()
	}
	/**Builds the gui components.*/
	def buildGUI(){
		this.window.setBounds(0, 0, WINDOW_WIDTH*2,
				WINDOW_HEIGHT)
		GUIHelper.makeMeCenter(this.display, this.window)
		this.resource=ResourceBundleStorage.getResourceBundle("AddMovieWindow")
		this.msgwindow = new Messagewindow("Statusmeldung", this.display)
		this.window.setText(this.resource.getString("AddMovies"))
		this.apply = new Button(this.window,SWT.BORDER)
		this.apply.setText(this.resource.getString("apply"))
		this.apply.setBounds(this.window.getSize().x-CONSTNUMBER165, BUTTON_POS,
				BUTTON_WIDTH, BUTTON_HEIGHT)
		this.okbutton=new Button(this.window,SWT.BORDER)
		this.okbutton.setText(this.resource.getString("ok"))
		this.okbutton.setBounds(this.window.getSize().x-CONSTNUMBER170-BUTTON_WIDTH,
				BUTTON_POS,
				BUTTON_WIDTH,BUTTON_HEIGHT)

		this.texturl=new Text(this.window,SWT.BORDER)
		this.texturl.setBounds(WINDOW_WIDTH, CONSTNUMBER32, BUTTON_WIDTH*2,
				BUTTON_HEIGHT)
		this.textcomment=new Text(this.window,SWT.BORDER)
		this.textcomment.setBounds(WINDOW_WIDTH,0, BUTTON_WIDTH*2, BUTTON_HEIGHT)


		val labelcomment:Label = new Label(this.window, SWT.BORDER)
		labelcomment.setText(this.resource.getString("labelcomment"))
		labelcomment.setBounds(0, 0, BUTTON_WIDTH*2,
				BUTTON_HEIGHT)
		val labelurladdress:Label = new Label(this.window, SWT.BORDER)
		labelurladdress.setBounds(0, CONSTNUMBER32, BUTTON_WIDTH*2, BUTTON_HEIGHT)
		labelurladdress.setText(this.resource.getString("labelurladdress"))
		buttonListener()
	}

	/**Button Listener the action performed here*/
	def buttonListener(){
		this.okbuttonselect=new SelectionAdapter(){
			override def widgetSelected(event:SelectionEvent) {
				AddMovieWindow.this.window.close()
			}
		}
		this.okbutton.addSelectionListener(this.okbuttonselect)

		this.applyselect=new SelectionAdapter(){
			override def widgetSelected(event:SelectionEvent) {
				if (AddMovieWindow.this.msgwindow.getShell.isDisposed()) {
					AddMovieWindow.this.msgwindow = new Messagewindow(
							"Statusmeldung", AddMovieWindow.this.display)
				}
				if("".equals(AddMovieWindow.this.textcomment.getText())
						|| "".equals(AddMovieWindow.this.texturl.getText())){
					AddMovieWindow.this.msgwindow
					.setMessage("AllFieldsMovie", true)
					AddMovieWindow.this.msgwindow.openwindow()
				}else{
					try {
						val regurl:String = AddMovieWindow.this.texturl.getText()
						if(regurl.matches(URL_REGEX)){
							//Mainwindow.getChangetab().getURLmap+:
							//	AddMovieWindow.this.textcomment.getText()->new URI(regurl)
							val urlvid:TableItem = new TableItem(Mainwindow.getChangetab().getURLTable, SWT.NONE)
							urlvid.setText(0 , AddMovieWindow.this.textcomment.getText())
							Mainwindow.getChangetab().getURLTable.setEnabled(true)
							AddMovieWindow.this.window.close()
						}else{
							AddMovieWindow.this.msgwindow
							.setMessage("URLregEX", true)
							AddMovieWindow.this.msgwindow.openwindow()
						}
					} catch{
						case urie:URISyntaxException =>SWTLogger.writeerror(urie.getMessage)
					}
				}
			}
		}
		this.apply.addSelectionListener(this.applyselect)
	}

	@Override
	override def widgetSelected(event:SelectionEvent) {
		if (this.window.isDisposed()) {
			this.window = new Shell(this.display,
					SWT.CLOSE | SWT.TITLE | SWT.MIN | SWT.MAX)
			this.buildGUI()
		}
		this.window.open()
	}
}
