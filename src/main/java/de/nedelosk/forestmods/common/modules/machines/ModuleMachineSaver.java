package de.nedelosk.forestmods.common.modules.machines;

import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modules.machines.IModuleMachineSaver;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import net.minecraft.nbt.NBTTagCompound;

public class ModuleMachineSaver implements IModuleMachineSaver {

	protected int timer = 0;
	protected final int timerTotal;

	public ModuleMachineSaver() {
		this.timerTotal = 50;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		timer = nbt.getInteger("timer");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		nbt.setInteger("timer", timer);
	}

	@Override
	public int getTimer() {
		return timer;
	}

	@Override
	public void setTimer(int timer) {
		this.timer = timer;
	}

	@Override
	public int getTimerTotal() {
		return timerTotal;
	}

	@Override
	public void addTimer(int timer) {
		this.timer = +timer;
	}
}
