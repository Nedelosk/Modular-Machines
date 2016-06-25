package de.nedelosk.modularmachines.common.items;

import java.util.List;

import de.nedelosk.forestmods.library.Tabs;
import de.nedelosk.modularmachines.common.core.Registry;
import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemNature extends ItemForest implements IItemModelRegister{

	public String[] material = new String[] { "sawdust", "peat", "ash", "mortar", "ash_brick" };

	public ItemNature() {
		super(null, Tabs.tabForestMods);
		setHasSubtypes(true);
		setUnlocalizedName("nature");
	}

	@Override
	public int getMaxDamage(ItemStack stack) {
		return super.getMaxDamage(stack);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel(Item item, IModelManager manager) {
		for(int i = 0; i < material.length; ++i) {
			manager.registerItemModel(item, i, material[i]);
		}
	}

	@Override
	public void getSubItems(Item id, CreativeTabs tab, List list) {
		for(int i = 0; i < material.length; i++) {
			list.add(new ItemStack(id, 1, i));
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return Registry.setUnlocalizedItemName("nature." + itemstack.getItemDamage());
	}
}
