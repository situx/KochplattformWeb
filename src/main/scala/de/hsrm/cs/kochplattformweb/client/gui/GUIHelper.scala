package de.hsrm.cs.kochplattformweb.client.gui

import org.eclipse.swt.graphics.Rectangle
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.Monitor
import org.eclipse.swt.widgets.Shell;


/** This is a help class for the other GUI classes.
 * @author Alex & Lehel
 */
object GUIHelper {


	/** The function makeMeCenter will put the window into the middle of the screen.
	 * @param display as Display
	 * @param window as Shell **/
	def makeMeCenter(display:Display, window:Shell) {
		val primary:Monitor = display.getPrimaryMonitor();
		val bounds:Rectangle = primary.getBounds();
		val rect:Rectangle = window.getBounds();
		val xint = bounds.x+(bounds.width - rect.width)/2;
		val yint = bounds.y+(bounds.height - rect.height)/2;
		window.setLocation(xint, yint);
	}
	/**
	 * Gets String of EnumAmountType in correct singular oder plural order.
	 * @return String of EnumAmountType
	 * @param enumtype from type EnumAmountType
	 */
	def enumAmountToString(enumtype:String):String= {
		return ResourceBundleStorage.getResourceBundle("EnumAmountType").getString(enumtype);
	}

}
