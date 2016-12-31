package de.hsrm.cs.kochplattformweb.client.gui

import de.hsrm.cs.kochplattformweb.client.Client
import de.hsrm.cs.kochplattformweb.db.EnumAmountType
import de.hsrm.cs.kochplattformweb.db.EnumAmountType.EnumAmountType
import de.hsrm.cs.kochplattformweb.messages.{IngredientMessage, Loginmessage, Message, RecipeListMessage};

/**
 * This Class contains the filled Interface-Methods from the ControllerInterface.
 * @author Daniel Torpus
 */
class Controller extends ControllerInterface {


	@Override
	def login(name:String, password:String, servercon:Client):Message= {
		servercon.sendUser(Loginmessage(name, password));
 	}

	@Override
	def bind(server:String,localhost:String , port:Int):Client= {
		//return new Client(server, localhost, port);
		new Client("",0);
	}

	@Override
	def logout() {
	}


	@Override
	def register(name:String, password:String) {
	}

	@Override
	def search(ingm:IngredientMessage):RecipeListMessage= {
		null;
	}


	/** Get the enumerate names.
	 * @return result as a list of Strings
	 */
	def getEnumNames():List[String]={
		val result = List[String]();
		for(value:EnumAmountType<-EnumAmountType.values){
			result:+(value.toString());
		}
		return result;
	}
}
