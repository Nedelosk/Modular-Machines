package modularmachines.common.modules.engines;

import java.util.List;

import net.minecraft.inventory.Slot;

import net.minecraftforge.fluids.IFluidTank;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.client.gui.widgets.WidgetFluidTank;
import modularmachines.common.containers.SlotModule;
import modularmachines.common.inventory.ItemHandlerModule;
import modularmachines.common.modules.pages.ModulePageWidget;

public class PageSteamEngine extends ModulePageWidget<ModuleEngineSteam> {

	public PageSteamEngine(ModuleEngineSteam parent) {
		super(parent);
	}
	
	@Override
	public void createSlots(List<Slot> slots) {
		ItemHandlerModule itemHandler = parent.getItemHandler();
		slots.add(new SlotModule(itemHandler, 0, 15, 28));
		slots.add(new SlotModule(itemHandler, 1, 15, 48));
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addWidgets() {
		IFluidTank tank = parent.getFluidTank();
		addWidget(new WidgetFluidTank(80, 18, tank));
	}
}
