package nedelosk.modularmachines.api.modules.managers.fluids;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestcore.library.gui.IGuiBase;
import nedelosk.forestcore.library.gui.Widget;
import nedelosk.forestcore.library.gui.WidgetFluidTank;
import nedelosk.modularmachines.api.client.widget.WidgetDirection;
import nedelosk.modularmachines.api.client.widget.WidgetProducer;
import nedelosk.modularmachines.api.client.widget.WidgetTankMode;
import nedelosk.modularmachines.api.client.widget.WidgetTextCapacity;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.modules.fluids.TankData;
import nedelosk.modularmachines.api.modules.managers.ModuleManagerGui;
import nedelosk.modularmachines.api.modules.managers.fluids.IModuleTankManager.TankMode;
import nedelosk.modularmachines.api.utils.ModularUtils;
import nedelosk.modularmachines.api.utils.ModuleStack;

@SideOnly(Side.CLIENT)
public class ModuleTankManagerGui<M extends IModuleTankManager<S>, S extends IModuleTankManagerSaver> extends ModuleManagerGui<M, S> {

	public ModuleTankManagerGui(String categoryUID, String guiName) {
		super(categoryUID, guiName);
	}

	@Override
	protected void addWidgets(int tabID, IGuiBase<IModularTileEntity<IModular>> gui, ModuleStack<M, S> stack, List<Widget> widgets) {
		IModuleTankManagerSaver saver = stack.getSaver();
		if (!(saver.getDatas().length <= tabID)) {
			TankData data = saver.getData(tabID);
			WidgetFluidTank widget = new WidgetFluidTank(data == null ? null : data.getTank(), 36 + tabID * 51, 18, tabID);
			if (widget != null && widget instanceof WidgetFluidTank) {
				WidgetFluidTank tank = widget;
				widgets.add(tank);
				widgets.add(new WidgetTankMode(tank.posX - 22, tank.posY, data == null ? null : data.getMode(), tabID));
				if (ModularUtils.getFluidProducers(gui.getTile().getModular()) != null
						&& !ModularUtils.getFluidProducers(gui.getTile().getModular()).isEmpty()) {
					widgets.add(new WidgetProducer(tank.posX - 22, tank.posY + 21, data == null ? null : data.getModule(), tabID));
				}
				widgets.add(new WidgetDirection(tank.posX - 22, tank.posY + 42, data == null ? null : data.getDirection(), tabID));
				widgets.add(new WidgetTextCapacity(tank.posX - 22, tank.posY + 42, tabID));
			}
		}
	}

	@Override
	public void updateGui(IGuiBase base, int x, int y, IModular modular, ModuleStack<M, S> stack) {
		IModuleTankManagerSaver saver = stack.getSaver();
		List<Widget> widgets = base.getWidgetManager().getWidgets();
		for ( Widget widget : widgets ) {
			if (widget instanceof WidgetFluidTank) {
				int ID = ((WidgetFluidTank) widget).ID;
				if (saver.getData(ID) != null) {
					((WidgetFluidTank) widget).tank = saver.getData(ID).getTank();
				} else {
					((WidgetFluidTank) widget).tank = null;
				}
			} else if (widget instanceof WidgetDirection) {
				int ID = ((WidgetDirection) widget).ID;
				if (saver.getData(ID) != null) {
					((WidgetDirection) widget).direction = saver.getData(ID).getDirection();
				} else {
					((WidgetDirection) widget).direction = null;
				}
			} else if (widget instanceof WidgetProducer) {
				int ID = ((WidgetProducer) widget).ID;
				if (saver.getData(ID) != null && saver.getData(ID).getMode() != null && saver.getData(ID).getMode() != TankMode.NONE) {
					((WidgetProducer) widget).module = saver.getData(ID).getModule();
				} else {
					((WidgetProducer) widget).module = null;
				}
			} else if (widget instanceof WidgetTankMode) {
				int ID = ((WidgetTankMode) widget).ID;
				if (saver.getData(ID) != null) {
					((WidgetTankMode) widget).mode = saver.getData(ID).getMode();
				} else {
					((WidgetTankMode) widget).mode = null;
				}
			}
		}
	}
}
