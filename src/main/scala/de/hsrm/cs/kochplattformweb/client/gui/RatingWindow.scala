package de.hsrm.cs.kochplattformweb.client.gui

import java.util.ResourceBundle

import de.hsrm.cs.kochplattformweb.client.Client
import de.hsrm.cs.kochplattformweb.db.Rating
import de.hsrm.cs.kochplattformweb.messages.{ErrorReport, Message, XMLRatingMessage}
import de.hsrm.cs.kochplattformweb.parser.XMLparser
import org.eclipse.swt.SWT
import org.eclipse.swt.events.SelectionAdapter
import org.eclipse.swt.events.SelectionEvent
import org.eclipse.swt.graphics.Point
import org.eclipse.swt.graphics.Rectangle
import org.eclipse.swt.widgets.Button
import org.eclipse.swt.widgets.Combo
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.Label
import org.eclipse.swt.widgets.Shell
import org.eclipse.swt.widgets.Text


/**
 * This class generates a Window where it is possible to Register an new User.
 * @author Philipp Macho
 * @version
 * @see
 * @since
 */
class RatingWindow(client:Client, recipeid:BigInt, display:Display) {

	/** Width of the Registerwindow{@value}. **/
	val HWINDOW_WIDTH = 450
	/** Height of the Registerwindow{@value}. **/
	val HWINDOW_HEIGHT = 450

	/** X-Position of the Textfield textname{@value}. **/
	val LABELRATING_X = 10
	/** Y-Position of the Textfield LABELRATING_Y{@value}. **/
	val LABELRATING_Y = 10
	/** Height of the Textfield LABELRATING_WIDTH{@value}. **/
	val LABELRATING_WIDTH = 80
	/** Width of the Textfield LABELRATING_HEIGHT{@value}. **/
	val LABELRATING_HEIGHT = 30

	/** X-Position of the Label LABELCOMMENT_X{@value}. **/
	val LABELCOMMENT_X = 10
	/** Y-Position of the Label LABELCOMMENT_Y{@value}. **/
	val LABELCOMMENT_Y = 45
	/** Height of the Label LABELCOMMENT_WIDTH{@value}. **/
	val LABELCOMMENT_WIDTH = 80
	/** Width of the Label LABELCOMMENT_HEIGHT{@value}. **/
	val LABELCOMMENT_HEIGHT = 30

	/** X-Position of the Textfield RATINGCOMBO_X{@value}. **/
	val RATINGCOMBO_X = 100
	/** Y-Position of the Textfield RATINGCOMBO_Y{@value}. **/
	val RATINGCOMBO_Y = 10
	/** Heigth of the Textfield RATINGCOMBO_WIDTH{@value}. **/
	val RATINGCOMBO_WIDTH = 300
	/** Width of the Textfield RATINGCOMBO_HEIGHT{@value}. **/
	val RATINGCOMBO_HEIGHT = 30

	/** X-Position of the Label TEXTCOMMENT_X{@value}. **/
	val TEXTCOMMENT_X = 100
	/** Y-Position of the Label TEXTCOMMENT_Y{@value}. **/
	val TEXTCOMMENT_Y = 45
	/** Height of the Label TEXTCOMMENT_WIDTH{@value}. **/
	val TEXTCOMMENT_WIDTH = 300
	/** Width of the Label TEXTCOMMENT_HEIGHT{@value}. **/
	val TEXTCOMMENT_HEIGHT = 300

	/** X-Position of the Button COMMITBUTTON_X{@value}. **/
	val COMMITBUTTON_X = 175
	/** Y-Position of the Button COMMITBUTTON_Y{@value}. **/
	val COMMITBUTTON_Y = 380
	/** Height of the Button COMMITBUTTON_WIDTH{@value}. **/
	val COMMITBUTTON_WIDTH = 100
	/** Width of the Button COMMITBUTTON_HEIGHT{@value}. **/
	val COMMITBUTTON_HEIGHT = 30
	/** Width of the Button CONST6{@value}. **/
	val CONST6 = 6

	/** Window for this Class. **/
	var window: Shell=new Shell()
	this.window.setText(this.resource.getString("RatingWindow"))
	this.window.setSize(new Point(HWINDOW_WIDTH,HWINDOW_HEIGHT))
	/** Textfield for typing in the Loginname. **/
	var textcomment: Text=null
	/** Textfield for typing in the Password. **/
	var comborating: Combo=null
	/** Label for the Loginname. **/
	var commentlabel: Label=null
	/** Label for the Password. **/
	var ratinglabel: Label=null
	/** Button to Submit the Registerinformation. **/
	var commitButton: Button=null
	/** Serverconnection to Connect to the Server. **/
	var servcon: Client=null
	/**Selection Adapter for the ok button.*/
	val commitButtonselect:SelectionAdapter=new SelectionAdapter() {

		@Override
		override def widgetSelected(sevent:SelectionEvent) {
			val message:Message=RatingWindow.this.client.setRating(RatingWindow.this.recipeid,
				Rating(RatingWindow.this.comborating.getText.toDouble,
					RatingWindow.this.textcomment.getText(),
					RatingWindow.this.client.getUsertype.id))
			if(message.isInstanceOf[ErrorReport]){
				new Messagewindow(
					message.asInstanceOf[ErrorReport].message,RatingWindow.this.display).openwindow()
			}
			else if(message.isInstanceOf[XMLRatingMessage]){
				val parsit:XMLparser = new XMLparser()
				val ratelist:List[Rating] = parsit.parseRating(message.asInstanceOf[XMLRatingMessage].message)
				val buffer:StringBuffer= new StringBuffer(500)
				val iter:Iterator[Rating] = ratelist.iterator
				var ratingiterator: Rating=null
				buffer.append(RatingWindow.this.resource.getString("Avg"))
				buffer.append(": ")
				buffer.append(iter.next().rating)
				buffer.append("\n\n")
				while (iter.hasNext){
					ratingiterator = iter.next
					buffer.append(RatingWindow.this.resource.getString("Rating"))
					buffer.append(": ")
					buffer.append(ratingiterator.rating)
					buffer.append('\n')
					buffer.append(ratingiterator.comment)
					buffer.append("\n\n")
				}
				Mainwindow.getChangetab().getRatingText.setText(buffer.toString())
				RatingWindow.this.window.close()
			}
		}
	}
	/**ResourceBundle for the localisation.*/
	val resource:ResourceBundle=ResourceBundleStorage.getResourceBundle(
		ResourceBundleStorage.RATINGWINDOW)

	this.buildGUI()
	this.buttonListeners()


	/**This Method return the Shell of this Class.
	 * @return window Shell of this Class**/
	def getShell:Shell= {
		this.window
	}

	/**This Method sets the Shell of  this class.
	 * @param shell window Shell to set.**/
	def setShell(shell:Shell) {
		this.window=shell
	}

	/** This method is for opening the Registerwindow.
	 * @param servconn Serverconnection
	 **/
	def openwindow(servconn:Client) {
		this.servcon = servconn
		this.window.open()
	}

	/**This Method builds the Widgets of this Class.**/
	def buildGUI() {

		this.comborating = new Combo(this.window, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.READ_ONLY |SWT.BORDER)
		this.comborating.setVisibleItemCount(CONST6)
		this.comborating.setBounds(new Rectangle(RATINGCOMBO_X,
				RATINGCOMBO_Y, RATINGCOMBO_WIDTH,
				RATINGCOMBO_HEIGHT))
		this.comborating.add("0.0")
		this.comborating.add("1.0")
		this.comborating.add("2.0")
		this.comborating.add("3.0")
		this.comborating.add("4.0")
		this.comborating.add("5.0")
		this.comborating.setText("0.0")

		this.textcomment = new Text(this.window, SWT.MULTI
				| SWT.H_SCROLL | SWT.V_SCROLL |SWT.BORDER)
		this.textcomment.setBounds(new Rectangle(TEXTCOMMENT_X,
				TEXTCOMMENT_Y, TEXTCOMMENT_WIDTH,
				TEXTCOMMENT_HEIGHT))

		this.ratinglabel = new Label(this.window, SWT.NONE)
		this.ratinglabel.setText(this.resource.getString("RatingLabel"))
		this.ratinglabel.setBounds(new Rectangle(LABELRATING_X,
				LABELRATING_Y, LABELRATING_WIDTH,
				LABELRATING_HEIGHT))

		this.commentlabel = new Label(this.window, SWT.NONE)
		this.commentlabel.setText(this.resource.getString("CommentLabel"))
		this.commentlabel.setBounds(new Rectangle(LABELCOMMENT_X,
				LABELCOMMENT_Y, LABELCOMMENT_WIDTH,
				LABELCOMMENT_HEIGHT))

		this.commitButton = new Button(this.window, SWT.NONE)
		this.commitButton.setBounds(new Rectangle(COMMITBUTTON_X,
				COMMITBUTTON_Y, COMMITBUTTON_WIDTH,
				COMMITBUTTON_HEIGHT))
		this.commitButton.setText(this.resource.getString("Commit"))
		GUIHelper.makeMeCenter(this.display, this.window)
	}

	/**This Method contains the ButtonListeners for this Class.**/
	def buttonListeners() {
		this.commitButton.addSelectionListener(this.commitButtonselect)
	}

	/**
	 * This Method returns the Shell of this Class.
	 * @return the window
	 */
	def getWindow:Shell= {
		 this.window
	}

	/**
	 * This Method returns the Text-Widget textname of this Class.
	 * @return the textname
	 */
	def getTextRating:Combo= {
		 this.comborating
	}

	/**
	 * This Method returns the Text-Widget textpassword of this Class.
	 * @return the textpassword
	 */
	def getTextComment:Text= {
		 this.textcomment
	}

	/**
	 * This Method returns the CLabel-Widget namelabel of this Class.
	 * @return the namelabel
	 */
	def getCommentlabel:Label= {
		 this.commentlabel
	}

	/**
	 * This Method returns the CLabel-Widget passwordlabel of this Class.
	 * @return the passwordlabel
	 */
	def getRatinglabel:Label= {
		 this.ratinglabel
	}

	/**
	 * This Method returns the Button-Widget okbutton of this Class.
	 * @return the okbutton
	 */
	def getcommitButton:Button= {
		 this.commitButton
	}

	/**
	 * This Method returns the Client Instance of this Class.
	 * @return the servcon
	 */
	def getServcon:Client= {
		 this.servcon
	}

	/**
	 * This Method returns the SelectionAdapter of this Class.
	 * @return the okbuttonselect
	 */
	def getcommitButtonselect:SelectionAdapter= {
		 this.commitButtonselect
	}
}
