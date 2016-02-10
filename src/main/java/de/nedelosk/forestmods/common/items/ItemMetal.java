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

public class ItemMetal extends ItemModular {

	@SideOnly(Side.CLIENT)
	private IIcon[] icons;
	private String[][] metals;
	private String uln;
	private String iconName;

	public ItemMetal(String uln, String iconName, String[][] metals) {
		super(uln);
		setHasSubtypes(true);
		this.metals = metals;
		this.uln = uln;
		this.iconName = iconName;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister iconRegister) {
		this.icons = new IIcon[metals.length * 10];
		for ( int m = 0; m < metals.length; m++ ) {
			String[] metal = metals[m];
			for ( int i = 0; i < metal.length; ++i ) {
				this.icons[m * 10 + i] = iconRegister.registerIcon("forestmods:" + uln + "/" + iconName + metal[i]);
			}
		}
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for ( int m = 0; m < metals.length; m++ ) {
			String[] metal = metals[m];
			for ( int i = 0; i < metal.length; ++i ) {
				list.add(new ItemStack(item, 1, m * 10 + i));
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconFromDamage(int meta) {
		return icons[meta];
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return Registry.setUnlocalizedItemName(iconName + getName(itemstack.getItemDamage()));
	}

	private String getName(int id) {
		int i = 0;
		while (id > 9) {
			i++;
			id -= 10;
		}
		return metals[i][id];
	}
}
