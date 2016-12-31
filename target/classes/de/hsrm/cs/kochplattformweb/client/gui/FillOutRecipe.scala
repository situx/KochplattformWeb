package de.hsrm.cs.kochplattformweb.client.gui


import de.hsrm.cs.kochplattformweb.db.{Ingredient, Recipe};


/**
 * This Interface is for RecipeWindow to handle client.
 * @author aahri001
 * @version
 * @see
 * @since
 */
trait FillOutRecipe {
	/**
	 * getIngredients from the db to gui RecipeWindow.
	 * @return the list with the Ingredients.
	 * @see
	 * @since
	 */
	def getIngredients():List[Ingredient];
	/**
	 * Getter for client, to get filled recipe.
	 * @param recipe the folles recipe.
	 * @return
	 * @see
	 * @since
	 */
	def createRecipe(recipe:Recipe);
}
