package nedelosk.modularmachines.common.producers.machines.assembler.module;

import java.util.List;

import nedelosk.forestcore.library.gui.IGuiBase;
import nedelosk.forestcore.library.gui.Widget;
import nedelosk.forestcore.library.gui.WidgetProgressBar;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modules.engine.IModuleEngine;
import nedelosk.modularmachines.api.modules.engine.IModuleEngineSaver;
import nedelosk.modularmachines.api.modules.machines.IModuleMachineSaver;
import nedelosk.modularmachines.api.modules.machines.recipe.ModuleMachineRecipeGui;
import nedelosk.modularmachines.api.utils.ModularUtils;
import nedelosk.modularmachines.api.utils.ModuleStack;

public class ModuleModuleAssemblerGui extends ModuleMachineRecipeGui<ModuleModuleAssembler, IModuleMachineSaver> {

	public ModuleModuleAssemblerGui(String categoryUID, String guiName) {
		super(categoryUID, guiName);
	}

	@Override
	public void addWidgets(IGuiBase gui, IModular modular, ModuleStack<ModuleModuleAssembler, IModuleMachineSaver> stack, List<Widget> widgets) {
		ModuleStack<IModuleEngine<IModuleEngineSaver>, IModuleEngineSaver> engine = ModularUtils.getEngine(modular).getStack();
		int burnTime = 0;
		int burnTimeTotal = 0;
		if (engine != null) {
			IModuleEngineSaver saver = engine.getSaver();
			burnTime = saver.getBurnTime(engine);
			burnTimeTotal = saver.getBurnTimeTotal(engine);
		}
		widgets.add(new WidgetProgressBar(82, 46, burnTime, burnTimeTotal));
	}
}
