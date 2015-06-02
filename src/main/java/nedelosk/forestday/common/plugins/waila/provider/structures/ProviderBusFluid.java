package nedelosk.forestday.common.plugins.waila.provider.structures;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import nedelosk.forestday.structure.base.blocks.tile.TileBusFluid;
import nedelosk.forestday.structure.base.blocks.tile.TileCoilHeat;
import nedelosk.nedeloskcore.common.fluids.FluidTankNedelosk;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class ProviderBusFluid implements IWailaDataProvider {

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
		TileBusFluid bus = (TileBusFluid) accessor.getTileEntity();
		FluidTankNedelosk tank = bus.getTank();
		if(tank.getFluid() != null)
		{
		currenttip.add("Fluid Tank: " + "Fluid ( " + StatCollector.translateToLocal(tank.getFluid().getLocalizedName()) + " " + tank.getCapacity() + " / " + tank.getFluidAmount() + " )");
		}
		else
		{
			currenttip.add("Fluid Tank: " + "Fluid ( None )");
		}
		return currenttip;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack,
			List<String> currenttip, IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
		return null;
	}

}
