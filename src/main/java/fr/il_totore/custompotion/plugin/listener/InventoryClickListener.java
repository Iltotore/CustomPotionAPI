package fr.il_totore.custompotion.plugin.listener;

import fr.il_totore.custompotion.plugin.CustomListener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.ItemStack;

public class InventoryClickListener extends CustomListener<InventoryClickEvent> {
    @Override
    public void run(InventoryClickEvent event) {

        if(
                event.getClickedInventory() == null ||
                !(event.getClickedInventory().getType() == InventoryType.BREWING)

        ) return;

        BrewerInventory inventory = (BrewerInventory) event.getClickedInventory();

        ItemStack ingredient = inventory.getIngredient();


    }
}
