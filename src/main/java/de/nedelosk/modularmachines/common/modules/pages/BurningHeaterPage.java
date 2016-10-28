package de.nedelosk.modularmachines.common.modules.pages;

import java.awt.Color;
import java.text.DecimalFormat;

import de.nedelosk.modularmachines.api.modules.handlers.filters.ItemFliterFurnaceFuel;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventoryBuilder;
import de.nedelosk.modularmachines.api.modules.heaters.IModuleHeaterBurning;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.client.gui.widgets.WidgetBurning;
import de.nedelosk.modularmachines.common.utils.Translator;
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
