package de.nedelosk.forestmods.common.modules.heater;

import de.nedelosk.forestmods.common.modules.Module;
import de.nedelosk.forestmods.library.modular.IModular;
import de.nedelosk.forestmods.library.modules.IModuleContainer;
import de.nedelosk.forestmods.library.modules.heater.IModuleHeater;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ModuleHeater extends Module implements IModuleHeater {

	protected int heat;
	protected final int maxHeat;

	public ModuleHeater(IModular modular, IModuleContainer container, int maxHeat) {
		super(modular, container);
		this.maxHeat = maxHeat;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular) {
		super.readFromNBT(nbt, modular);
		heat = nbt.getInteger("Heat");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular) {
		super.writeToNBT(nbt, modular);
		nbt.setInteger("Heat", heat);
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
	public int getMaxHeat() {
		return maxHeat;
	}
}
