package modularmachines.api.modules.containers;

import modularmachines.api.modules.ModuleData;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ModuleContainerDamage extends ModuleContainer {

	public ModuleContainerDamage(ItemStack parent, ModuleData... datas) {
		super(parent, datas);
	}

	@Override
	public boolean matches(ItemStack stack) {
		return super.matches(stack) && (stack.getItemDamage() == parent.getItemDamage() || parent.getItemDamage() == OreDictionary.WILDCARD_VALUE);
	}
}
