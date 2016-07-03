package de.nedelosk.modularmachines.api.modules.handlers.tank;

import de.nedelosk.modularmachines.common.fluids.FluidTankSimple;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class FluidTankAdvanced extends FluidTankSimple {

	private EnumTankMode mode;
	public IModuleTank moduleTank;
	public final int index;

	public FluidTankAdvanced(int capacity, IModuleTank moduleTank, int index, EnumTankMode mode) {
		super(capacity);
		this.mode = mode;
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
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("Mode", mode.ordinal());
		return super.writeToNBT(nbt);
	}

	@Override
	public FluidTank readFromNBT(NBTTagCompound nbt) {
		mode = EnumTankMode.values()[nbt.getInteger("Mode")];
		return super.readFromNBT(nbt);
	}

	@Override
	public boolean canDrainFluidType(FluidStack resource) {
		return super.canDrainFluidType(resource) && mode == EnumTankMode.OUTPUT && moduleTank.getExtractFilter().isValid(index, resource, moduleTank.getModuleState());
	}

	@Override
	public boolean canFillFluidType(FluidStack fluid) {
		return super.canFillFluidType(fluid) && mode == EnumTankMode.INPUT && moduleTank.getInsertFilter().isValid(index, fluid, moduleTank.getModuleState());
	}

	public EnumTankMode getMode() {
		return mode;
	}

	@Override
	protected void onContentsChanged() {
		moduleTank.getModuleState().getModular().getHandler().markDirty();
		moduleTank.onChange();
	}
}
