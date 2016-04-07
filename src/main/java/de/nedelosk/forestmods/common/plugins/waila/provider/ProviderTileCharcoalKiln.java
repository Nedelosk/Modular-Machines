package de.nedelosk.forestmods.common.plugins.waila.provider;

import java.util.List;

import de.nedelosk.forestmods.common.blocks.tile.TileCharcoalKiln;
import de.nedelosk.forestmods.common.config.Config;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.item.ItemStack;

public class ProviderTileCharcoalKiln implements IWailaDataProvider {

	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return null;
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return currenttip;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		if (accessor.getTileEntity() != null && accessor.getTileEntity() instanceof TileCharcoalKiln) {
			TileCharcoalKiln kiln = (TileCharcoalKiln) accessor.getTileEntity();
			if (kiln.isConnected() && kiln.getController().isAssembled() && kiln.getController().isActive()) {
				int p = kiln.getController().getBurnTime() * 100 / Config.charcoalKilnBurnTime * 100;
				currenttip.add(p / 100 + " %");
			}
		}
		return currenttip;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return currenttip;
	}
}
