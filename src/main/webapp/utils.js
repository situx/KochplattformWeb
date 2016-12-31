function move(leftright){
	if(leftright){
		var ingredients = document.getElementById("ings");
		var selection = document.getElementById("ingselect");
	}
	else{
		var ingredients = document.getElementById("ingselect");
		var selection = document.getElementById("ings");
	}
	while(ingredients.selectedIndex != -1)
	{
	 	selection.add(ingredients.options[ingredients.selectedIndex],null);
	 	ingredients.remove(ingredients.selectedIndex);
	}
}