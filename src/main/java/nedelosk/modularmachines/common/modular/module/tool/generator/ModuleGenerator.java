package nedelosk.modularmachines.common.modular.module.tool.generator;

import java.util.ArrayList;

import nedelosk.modularmachines.api.basic.modular.IModular;
import nedelosk.modularmachines.api.basic.modular.module.IModuleGenerator;
import nedelosk.modularmachines.api.basic.modular.module.Module;
import nedelosk.modularmachines.api.basic.modular.module.recipes.NeiStack;
import nedelosk.nedeloskcore.api.machines.IContainerBase;
import nedelosk.nedeloskcore.api.machines.IGuiBase;
import net.minecraft.inventory.Slot;

public class ModuleGenerator extends Module implements IModuleGenerator {

	@Override
	public ArrayList<Slot> addSlots(IContainerBase container, IModular modular) {
		return null;
	}

	@Override
	public void addButtons(IGuiBase gui, IModular modular) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addWidgets(IGuiBase gui, IModular modular) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getModuleName() {
		return "Generator";
	}

	@Override
	public ArrayList<NeiStack> addNEIStacks() {
		return null;
	}

}
