package nedelosk.modularmachines.api.modular.integration;

import java.util.List;

import net.minecraft.item.ItemStack;

public interface IWailaProvider {

	List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaData data);

	List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaData data);

	List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaData data);
}
