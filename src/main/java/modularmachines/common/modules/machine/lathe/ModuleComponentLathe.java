package modularmachines.common.modules.machine.lathe;

import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Slot;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.pages.DefaultModuleComponent;
import modularmachines.api.modules.pages.IPageComponent;
import modularmachines.client.gui.widgets.WidgetMode;
import modularmachines.client.gui.widgets.WidgetProgressBar;
import modularmachines.common.containers.SlotModule;
import modularmachines.common.inventory.ItemHandlerModule;
import modularmachines.common.modules.pages.PageWidget;

public class ModuleComponentLathe extends DefaultModuleComponent<ModuleLathe> {
	
	public ModuleComponentLathe(ModuleLathe parent) {
		super(parent, PageLathe::new);
	}
	
	@Override
	public void createSlots(List<Slot> slots) {
		super.createSlots(slots);
		ItemHandlerModule itemHandler = parent.getItemHandler();
		slots.add(new SlotModule(itemHandler, 0, 54, 35));
		slots.add(new SlotModule(itemHandler, 1, 116, 35));
		slots.add(new SlotModule(itemHandler, 2, 134, 35));
	}
	
	@SideOnly(Side.CLIENT)
	public static class PageLathe extends PageWidget<ModuleLathe> {
		
		public PageLathe(IPageComponent page, GuiContainer gui) {
			super(page, gui);
		}
		
		@Override
		public void addWidgets() {
			super.addWidgets();
			addWidget(new WidgetProgressBar(82, 36, module));
			addWidget(new WidgetMode(86, 16, module));
		}
	}
}
