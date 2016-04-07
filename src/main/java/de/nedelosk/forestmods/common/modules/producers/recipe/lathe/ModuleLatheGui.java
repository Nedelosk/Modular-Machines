package de.nedelosk.forestmods.common.modules.producers.recipe.lathe;

import java.util.List;

import de.nedelosk.forestcore.gui.IGuiBase;
import de.nedelosk.forestcore.gui.Widget;
import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modules.engine.IModuleEngine;
import de.nedelosk.forestmods.api.utils.ModularUtils;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.api.utils.ModuleUID;
import de.nedelosk.forestmods.client.gui.widgets.WidgetButtonMode;
import de.nedelosk.forestmods.client.gui.widgets.WidgetProgressBar;

public class ModuleLatheGui extends ModuleMachineRecipeModeGui<ModuleLathe, IModuleProducerRecipeModeSaver> {

	public ModuleLatheGui(ModuleUID UID) {
		super(UID);
	}

	@Override
	public void addWidgets(IGuiBase gui, IModular modular, ModuleStack<ModuleLathe, IModuleProducerRecipeModeSaver> stack, List<Widget> widgets) {
		ModuleStack<IModuleEngine, IModuleEngineSaver> engine = ModularUtils.getEngine(modular).getStack();
		int burnTime = 0;
		int burnTimeTotal = 0;
		if (engine != null) {
			IModuleEngineSaver saver = engine.getSaver();
			burnTime = saver.getBurnTime(engine);
			burnTimeTotal = saver.getBurnTimeTotal(engine);
		}
		widgets.add(new WidgetProgressBar(82, 36, burnTime, burnTimeTotal));
		IModuleProducerRecipeModeSaver saver = stack.getSaver();
		gui.getWidgetManager().add(new WidgetButtonMode(86, 16, saver.getCurrentMode()));
	}
}
