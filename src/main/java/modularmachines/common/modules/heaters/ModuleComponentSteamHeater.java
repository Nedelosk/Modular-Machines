package modularmachines.common.modules.heaters;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.List;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Slot;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.energy.IHeatSource;
import modularmachines.api.modules.pages.DefaultModuleComponent;
import modularmachines.api.modules.pages.IModuleComponent;
import modularmachines.client.gui.widgets.WidgetFluidTank;
import modularmachines.common.containers.SlotModule;
import modularmachines.common.inventory.ItemHandlerModule;
import modularmachines.common.modules.pages.PageWidget;
import modularmachines.common.utils.ModuleUtil;
import modularmachines.common.utils.Translator;

public class ModuleComponentSteamHeater extends DefaultModuleComponent<ModuleHeaterSteam> {

	public static final DecimalFormat FORMATE = new DecimalFormat("#0.00");
	
	public ModuleComponentSteamHeater(ModuleHeaterSteam parent) {
		super(parent, PageSteamHeater::new);
	}
	
	@Override
	public void createSlots(List<Slot> slots) {
		super.createSlots(slots);
		ItemHandlerModule itemHandler = parent.getItemHandler();
		slots.add(new SlotModule(itemHandler, 0, 15, 28));
		slots.add(new SlotModule(itemHandler, 0, 15, 48));
	}
	
	@SideOnly(Side.CLIENT)
	public static class PageSteamHeater extends PageWidget<ModuleHeaterSteam> {
		public PageSteamHeater(IModuleComponent component, GuiContainer gui) {
			super(component, gui);
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void drawForeground(FontRenderer fontRenderer, int mouseX, int mouseY) {
			super.drawForeground(fontRenderer, mouseX, mouseY);
			IHeatSource heatSource = ModuleUtil.getHeat(module.getContainer());
			String heatName = Translator.translateToLocalFormatted("module.heater.heat", FORMATE.format(heatSource.getHeatStored()));
			fontRenderer.drawString(heatName, 135 - (fontRenderer.getStringWidth(heatName) / 2), 45, Color.GRAY.getRGB());
		}
		
		@Override
		public void addWidgets() {
			addWidget(new WidgetFluidTank(80, 18, module.getTank()));
		}
	}
}
