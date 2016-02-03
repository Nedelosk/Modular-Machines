package de.nedelosk.forestmods.common.modules.storage;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestcore.library.gui.IGuiBase;
import de.nedelosk.forestcore.library.gui.Widget;
import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modules.storage.battery.IModuleBattery;
import de.nedelosk.forestmods.api.modules.storage.battery.IModuleBatterySaver;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.client.gui.widgets.WidgetEnergyField;
import de.nedelosk.forestmods.common.modular.handlers.EnergyHandler;
import de.nedelosk.forestmods.common.modules.gui.ModuleGuiDefault;

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
