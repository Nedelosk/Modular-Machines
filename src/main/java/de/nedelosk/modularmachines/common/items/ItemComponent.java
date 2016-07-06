package de.nedelosk.modularmachines.common.items;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import de.nedelosk.modularmachines.common.core.TabModularMachines;
import de.nedelosk.modularmachines.common.utils.IColoredItem;
import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemComponent extends Item implements IColoredItem, IItemModelRegister {

	public ArrayList<List> metas = Lists.newArrayList();
	public String componentName;

	public ItemComponent(String name) {
		this.setUnlocalizedName("component." + name);
		this.setCreativeTab(TabModularMachines.tabModularMachines);
		this.componentName = name;
	}

	public ItemComponent addMetaData(int color, String name, String... oreDict) {
		metas.add(Lists.newArrayList(color, name, oreDict));
		return this;
	}

	@Override
	public void registerModel(Item item, IModelManager manager) {
		for(int i = 0; i < metas.size(); i++){
			manager.registerItemModel(item, i, "components/" + componentName);
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "component." + componentName + "." + metas.get(stack.getItemDamage()).get(1);
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for(int i = 0; i < metas.size(); i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public int getColorFromItemstack(ItemStack stack, int renderpass) {
		if (metas.size() > stack.getItemDamage() && metas.get(stack.getItemDamage()) != null) {
			return (int) metas.get(stack.getItemDamage()).get(0);
		}
		return 16777215;
	}
}
