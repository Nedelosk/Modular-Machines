package de.nedelosk.forestmods.api.integration;

import java.util.List;

import net.minecraft.item.ItemStack;

public interface IWailaProvider {

	List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaState data);

	List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaState data);

	List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaState data);
}
