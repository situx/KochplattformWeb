package de.hsrm.cs.kochplattformweb.client.gui

import java.io.IOException
import java.util.ResourceBundle

import de.hsrm.cs.kochplattformweb.client.Client
import de.hsrm.cs.kochplattformweb.messages.{ConfirmReport, ErrorReport, Message}
import de.hsrm.cs.kochplattformweb.utils.SWTLogger
import org.eclipse.swt.SWT
import org.eclipse.swt.events.SelectionAdapter
import org.eclipse.swt.events.SelectionEvent
import org.eclipse.swt.graphics.Image
import org.eclipse.swt.widgets.Button
import org.eclipse.swt.widgets.Combo
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.Label
import org.eclipse.swt.widgets.Shell

/**BackupWindow for the servers' backup management.
 * @author Timo Homburg
 */
class BackupWindow(display:Display,client:Client) extends SelectionAdapter{
	/**Button width with value {@value}.*/
	val COMBO_POS=160
	/**Button width with value {@value}.*/
	val COMBO_POSH=35
	/**Button position with value {@value}.*/
	val BUTTON_POS=70
	/**Button height with value {@value}.*/
	val BUTTON_HEIGHT=28
	/**Button width with value {@value}.*/
	val BUTTON_WIDTH=140
	/**Window height with value {@value}.*/
	val WINDOW_HEIGHT=140
	/**Window with value {@value}.*/
	val WINDOW_WIDTH=320
	/**A CONST NUMBER 10 CONSTNUMBER10 {@value}.*/
	val CONSTNUMBER10=10
	/**A CONST NUMBER 20 CONSTNUMBER20 {@value}.*/
	val CONSTNUMBER20=20
	/**A CONST NUMBER 32 CONSTNUMBER32 {@value}.*/
	val CONSTNUMBER32 = 32
	/**A CONST NUMBER 165 CONSTNUMBER165 {@value}.*/
	val CONSTNUMBER165=165
	/**A CONST NUMBER 170 CONSTNUMBER170 {@value}.*/
	val CONSTNUMBER170=170
	/** The apply button for the */
	var apply: Button=null
	/** SelectionAdapter for the apply button. */
	var applyselect: SelectionAdapter=null
	/** The Combobox for choosing which backup will be selected. */
	var backcombo: Combo=null
	/** Label for the Combobox. */
	var combolabel: Label=null
	/** The apply button for the */
	var okbutton: Button=null
	/** SelectionAdapter for the user radio button. */
	var okbuttonselect: SelectionAdapter=null
	/** Choose Button for the recipe backup. */
	var radiorecipes: Button=null
	/** SelectionAdapter for the recipe radio button. */
	var radiorecselect: SelectionAdapter=null
	/** Choose Button for the user backup. */
	var radiouser: Button=null
	/** SelectionAdapter for the user radio button. */
	var radiouserselect: SelectionAdapter=null
	/** The ResourceBundle to use. */
	var resource: ResourceBundle=null
	/** Decider if the user or recipe DB ist backed up. */
	var userorrecipe: Boolean=false
	/** The shell used for the backup window. */
	var window: Shell = new Shell(display, SWT.CLOSE | SWT.TITLE
		| SWT.MIN | SWT.MAX)

	this.buildGUI()



	/**Method for building the needed adapters for this class.*/
	def buildAdapters(){
		this.radiouserselect=new SelectionAdapter(){
			override def widgetSelected(event:SelectionEvent) {
				BackupWindow.this.userorrecipe=false
			}
		}
		this.radiorecselect=new SelectionAdapter(){
			override def widgetSelected(event:SelectionEvent) {
				BackupWindow.this.userorrecipe=true
			}
		}
		this.okbuttonselect=new SelectionAdapter(){
			override def widgetSelected(event:SelectionEvent) {
				BackupWindow.this.window.close()
			}
		}
		this.okbutton.addSelectionListener(this.okbuttonselect)
		this.applyselect=new SelectionAdapter(){
			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.SelectionAdapter
			 * #widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			override def widgetSelected(event:SelectionEvent) {
				val backup:String=
					BackupWindow.this.backcombo.getText
				var messagewin: Messagewindow = new Messagewindow("Statusmeldung",
					BackupWindow.this.display)
				if(backup.isEmpty){
					messagewin=new Messagewindow("nobackup",
						BackupWindow.this.display)
				}
				else{
					val message:Message=
						BackupWindow.this.client.serverinputbackup(
							Integer
						.valueOf(backup.subSequence(backup.length()-1,
								backup.length()).toString()),
						BackupWindow.this.userorrecipe)
					if(message.isInstanceOf[ErrorReport]){
						messagewin=new Messagewindow(message.asInstanceOf[ErrorReport].message,
							BackupWindow.this.display)
					}
					else if(message.isInstanceOf[ConfirmReport]){
						messagewin=new Messagewindow(message.asInstanceOf[ConfirmReport].message,
								BackupWindow.this.display)
					}
				}
				messagewin.openwindow()
			}
		}
		this.apply.addSelectionListener(this.applyselect)
		this.radiorecipes.addSelectionListener(this.radiorecselect)
		this.radiouser.addSelectionListener(this.radiouserselect)
	}


	/**Builds the GUI of this window.*/
	def buildGUI(){
		this.window.setBounds(0, 0, WINDOW_WIDTH,
				WINDOW_HEIGHT)
		GUIHelper.makeMeCenter(this.display, this.window)
		this.resource=ResourceBundleStorage.getResourceBundle("Backup")
		this.window.setText(this.resource.getString("Backup"))
		this.backcombo = new Combo(this.window, SWT.BORDER)
		this.backcombo.add("Backup 0")
		this.backcombo.add("Backup 1")
		this.backcombo.add("Backup 2")
		this.backcombo.setBounds(COMBO_POS,
				COMBO_POSH,
				BUTTON_WIDTH,BUTTON_HEIGHT)
		this.backcombo.select(0)
		this.combolabel=new Label(this.window,SWT.BORDER)
		this.combolabel.setText(this.resource.getString("backupchoose"))
		this.combolabel.setBounds(COMBO_POS+CONSTNUMBER10,
				2,
				BUTTON_WIDTH-CONSTNUMBER20,BUTTON_HEIGHT)
		this.apply = new Button(this.window,SWT.BORDER)
		this.apply.setText(this.resource.getString("apply"))
		this.apply.setBounds(this.window.getSize().x-CONSTNUMBER165, BUTTON_POS,
				BUTTON_WIDTH, BUTTON_HEIGHT)
		this.okbutton=new Button(this.window,SWT.BORDER)
		this.okbutton.setText(this.resource.getString("ok"))
		this.okbutton.setBounds(this.window.getSize().x-CONSTNUMBER170-BUTTON_WIDTH,
				BUTTON_POS,
				BUTTON_WIDTH,BUTTON_HEIGHT)
		this.radiorecipes=new Button(this.window,SWT.RADIO)
		this.radiorecipes.setBounds(0, 0, BUTTON_WIDTH,
				BUTTON_HEIGHT)
		this.radiorecipes.setText(this.resource.getString("recDB"))
		this.radiouser=new Button(this.window,SWT.RADIO)
		this.radiouser.setText(this.resource.getString("usrDB"))
		this.radiouser.setBounds(0, CONSTNUMBER32, BUTTON_WIDTH,
				BUTTON_HEIGHT)
		this.radiorecipes.setSelection(true)
		try {
			this.window.setImage(new Image(this.display,
					this.getClass().getResource("img/backup.png").openStream()))
		} catch{
			case e:IOException =>			SWTLogger.writeerror(e.getMessage())
		}
		this.buildAdapters()
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

}
