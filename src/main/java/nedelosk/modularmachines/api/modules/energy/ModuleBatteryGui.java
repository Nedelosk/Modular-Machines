package nedelosk.modularmachines.api.modules.energy;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestcore.library.gui.IGuiBase;
import nedelosk.forestcore.library.gui.Widget;
import nedelosk.modularmachines.api.client.widget.WidgetEnergyField;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.handlers.EnergyHandler;
import nedelosk.modularmachines.api.modules.gui.ModuleGuiDefault;
import nedelosk.modularmachines.api.utils.ModuleStack;

@SideOnly(Side.CLIENT)
public class ModuleBatteryGui<P extends IModuleBattery, S extends IModuleBatterySaver> extends ModuleGuiDefault<P, S> {

	public ModuleBatteryGui(String UID) {
		super(UID);
	}

	@Override
	public void addWidgets(IGuiBase gui, IModular modular, ModuleStack<P, S> stack, List<Widget> widgets) {
		widgets.add(new WidgetEnergyField(((EnergyHandler) modular.getUtilsManager().getEnergyHandler()).getStorage(), 45, 15));
	}

	@Override
	public boolean hasCustomInventoryName(ModuleStack stack) {
		return true;
	}

	@Override
	public void updateGui(IGuiBase base, int x, int y, IModular modular, ModuleStack stack) {
		for ( Widget widget : (ArrayList<Widget>) base.getWidgetManager().getWidgets() ) {
			if (widget instanceof WidgetEnergyField) {
				((WidgetEnergyField) widget).storage = ((EnergyHandler) modular.getUtilsManager().getEnergyHandler()).getStorage();
			}
		}
	}
}
