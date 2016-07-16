package de.nedelosk.modularmachines.common.modules.heater;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.handlers.IContentFilter;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class ItemFliterFurnaceFuel implements IContentFilter<ItemStack, IModule> {

	@Override
	public boolean isValid(int index, ItemStack content, IModuleState<IModule> moduleState) {
		return TileEntityFurnace.getItemBurnTime(content) > 0;
	}
}
