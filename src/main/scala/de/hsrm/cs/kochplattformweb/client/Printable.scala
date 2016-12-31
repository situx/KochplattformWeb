/*
 * This file is part of KochplattformWeb.
 *
 *   KochplattformWeb is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *    KochplattformWeb is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with KochplattformWeb.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package de.hsrm.cs.kochplattformweb.client

import de.hsrm.cs.kochplattformweb.db.Recipe
import de.hsrm.cs.kochplattformweb.db.RecipeEntry
import java.util.LinkedList

import org.apache.batik.gvt.event.SelectionListener
/**
  * Created by timo on 28.08.16 .
  */
trait Printable extends SelectionListener {

    /** Void function to print admin.
      * @param admin as Admin
      */
    def printIt(admin:Admin)

    /**Prints a recipeentry list.
      * @param rlist as List of RecipeEntry
      */
    def printIt(rlist:List[RecipeEntry])

    /** Print Linked List.
      * @param userlist as LinkedList with AbstarctUsers
      */
    def printIt(userlist:LinkedList[AbstractUser])

    /** Void function to print mainuser.
      * @param user as MainUser
      */
    def printIt(user:MainUser)

    /**To print a Recipe.
      * @param printRecipe as Recipe
      */
    def printIt(printRecipe:Recipe)

    /** Exemplarfunction.
      * @param message as String
      */
    def printIt(message:String)


}
