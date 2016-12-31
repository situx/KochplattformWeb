package de.hsrm.cs.kochplattformweb.client.gui

import java.io.IOException

import org.eclipse.swt.SWT
import org.eclipse.swt.events.SelectionAdapter
import org.eclipse.swt.events.SelectionEvent
import org.eclipse.swt.graphics.Image
import org.eclipse.swt.graphics.Point
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.Label
import org.eclipse.swt.widgets.Shell
import de.hsrm.cs.kochplattformweb.client.Client
import de.hsrm.cs.kochplattformweb.utils.SWTLogger

/**
 * This class generates a Window that holds the Helptext.
 * @author Daniel Torpus
 * @version
 * @see
 * @since
 */
class Userwindow(display:Display, client:Client) extends SelectionAdapter {

	/** Height of the Helpwindow{@value} . **/
	val UWINDOW_HEIGHT = 80
	/** Width of the Helpwindow{@value} . **/
	val UWINDOW_WIDTH = 300
	/** Window for this Class. **/
	var window: Shell=new Shell(this.display,SWT.CLOSE | SWT.TITLE | SWT.MIN | SWT.MAX)


	/** Contentlabel of the Window holding the text. **/
	var namelabel: Label=new Label(this.window, SWT.TOP)
	/** Contentlabel of the Window holding the text. **/
	var passwordlabel: Label=new Label(this.window, SWT.DOWN)

	this.buildGUI()


	/**
	 * The Method to build the GUI for this class .**/
	def buildGUI(){
		this.window = new Shell(this.display,SWT.CLOSE | SWT.TITLE | SWT.MIN | SWT.MAX)
		this.window.setText("Userwindow")
		this.window.setLayout(new GridLayout())
		this.window.setSize(new Point(UWINDOW_WIDTH,UWINDOW_HEIGHT))
		this.namelabel = new Label(this.window, SWT.TOP)

//		this.namelabel.setBounds(new Rectangle(NAMELABEL_X,
//				NAMELABEL_Y, NAMELABEL_HEIGHT,
//				NAMELABEL_WIDTH))
		this.namelabel.setText("Name: "+this.client.getUsertype.name)

		this.passwordlabel = new Label(this.window, SWT.DOWN)

//		this.passwordlabel.setBounds(new Rectangle(PASSWORDLABEL_X,
//				PASSWORDLABEL_Y, PASSWORDLABEL_HEIGHT,
//				PASSWORDLABEL_WIDTH))

		this.passwordlabel.setText("Passwort: "+this.client.getUsertype.password)
		GUIHelper.makeMeCenter(this.display, this.window)
		//this.window.pack()
		try {
			this.window.setImage(new Image(this.window.getDisplay,
					this.getClass.getResource("img/info.png").openStream()))
		} catch{
			case e:IOException => SWTLogger.writeerror(e.getMessage)
		} 
	}
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#
	 * widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	override def widgetSelected(arg0:SelectionEvent) {
		if (this.window.isDisposed) {
			this.window = new Shell(this.display, SWT.CLOSE | SWT.TITLE
					| SWT.MIN | SWT.MAX)
			this.buildGUI()
		}
		this.namelabel.setText("Name :"+this.client.getUsertype.name)
		this.passwordlabel.setText("Password :"+this.client.getUsertype.password)
		this.window.open()
	}


	/** This method is for opening the Helpwindow. **/
	def openWindow() {
		this.window.open()
	}

	/**
	 * This Method return the Shell of this Class.
	 * @return window Shell of this Class
	 **/
	def getShell:Shell= {
		 this.window
	}

	/**
	 * This Method sets the Shell of this Class.
	 *
	 * @param shell
	 *            the shell to set
	 **/
	def setShell(shell:Shell) {
		this.window = shell
	}


}
