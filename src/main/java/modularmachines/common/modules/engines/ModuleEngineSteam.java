package modularmachines.common.modules.engines;

import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import modularmachines.common.core.managers.FluidManager;
import modularmachines.common.inventory.ItemHandlerModule;
import modularmachines.common.modules.filters.FluidFilter;
import modularmachines.common.modules.filters.ItemFilterFluid;
import modularmachines.common.modules.filters.OutputFilter;
import modularmachines.common.tanks.FluidTankHandler;
import modularmachines.common.utils.ModuleUtil;

public class ModuleEngineSteam extends ModuleEngine implements IFluidHandler {
	
	public final ItemHandlerModule itemHandler;
	public final FluidTankHandler fluidHandler;
	public final IFluidTank fluidTank;
	
	public ModuleEngineSteam(int capacity, int maxTransfer, int materialPerWork, double kineticModifier) {
		super(capacity, maxTransfer, materialPerWork, kineticModifier);
		itemHandler = new ItemHandlerModule(this);
		itemHandler.addContainer(true, "liquid").addFilter(ItemFilterFluid.get(FluidManager.STEAM));
		itemHandler.addContainer(false, "container").addFilter(OutputFilter.INSTANCE);
		fluidHandler = new FluidTankHandler(this);
		fluidTank = fluidHandler.addTank(true, Fluid.BUCKET_VOLUME).addFilter(FluidFilter.get(FluidManager.STEAM));
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		fluidHandler.readFromNBT(compound);
		itemHandler.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		fluidHandler.writeToNBT(compound);
		itemHandler.writeToNBT(compound);
		return compound;
	}
	
	@Override
	public boolean removeMaterial() {
		if (fluidHandler == null) {
			return false;
		}
		FluidStack steam = new FluidStack(FluidManager.STEAM, getMaterialPerWork());
		FluidStack drained = fluidHandler.drainInternal(steam, false);
		if (drained != null && drained.amount >= getMaterialPerWork()) {
			return fluidHandler.drainInternal(steam, true).amount >= getMaterialPerWork();
		} else {
			return false;
		}
	}
	
	@Override
	public void update() {
		super.update();
		if (ModuleUtil.getUpdate(container).updateOnInterval(20)) {
			ModuleUtil.tryEmptyContainer(0, 1, itemHandler, fluidHandler);
		}
	}
	
	@Override
	public boolean canWork() {
		if (fluidTank.getFluid() == null) {
			return false;
		}
		return fluidTank.getFluid().amount > 0;
	}
	
	@Override
	public void createComponents() {
		super.createComponents();
		addComponent(new ModuleComponentSteamEngine(this));
	}
	
	public IFluidTank getFluidTank() {
		return fluidTank;
	}
	
	@Override
	public ItemHandlerModule getItemHandler() {
		return itemHandler;
	}
	
	@Override
	public IFluidTankProperties[] getTankProperties() {
		return fluidHandler.getTankProperties();
	}
	
	@Override
	public int fill(FluidStack resource, boolean doFill) {
		return fluidHandler.fill(resource, doFill);
	}
	
	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		return fluidHandler.drain(resource, doDrain);
	}
	
	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		return fluidHandler.drain(maxDrain, doDrain);
	}
}