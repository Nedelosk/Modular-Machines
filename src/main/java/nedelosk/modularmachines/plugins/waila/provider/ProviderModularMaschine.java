package nedelosk.modularmachines.plugins.waila.provider;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import nedelosk.modularmachines.api.modular.utils.ModularUtils;
import nedelosk.modularmachines.common.blocks.tile.TileModular;
import net.minecraft.item.ItemStack;

public class ProviderModularMaschine implements IWailaDataProvider {

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
		TileModular machine = (TileModular) accessor.getTileEntity();
		if(ModularUtils.getModuleStackProducer(machine.modular) != null)
		{
			IModuleProducer producer = ModularUtils.getModuleStackProducer(machine.modular).getModule();
			currenttip.add(producer.getBurnTime() + " / " + producer.getBurnTimeTotal());
		}
		return currenttip;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return null;
	}

}
