package de.nedelosk.forestmods.common.modules.handlers.tanks;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.forestmods.common.modules.handlers.FilterWrapper;
import de.nedelosk.forestmods.library.fluids.FluidTankSimple;
import de.nedelosk.forestmods.library.modular.IModular;
import de.nedelosk.forestmods.library.modules.IModule;
import de.nedelosk.forestmods.library.modules.handlers.IContentFilter;
import de.nedelosk.forestmods.library.modules.handlers.tank.EnumTankMode;
import de.nedelosk.forestmods.library.modules.handlers.tank.IModuleTank;
import de.nedelosk.forestmods.library.modules.handlers.tank.IModuleTankBuilder;
import de.nedelosk.forestmods.library.modules.handlers.tank.ITankData;
import de.nedelosk.forestmods.library.modules.handlers.tank.TankData;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

public class ModuleTankBuilder<M extends IModule> implements IModuleTankBuilder<M> {

	protected IModular modular;
	protected IModule module;
	protected FilterWrapper<FluidStack, M> insertFilter = new FilterWrapper();
	protected FilterWrapper<FluidStack, M> extractFilter = new FilterWrapper();
	protected List<ITankData> tankSlots = new ArrayList();

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
	public void setModule(IModule module) {
		this.module = module;
	}

	@Override
	public void setModular(IModular modular) {
		this.modular = modular;
	}

	@Override
	public void initTank(int index, int capacity, ForgeDirection direction, EnumTankMode mode, IContentFilter<FluidStack, M>... filters) {
		tankSlots.add(new TankData(new FluidTankSimple(capacity), direction, mode));
		if (mode == EnumTankMode.INPUT) {
			addInsertFilter(index, filters);
		} else {
			addExtractFilter(index, filters);
		}
	}

	@Override
	public IModuleTank build() {
		return new ModuleTank(tankSlots.toArray(new TankData[tankSlots.size()]), modular, module, insertFilter, extractFilter);
	}
}
