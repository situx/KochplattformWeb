package de.hsrm.cs.kochplattformweb.client.gui

import java.util.ResourceBundle

import org.eclipse.swt.SWT
import org.eclipse.swt.graphics.Rectangle
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Table
import org.eclipse.swt.widgets.TableColumn

/**
 * This class contains the Composite with the 
 * @author Daniel Torpus
 */
class Mainviewtab {

	/** X-Position of the Mainviewtable{@value}. **/
	val TABLE_X = 0
	/** Y-Position of the Mainviewtable{@value}. **/
	val TABLE_Y = 0
	/** Height of the Mainviewtable{@value}. **/
	val TABLE_HEIGHT = 500
	/** Width of the Mainviewtable{@value}. **/
	val TABLE_WIDTH = 1000

	/** This field is the Table that contains the result of the last Searchrequest. **/
	var mainviewtable: Table=new Table(this.mainviewcomp, SWT.MULTI
		| SWT.BORDER | SWT.FULL_SELECTION)
	/** This field is the Table that contains Composite of this Class. **/
	var mainviewcomp: Composite=new Composite(Mainwindow.getTabfolder(), SWT.NONE)
	/**This field contains the ResourceBundel of this Class to change language.**/
	val resource:ResourceBundle=ResourceBundleStorage.getResourceBundle(
		ResourceBundleStorage.TABS)
	this.tabfoldercontent1()


	

	/**
	 *This Method is for preparing the content in the Tabfolder1(Mainview of
	 * displayed Recipes).
	 **/
	def tabfoldercontent1() {
		this.mainviewtable.setHeaderVisible(true)
		this.mainviewtable.setLinesVisible(true)
		this.mainviewtable.setBounds(new Rectangle(TABLE_X,
				TABLE_Y, TABLE_WIDTH,
				TABLE_HEIGHT))

		val tableColumn:TableColumn = new TableColumn(this.mainviewtable,
				SWT.VIRTUAL)
		tableColumn.setText(this.resource.getString("name"))
		val tableColumn1:TableColumn = new TableColumn(this.mainviewtable,
				SWT.VIRTUAL)
		tableColumn1.setText(this.resource.getString("category"))

		val tableColumn2:TableColumn = new TableColumn(this.mainviewtable,
				SWT.VIRTUAL)
		tableColumn2.setText(this.resource.getString("rating"))

		val tableColumn3:TableColumn = new TableColumn(this.mainviewtable,
				SWT.VIRTUAL)
		tableColumn3.setText(this.resource.getString("zombies"))

		val tableColumn4:TableColumn = new TableColumn(this.mainviewtable,
				SWT.VIRTUAL)
		tableColumn4.setText(this.resource.getString("ingredients"))

		Mainwindow.gettabFolderItem().setControl(this.mainviewcomp)

	}

	/**This Method returns the Mainviewcomposite of this class.
	 * @return mainviewcomp Composite of this class**/
	def getMainviewComp:Composite={
		 this.mainviewcomp
	}

	/**This Method returns the Mainviewtable of this class.
	 * @return mainviewtable Table of this class**/
	def getMainviewTable:Table={
		this.mainviewtable
	}
}
