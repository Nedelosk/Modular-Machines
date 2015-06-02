package nedelosk.forestday.common.plugins.waila.provider.structures;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import nedelosk.forestday.structure.base.blocks.tile.TileRegulator;
import nedelosk.forestday.structure.base.blocks.tile.TileRegulatorHeat;
import net.minecraft.item.ItemStack;

public class ProviderTileRegulatorHeat implements IWailaDataProvider {

	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
		return null;
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack,
			List<String> currenttip, IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
		return null;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack,
			List<String> currenttip, IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
		TileRegulatorHeat regulator = (TileRegulatorHeat) accessor.getTileEntity();
		int heat = regulator.getHeat();
		int heatR = regulator.getHeatRegulator();
		int heatC = regulator.getCoilHeat();
		int heatCM = regulator.getCoilMaxHeat();
		currenttip.add("Heat: " + heat);
		currenttip.add("Regulator Heat: " + heatR);
		currenttip.add("Coil Heat: " + heatC);
		currenttip.add("Coil Max Heat: " + heatCM);
		return currenttip;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack,
			List<String> currenttip, IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
		return null;
	}

}
