package modularmachines.common.modules;

import java.util.List;

import net.minecraft.item.ItemStack;

import modularmachines.api.modules.IModuleProvider;
import modularmachines.api.modules.container.IModuleContainer;
import modularmachines.api.modules.positions.IModulePosition;

public class CasingModuleHandler extends ModuleHandler {
	public CasingModuleHandler(IModuleProvider provider, IModulePosition... positions) {
		super(provider, positions);
	}
	
	@Override
	public List<ItemStack> extractModule(IModulePosition position, boolean simulate) {
		List<ItemStack> result = super.extractModule(position, simulate);
		if (!result.isEmpty() && !simulate) {
			IModuleContainer container = (IModuleContainer) getProvider();
			if (!container.getLocatable().getWorldObj().isRemote) {
				container.markForDeletion();
			}
		}
		return result;
	}
}
