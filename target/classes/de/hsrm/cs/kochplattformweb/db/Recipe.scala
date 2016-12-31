/*
 * This file is part of KochplattformWeb.
 *
 *   KochplattformWeb is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *    KochplattformWeb is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with KochplattformWeb.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

/**
 * 
 */
package de.hsrm.cs.kochplattformweb.db

import java.net.URI

import de.hsrm.cs.kochplattformweb.messages.Message

class RecipeEntry(var category:String,var ingredients:List[Ingredient],var name:String,var recipeid:BigInt,var rating:Double,var time:String,var zombies:Integer) extends Message with Comparable[RecipeEntry]{
	def this()=this("",null,null,null,0.0,"",0)

	override def compareTo(o: RecipeEntry): Int = recipeid.compare(o.recipeid)
}
/**
 * @author Timo
 *
 */
class Recipe(category:String, ingredients:List[Ingredient], name:String, recipeid:BigInt,  rating:Double, time:String, zombies:Integer) extends RecipeEntry(category,ingredients,name,recipeid,rating,time,zombies){

  def this()=this("",null,null,null,0.0,"",0)
	/**authorId as Integer for the ID of the author.**/
	var authorId:BigInt=BigInt(0)
	/**difficulty as String - can you handle that?.**/
	var difficulty:String=""
	/**The language in which the recipe is written to db.**/
	var language:String=""
	/**nutrition facts as node attributes in xml.**/
	var nutrition:Nutrition=new Nutrition(0.0,0.0,0.0,0.0)
	/**pictures as List of images for all the pictures.**/
	var picture:List[ByteImage]=List[ByteImage]()
	/**Preparation steps as one very long string.**/
	var preparation:String=""
	/**videos as List of URL for the videos.**/
	var videos:Map[String,URI]=Map[String,URI]()
	/**Rating list for the recipe.*/
	var ratings:List[Rating]=List[Rating]()

	def toXML(version:String) ={
    <recipe id={recipeid.toString} category={category.toString} difficulty={difficulty.toString} ingredients={ingredients.length.toString} time={time.toString} language={language.toString} authorId={authorId.toString} zombies={zombies.toString} version={version.toString}>
      <title>{name.toString}</title>
      <preparation>{preparation.toString}</preparation>
      {for {ing <- ingredients}
        ing.toXML
      }
      {nutrition.toXML}
      {for {video <- videos.keySet}
        <video url={videos.get(video).toString}>{video.toString}</video>
      }
      {for {pic <- picture}
        <image>scala.xml.Unparsed("<![CDATA[%s]]>".format(Base64.encode(pic.getBytes)))</image>
      }
    </recipe>
	}
}