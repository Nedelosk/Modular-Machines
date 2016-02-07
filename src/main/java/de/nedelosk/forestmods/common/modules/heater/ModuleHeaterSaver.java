package de.nedelosk.forestmods.common.modules.heater;

import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modules.heater.IModuleHeaterSaver;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import net.minecraft.nbt.NBTTagCompound;

public class ModuleHeaterSaver implements IModuleHeaterSaver {

	protected int heat;
	protected int burnTime;

	public ModuleHeaterSaver() {
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		heat = nbt.getInteger("Heat");
		burnTime = nbt.getInteger("BurnTime");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		nbt.setInteger("Heat", heat);
		nbt.setInteger("BurnTime", burnTime);
	}

	@Override
	public int getHeat() {
		return heat;
	}

	@Override
	public void setHeat(int heat) {
		this.heat = heat;
	}

	@Override
	public void addHeat(int heat) {
		this.heat += heat;
	}

	@Override
	public int getBurnTime() {
		return burnTime;
	}

	@Override
	public void setBurnTime(int burnTime) {
		this.burnTime = burnTime;
	}

	@Override
	public void addBurnTime(int burnTime) {
		this.burnTime += burnTime;
	}
}
