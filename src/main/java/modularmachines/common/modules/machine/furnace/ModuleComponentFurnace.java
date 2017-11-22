package modularmachines.common.modules.machine.furnace;

import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Slot;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.pages.DefaultModuleComponent;
import modularmachines.api.modules.pages.IPageComponent;
import modularmachines.client.gui.widgets.WidgetProgressBar;
import modularmachines.common.containers.SlotModule;
import modularmachines.common.inventory.ItemHandlerModule;
import modularmachines.common.modules.pages.PageWidget;

public class ModuleComponentFurnace extends DefaultModuleComponent<ModuleFurnace> {
	
	public ModuleComponentFurnace(ModuleFurnace module) {
		super(module, PageFurnace::new);
	}
	
	@Override
	public void createSlots(List<Slot> slots) {
		ItemHandlerModule itemHandler = parent.getItemHandler();
		slots.add(new SlotModule(itemHandler, 0, 55, 35));
		slots.add(new SlotModule(itemHandler, 1, 116, 35));
	}
	
	@SideOnly(Side.CLIENT)
	public static class PageFurnace extends PageWidget<ModuleFurnace> {
		public PageFurnace(IPageComponent component, GuiContainer gui) {
			super(component, gui);
		}
		
		@Override
		public void addWidgets() {
			super.addWidgets();
			addWidget(new WidgetProgressBar(82, 35, module));
		}
	}
}
