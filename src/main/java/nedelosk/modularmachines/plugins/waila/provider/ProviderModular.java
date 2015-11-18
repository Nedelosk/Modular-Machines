package nedelosk.modularmachines.plugins.waila.provider;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import nedelosk.modularmachines.api.modular.module.basic.IModule;
import nedelosk.modularmachines.api.modular.module.tool.producer.energy.IProducerEngine;
import nedelosk.modularmachines.api.modular.module.tool.producer.machine.IProducerMachine;
import nedelosk.modularmachines.api.modular.utils.ModuleUtils;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.common.blocks.tile.TileModular;
import net.minecraft.item.ItemStack;

public class ProviderModular implements IWailaDataProvider {

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
		TileModular modular = (TileModular) accessor.getTileEntity();
		ModuleStack<IModule, IProducerMachine> stackMachine = ModuleUtils.getModuleStackMachine(modular.modular);
		if (ModuleUtils.getModuleStackMachine(modular.modular) != null) {
			IProducerMachine machine = stackMachine.getProducer();
		}
		ModuleStack<IModule, IProducerEngine> stackEngine = ModuleUtils.getModuleStackEngine(modular.modular);
		if (ModuleUtils.getModuleStackEngine(modular.modular) != null) {
			IProducerEngine engine = stackEngine.getProducer();
			currenttip.add(engine.getBurnTime(stackEngine) + " / " + engine.getBurnTimeTotal(stackEngine));
		}
		return currenttip;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
		return null;
	}

}
