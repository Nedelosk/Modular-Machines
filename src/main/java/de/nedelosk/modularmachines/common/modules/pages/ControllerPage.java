package de.nedelosk.modularmachines.common.modules.pages;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.modularmachines.api.modules.controller.IModuleControl;
import de.nedelosk.modularmachines.api.modules.controller.IModuleControlled;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.client.gui.widgets.WidgetController;
import de.nedelosk.modularmachines.client.gui.widgets.WidgetRedstoneMode;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ControllerPage extends ModulePage<IModuleControlled> {

	private final List<Integer> usedModules;

	public ControllerPage(IModuleState<IModuleControlled> module) {
		super("Controller", "controller", module);
		this.usedModules = new ArrayList<>();
		IModuleControl control = module.getModule().getModuleControl(module);
		for(IModuleState state : module.getModule().getUsedModules(module)){
			this.usedModules.add(state.getIndex());
			control.setPermission(state, true);
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addWidgets() {
		add(new WidgetRedstoneMode(6, 9, moduleState));
		if(usedModules.isEmpty()){
			return;
		}
		int index = 0;
		for(int y = 0;y < 3;y++){
			for(int x = 0;x < 5;x++){
				if(usedModules.size() == index){
					return;
				}
				IModuleState state = moduleState.getModular().getModule(usedModules.get(index));
				add(new WidgetController(39 + x * 27, 9 + y * 25, moduleState, state));
				index++;
			}
		}
	}
}
