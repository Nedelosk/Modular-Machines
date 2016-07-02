package de.nedelosk.modularmachines.common.modules.handlers.tanks;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.handlers.IContentFilter;
import de.nedelosk.modularmachines.api.modules.handlers.tank.EnumTankMode;
import de.nedelosk.modularmachines.api.modules.handlers.tank.FluidTankAdvanced;
import de.nedelosk.modularmachines.api.modules.handlers.tank.IModuleTank;
import de.nedelosk.modularmachines.api.modules.handlers.tank.IModuleTankBuilder;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.common.modules.handlers.FilterWrapper;
import net.minecraftforge.fluids.FluidStack;

public class ModuleTankBuilder<M extends IModule> implements IModuleTankBuilder<M> {

	protected IModuleState<M> state;
	protected FilterWrapper<FluidStack, M> insertFilter = new FilterWrapper();
	protected FilterWrapper<FluidStack, M> extractFilter = new FilterWrapper();
	protected List<FluidTankAdvanced> tankSlots = new ArrayList();
	protected boolean isEmpty = true;

	public ModuleTankBuilder() {
	}

	@Override
	public void addInsertFilter(int index, IContentFilter<FluidStack, M>... filters) {
		insertFilter.add(index, filters);
	}

	@Override
	public void addExtractFilter(int index, IContentFilter<FluidStack, M>... filters) {
		extractFilter.add(index, filters);
	}

	@Override
	public void setModuleState(IModuleState<M> state) {
		this.state = state;
	}

	@Override
	public int addFluidTank(int capacity, EnumTankMode mode, IContentFilter<FluidStack, M>... filters) {
		int newIndex = tankSlots.size();
		tankSlots.add(new FluidTankAdvanced(capacity, mode));
		if (mode == EnumTankMode.INPUT) {
			addInsertFilter(newIndex, filters);
		} else {
			addExtractFilter(newIndex, filters);
		}

		isEmpty = false;
		return newIndex;
	}

	@Override
	public IModuleTank build() {
		return new ModuleTank(tankSlots.toArray(new FluidTankAdvanced[tankSlots.size()]), state, insertFilter, extractFilter);
	}

	@Override
	public boolean isEmpty() {
		return isEmpty;
	}
}
