package nedelosk.forestday.api.crafting;

import net.minecraft.item.ItemStack;

public interface IWoodTypeManager {

	void add(ItemStack wood, ItemStack... dropps);
	
}
