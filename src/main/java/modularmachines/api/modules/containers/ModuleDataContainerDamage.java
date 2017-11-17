package modularmachines.api.modules.containers;

import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.OreDictionary;

import modularmachines.api.modules.ModuleData;

public class ModuleDataContainerDamage extends ModuleDataContainer {

	public ModuleDataContainerDamage(ItemStack parent, ModuleData data) {
		super(parent, data);
	}

	@Override
	public boolean matches(ItemStack stack) {
		return super.matches(stack) && (stack.getItemDamage() == parent.getItemDamage() || parent.getItemDamage() == OreDictionary.WILDCARD_VALUE);
	}
}
