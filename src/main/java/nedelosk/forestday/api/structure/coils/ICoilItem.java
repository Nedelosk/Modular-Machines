/**
 * (C) 2015 Nedelosk
 */
package nedelosk.forestday.api.structure.coils;

import net.minecraft.item.ItemStack;

public interface ICoilItem {

	public int getCoilSharpness(ItemStack stack);
	
	public int addCoilSharpness(ItemStack stack, int sharpness);
	
	public int sharpCoil(ItemStack stack, int sharpness);
	
}
