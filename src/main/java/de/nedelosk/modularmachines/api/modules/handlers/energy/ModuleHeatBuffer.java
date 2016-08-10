package de.nedelosk.modularmachines.api.modules.handlers.energy;

import java.util.List;

import de.nedelosk.modularmachines.api.energy.HeatBuffer;
import de.nedelosk.modularmachines.api.energy.IHeatLevel;
import de.nedelosk.modularmachines.api.energy.IHeatSource;
import de.nedelosk.modularmachines.api.modules.handlers.IModuleContentHandler;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public class ModuleHeatBuffer implements IHeatSource, IModuleContentHandler, INBTSerializable<NBTTagCompound> {

	protected HeatBuffer heatSource;
	protected IModuleState state;

	public ModuleHeatBuffer(IModuleState state, float capacity, float maxTransfer) {
		this(state, capacity, maxTransfer, maxTransfer);
	}

	public ModuleHeatBuffer(IModuleState state, float capacity, float maxReceive, float maxExtract) {
		this.state = state;
		this.heatSource = new HeatBuffer(capacity, maxReceive, maxExtract);
	}

	@Override
	public IModuleState getModuleState() {
		return state;
	}

	@Override
	public String getUID() {
		return "HeatBuffer";
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		heatSource.deserializeNBT(nbt);
	}

	@Override
	public NBTTagCompound serializeNBT() {
		return heatSource.serializeNBT();
	}

	@Override
	public double extractHeat(double maxExtract, boolean simulate) {
		return  heatSource.extractHeat(maxExtract, simulate);
	}

	@Override
	public double receiveHeat(double maxReceive, boolean simulate) {
		return heatSource.receiveHeat(maxReceive, simulate);
	}

	@Override
	public void increaseHeat(int heatModifier) {
		heatSource.increaseHeat(heatModifier);
	}

	@Override
	public void reduceHeat(int heatModifier) {
		heatSource.reduceHeat(heatModifier);
	}

	@Override
	public IHeatLevel getHeatLevel() {
		return  heatSource.getHeatLevel();
	}

	@Override
	public double getHeatStored() {
		return  heatSource.getHeatStored();
	}
	
	@Override
	public void setHeatStored(double heatBuffer) {
		this.heatSource.setHeatStored(heatBuffer);
	}

	@Override
	public double getCapacity() {
		return heatSource.getCapacity();
	}

	@Override
	public void addToolTip(List<String> tooltip, ItemStack stack, IModuleState state) {
	}
}
