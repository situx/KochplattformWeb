package de.hsrm.cs.kochplattformweb.client.gui

import java.io.IOException
import java.util.Locale
import java.util.ResourceBundle

import de.hsrm.cs.kochplattformweb.client.Client
import de.hsrm.cs.kochplattformweb.utils.SWTLogger
import org.eclipse.swt.SWT
import org.eclipse.swt.events.SelectionAdapter
import org.eclipse.swt.events.SelectionEvent
import org.eclipse.swt.graphics.Font
import org.eclipse.swt.graphics.FontData
import org.eclipse.swt.graphics.Image
import org.eclipse.swt.widgets.Button
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.Label
import org.eclipse.swt.widgets.Shell


/**
 * This class generates a Window where the Language for the Programm can be choosed.
 * @author Daniel Torpus
 */
object Languagewindow {
	/** Height of the Englishbutton{@value}. **/
	val  ENGLISHBUTTON_H = 50
	/** Width of the Englishbutton{@value}. **/
	val  ENGLISHBUTTON_W = 200
	/** X-Position of the Englishbutton{@value}. **/
	val  ENGLISHBUTTON_X = 425
	/** Y-Position of the Englishbutton{@value}. **/
	val  ENGLISHBUTTON_Y = 413
	/** Fontsize{@value}. **/
	val  FONT_SIZE = 18
	/** Height of the Germanbutton{@value}. **/
	val  GERMANBUTTON_H = 50
	/** Width of the Germanbutton{@value}. **/
	val  GERMANBUTTON_W = 200
	/** X-Position of the Germanbutton{@value}. **/
	val  GERMANBUTTON_X = 53
	/** Y-Position of the Germanbutton{@value}. **/
	val  GERMANBUTTON_Y = 413
	/** Height of the LANGUAGELABEL{@value}. **/
	val  LANGUAGELABEL_H = 86
	/** Width of the LANGUAGELABEL{@value}. **/
	val  LANGUAGELABEL_W = 678
	/** X-Position of the LANGUAGELABEL{@value}. **/
	val  LANGUAGELABEL_X = 0
	/** Y-Position of the LANGUAGELABEL{@value}. **/
	val  LANGUAGELABEL_Y = 322
	/** Height of the LANGUAGELABEL{@value}. **/
	val  LOGOLABEL_H = 260
	/** Width of the LANGUAGELABEL{@value}. **/
	val  LOGOLABEL_W = 360
	/** X-Position of the LANGUAGELABEL{@value}. **/
	val  LOGOLABEL_X = 160
	/** Y-Position of the LANGUAGELABEL{@value}. **/
	val  LOGOLABEL_Y = 36
	/** Height of the Languagewindow{@value}. **/
	val  LWINDOW_HEIGHT = 536
	/** Width of the Languagewindow{@value}. **/
	val  LWINDOW_WIDTH = 678
  /** The instance of Languagewindow{@value }. */
  //var instance: Languagewindow = this

	/** This field is for choosing English Language. **/
	var englishbutton: Button=new Button(this.window, SWT.PUSH)
	/**Selection Adapter for the english button.*/
	val englishselect:SelectionAdapter=new SelectionAdapter() {
    @Override
    override def widgetSelected(sevent:SelectionEvent){
    Languagewindow.this.window.close()
    ResourceBundleStorage.setLocale(new Locale("en","US"))
    Mainwindow
  }
  }
	/** This field is for choosing German Language. **/
	var germanbutton: Button=new Button(this.window, SWT.PUSH)
	/**Selection Adapter for the german button.*/
	val germanselect:SelectionAdapter=new SelectionAdapter() {
    @Override
    override def widgetSelected(sevent:SelectionEvent){
    Languagewindow.this.window.close()
    ResourceBundleStorage.setLocale(new Locale("de","DE"))
    Mainwindow
  }
  }
	/** This field is the Locale of this Class. **/
	val locale:Locale=new Locale(System.getProperty("user.language"))
	/** This field is the ResourceBundle of this Class. **/
	val localepropfile:ResourceBundle=ResourceBundle.getBundle("Language", this.locale)
	/** Window for this Class. **/
	val window:Shell=new Shell(SWT.CLOSE | SWT.TITLE | SWT.MIN | SWT.MAX)
  val display:Display=null
  val client:Client=null



	/**
	 * This method returns the Instance of 
	 * @return instance Instance of Languagewindow
	 **/
	def getInstance(client:Client,display:Display) ={
		this.buildGUI()
		this.buttonListener()
		//if(instance==null){
		//	instance=new Languagewindow(null,null)
		//}
		/*if (instance.window.isDisposed()) {
			instance.window = new Shell(instance.display,
					SWT.CLOSE | SWT.TITLE | SWT.MIN | SWT.MAX)
			instance.buildGUI()
		}*/
		this
	}

	/**This Method builds the Widgets of this Class.
	 * @return
	 * @see
	 * @since
	 **/
	def buildGUI(){
		this.window.setText(this.localepropfile.getString("title"))
		this.window.setSize(LWINDOW_WIDTH,
				LWINDOW_HEIGHT)
		try {
			this.window.setImage(new Image(this.display, this.getClass.
					getResource("img/icon.png").openStream()))
			this.window.setImage(new Image(this.display,
					this.getClass.getResource("img/icon.png").openStream()))
		} catch{
			case e:IOException =>SWTLogger.writeerror(e.getMessage)
		}
		this.germanbutton = new Button(this.window, SWT.PUSH)
		this.germanbutton.setLocation(GERMANBUTTON_X,
				GERMANBUTTON_Y)
		this.germanbutton.setSize(GERMANBUTTON_W,
				GERMANBUTTON_H)
		this.germanbutton.setText(this.localepropfile.getString("german"))

		this.englishbutton = new Button(this.window, SWT.PUSH)
		this.englishbutton.setLocation(ENGLISHBUTTON_X,
				ENGLISHBUTTON_Y)
		this.englishbutton.setSize(ENGLISHBUTTON_W,
				ENGLISHBUTTON_H)
		this.englishbutton.setText(this.localepropfile.getString("english"))
		val languagelabel:Label = new Label(this.window, SWT.CENTER)
		languagelabel.setText(this.localepropfile.getString("choose"))
		languagelabel.setLocation(LANGUAGELABEL_X,
				LANGUAGELABEL_Y)
		languagelabel.setSize(LANGUAGELABEL_W,
				LANGUAGELABEL_H)
		val fontDat:Array[FontData] = languagelabel.getFont.getFontData
		fontDat(0).setHeight(FONT_SIZE)
		languagelabel.setFont(new Font(this.display,fontDat(0)))
		val logolabel:Label = new Label(this.window, SWT.CENTER)
		logolabel.setSize(LOGOLABEL_W, LOGOLABEL_H)
		logolabel.setLocation(LOGOLABEL_X, LOGOLABEL_Y)
		try {
			val logo:Image = new Image(this.display,
					this.getClass().getResource("img/logo.png").openStream())
			logolabel.setImage(logo)
		} catch{
			case e:IOException =>SWTLogger.writeerror(e.getMessage())
		}
		GUIHelper.makeMeCenter(this.display, this.window)
	}

	/**This Method contains the ButtonListeners for this Class.**/
	def buttonListener(){
		this.germanbutton.addSelectionListener(this.germanselect)
		this.englishbutton.addSelectionListener(this.englishselect)
	}


	/**
	 * This is the Mainmethod for creating the Mainwindow.
	 *
	 * @param args Main parameter
	 */
	def main(args:Array[String]) {
		this.window.open()
		while (!this.window.isDisposed) {
			if (!this.display.readAndDispatch()) {
				this.display.sleep()
			}
		}
	}

	/**Gets the SelectionAdapters of this class.
	 * @return a list of SelectionAdapters.*/
	def getSelect:List[SelectionAdapter]={
		val result:List[SelectionAdapter]=List[SelectionAdapter]()
		result:+ this.germanselect
		result:+ this.englishselect
		result
	}

	/**Gets the display used by this class.
	 * @return the used display*/
	def getDisplay:Display={
		this.display
	}

	/**
	 * This Method return the Englishbutton of this Class.
	 * @return the englishbutton
	 */
	def getEnglishbutton:Button= {
		this.englishbutton
	}

	/**
	 * This Method return the English-Selection-Adapter of this Class.
	 * @return the englishselect
	 */
	def getEnglishselect:SelectionAdapter= {
		this.englishselect
	}

	/**
	 * This Method return the Germanbutton of this Class.
	 * @return the germanbutton
	 */
	def getGermanbutton:Button= {
		this.germanbutton
	}

	/**
	 * This Method return the German-Selection-Adapter of this Class.
	 * @return the germanselect
	 */
	def getGermanselect:SelectionAdapter= {
		this.germanselect
	}

	/**
	 * This Method return the Locale of this Class.
	 * @return the locale
	 */
	def getLocale:Locale= {
		this.locale
	}

	/**
	 * This Method return the ResourceBundle of this Class.
	 * @return the localepropfile
	 */
	def getLocalepropfile:ResourceBundle= {
		this.localepropfile
	}

	/**
	 * This Method return the Shell of this Class.
	 * @return the window
	 */
	def getWindow:Shell= {
		this.window
	}

	/**
	 * This Method returns the client of this Class.
	 * @return the client
	 */
	def getClient:Client= {
		this.client
	}

}
