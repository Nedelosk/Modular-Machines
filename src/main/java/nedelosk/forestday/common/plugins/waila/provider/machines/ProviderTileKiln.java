package nedelosk.forestday.common.plugins.waila.provider.machines;

import java.util.List;

import nedelosk.forestday.common.machines.base.wood.kiln.TileKiln;
import nedelosk.nedeloskcore.common.fluids.FluidTankNedelosk;
import net.minecraft.item.ItemStack;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;

public class ProviderTileKiln implements IWailaDataProvider {

	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return null;
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return null;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		TileKiln tile = (TileKiln) accessor.getTileEntity();
		
		int heat = tile.getHeat();
		int resinAmount = /*tile.getTank()[0].getFluidAmount()*/ 0;
		int tarAmount = /*tile.getTank()[1].getFluidAmount()*/ 0;
		
		currenttip.add("Heat: " + heat + " H");
		currenttip.add("Resin: 16000 mb / " + resinAmount + " mb");
		currenttip.add("Tar: 16000 mb / " + tarAmount + " mb");
		currenttip.add("Progress: " + tile.getBurnTimeTotal() + " / " + tile.getBurnTime());
		return currenttip;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return null;
	}

}
