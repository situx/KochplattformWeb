package de.hsrm.cs.kochplattformweb.client.gui

import java.util.Locale;
import java.util.ResourceBundle;

/**Storage class for all Resource Bundles.
 * @author Timo Homburg
 */
object ResourceBundleStorage {
	/**Language file constant. {@value}*/
	val ADDMOVIEWINDOW="AddMovieWindow";
	/**Language file constant. {@value}*/
	val REGISTER="Register";
	/**Language file constant. {@value}*/
	val LANGUAGE="Language";
	/**Language file constant. {@value}*/
	val MENUE="Menue";
	/**Language file constant. {@value}*/
	val TABS="Tabs";
	/**Language file constant. {@value}*/
	val ABOUT="About";
	/**Language file constant. {@value}*/
	val USERMANAGEMENT="UserManagement";
	/**Language file constant. {@value}*/
	val HELP="Help";
	/**Language file constant. {@value}*/
	val LOGIN="Login";
	/**Language file constant. {@value}*/
	val MESSAGES="Messages";
	/**Language file constant. {@value}*/
	val RECIPEWINDOW="RecipeWindow";
	/**Language file constant. {@value}*/
	val RATINGWINDOW="RatingWindow";
	/** The instance of ResourceBundleStorage. */
	var locale:Locale=new Locale("en")

	/**Gets the required ResourceBundle.
	 * @param name the locale name
	 * @return the ResourceBundle*/
	def getResourceBundle(name:String):ResourceBundle={
		 ResourceBundle.getBundle(name,this.locale);
	}

	/**Gets the required ResourceBundle.
		* @param name the locale name
		* @return the ResourceBundle*/
	def getResourceBundle(name:String,locale:Locale):ResourceBundle={
		ResourceBundle.getBundle(name,locale);
	}

	/**Gets the language.
	 * @return the language*/
	def getLanguage:Locale={
		this.locale;
	}
	/**Sets the locale.
	 * @param locale the locale to set.*/
	def setLocale(locale:Locale){
		this.locale=locale;
	}
}
