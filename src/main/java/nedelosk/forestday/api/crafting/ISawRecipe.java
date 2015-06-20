/**
 * (C) 2015 Nedelosk
 */
package nedelosk.forestday.api.crafting;

import nedelosk.forestday.common.machines.base.saw.SawRecipe;
import net.minecraft.item.ItemStack;

public interface ISawRecipe {

	public void addRecipe(ItemStack input, ItemStack output);

	
}
