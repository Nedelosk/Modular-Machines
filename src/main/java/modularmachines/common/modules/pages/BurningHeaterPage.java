package modularmachines.common.modules.pages;

import java.awt.Color;
import java.text.DecimalFormat;

import modularmachines.api.modules.handlers.filters.ItemFliterFurnaceFuel;
import modularmachines.api.modules.handlers.inventory.IModuleInventoryBuilder;
import modularmachines.api.modules.heaters.IModuleHeaterBurning;
import modularmachines.api.modules.state.IModuleState;
import modularmachines.client.gui.widgets.WidgetBurning;
import modularmachines.common.utils.Translator;
import net.minecraft.client.gui.FontRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BurningHeaterPage extends MainPage<IModuleHeaterBurning> {

	public BurningHeaterPage(IModuleState<IModuleHeaterBurning> heaterState) {
		super("heater", heaterState);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addWidgets() {
		super.addWidgets();
		add(new WidgetBurning(80, 18, moduleState));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void drawForeground(FontRenderer fontRenderer, int mouseX, int mouseY) {
		super.drawForeground(fontRenderer, mouseX, mouseY);
		DecimalFormat f = new DecimalFormat("#0.00");
		String heatName = Translator.translateToLocalFormatted("module.heater.heat", f.format(moduleState.getModular().getHeatSource().getHeatStored()));
		fontRenderer.drawString(heatName, 90 - (fontRenderer.getStringWidth(heatName) / 2), 55, Color.gray.getRGB());
	}

	@Override
	public void createInventory(IModuleInventoryBuilder invBuilder) {
		invBuilder.addInventorySlot(true, 80, 35, ItemFliterFurnaceFuel.INSTANCE);
	}
}
