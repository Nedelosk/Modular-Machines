package de.nedelosk.forestmods.common.modules.machines.generator;

import java.util.List;

import de.nedelosk.forestcore.gui.IGuiBase;
import de.nedelosk.forestcore.gui.Widget;
import de.nedelosk.forestcore.gui.WidgetProgressBar;
import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modules.engine.IModuleEngine;
import de.nedelosk.forestmods.api.modules.engine.IModuleEngineSaver;
import de.nedelosk.forestmods.api.utils.ModularUtils;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.common.modules.machines.recipe.ModuleMachineRecipeGui;
import de.nedelosk.forestmods.common.modules.machines.recipe.assembler.ModuleAssembler;

public class ModuleAssemblerGui extends ModuleMachineRecipeGui<ModuleAssembler> {

	public ModuleAssemblerGui(String categoryUID, String guiName) {
		super(categoryUID, guiName);
	}

	@Override
	public void addWidgets(IGuiBase gui, IModular modular, ModuleStack<ModuleAssembler> stack, List<Widget> widgets) {
		ModuleStack<IModuleEngine> engine = ModularUtils.getEngine(modular).getStack();
		int burnTime = 0;
		int burnTimeTotal = 0;
		if (engine != null) {
			IModuleEngineSaver saver = (IModuleEngineSaver) engine.getSaver();
			burnTime = saver.getBurnTime(engine);
			burnTimeTotal = saver.getBurnTimeTotal(engine);
		}
		widgets.add(new WidgetProgressBar(82, 36, burnTime, burnTimeTotal));
	}
}
