package modularmachines.api.modules.handlers.filters;

import net.minecraft.item.ItemStack;
import net.minecraftforge.energy.CapabilityEnergy;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.state.IModuleState;

public class ItemFilterEnergy implements IContentFilter<ItemStack, IModule> {

	public static final ItemFilterEnergy INSTANCE = new ItemFilterEnergy();

	private ItemFilterEnergy() {
	}

	@Override
	public boolean isValid(int index, ItemStack content, IModuleState<IModule> module) {
		if (content == null) {
			return false;
		}
		return content.hasCapability(CapabilityEnergy.ENERGY, null);
	}
}
