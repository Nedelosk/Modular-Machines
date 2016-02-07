package de.nedelosk.forestmods.common.modules.engine;

import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modules.engine.IModuleEngineSaver;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import net.minecraft.nbt.NBTTagCompound;

public class ModuleEngineSaver implements IModuleEngineSaver {

	protected int burnTime, burnTimeTotal;
	protected boolean isWorking;
	protected float progress;

	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		progress = nbt.getFloat("Progress");
		burnTime = nbt.getInteger("burnTime");
		burnTimeTotal = nbt.getInteger("burnTimeTotal");
		isWorking = nbt.getBoolean("isWorking");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		nbt.setFloat("Progress", progress);
		nbt.setInteger("burnTime", burnTime);
		nbt.setInteger("burnTimeTotal", burnTimeTotal);
		nbt.setBoolean("isWorking", isWorking);
	}

	@Override
	public float getProgress() {
		return progress;
	}

	@Override
	public void setProgress(float progress) {
		this.progress = progress;
	}

	@Override
	public void addProgress(float progress) {
		this.progress += progress;
	}

	@Override
	public int getBurnTime(ModuleStack stack) {
		return burnTime;
	}

	@Override
	public void setBurnTime(int burnTime) {
		this.burnTime = burnTime;
	}

	@Override
	public int getBurnTimeTotal(ModuleStack stack) {
		return burnTimeTotal;
	}

	@Override
	public void setBurnTimeTotal(int burnTimeTotal) {
		this.burnTimeTotal = burnTimeTotal;
	}

	@Override
	public void addBurnTime(int burntime) {
		this.burnTime += burntime;
	}

	@Override
	public boolean isWorking() {
		return isWorking;
	}

	@Override
	public void setIsWorking(boolean isWorking) {
		this.isWorking = isWorking;
	}
}
