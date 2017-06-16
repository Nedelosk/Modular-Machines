package modularmachines.common.modules.machine.lathe;

import java.util.List;

import net.minecraft.inventory.Slot;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.client.gui.widgets.WidgetMode;
import modularmachines.client.gui.widgets.WidgetProgressBar;
import modularmachines.common.containers.SlotModule;
import modularmachines.common.inventory.ItemHandlerModule;
import modularmachines.common.modules.pages.ModulePageWidget;

public class PageLathe extends ModulePageWidget<ModuleLathe> {

	public PageLathe(ModuleLathe parent) {
		super(parent);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addWidgets() {
		super.addWidgets();
		addWidget(new WidgetProgressBar(82, 36, parent));
		addWidget(new WidgetMode(86, 16, parent));
	}
	
	@Override
	public void createSlots(List<Slot> slots) {
		super.createSlots(slots);
		ItemHandlerModule itemHandler = parent.getItemHandler();
		slots.add(new SlotModule(itemHandler, 0, 54, 35));
		slots.add(new SlotModule(itemHandler, 1, 116, 35));
		slots.add(new SlotModule(itemHandler, 2, 134, 35));
	}
}
