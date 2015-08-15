package nedelosk.modularmachines.common.modular.module.manager;

import java.util.ArrayList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.basic.modular.IModular;
import nedelosk.modularmachines.api.basic.modular.module.Module;
import nedelosk.modularmachines.api.basic.modular.module.manager.IModuleFanManager;
import nedelosk.modularmachines.api.basic.modular.module.recipes.NeiStack;
import nedelosk.nedeloskcore.api.machines.IContainerBase;
import nedelosk.nedeloskcore.api.machines.IGuiBase;
import net.minecraft.inventory.Slot;

public class ModuleFanManager extends Module implements IModuleFanManager{

	public ModuleFanManager() {
	}
	
	@Override
	public ArrayList<Slot> addSlots(IContainerBase container, IModular modular) {
		return null;
	}

	@Override
	public void addButtons(IGuiBase gui, IModular modular) {
		
	}
	
	@Override
	public ArrayList<NeiStack> addNEIStacks() {
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addWidgets(IGuiBase gui, IModular modular) {
		
	}

	@Override
	public String getModuleName() {
		return "FanManager";
	}

}
