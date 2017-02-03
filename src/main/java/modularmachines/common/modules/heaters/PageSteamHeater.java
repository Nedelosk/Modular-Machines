package modularmachines.common.modules.heaters;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.List;

import modularmachines.api.modules.Module;
import modularmachines.api.modules.energy.IHeatSource;
import modularmachines.client.gui.widgets.WidgetFluidTank;
import modularmachines.common.containers.SlotModule;
import modularmachines.common.inventory.ItemHandlerModule;
import modularmachines.common.modules.pages.ModulePageWidget;
import modularmachines.common.utils.ModuleUtil;
import modularmachines.common.utils.Translator;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PageSteamHeater extends ModulePageWidget<ModuleHeaterSteam> {

	public static final DecimalFormat FORMATE = new DecimalFormat("#0.00");
	
	public PageSteamHeater(ModuleHeaterSteam parent) {
		super(parent);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void drawForeground(FontRenderer fontRenderer, int mouseX, int mouseY) {
		super.drawForeground(fontRenderer, mouseX, mouseY);
		IHeatSource heatSource = ModuleUtil.getHeat(parent.getLogic());
		String heatName = Translator.translateToLocalFormatted("module.heater.heat", FORMATE.format(heatSource.getHeatStored()));
		fontRenderer.drawString(heatName, 135 - (fontRenderer.getStringWidth(heatName) / 2), 45, Color.GRAY.getRGB());
	}
	
	@Override
	public void createSlots(List<Slot> slots) {
		super.createSlots(slots);
		ItemHandlerModule itemHandler = parent.getItemHandler();
		slots.add(new SlotModule(itemHandler, 0, 15, 28));
		slots.add(new SlotModule(itemHandler, 0, 15, 48));
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addWidgets() {
		super.addWidgets();
		addWidget(new WidgetFluidTank(80, 18, parent.getTank()));
	}
}
