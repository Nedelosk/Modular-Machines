package modularmachines.common.modules.data;

import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.OreDictionary;

import modularmachines.api.modules.data.IModuleData;

public class ModuleDataContainerDamage extends ModuleDataContainer {
	
	public ModuleDataContainerDamage(ItemStack parent, IModuleData data) {
		super(parent, data);
	}
	
	@Override
	public boolean matches(ItemStack stack) {
		return super.matches(stack) && (stack.getItemDamage() == parent.getItemDamage() || parent.getItemDamage() == OreDictionary.WILDCARD_VALUE);
	}
}
