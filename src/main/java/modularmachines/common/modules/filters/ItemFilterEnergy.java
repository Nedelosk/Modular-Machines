package modularmachines.common.modules.filters;

import net.minecraft.item.ItemStack;

import net.minecraftforge.energy.CapabilityEnergy;

import modularmachines.api.modules.IModule;
import modularmachines.common.inventory.IContentFilter;

public class ItemFilterEnergy implements IContentFilter<ItemStack, IModule> {
	
	public static final ItemFilterEnergy INSTANCE = new ItemFilterEnergy();
	
	private ItemFilterEnergy() {
	}
	
	@Override
	public boolean isValid(int index, ItemStack content, IModule module) {
		if (content == null) {
			return false;
		}
		return content.hasCapability(CapabilityEnergy.ENERGY, null);
	}
}
