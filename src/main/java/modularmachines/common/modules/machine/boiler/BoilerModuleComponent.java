package modularmachines.common.modules.machine.boiler;

import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Slot;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.pages.DefaultModuleComponent;
import modularmachines.api.modules.pages.IPageComponent;
import modularmachines.client.gui.widgets.WidgetFluidTank;
import modularmachines.common.containers.SlotModule;
import modularmachines.common.inventory.ItemHandlerModule;
import modularmachines.common.modules.pages.PageWidget;

public class BoilerModuleComponent extends DefaultModuleComponent<ModuleBoiler> {
	
	public BoilerModuleComponent(ModuleBoiler parent) {
		super(parent, PageBoiler::new);
	}
	
	@Override
	public void createSlots(List<Slot> slots) {
		ItemHandlerModule itemHandler = parent.getItemHandler();
		slots.add(new SlotModule(itemHandler, 0, 15, 28));
		slots.add(new SlotModule(itemHandler, 1, 15, 48));
		slots.add(new SlotModule(itemHandler, 2, 147, 28));
		slots.add(new SlotModule(itemHandler, 3, 147, 48));
	}
	
	@SideOnly(Side.CLIENT)
	public static class PageBoiler extends PageWidget<ModuleBoiler> {
		public PageBoiler(IPageComponent<ModuleBoiler> component, GuiContainer gui) {
			super(component, gui);
		}
		
		@Override
		public void addWidgets() {
			super.addWidgets();
			addWidget(new WidgetFluidTank(55, 15, module.getTankWater()));
			addWidget(new WidgetFluidTank(105, 15, module.getTankSteam()));
		}
	}
}
