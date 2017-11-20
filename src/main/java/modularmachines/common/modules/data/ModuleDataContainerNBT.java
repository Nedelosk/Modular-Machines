package modularmachines.common.modules.data;

import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.OreDictionary;

import modularmachines.api.modules.data.IModuleData;

public class ModuleDataContainerNBT extends ModuleDataContainer {
	
	public ModuleDataContainerNBT(ItemStack parent, IModuleData data) {
		super(parent, data);
	}
	
	@Override
	public boolean matches(ItemStack stack) {
		return ItemStack.areItemStackTagsEqual(stack, parent) && (stack.getItemDamage() == parent.getItemDamage() || parent.getItemDamage() == OreDictionary.WILDCARD_VALUE);
	}
}
