package nedelosk.forestday.api.crafting;

import net.minecraft.item.ItemStack;

public interface IWoodType {

	void add(ItemStack wood, ItemStack... dropps);
	
}
