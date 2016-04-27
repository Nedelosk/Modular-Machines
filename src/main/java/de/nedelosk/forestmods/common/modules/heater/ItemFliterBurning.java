package de.nedelosk.forestmods.common.modules.heater;

import de.nedelosk.forestmods.library.modules.IModule;
import de.nedelosk.forestmods.library.modules.handlers.IContentFilter;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemFliterBurning implements IContentFilter<ItemStack, IModule> {

	@Override
	public boolean isValid(int index, ItemStack content, IModule module, ForgeDirection facing) {
		return TileEntityFurnace.getItemBurnTime(content) > 0;
	}
}
