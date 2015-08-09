package nedelosk.nedeloskcore.api.crafting;

import net.minecraft.item.ItemStack;

public interface IPlanRecipe {
	
	void add(ItemStack outputBlock, int stages, ItemStack[]... input);
	
	void add(ItemStack updateBlock, ItemStack outputBlock, int stages, ItemStack[]... input);
	
}
