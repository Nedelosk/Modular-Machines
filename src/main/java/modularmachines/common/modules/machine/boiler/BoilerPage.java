package modularmachines.common.modules.machine.boiler;

import java.util.List;

import modularmachines.client.gui.widgets.WidgetFluidTank;
import modularmachines.common.containers.SlotModule;
import modularmachines.common.inventory.ItemHandlerModule;
import modularmachines.common.modules.pages.ModulePageWidget;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BoilerPage extends ModulePageWidget<ModuleBoiler> {

	public BoilerPage(ModuleBoiler parent) {
		super(parent);
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
	@Override
	public void addWidgets() {
		addWidget(new WidgetFluidTank(55, 15, parent.getTankWater()));
		addWidget(new WidgetFluidTank(105, 15, parent.getTankSteam()));
	}
}
