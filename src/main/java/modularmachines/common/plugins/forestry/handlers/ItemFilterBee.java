package modularmachines.common.plugins.forestry.handlers;

import net.minecraft.item.ItemStack;

import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.EnumBeeType;
import modularmachines.api.modules.IModule;
import modularmachines.api.modules.handlers.filters.IContentFilter;
import modularmachines.api.modules.state.IModuleState;

public class ItemFilterBee implements IContentFilter<ItemStack, IModule> {

	private final boolean isDrone;

	public ItemFilterBee(boolean isDrone) {
		this.isDrone = isDrone;
	}

	@Override
	public boolean isValid(int index, ItemStack content, IModuleState<IModule> module) {
		EnumBeeType type = BeeManager.beeRoot.getType(content);
		return type == null ? false : isDrone && type == EnumBeeType.DRONE || !isDrone && (type == EnumBeeType.PRINCESS || type == EnumBeeType.QUEEN);
	}
}
