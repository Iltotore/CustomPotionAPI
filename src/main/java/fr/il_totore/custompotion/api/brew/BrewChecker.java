package fr.il_totore.custompotion.api.brew;

import org.bukkit.inventory.BrewerInventory;

import java.util.concurrent.atomic.AtomicBoolean;

public class BrewChecker {

    private BrewRecipeRegistry registry;

    public BrewChecker(BrewRecipeRegistry registry) {
        this.registry = registry;
    }

    public boolean check(BrewerInventory inventory){

        AtomicBoolean b = new AtomicBoolean(false);

        registry.getRecipes().forEach((recipe -> {
            if(recipe.getIngredient() == inventory.getIngredient()){
                b.set(true);
            }
        }));

        return b.get();
    }
}
