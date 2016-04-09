package de.nedelosk.forestmods.common.modules.handlers.tanks;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.forestcore.fluids.FluidTankSimple;
import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modules.handlers.IContentFilter;
import de.nedelosk.forestmods.api.modules.handlers.tank.EnumTankMode;
import de.nedelosk.forestmods.api.modules.handlers.tank.IModuleTank;
import de.nedelosk.forestmods.api.modules.handlers.tank.IModuleTankBuilder;
import de.nedelosk.forestmods.api.modules.handlers.tank.ITankData;
import de.nedelosk.forestmods.api.modules.handlers.tank.TankData;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.common.modules.handlers.FilterWrapper;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

public class ModuleTankBuilder implements IModuleTankBuilder {

	protected IModular modular;
	protected ModuleStack moduleStack;
	protected FilterWrapper<FluidStack> insertFilter = new FilterWrapper();
	protected FilterWrapper<FluidStack> extractFilter = new FilterWrapper();
	protected List<ITankData> tankSlots = new ArrayList();

	public ModuleTankBuilder() {
	}

	@Override
	public void addInsertFilter(int index, IContentFilter<FluidStack>... filters) {
		insertFilter.add(index, filters);
	}

	@Override
	public void addExtractFilter(int index, IContentFilter<FluidStack>... filters) {
		extractFilter.add(index, filters);
	}

	@Override
	public void setModuleStack(ModuleStack moduleStack) {
		this.moduleStack = moduleStack;
	}

	@Override
	public void setModular(IModular modular) {
		this.modular = modular;
	}

	@Override
	public void initTank(int index, int capacity, ForgeDirection direction, EnumTankMode mode, IContentFilter<FluidStack>... filters) {
		tankSlots.add(new TankData(new FluidTankSimple(capacity), direction, mode));
		if (mode == EnumTankMode.INPUT) {
			addInsertFilter(index, filters);
		} else {
			addExtractFilter(index, filters);
		}
	}

	@Override
	public IModuleTank build() {
		return new ModuleTank(tankSlots.toArray(new TankData[tankSlots.size()]), modular, moduleStack, insertFilter, extractFilter);
	}
}
