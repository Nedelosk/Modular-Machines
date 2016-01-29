package nedelosk.modularmachines.api.modules.managers.fluids;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import nedelosk.forestcore.library.fluids.FluidTankSimple;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.handlers.FluidHandler;
import nedelosk.modularmachines.api.modules.IModuleGui;
import nedelosk.modularmachines.api.modules.fluids.IModuleTank;
import nedelosk.modularmachines.api.modules.fluids.TankData;
import nedelosk.modularmachines.api.modules.inventory.IModuleInventory;
import nedelosk.modularmachines.api.modules.managers.ModuleManager;
import nedelosk.modularmachines.api.utils.ModularException;
import nedelosk.modularmachines.api.utils.ModularUtils;
import nedelosk.modularmachines.api.utils.ModuleCategoryUIDs;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

public class ModuleTankManager extends ModuleManager<ModuleTankManagerSaver> implements IModuleTankManager<ModuleTankManagerSaver> {

	protected final int tankSlots;

	public ModuleTankManager() {
		super(ModuleCategoryUIDs.MANAGER_TANK);
		this.tankSlots = 2;
	}

	public ModuleTankManager(int tankSlots) {
		super(ModuleCategoryUIDs.MANAGER_TANK);
		this.tankSlots = tankSlots;
	}

	@Override
	public void onAddInModular(IModular modular, ModuleStack stack) throws ModularException {
		modular.getUtilsManager().setFluidHandler(new FluidHandler(modular));
		super.onAddInModular(modular, stack);
	}

	@Override
	public int getColor() {
		return 0x1719A4;
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill, ModuleStack stack, IModular modular, boolean canFillOutput) {
		if (resource == null) {
			return 0;
		}
		IModuleTankManagerSaver saver = (IModuleTankManagerSaver) stack.getSaver();
		for ( int i = 0; i < tankSlots; i++ ) {
			TankData data = saver.getData(i);
			if (data == null || data.getTank().isFull()) {
				continue;
			}
			if (!data.getTank().isEmpty()) {
				if (resource.getFluid() != data.getTank().getFluid().getFluid()) {
					continue;
				}
			}
			if (ModularUtils.getFluidProducers(modular) != null && !ModularUtils.getFluidProducers(modular).isEmpty()) {
				ModuleStack stackT = ModularUtils.getFluidProducers(modular).get(data.getProducer());
				if (!(stack == null) && !stack.equals(stackT)) {
					continue;
				}
			}
			if (data.getMode() == TankMode.OUTPUT && canFillOutput || data.getMode() == TankMode.INPUT) {
				if (from == data.getDirection() || from == ForgeDirection.UNKNOWN || data.getDirection() == ForgeDirection.UNKNOWN) {
					return data.getTank().fill(resource, doFill);
				} else {
					continue;
				}
			} else {
				continue;
			}
		}
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain, ModuleStack stack, IModular modular, boolean canDrainInput) {
		if (resource == null) {
			return null;
		}
		IModuleTankManagerSaver saver = (IModuleTankManagerSaver) stack.getSaver();
		for ( int i = 0; i < tankSlots; i++ ) {
			TankData data = saver.getData(i);
			if (data == null || data.getTank().isEmpty()) {
				continue;
			}
			if (resource.getFluid() != data.getTank().getFluid().getFluid()) {
				continue;
			}
			if (ModularUtils.getFluidProducers(modular) != null && !ModularUtils.getFluidProducers(modular).isEmpty()) {
				ModuleStack stackT = ModularUtils.getFluidProducers(modular).get(data.getProducer());
				if (!(stack == null) && !stack.equals(stackT)) {
					continue;
				}
			}
			if (data.getMode() == TankMode.OUTPUT || data.getMode() == TankMode.INPUT && canDrainInput) {
				if (from == data.getDirection() || from == ForgeDirection.UNKNOWN || data.getDirection() == ForgeDirection.UNKNOWN) {
					return data.getTank().drain(resource, doDrain);
				} else {
					continue;
				}
			} else {
				continue;
			}
		}
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain, ModuleStack stack, IModular modular, boolean canDrainInput) {
		if (maxDrain < 0) {
			return null;
		}
		IModuleTankManagerSaver saver = (IModuleTankManagerSaver) stack.getSaver();
		for ( int i = 0; i < tankSlots; i++ ) {
			TankData data = saver.getData(i);
			if (data == null || data.getTank().isEmpty()) {
				continue;
			}
			if (data.getTank().getFluid().amount < 0) {
				continue;
			}
			if (ModularUtils.getFluidProducers(modular) != null && !ModularUtils.getFluidProducers(modular).isEmpty()) {
				ModuleStack stackT = ModularUtils.getFluidProducers(modular).get(data.getProducer());
				if (!(stack == null) && !stack.equals(stackT)) {
					continue;
				}
			}
			if (data.getMode() == TankMode.OUTPUT || data.getMode() == TankMode.INPUT && canDrainInput) {
				if (from == data.getDirection() || from == ForgeDirection.UNKNOWN || data.getDirection() == ForgeDirection.UNKNOWN) {
					return data.getTank().drain(maxDrain, doDrain);
				} else {
					continue;
				}
			} else {
				continue;
			}
		}
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid, ModuleStack stack, IModular modular) {
		return true;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid, ModuleStack stack, IModular modular) {
		return true;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from, ModuleStack stack, IModular modular) {
		ArrayList<FluidTankInfo> infos = new ArrayList<>();
		IModuleTankManagerSaver saver = (IModuleTankManagerSaver) stack.getSaver();
		for ( int i = 0; i < tankSlots; i++ ) {
			TankData data = saver.getData(i);
			if (data != null) {
				infos.add(data.getTank().getInfo());
			}
		}
		return infos.toArray(new FluidTankInfo[infos.size()]);
	}

	@Override
	public int getMaxTabs() {
		return (tankSlots / 3) + 1;
	}

	@Override
	public void addTank(int id, IModuleTank tank, ModuleStack stack) {
		IModuleTankManagerSaver saver = (IModuleTankManagerSaver) stack.getSaver();
		TankData data = saver.getData(id);
		if (tank == null) {
			data = null;
		} else {
			data = new TankData(new FluidTankSimple(tank.getCapacity()));
		}
	}

	@Override
	public List<TankData> getDatas(IModular modular, ModuleStack stack, TankMode mode) {
		ArrayList<TankData> datasL = Lists.newArrayList();
		if (stack != null) {
			if (ModularUtils.getFluidProducers(modular) == null || ModularUtils.getFluidProducers(modular).isEmpty()
					|| !ModularUtils.getFluidProducers(modular).contains(stack)) {
				return datasL;
			}
			IModuleTankManagerSaver saver = (IModuleTankManagerSaver) stack.getSaver();
			for ( int ID = 0; ID < tankSlots; ID++ ) {
				TankData data = saver.getData(ID);
				if (data != null) {
					if (mode == data.getMode()) {
						if (data.getProducer() == ModularUtils.getFluidProducers(modular).indexOf(stack)) {
							datasL.add(data);
						}
					}
				}
			}
		}
		return datasL;
	}

	@Override
	public ModuleTankManagerSaver getSaver(ModuleStack stack) {
		return new ModuleTankManagerSaver(tankSlots);
	}

	@Override
	public IModuleGui getGui(ModuleStack stack) {
		return new ModuleTankManagerGui<>(getName(stack));
	}

	@Override
	public IModuleInventory getInventory(ModuleStack stack) {
		return new ModuleTankManagerInventory<>(getModuleUID(), getModuleUID(), tankSlots);
	}

	@Override
	public String getCategoryUID() {
		return ModuleCategoryUIDs.MANAGERS;
	}

	@Override
	public String getModuleUID() {
		return ModuleCategoryUIDs.MANAGER_TANK;
	}
}
