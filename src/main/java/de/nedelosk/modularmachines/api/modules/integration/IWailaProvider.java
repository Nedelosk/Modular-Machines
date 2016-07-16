package de.nedelosk.modularmachines.api.modules.integration;

import java.util.List;

import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraft.item.ItemStack;

public interface IWailaProvider {

	List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IModuleState state, IWailaState data);

	List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IModuleState state, IWailaState data);

	List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IModuleState state, IWailaState data);
}
