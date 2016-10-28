package de.nedelosk.modularmachines.api.modules.handlers.filters;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class ItemFliterFurnaceFuel implements IContentFilter<ItemStack, IModule> {

	public static final ItemFliterFurnaceFuel INSTANCE = new ItemFliterFurnaceFuel();

	private ItemFliterFurnaceFuel() {
	}

	@Override
	public boolean isValid(int index, ItemStack content, IModuleState<IModule> moduleState) {
		if (content == null) {
			return false;
		}
		return TileEntityFurnace.getItemBurnTime(content) > 0;
	}
}
