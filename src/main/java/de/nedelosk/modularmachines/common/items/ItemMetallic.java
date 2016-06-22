package de.nedelosk.modularmachines.common.items;

import java.util.List;

import de.nedelosk.modularmachines.common.core.Registry;
import de.nedelosk.modularmachines.common.core.TabModularMachines;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemMetallic extends ItemForest {

	public String[] material = new String[] { "coke" };
	@SideOnly(Side.CLIENT)
	public IIcon[] itemIcon;

	public ItemMetallic() {
		super(null, TabModularMachines.tabForestMods);
		setHasSubtypes(true);
		setUnlocalizedName("nature");
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
