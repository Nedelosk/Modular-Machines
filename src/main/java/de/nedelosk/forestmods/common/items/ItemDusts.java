package de.nedelosk.forestmods.common.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestcore.core.Registry;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemDusts extends ModularItem {

	private String[] name = new String[] { "Coal", "Obsidian", "Iron", "Gold", "Diamond", "Copper", "Tin", "Silver", "Lead", "Nickel", "Bronze", "Invar",
			"Ruby", "Columbite", "Niobium", "Tantalum", "Aluminum", "Steel", "White_Steel", "Gray_Steel" };
	@SideOnly(Side.CLIENT)
	public IIcon[] itemIcon;

	public ItemDusts() {
		super("dusts");
		setHasSubtypes(true);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister iconRegister) {
		this.itemIcon = new IIcon[name.length];
		for ( int i = 0; i < this.itemIcon.length; ++i ) {
			this.itemIcon[i] = iconRegister.registerIcon("forestmods:dusts/dust" + name[i]);
		}
	}

	@Override
	public void getSubItems(Item id, CreativeTabs tab, List list) {
		for ( int i = 0; i < name.length; i++ ) {
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
		return Registry.setUnlocalizedItemName("dust." + itemstack.getItemDamage(), "mm");
	}
}
