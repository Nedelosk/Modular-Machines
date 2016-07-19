package de.nedelosk.modularmachines.common.modules.storaged.storage;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.modularmachines.api.energy.IEnergyType;
import de.nedelosk.modularmachines.api.gui.IContainerBase;
import de.nedelosk.modularmachines.api.gui.Widget;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.handlers.IModuleContentHandler;
import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import de.nedelosk.modularmachines.api.modules.handlers.energy.IModuleEnergyInterface;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.slots.SlotModule;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storaged.storage.IModuleBattery;
import de.nedelosk.modularmachines.client.gui.widgets.WidgetEnergyField;
import de.nedelosk.modularmachines.common.modules.Module;
import de.nedelosk.modularmachines.common.modules.handlers.ModulePage;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ModuleBattery extends Module implements IModuleBattery {

	public ModuleBattery(String name, int complexity) {
		super(name, complexity);
	}

	@Override
	public List<IModuleContentHandler> createContentHandlers(IModuleState state) {
		List<IModuleContentHandler> handlers = new ArrayList<>();
		handlers.add(getEnergyInterface(state));
		return super.createContentHandlers(state);
	}

	@Override
	public IModuleState loadStateFromItem(IModuleState state, ItemStack stack) {
		((IModuleEnergyInterface)state.getContentHandler(IModuleEnergyInterface.class)).receiveEnergy(getEnergyType(state), getStorageEnergy(state, stack), false);
		return state;
	}

	@Override
	public ItemStack saveDataToItem(IModuleState state) {
		ItemStack stack = super.saveDataToItem(state);
		setStorageEnergy(state, ((IModuleEnergyInterface)state.getContentHandler(IModuleEnergyInterface.class)).getEnergyStored(getEnergyType(state)), stack);
		return stack;
	}

	public abstract IModuleEnergyInterface getEnergyInterface(IModuleState state);

	public abstract IEnergyType getEnergyType(IModuleState state);

	@Override
	public List<IModulePage> createPages(IModuleState state) {
		List<IModulePage> pages = super.createPages(state);
		pages.add(new BatteryPage("Basic", state));
		return pages;
	}

	public class BatteryPage extends ModulePage<IModuleBattery> {

		public BatteryPage(String pageID, IModuleState<IModuleBattery> state) {
			super(pageID, "battery", state);
		}

		@SideOnly(Side.CLIENT)
		@Override
		public void updateGui(int x, int y) {
			super.updateGui(x, y);
			for(Widget widget : (ArrayList<Widget>) gui.getWidgetManager().getWidgets()) {
				if (widget instanceof WidgetEnergyField) {
					((WidgetEnergyField) widget).energyInterface = state.getContentHandler(IModuleEnergyInterface.class);
				}
			}
		}

		@Override
		public void createSlots(IContainerBase<IModularHandler> container, List<SlotModule> modularSlots) {
		}

		@SideOnly(Side.CLIENT)
		@Override
		public void addWidgets(List widgets) {
			super.addWidgets(widgets);
			widgets.add(new WidgetEnergyField(state.getContentHandler(IModuleEnergyInterface.class), getEnergyType(state), 55, 15));
		}
	}
}