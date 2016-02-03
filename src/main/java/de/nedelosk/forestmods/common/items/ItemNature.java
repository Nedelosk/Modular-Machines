package de.nedelosk.forestmods.common.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestcore.core.Registry;
import de.nedelosk.forestcore.items.ItemForest;
import de.nedelosk.forestmods.api.Tabs;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemNature extends ItemForest {

	public String[] material = new String[] { "sawdust", "peat", "ash", "mortar" };
	@SideOnly(Side.CLIENT)
	public IIcon[] itemIcon;

	public ItemNature() {
		super(null, Tabs.tabForestMods);
		setHasSubtypes(true);
		setUnlocalizedName("nature");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister iconRegister) {
		this.itemIcon = new IIcon[material.length];
		for ( int i = 0; i < this.itemIcon.length; ++i ) {
			this.itemIcon[i] = iconRegister.registerIcon("forestday:" + material[i]);
		}
	}

	@Override
	public void getSubItems(Item id, CreativeTabs tab, List list) {
		for ( int i = 0; i < material.length; i++ ) {
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
		return Registry.setUnlocalizedItemName("nature." + itemstack.getItemDamage(), "fd");
	}
}
