package de.nedelosk.forestmods.api.transport.node;

import cofh.api.energy.IEnergyHandler;
import de.nedelosk.forestmods.api.transport.IPartSide;
import net.minecraft.inventory.ISidedInventory;
import net.minecraftforge.fluids.IFluidHandler;

public interface INodeSide extends IPartSide {

	ISidedInventory getInventory();

	IFluidHandler getFluidHandler();

	IEnergyHandler getEnergyHandler();
}
