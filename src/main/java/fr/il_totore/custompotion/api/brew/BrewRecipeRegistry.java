package fr.il_totore.custompotion.api.brew;

import org.bukkit.NamespacedKey;

import java.util.*;

public class BrewRecipeRegistry {

    List<BrewRecipe> recipes = new ArrayList<>();

    public void addRecipes(Collection<BrewRecipe> recipes){
        recipes.forEach(this::addRecipe);
    }

    public void addRecipe(BrewRecipe recipe){
        if(hasRecipe(recipe.getKey())) throw new IllegalArgumentException("Recipe with id " + recipe.getKey() + " already exists !");
        recipes.add(recipe);
    }

    public void removeRecipes(Collection<BrewRecipe> recipes){
        recipes.forEach(this::removeRecipe);
    }

    public void removeRecipe(BrewRecipe recipe){
        recipes.remove(recipe);
    }

    public boolean hasRecipe(NamespacedKey key){
        return getRecipe(key) != null;
    }

    public Collection<BrewRecipe> getRecipes() {
        return recipes;
    }

    public BrewRecipe getRecipe(NamespacedKey key){
        for(BrewRecipe recipe : recipes){
            if(recipe.getKey().equals(key)) return recipe;
        }
        return null;
    }
}
