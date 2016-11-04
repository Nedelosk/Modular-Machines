package modularmachines.api.modules.integration;

import java.util.List;

import modularmachines.api.integration.IWailaState;
import modularmachines.api.modules.IModule;
import net.minecraft.item.ItemStack;

public interface IModuleWaila extends IModule {

	List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaState data);

	List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaState data);

	List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaState data);
}
