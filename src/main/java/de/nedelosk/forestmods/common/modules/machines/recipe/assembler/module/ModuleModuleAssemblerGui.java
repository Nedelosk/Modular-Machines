package de.nedelosk.forestmods.common.modules.machines.recipe.assembler.module;

import java.util.List;

import de.nedelosk.forestcore.library.gui.IGuiBase;
import de.nedelosk.forestcore.library.gui.Widget;
import de.nedelosk.forestcore.library.gui.WidgetProgressBar;
import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modules.engine.IModuleEngine;
import de.nedelosk.forestmods.api.modules.engine.IModuleEngineSaver;
import de.nedelosk.forestmods.api.modules.machines.IModuleMachineSaver;
import de.nedelosk.forestmods.api.utils.ModularUtils;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.common.modules.machines.recipe.ModuleMachineRecipeGui;

public class ModuleModuleAssemblerGui extends ModuleMachineRecipeGui<ModuleModuleAssembler, IModuleMachineSaver> {

	public ModuleModuleAssemblerGui(String UID) {
		super(UID);
	}

	@Override
	public void addWidgets(IGuiBase gui, IModular modular, ModuleStack<ModuleModuleAssembler, IModuleMachineSaver> stack, List<Widget> widgets) {
		ModuleStack<IModuleEngine, IModuleEngineSaver> engine = ModularUtils.getEngine(modular).getStack();
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
