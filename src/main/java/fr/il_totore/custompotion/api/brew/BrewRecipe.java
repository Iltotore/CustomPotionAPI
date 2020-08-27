package fr.il_totore.custompotion.api.brew;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public class BrewRecipe implements Recipe {

    private ItemStack ingredient;
    private ItemStack bottle;

    private ItemStack result;

    private NamespacedKey key;

    public BrewRecipe(ItemStack ingredient, ItemStack bottle, ItemStack result, NamespacedKey key) {
        this.ingredient = ingredient;
        this.bottle = bottle;
        this.result = result;
        this.key = key;
    }

    public ItemStack getIngredient() {
        return ingredient;
    }

    public ItemStack getBottle() {
        return bottle;
    }

    @Override
    public ItemStack getResult() {
        return result;
    }

    public NamespacedKey getKey() {
        return key;
    }
}
