package de.hsrm.cs.kochplattformweb.client.gui

import java.io.IOException
import java.util.ResourceBundle

import de.hsrm.cs.kochplattformweb.utils.SWTLogger
import org.eclipse.swt.SWT
import org.eclipse.swt.browser.Browser
import org.eclipse.swt.events.SelectionAdapter
import org.eclipse.swt.events.SelectionEvent
import org.eclipse.swt.graphics.Image
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.Shell;

/**
 * This class generates a Window that holds the Helptext.
 *
 * @author Daniel Torpus
 * @version
 * @see
 * @since
 */
class Helpwindow(display:Display) extends SelectionAdapter  {

	/** Height of the Helpwindow{@value} . **/
	val HWINDOW_HEIGHT = 700;
	/** Width of the Helpwindow{@value} . **/
	val HWINDOW_WIDTH = 900;
	/**The Resourcebundle for */
	val resource:ResourceBundle=ResourceBundleStorage.getResourceBundle(ResourceBundleStorage.HELP);;
  /** Window for this Class. **/
  var window: Shell = new Shell(SWT.CLOSE | SWT.TITLE | SWT.MIN | SWT.MAX);

	this.buildGUI();



	/** This method is for opening the  **/
	def openWindow() {
		this.window.open();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#
	 * widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	override def widgetSelected(arg0:SelectionEvent) {
		if (this.window.isDisposed()) {
			this.window = new Shell(this.display,
					SWT.CLOSE | SWT.TITLE | SWT.MIN | SWT.MAX);
			this.buildGUI();
		}
		this.window.open();
	}

	/** This method will build the GUI for this class. **/
	def buildGUI() {
		this.window.setText(this.resource.getString("titel"));
		val browser:Browser = new Browser(this.window, SWT.NONE);
		browser.setText(this.resource.getString("helps"));
		browser.setBounds(0, 0, HWINDOW_WIDTH, HWINDOW_HEIGHT);
		try {
			this.window.setImage(new Image(this.display,
					this.getClass().getResource("img/help.png").openStream()));
		} catch{
      case e:IOException => SWTLogger.writeerror(e.getMessage());
    }
		this.window.pack();
		GUIHelper.makeMeCenter(this.display, this.window);
	}

	/**
	 * This Method return the Shell of this Class.
	 * @return window Shell of this Class
	 **/
	def getShell():Shell= {
		return this.window;
	}

	/**
	 * This Method sets the Shell of this Class.
	 * @param shell the shell to set
	 **/
	def setShell(shell:Shell) {
		this.window = shell;
	}
}
