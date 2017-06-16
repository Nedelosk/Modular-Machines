package modularmachines.common.modules.machine.alloysmelter;

import java.util.List;

import net.minecraft.inventory.Slot;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.client.gui.widgets.WidgetProgressBar;
import modularmachines.common.containers.SlotModule;
import modularmachines.common.inventory.ItemHandlerModule;
import modularmachines.common.modules.pages.ModulePageWidget;

public class PageAlloySmelter extends ModulePageWidget<ModuleAlloySmelter> {

	public PageAlloySmelter(ModuleAlloySmelter module) {
		super(module);
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
	@Override
	public void addWidgets() {
		super.addWidgets();
		addWidget(new WidgetProgressBar(82, 35, parent));
	}
}
