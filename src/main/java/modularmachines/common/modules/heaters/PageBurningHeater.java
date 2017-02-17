package modularmachines.common.modules.heaters;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.List;

import modularmachines.api.modules.energy.IHeatSource;
import modularmachines.client.gui.widgets.WidgetBurning;
import modularmachines.common.containers.SlotModule;
import modularmachines.common.modules.pages.ModulePageWidget;
import modularmachines.common.utils.ModuleUtil;
import modularmachines.common.utils.Translator;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PageBurningHeater extends ModulePageWidget<ModuleHeaterBurning> {

	public static final DecimalFormat FORMATE = new DecimalFormat("#0.00");
	
	public PageBurningHeater(ModuleHeaterBurning parent) {
		super(parent);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addWidgets(){
		super.addWidgets();
		addWidget(new WidgetBurning(80, 18, parent));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void drawForeground(FontRenderer fontRenderer, int mouseX, int mouseY) {
		super.drawForeground(fontRenderer, mouseX, mouseY);
		IHeatSource heatSource = ModuleUtil.getHeat(parent.getLogic());
		String heatName = Translator.translateToLocalFormatted("module.heater.heat", FORMATE.format(heatSource.getHeatStored()));
		fontRenderer.drawString(heatName, 90 - (fontRenderer.getStringWidth(heatName) / 2), 55, Color.GRAY.getRGB());
	}
	
	@Override
	public void createSlots(List<Slot> slots) {
		super.createSlots(slots);
		slots.add(new SlotModule(parent.getItemHandler(), 0, 80, 35));
	}
}
