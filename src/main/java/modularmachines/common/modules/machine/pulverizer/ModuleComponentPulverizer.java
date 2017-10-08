package modularmachines.common.modules.machine.pulverizer;

import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Slot;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.pages.DefaultModuleComponent;
import modularmachines.api.modules.pages.IModuleComponent;
import modularmachines.client.gui.widgets.WidgetProgressBar;
import modularmachines.common.containers.SlotModule;
import modularmachines.common.inventory.ItemHandlerModule;
import modularmachines.common.modules.pages.PageWidget;

public class ModuleComponentPulverizer extends DefaultModuleComponent<ModulePulverizer> {
	
	public ModuleComponentPulverizer(ModulePulverizer parent) {
		super(parent, PagePulverizer::new);
	}
	
	@Override
	public void createSlots(List<Slot> slots) {
		super.createSlots(slots);
		ItemHandlerModule itemHandler = parent.getItemHandler();
		slots.add(new SlotModule(itemHandler, 0, 56, 35));
		slots.add(new SlotModule(itemHandler, 1, 116, 26));
		slots.add(new SlotModule(itemHandler, 2, 134, 26));
		slots.add(new SlotModule(itemHandler, 3, 116, 44));
		slots.add(new SlotModule(itemHandler, 4, 134, 44));
	}
	
	@SideOnly(Side.CLIENT)
	public static class PagePulverizer extends PageWidget<ModulePulverizer> {
		public PagePulverizer(IModuleComponent<ModulePulverizer> component, GuiContainer gui) {
			super(component, gui);
		}
		
		@Override
		public void addWidgets() {
			super.addWidgets();
			addWidget(new WidgetProgressBar(82, 35, module));
		}
	}
}
