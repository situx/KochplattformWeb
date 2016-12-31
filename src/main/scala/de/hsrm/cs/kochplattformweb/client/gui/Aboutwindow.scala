package de.hsrm.cs.kochplattformweb.client.gui

import java.io.IOException
import java.net.URI
import java.net.URISyntaxException
import java.util.ResourceBundle
import de.hsrm.cs.kochplattformweb.utils.SWTLogger
import org.eclipse.swt.SWT
import org.eclipse.swt.events.SelectionAdapter
import org.eclipse.swt.events.SelectionEvent
import org.eclipse.swt.graphics.Font
import org.eclipse.swt.graphics.FontData
import org.eclipse.swt.graphics.Image
import org.eclipse.swt.layout.RowLayout
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.Event
import org.eclipse.swt.widgets.Label
import org.eclipse.swt.widgets.Link
import org.eclipse.swt.widgets.Listener
import org.eclipse.swt.widgets.Shell

/**
 * AboutWindow class.
 * @author Alexander Aring
 * @author Daniel Torpus
 * @version
 * @see
 * @since
 */
class Aboutwindow(display:Display) extends SelectionAdapter {
	/** Fontsize for headline font. **/
	val FONT_SIZE = 32
	/** Fontsize for label font. **/
	val FONT_SIZE_LABEL = 14
	/** Fontsize for link font. **/
	val FONT_SIZE_LINK = 12
	/** Space between shell and layout. **/
	val SPACE = 22

	/**
	 * Linklabel event listener.
	 */
	val linklistener:Listener = new Listener() {
		def handleEvent(event:Event) {
			try {
				java.awt.Desktop.getDesktop.browse(new URI(event.text))
			} catch{
        case e:IOException => 				SWTLogger.writeerror(e.getMessage)
        case e:URISyntaxException => 				SWTLogger.writeerror(e.getMessage)
      }
		}
	}


	/**The ResourceBundle for Aboutwindow.*/
	val resource:ResourceBundle =ResourceBundleStorage.getResourceBundle(ResourceBundleStorage.ABOUT)
	/**
		* Shell of Process.
		*/
	var window: Shell=new Shell(this.display, SWT.CLOSE | SWT.TITLE | SWT.MIN | SWT.MAX)

	this.buildGui()


	/**
	 * addLinkLabel to Shell.
	 * @param mail mailadress of name.
	 * @param name the name of teamuser.
	 * @see
	 * @since
	 */
	def addLinkLabel(mail:String, name:String){
		val linklabel:Link  = new Link(this.window, SWT.BORDER)
		val fontDat:Array[FontData] = linklabel.getFont.getFontData
		fontDat(0).setHeight(FONT_SIZE_LINK)
		val labelfont:Font = new Font(this.display,fontDat(0))
		linklabel.setFont(labelfont)
		linklabel.addListener(SWT.Selection, this.linklistener)
		linklabel.setText("<a href=\"mailto:"+mail+"\">"+name+"</a>")
	}
	/**
	 * Set up Shell GUI.
	 * @return
	 * @see
	 * @since
	 */
	def buildGui() {
		this.window = new Shell(this.display, SWT.CLOSE | SWT.TITLE | SWT.MIN | SWT.MAX)
		this.window.setText(this.resource.getString("about"))
		try {
			this.window.setImage(new Image(this.display,
					this.getClass.getResource("img/about.png").openStream()))
		} catch{
      case e:IOException => 			SWTLogger.writeerror(e.getMessage)
    }
		val layout:RowLayout = new RowLayout(SWT.HORIZONTAL | SWT.CENTER)
		layout.center = true
		layout.marginTop = SPACE
		layout.marginLeft = SPACE
		layout.marginRight = SPACE
		layout.marginBottom = SPACE
		this.window.setLayout(layout)
		val aboutlabel:Label = new Label(this.window, SWT.CENTER)
		aboutlabel.setText(this.resource.getString("about"))
		val fontDat:Array[FontData] = aboutlabel.getFont.getFontData
		fontDat(0).setHeight(FONT_SIZE)
		aboutlabel.setFont(new Font(this.display,fontDat(0)))
		val logolabel:Label  = new Label(this.window, SWT.CENTER)
		try {
			val logo:Image = new Image(this.display,
					this.getClass.getResource("img/logo.png").openStream())
			logolabel.setImage(logo)
		} catch{
      case e:IOException => 			SWTLogger.writeerror(e.getMessage)
    }
    fontDat(0).setHeight(FONT_SIZE_LABEL)
		val infolabel:Label = new Label(this.window, SWT.CENTER)
		infolabel.setText(this.resource.getString("swt"))
		infolabel.setFont(new Font(this.display,fontDat(0)))
		val infolabel2:Label = new Label(this.window, SWT.CENTER)
		infolabel2.setText(this.resource.getString("hsrm"))
		infolabel2.setFont(new Font(this.display,fontDat(0)))
		val infolabel3:Label = new Label(this.window, SWT.CENTER)
		infolabel3.setText(this.resource.getString("version"))
		infolabel3.setFont(new Font(this.display,fontDat(0)))
		val infolabel4:Label = new Label(this.window, SWT.CENTER)
		infolabel4.setText("")
		infolabel4.setFont(new Font(this.display,fontDat(0)))
		this.addLinkLabel("alex@test.de", "Alexander Aring")
		this.addLinkLabel("timo@test.de", "Timo Homburg")
		this.addLinkLabel("dirk@test.de", "Dirk Preissner")
		this.addLinkLabel("lehel@test.de", "Lehel Eses")
		this.addLinkLabel("daniel@test.de", "Daniel Torpus")
		this.addLinkLabel("daniel@test.de", "Daniel Schröpfer")
		this.addLinkLabel("philipp@test.de", "Philipp Macho")
		this.window.pack()
		GUIHelper.makeMeCenter(this.display, this.window)
	}


	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#
	 * widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	override def widgetSelected(arg0:SelectionEvent) {
		if (this.window.isDisposed) {
			this.window = new Shell
			(this.display, SWT.CLOSE | SWT.TITLE | SWT.MIN | SWT.MAX)
			this.buildGui()
		}
		this.window.open()
	}



	/**
	 * This Method returns the Diplay of this Class.
	 * @return the display
	 */
	def getDisplay:Display= {
		this.display
	}

	/**
	 * This Method returns the Listener of this Class.
	 * @return the linklistener
	 */
	def getLinklistener:Listener= {
		this.linklistener
	}

	/**
	 * This Method returns the Shell of this Class.
	 * @return the window
	 */
	def getWindow:Shell= {
		this.window
	}
}
