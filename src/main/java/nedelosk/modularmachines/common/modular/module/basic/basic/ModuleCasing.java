package nedelosk.modularmachines.common.modular.module.basic.basic;

import java.util.ArrayList;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.module.basic.basic.IModuleCasing;
import nedelosk.modularmachines.api.modular.module.basic.gui.ModuleGui;
import nedelosk.nedeloskcore.api.machines.IContainerBase;
import nedelosk.nedeloskcore.api.machines.IGuiBase;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;

public class ModuleCasing extends ModuleGui implements IModuleCasing {

	public ModuleCasing() {
	}
	
	public ModuleCasing(NBTTagCompound nbt) {
		super(nbt);
	}
	
	public ModuleCasing(String modifier) {
		super(modifier);
	}
	
	@Override
	public String getModuleName() {
		return "Casing";
	}

	@Override
	public void addWidgets(IGuiBase gui, IModular modular) {
		
	}

	@Override
	public ArrayList<Slot> addSlots(IContainerBase container, IModular modular) {
		return new ArrayList<Slot>();
	}

	@Override
	public int getSizeInventory() {
		return 2;
	}

}
