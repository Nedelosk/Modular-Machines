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
import modularmachines.api.modules.pages.IPageComponent;
import modularmachines.client.gui.widgets.WidgetBurning;
import modularmachines.common.containers.SlotModule;
import modularmachines.common.modules.pages.PageWidget;
import modularmachines.common.utils.ModuleUtil;
import modularmachines.common.utils.Translator;

public class ModuleComponentBurningHeater extends DefaultModuleComponent<ModuleHeaterBurning> {
	
	public static final DecimalFormat FORMATE = new DecimalFormat("#0.00");
	
	public ModuleComponentBurningHeater(ModuleHeaterBurning parent) {
		super(parent, PageBurningHeater::new);
	}
	
	@Override
	public void createSlots(List<Slot> slots) {
		super.createSlots(slots);
		slots.add(new SlotModule(parent.getItemHandler(), 0, 80, 35));
	}
	
	public static class PageBurningHeater extends PageWidget<ModuleHeaterBurning> {
		public PageBurningHeater(IPageComponent<ModuleHeaterBurning> component, GuiContainer gui) {
			super(component, gui);
		}
		
		@Override
		@SideOnly(Side.CLIENT)
		public void addWidgets() {
			super.addWidgets();
			addWidget(new WidgetBurning(80, 18, module));
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void drawForeground(FontRenderer fontRenderer, int mouseX, int mouseY) {
			super.drawForeground(fontRenderer, mouseX, mouseY);
			IHeatSource heatSource = ModuleUtil.getHeat(module.getContainer());
			String heatName = Translator.translateToLocalFormatted("module.heater.heat", FORMATE.format(heatSource.getHeatStored()));
			fontRenderer.drawString(heatName, 90 - (fontRenderer.getStringWidth(heatName) / 2), 55, Color.GRAY.getRGB());
		}
	}
}
