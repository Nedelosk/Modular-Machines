package modularmachines.common.modules.pages;

import java.util.List;

import modularmachines.client.gui.widgets.WidgetFluidTank;
import modularmachines.common.containers.SlotModule;
import modularmachines.common.inventory.ItemHandlerModule;
import modularmachines.common.modules.engines.ModuleEngineSteam;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SteamEnginePage extends ModulePageWidget<ModuleEngineSteam> {

	public SteamEnginePage(ModuleEngineSteam parent) {
		super(parent);
	}
	
	@Override
	public void createSlots(List<Slot> slots) {
		ItemHandlerModule itemHandler = parent.getItemHandler();
		slots.add(new SlotModule(itemHandler, itemHandler.getContainer(0), 15, 28));
		slots.add(new SlotModule(itemHandler, itemHandler.getContainer(1), 15, 48));
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addWidgets() {
		IFluidTank tank = parent.getFluidTank();
		addWidget(new WidgetFluidTank(80, 18, tank));
	}
}
