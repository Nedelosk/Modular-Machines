package nedelosk.modularmachines.api.modules.managers;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.nbt.NBTTagCompound;

public class ModuleManagerSaver implements IModuleManagerSaver {

	public ModuleManagerSaver() {
		this.tab = 0;
	}

	protected int tab;

	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		nbt.setInteger("Tab", tab);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		tab = nbt.getInteger("Tab");
	}

	@Override
	public void setTab(int tab) {
		this.tab = tab;
	}

	@Override
	public int getTab() {
		return tab;
	}
}
