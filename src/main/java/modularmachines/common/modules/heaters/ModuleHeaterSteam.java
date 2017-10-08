package modularmachines.common.modules.heaters;

import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import modularmachines.api.modules.IModuleStorage;
import modularmachines.common.inventory.ItemHandlerModule;
import modularmachines.common.modules.filters.ItemFilterFluid;
import modularmachines.common.modules.filters.OutputFilter;
import modularmachines.common.tanks.FluidTankHandler;
import modularmachines.common.tanks.FluidTankModule;
import modularmachines.common.utils.ModuleUtil;

public class ModuleHeaterSteam extends ModuleHeater {

	public final ItemHandlerModule itemHandler;
	public final FluidTankHandler fluidHandler;
	public final FluidTankModule tank;
	
	public ModuleHeaterSteam(IModuleStorage storage, double maxHeat, int heatModifier) {
		super(storage, maxHeat, heatModifier);
		itemHandler = new ItemHandlerModule(this);
		itemHandler.addContainer(true, "liquid").addFilter(ItemFilterFluid.get(FluidRegistry.WATER));
		itemHandler.addContainer(false, "container").addFilter(OutputFilter.INSTANCE);
		fluidHandler = new FluidTankHandler(this);
		tank = fluidHandler.addTank(true, Fluid.BUCKET_VOLUME).addFilter(OutputFilter.INSTANCE);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		fluidHandler.readFromNBT(compound);
		itemHandler.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		fluidHandler.writeToNBT(compound);
		itemHandler.writeToNBT(compound);
		return compound;
	}

	@Override
	protected boolean canAddHeat() {
		return fuel > 0;
	}

	@Override
	protected void afterAddHeat() {
		fuel-=10 * getData().getSize().ordinal();
	}

	@Override
	protected boolean updateFuel() {
		FluidStack input = tank.getFluid();
		if (input == null) {
			if (tank.drainInternal(80, true) != null) {
				fuel=50;
				fuelTotal=50;
				return true;
			}
		}
		return false;
	}

	@Override
	public void update() {
		if (ModuleUtil.getUpdate(logic).updateOnInterval(20)) {
			ModuleUtil.tryEmptyContainer(0, 1, itemHandler, fluidHandler);
		}
	}
	
	@Override
	public ItemHandlerModule getItemHandler() {
		return itemHandler;
	}
	
	public FluidTankModule getTank() {
		return tank;
	}
	
	//TODO: rendering
	/*@SideOnly(Side.CLIENT)
	@Override
	public boolean needHandlerReload(IModuleStateClient state) {
		IModelHandler handler = state.getModelHandler();
		if (handler instanceof ModelHandlerStatus) {
			ModelHandlerStatus status = (ModelHandlerStatus) handler;
			if (getBurnTime(state) > 0) {
				if (!status.status) {
					status.status = true;
					return true;
				}
			} else {
				if (status.status) {
					status.status = false;
					return true;
				}
			}
		}
		return false;
	}*/
	
	@Override
	protected void createComponents() {
		super.createComponents();
		addComponent(new ModuleComponentSteamHeater(this));
	}
}
