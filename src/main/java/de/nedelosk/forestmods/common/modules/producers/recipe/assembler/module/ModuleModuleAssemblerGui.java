package de.nedelosk.forestmods.common.modules.producers.recipe.assembler.module;

import java.util.List;

import de.nedelosk.forestmods.client.gui.widgets.WidgetProgressBar;
import de.nedelosk.forestmods.common.modules.producers.recipe.ModuleProducerRecipeGui;
import de.nedelosk.forestmods.library.gui.IGuiBase;
import de.nedelosk.forestmods.library.gui.Widget;
import de.nedelosk.forestmods.library.modular.IModular;
import de.nedelosk.forestmods.library.modular.ModularHelper;
import de.nedelosk.forestmods.library.modules.ModuleUID;
import de.nedelosk.forestmods.library.modules.engine.IModuleEngine;

public class ModuleModuleAssemblerGui extends ModuleProducerRecipeGui<ModuleModuleAssembler, IModuleSaver> {

	public ModuleModuleAssemblerGui(ModuleUID UID) {
		super(UID);
	}

	@Override
	public void addWidgets(IGuiBase gui, IModular modular, ModuleStack<ModuleModuleAssembler, IModuleSaver> stack, List<Widget> widgets) {
		ModuleStack<IModuleEngine, IModuleEngineSaver> engine = ModularHelper.getEngine(modular).getItemStack();
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
