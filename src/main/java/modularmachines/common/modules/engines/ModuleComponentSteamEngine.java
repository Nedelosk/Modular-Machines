package modularmachines.common.modules.engines;

import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Slot;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.pages.DefaultModuleComponent;
import modularmachines.api.modules.pages.IModuleComponent;
import modularmachines.client.gui.widgets.WidgetFluidTank;
import modularmachines.common.containers.SlotModule;
import modularmachines.common.inventory.ItemHandlerModule;
import modularmachines.common.modules.pages.PageWidget;

public class ModuleComponentSteamEngine extends DefaultModuleComponent<ModuleEngineSteam> {
	
	public ModuleComponentSteamEngine(ModuleEngineSteam parent) {
		super(parent, PageSteamEngine::new);
	}
	
	@Override
	public void createSlots(List<Slot> slots) {
		ItemHandlerModule itemHandler = parent.getItemHandler();
		slots.add(new SlotModule(itemHandler, 0, 15, 28));
		slots.add(new SlotModule(itemHandler, 1, 15, 48));
	}
	
	@SideOnly(Side.CLIENT)
	public static class PageSteamEngine extends PageWidget<ModuleEngineSteam> {
		public PageSteamEngine(IModuleComponent<ModuleEngineSteam> component, GuiContainer gui) {
			super(component, gui);
		}
		
		@Override
		public void addWidgets() {
			addWidget(new WidgetFluidTank(80, 18, module.getFluidTank()));
		}
	}
}
