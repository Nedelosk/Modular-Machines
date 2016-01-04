package nedelosk.forestday.plugins.waila.provider;

import java.util.List;

import net.minecraft.item.ItemStack;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import nedelosk.forestday.common.blocks.tiles.TileCampfire;

public class ProviderTileCampfire implements IWailaDataProvider {

	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return null;
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
		return null;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
		TileCampfire tile = (TileCampfire) accessor.getTileEntity();

		int fuel = tile.fuelStorage;
		boolean isWorking = tile.isWorking;

		currenttip.add("Fuel: " + fuel);
		currenttip.add("Is Working: " + isWorking);
		currenttip.add("Progress: " + tile.getBurnTime() + " / " + tile.getBurnTimeTotal());
		return currenttip;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
		return null;
	}

}
