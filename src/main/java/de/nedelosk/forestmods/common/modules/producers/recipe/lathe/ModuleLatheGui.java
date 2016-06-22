package de.nedelosk.forestmods.common.modules.producers.recipe.lathe;

import java.util.List;

import de.nedelosk.modularmachines.api.gui.IGuiBase;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.ModularHelper;
import de.nedelosk.modularmachines.api.modules.engine.IModuleEngine;
import de.nedelosk.modularmachines.client.gui.Widget;
import de.nedelosk.modularmachines.client.gui.widgets.WidgetButtonMode;
import de.nedelosk.modularmachines.client.gui.widgets.WidgetProgressBar;

public class ModuleLatheGui extends ModuleMachineRecipeModeGui<ModuleLathe, IModuleProducerRecipeModeSaver> {

	public ModuleLatheGui(ModuleUID UID) {
		super(UID);
	}

	@Override
	public void addWidgets(IGuiBase gui, IModular modular, ModuleStack<ModuleLathe, IModuleProducerRecipeModeSaver> stack, List<Widget> widgets) {
		ModuleStack<IModuleEngine, IModuleEngineSaver> engine = ModularHelper.getEngine(modular).getItemStack();
		int burnTime = 0;
		int burnTimeTotal = 0;
		if (engine != null) {
			IModuleEngineSaver saver = engine.getSaver();
			burnTime = saver.getWorkTime(engine);
			burnTimeTotal = saver.getWorkTimeTotal(engine);
		}
		widgets.add(new WidgetProgressBar(82, 36, burnTime, burnTimeTotal));
		IModuleProducerRecipeModeSaver saver = stack.getSaver();
		gui.getWidgetManager().add(new WidgetButtonMode(86, 16, saver.getCurrentMode()));
	}
}
