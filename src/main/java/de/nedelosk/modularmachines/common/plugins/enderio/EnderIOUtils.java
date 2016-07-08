package de.nedelosk.modularmachines.common.plugins.enderio;

import com.enderio.core.api.common.util.IItemReceptor;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularHandler;
import de.nedelosk.modularmachines.api.modular.ModularManager;
import de.nedelosk.modularmachines.common.blocks.tile.TileModular;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class EnderIOUtils implements IItemReceptor {

	@Override
	public boolean canInsertIntoObject(Object into, EnumFacing side) {
		if(into instanceof TileEntity){
			TileEntity tile = (TileEntity) into;
			if(tile.hasCapability(ModularManager.MODULAR_HANDLER_CAPABILITY, side)){
				IModularHandler modularHandler = tile.getCapability(ModularManager.MODULAR_HANDLER_CAPABILITY, side);
				IModular modular = modularHandler.getModular();
				if(modular != null){
					if(modular.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side)){
						IItemHandler itemHandler = modular.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);
						if(itemHandler != null){
							return itemHandler.getSlots() > 0;
						}
					}
				}
			}
		}
		return false;
	}

	@Override
	public int doInsertItem(Object into, ItemStack item, EnumFacing side) {
		TileEntity tile = (TileEntity) into;	
		IModularHandler modularHandler = tile.getCapability(ModularManager.MODULAR_HANDLER_CAPABILITY, side);
		IModular modular = modularHandler.getModular();
		IItemHandler itemHandler = modular.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);
		int startSize = item.stackSize;
		item = item.copy();
		
		for(int index = 0;index < itemHandler.getSlots();index++){
			item = itemHandler.insertItem(index, item, false);
			if(item == null || item.stackSize <= 0){
				return startSize;
			}
		}
		return startSize - item.stackSize;
	}
}
