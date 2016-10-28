package de.nedelosk.modularmachines.common.modules.pages;

import java.awt.Color;
import java.text.DecimalFormat;

import de.nedelosk.modularmachines.api.modules.handlers.filters.FluidFilter;
import de.nedelosk.modularmachines.api.modules.handlers.filters.ItemFilterFluid;
import de.nedelosk.modularmachines.api.modules.handlers.filters.OutputFilter;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventoryBuilder;
import de.nedelosk.modularmachines.api.modules.handlers.tank.IModuleTankBuilder;
import de.nedelosk.modularmachines.api.modules.heaters.IModuleHeaterBurning;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.common.core.FluidManager;
import de.nedelosk.modularmachines.common.utils.Translator;
import net.minecraft.client.gui.FontRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SteamHeaterPage extends MainPage<IModuleHeaterBurning> {

	public SteamHeaterPage(IModuleState<IModuleHeaterBurning> heaterState) {
		super("heater.steam", heaterState);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void drawForeground(FontRenderer fontRenderer, int mouseX, int mouseY) {
		super.drawForeground(fontRenderer, mouseX, mouseY);
		DecimalFormat f = new DecimalFormat("#0.00");
		String heatName = Translator.translateToLocalFormatted("module.heater.heat", f.format(moduleState.getModular().getHeatSource().getHeatStored()));
		fontRenderer.drawString(heatName, 135 - (fontRenderer.getStringWidth(heatName) / 2), 45, Color.gray.getRGB());
	}

	@Override
	public void createInventory(IModuleInventoryBuilder invBuilder) {
		invBuilder.addInventorySlot(true, 15, 28, "liquid", ItemFilterFluid.get(FluidManager.STEAM));
		invBuilder.addInventorySlot(false, 15, 48, "container", OutputFilter.INSTANCE);
	}

	@Override
	public void createTank(IModuleTankBuilder tankBuilder) {
		tankBuilder.addFluidTank(16000, true, 80, 18, FluidFilter.get(FluidManager.STEAM));
	}
}
