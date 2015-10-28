package nedelosk.forestday.common.plugins.waila.provider.machines;

import java.util.List;

import net.minecraft.item.ItemStack;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import nedelosk.forestday.common.blocks.tiles.TileKiln;

public class ProviderTileKiln implements IWailaDataProvider {

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
		TileKiln tile = (TileKiln) accessor.getTileEntity();

		int heat = tile.heat;
		int resinAmount = tile.tank1.getFluidAmount();
		int tarAmount = tile.tank2.getFluidAmount();
		int lavaAmount = tile.tankLava.getFluidAmount();

		currenttip.add("Heat: " + heat + " H");
		currenttip.add("Tank 1: 16000 mb / " + resinAmount + " mb");
		currenttip.add("Tank 2: 16000 mb / " + tarAmount + " mb");
		currenttip.add("Lava Tank: 8000 mb / " + lavaAmount + " mb");
		currenttip.add("Progress: " + tile.getBurnTimeTotal() + " / " + tile.getBurnTime());
		return currenttip;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
		return null;
	}

}
