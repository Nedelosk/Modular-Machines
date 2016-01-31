package nedelosk.modularmachines.common.producers.machines.lathe;

import java.util.List;

import nedelosk.forestcore.library.gui.IGuiBase;
import nedelosk.forestcore.library.gui.Widget;
import nedelosk.forestcore.library.gui.WidgetProgressBar;
import nedelosk.modularmachines.api.client.widget.WidgetButtonMode;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modules.engine.IModuleEngine;
import nedelosk.modularmachines.api.modules.engine.IModuleEngineSaver;
import nedelosk.modularmachines.api.modules.machines.recipe.IModuleMachineRecipeModeSaver;
import nedelosk.modularmachines.api.modules.machines.recipe.ModuleMachineRecipeModeGui;
import nedelosk.modularmachines.api.utils.ModularUtils;
import nedelosk.modularmachines.api.utils.ModuleStack;

public class ModuleLatheGui extends ModuleMachineRecipeModeGui<ModuleLathe, IModuleMachineRecipeModeSaver> {

	public ModuleLatheGui(String categoryUID, String guiName) {
		super(categoryUID, guiName);
	}

	@Override
	public void addWidgets(IGuiBase gui, IModular modular, ModuleStack<ModuleLathe, IModuleMachineRecipeModeSaver> stack, List<Widget> widgets) {
		ModuleStack<IModuleEngine<IModuleEngineSaver>, IModuleEngineSaver> engine = ModularUtils.getEngine(modular).getStack();
		int burnTime = 0;
		int burnTimeTotal = 0;
		if (engine != null) {
			IModuleEngineSaver saver = engine.getSaver();
			burnTime = saver.getBurnTime(engine);
			burnTimeTotal = saver.getBurnTimeTotal(engine);
		}
		widgets.add(new WidgetProgressBar(82, 36, burnTime, burnTimeTotal));
		IModuleMachineRecipeModeSaver saver = stack.getSaver();
		gui.getWidgetManager().add(new WidgetButtonMode(86, 16, saver.getMode()));
	}
}
