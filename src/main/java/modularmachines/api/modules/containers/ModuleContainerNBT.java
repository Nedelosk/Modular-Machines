package modularmachines.api.modules.containers;

import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.OreDictionary;

import modularmachines.api.modules.ModuleData;

public class ModuleContainerNBT extends ModuleContainer {

	public ModuleContainerNBT(ItemStack parent, ModuleData data) {
		super(parent, data);
	}

	@Override
	public boolean matches(ItemStack stack) {
		return ItemStack.areItemStackTagsEqual(stack, parent) && (stack.getItemDamage() == parent.getItemDamage() || parent.getItemDamage() == OreDictionary.WILDCARD_VALUE);
	}
}
