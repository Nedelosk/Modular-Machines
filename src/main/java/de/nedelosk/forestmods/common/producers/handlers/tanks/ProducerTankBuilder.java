package de.nedelosk.forestmods.common.producers.handlers.tanks;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.forestcore.fluids.FluidTankSimple;
import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.producers.handlers.IContentFilter;
import de.nedelosk.forestmods.api.producers.handlers.tank.EnumTankMode;
import de.nedelosk.forestmods.api.producers.handlers.tank.IModuleTank;
import de.nedelosk.forestmods.api.producers.handlers.tank.IModuleTankBuilder;
import de.nedelosk.forestmods.api.producers.handlers.tank.ITankData;
import de.nedelosk.forestmods.api.producers.handlers.tank.TankData;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.common.producers.handlers.FilterWrapper;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

public class ProducerTankBuilder implements IModuleTankBuilder {

	protected IModular modular;
	protected ModuleStack moduleStack;
	protected FilterWrapper<FluidStack> insertFilter = new FilterWrapper();
	protected FilterWrapper<FluidStack> extractFilter = new FilterWrapper();
	private List<ITankData> tankSlots = new ArrayList();
	protected boolean isDisabled;

	public ProducerTankBuilder() {
		isDisabled = true;
	}

	@Override
	public void addInsertFilter(int index, IContentFilter<FluidStack> filter) {
		insertFilter.add(index, filter);
	}

	@Override
	public void addExtractFilter(int index, IContentFilter<FluidStack> filter) {
		extractFilter.add(index, filter);
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
	public void setDisabled(boolean isDisabled) {
		this.isDisabled = isDisabled;
	}

	@Override
	public void initTank(int index, int capacity, ForgeDirection direction, EnumTankMode mode) {
		tankSlots.add(new TankData(new FluidTankSimple(capacity), direction, mode));
	}

	@Override
	public IModuleTank build() {
		if (isDisabled) {
			return null;
		}
		return new ProducerTank(tankSlots.toArray(new TankData[tankSlots.size()]), modular, moduleStack, insertFilter, extractFilter);
	}
}
