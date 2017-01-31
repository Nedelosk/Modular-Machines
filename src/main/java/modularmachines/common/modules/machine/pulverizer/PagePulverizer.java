package modularmachines.common.modules.machine.pulverizer;

import java.util.List;

import modularmachines.client.gui.widgets.WidgetProgressBar;
import modularmachines.common.containers.SlotModule;
import modularmachines.common.inventory.ItemHandlerModule;
import modularmachines.common.modules.pages.ModulePageWidget;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PagePulverizer extends ModulePageWidget<ModulePulverizer> {

	public PagePulverizer(ModulePulverizer parent) {
		super(parent);
	}
	
	@Override
	public void createSlots(List<Slot> slots) {
		super.createSlots(slots);
		ItemHandlerModule itemHandler = parent.getItemHandler();
		slots.add(new SlotModule(itemHandler, 0, 56, 35));
		slots.add(new SlotModule(itemHandler, 1, 116, 35));
		slots.add(new SlotModule(itemHandler, 2, 134, 35));
		slots.add(new SlotModule(itemHandler, 3, 116, 65));
		slots.add(new SlotModule(itemHandler, 4, 134, 65));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addWidgets() {
		super.addWidgets();
		addWidget(new WidgetProgressBar(82, 35, parent));
	}
}
