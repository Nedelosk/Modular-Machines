package de.nedelosk.modularmachines.common.modules.transport;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.transport.IModuleTansport;
import de.nedelosk.modularmachines.api.modules.transport.ITransportCycle;
import de.nedelosk.modularmachines.api.modules.transport.ITransportCycleList;
import de.nedelosk.modularmachines.api.modules.transport.ITransportCyclePage;
import de.nedelosk.modularmachines.api.modules.transport.ITransportHandler;
import de.nedelosk.modularmachines.api.modules.transport.TransportHandler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class ItemTransportHandler extends TransportHandler<IItemHandler, ItemTransportCycle> implements ITransportHandler<IItemHandler, ItemTransportCycle> {

	public ItemTransportHandler(int allowedComplexit) {
		super(IItemHandler.class, allowedComplexit);
	}

	@Override
	public void work(IModuleState<IModuleTansport> moduleState) {
		for(ITransportCycle cycle : getCycles(moduleState)) {
			if (cycle.canWork()) {
				cycle.work();
			}
		}
	}

	@Override
	public ITransportCyclePage<IItemHandler, ItemTransportCycle> createCyclePage(IModuleState<IModuleTansport> transportModule, ItemTransportCycle cycle) {
		return null;
	}

	@Override
	public List<ItemTransportCycle> getCycles(IModuleState<IModuleTansport> moduleState) {
		List<ItemTransportCycle> cycles = new ArrayList<>();
		for(IModuleState state : moduleState.getModular().getModules()) {
			ITransportCycleList transportCycleList = state.getContentHandlerFromAll(ITransportCycleList.class);
			if (transportCycleList != null) {
				for(ITransportCycle cycle : transportCycleList.getCycles()) {
					if (cycle instanceof ItemTransportCycle) {
						cycles.add((ItemTransportCycle) cycle);
					}
				}
			}
		}
		return cycles;
	}

	@Override
	protected IItemHandler getHandler(TileEntity tileEntity, EnumFacing facing) {
		if (tileEntity != null && tileEntity.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing)) {
			return tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing);
		}
		return null;
	}
}