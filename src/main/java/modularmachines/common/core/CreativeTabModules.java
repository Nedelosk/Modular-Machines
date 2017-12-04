package modularmachines.common.core;

import java.util.stream.Collectors;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.IModuleType;
import modularmachines.api.modules.ModuleManager;
import modularmachines.common.items.ModuleItems;

public class CreativeTabModules extends CreativeTabs {
	CreativeTabModules() {
		super("modularmachines.modules");
	}
	
	@Override
	public ItemStack getTabIconItem() {
		return ModuleItems.LARGE_TANK.get();
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void displayAllRelevantItems(NonNullList<ItemStack> itemStacks) {
		itemStacks.addAll(ModuleManager.registry.getTypes().stream().map(IModuleType::getItem).collect(Collectors.toList()));
	}
}
