package nedelosk.forestday.api.crafting;

import java.util.HashMap;

import net.minecraft.item.ItemStack;

public interface IWoodTypeManager {

	void add(String name, ItemStack wood, ItemStack... charcoalDropps);

	WoodType get(String name);

	HashMap<String, WoodType> getWoodTypes();
}
