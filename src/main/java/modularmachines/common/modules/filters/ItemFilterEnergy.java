package modularmachines.common.modules.filters;

import net.minecraft.item.ItemStack;

import net.minecraftforge.energy.CapabilityEnergy;

import modularmachines.api.modules.Module;
import modularmachines.common.inventory.IContentFilter;

public class ItemFilterEnergy implements IContentFilter<ItemStack, Module> {

	public static final ItemFilterEnergy INSTANCE = new ItemFilterEnergy();

	private ItemFilterEnergy() {
	}

	@Override
	public boolean isValid(int index, ItemStack content, Module module) {
		if (content == null) {
			return false;
		}
		return content.hasCapability(CapabilityEnergy.ENERGY, null);
	}
}
