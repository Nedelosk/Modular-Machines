package modularmachines.api.modules.handlers.energy;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

import modularmachines.api.energy.HeatBuffer;
import modularmachines.api.energy.HeatLevel;
import modularmachines.api.energy.IHeatSource;
import modularmachines.api.modules.handlers.BlankModuleContentHandler;
import modularmachines.api.modules.state.IModuleState;

public class ModuleHeatBuffer extends BlankModuleContentHandler implements IHeatSource, INBTSerializable<NBTTagCompound> {

	protected final HeatBuffer heatSource;

	public ModuleHeatBuffer(IModuleState moduleState, float capacity, float maxTransfer) {
		this(moduleState, capacity, maxTransfer, maxTransfer);
	}

	public ModuleHeatBuffer(IModuleState moduleState, float capacity, float maxReceive, float maxExtract) {
		super(moduleState, "HeatBuffer");
		this.heatSource = new HeatBuffer(capacity, maxReceive, maxExtract);
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
		return heatSource.extractHeat(maxExtract, simulate);
	}

	@Override
	public double receiveHeat(double maxReceive, boolean simulate) {
		return heatSource.receiveHeat(maxReceive, simulate);
	}

	@Override
	public void increaseHeat(double maxHeat, int heatModifier) {
		heatSource.increaseHeat(maxHeat, heatModifier);
	}

	@Override
	public void reduceHeat(int heatModifier) {
		heatSource.reduceHeat(heatModifier);
	}

	@Override
	public HeatLevel getHeatLevel() {
		return heatSource.getHeatLevel();
	}

	@Override
	public double getHeatStored() {
		return heatSource.getHeatStored();
	}

	@Override
	public void setHeatStored(double heatBuffer) {
		this.heatSource.setHeatStored(heatBuffer);
	}

	@Override
	public double getCapacity() {
		return heatSource.getCapacity();
	}
}
