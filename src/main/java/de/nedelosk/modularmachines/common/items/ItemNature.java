package de.nedelosk.modularmachines.common.items;

import java.util.List;

import de.nedelosk.forestmods.library.Tabs;
import de.nedelosk.modularmachines.common.core.Registry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemNature extends ItemForest {

	public String[] material = new String[] { "sawdust", "peat", "ash", "mortar", "ash_brick" };
	@SideOnly(Side.CLIENT)
	public IIcon[] itemIcon;

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
	public void registerIcons(IIconRegister iconRegister) {
		this.itemIcon = new IIcon[material.length];
		for(int i = 0; i < this.itemIcon.length; ++i) {
			this.itemIcon[i] = iconRegister.registerIcon("forestmods:" + material[i]);
		}
	}

	@Override
	public void getSubItems(Item id, CreativeTabs tab, List list) {
		for(int i = 0; i < material.length; i++) {
			list.add(new ItemStack(id, 1, i));
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconFromDamage(int meta) {
		return itemIcon[meta];
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return Registry.setUnlocalizedItemName("nature." + itemstack.getItemDamage());
	}
}
