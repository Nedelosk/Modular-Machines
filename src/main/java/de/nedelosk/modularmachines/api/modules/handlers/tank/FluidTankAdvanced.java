package de.nedelosk.modularmachines.api.modules.handlers.tank;

import de.nedelosk.modularmachines.common.fluids.FluidTankSimple;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

public class FluidTankAdvanced extends FluidTankSimple {

	public IModuleTank moduleTank;
	public final int index;
	public int xPosition;
	public int yPosition;

	public FluidTankAdvanced(int capacity, IModuleTank moduleTank, int index) {
		super(capacity);
		this.moduleTank = moduleTank;
		this.index = index;
	}

	public FluidTankAdvanced(int capacity, IModuleTank moduleTank, int index, NBTTagCompound nbtTag) {
		super(capacity);
		this.moduleTank = moduleTank;
		this.index = index;
		readFromNBT(nbtTag);
	}

	@Override
	public boolean canDrainFluidType(FluidStack resource) {
		return super.canDrainFluidType(resource) && !moduleTank.isInput(index) && moduleTank.getExtractFilter().isValid(index, resource, moduleTank.getModuleState());
	}

	@Override
	public boolean canFillFluidType(FluidStack fluid) {
		return super.canFillFluidType(fluid) && moduleTank.isInput(index) && moduleTank.getInsertFilter().isValid(index, fluid, moduleTank.getModuleState());
	}

	@Override
	protected void onContentsChanged() {
		moduleTank.getModuleState().getModular().getHandler().markDirty();
		moduleTank.onChange();
	}
}
