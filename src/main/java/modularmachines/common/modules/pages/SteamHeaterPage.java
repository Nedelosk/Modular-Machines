package modularmachines.common.modules.pages;

import java.awt.Color;
import java.text.DecimalFormat;

import net.minecraft.client.gui.FontRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.handlers.filters.FluidFilter;
import modularmachines.api.modules.handlers.filters.ItemFilterFluid;
import modularmachines.api.modules.handlers.filters.OutputFilter;
import modularmachines.api.modules.handlers.inventory.IModuleInventoryBuilder;
import modularmachines.api.modules.handlers.tank.IModuleTankBuilder;
import modularmachines.api.modules.heaters.IModuleHeaterBurning;
import modularmachines.api.modules.state.IModuleState;
import modularmachines.common.core.managers.FluidManager;
import modularmachines.common.utils.Translator;

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
