package modularmachines.common.plugins.forestry.handlers;

import net.minecraft.item.ItemStack;

import forestry.api.apiculture.IHiveFrame;
import modularmachines.api.modules.IModule;
import modularmachines.api.modules.handlers.filters.IContentFilter;
import modularmachines.api.modules.state.IModuleState;

public class ItemFilterFrame implements IContentFilter<ItemStack, IModule> {

	@Override
	public boolean isValid(int index, ItemStack content, IModuleState<IModule> module) {
		return content != null && content.getItem() instanceof IHiveFrame;
	}
}
