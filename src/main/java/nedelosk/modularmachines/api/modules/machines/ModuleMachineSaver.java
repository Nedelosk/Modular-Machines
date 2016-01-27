package nedelosk.modularmachines.api.modules.machines;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.nbt.NBTTagCompound;

public class ModuleMachineSaver implements IModuleMachineSaver {

	protected int timer = 0;
	protected final int timerTotal;

	public ModuleMachineSaver() {
		this.timerTotal = 50;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) throws Exception {
		timer = nbt.getInteger("timer");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) throws Exception {
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
