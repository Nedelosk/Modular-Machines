package nedelosk.forestday.common.plugins.waila.provider.structures;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import nedelosk.forestday.structure.base.blocks.tile.TileRegulator;
import nedelosk.forestday.structure.base.blocks.tile.TileRegulatorGrinding;
import nedelosk.forestday.structure.base.blocks.tile.TileRegulatorHeat;
import net.minecraft.item.ItemStack;

public class ProviderTileRegulatorGrinding implements IWailaDataProvider {

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
		TileRegulatorGrinding regulator = (TileRegulatorGrinding) accessor.getTileEntity();
		int roughness = regulator.getRoughness();
		int roughnessR = regulator.getRoughnessRegulator();
		int roughnessC = regulator.getCoilRoughness();
		int roughnessCM = regulator.getCoilMaxRoughness();
		currenttip.add("Roughness: " + roughness);
		currenttip.add("Regulator Roughness: " + roughnessR);
		currenttip.add("Coil Roughness: " + roughnessC);
		currenttip.add("Coil Max Roughness: " + roughnessCM);
		return currenttip;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack,
			List<String> currenttip, IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
		return null;
	}

}
