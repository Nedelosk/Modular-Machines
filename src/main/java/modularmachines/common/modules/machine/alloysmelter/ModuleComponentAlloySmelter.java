package modularmachines.common.modules.machine.alloysmelter;

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

public class ModuleComponentAlloySmelter extends DefaultModuleComponent<ModuleAlloySmelter> {
	
	public ModuleComponentAlloySmelter(ModuleAlloySmelter module) {
		super(module, PageAlloySmelter::new);
	}
	
	@Override
	public void createSlots(List<Slot> slots) {
		super.createSlots(slots);
		ItemHandlerModule itemHandler = parent.getItemHandler();
		slots.add(new SlotModule(itemHandler, 0, 36, 35));
		slots.add(new SlotModule(itemHandler, 1, 54, 35));
		slots.add(new SlotModule(itemHandler, 2, 116, 35));
		slots.add(new SlotModule(itemHandler, 3, 134, 35));
	}
	
	@SideOnly(Side.CLIENT)
	public static class PageAlloySmelter extends PageWidget<ModuleAlloySmelter> {
		public PageAlloySmelter(IPageComponent component, GuiContainer gui) {
			super(component, gui);
		}
		
		@Override
		public void addWidgets() {
			super.addWidgets();
			addWidget(new WidgetProgressBar(82, 35, module));
		}
	}
}
