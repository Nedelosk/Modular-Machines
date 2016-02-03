package de.nedelosk.forestmods.common.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestcore.core.Registry;
import de.nedelosk.forestcore.items.ItemForest;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemNugget extends ItemForest {

	private String[] nugget = new String[] { "Copper", "Tin", "Silver", "Lead", "Nickel", "Iron", "Niobium", "Tantalum", "Aluminum", "Steel", "White_Steel", "Gray_Steel" };
	@SideOnly(Side.CLIENT)
	public IIcon[] itemIcon;

	public ItemNugget() {
		super(null, CreativeTabs.tabMaterials);
		setHasSubtypes(true);
		setUnlocalizedName("nugget");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister iconRegister) {
		this.itemIcon = new IIcon[nugget.length];
		for ( int i = 0; i < this.itemIcon.length; ++i ) {
			this.itemIcon[i] = iconRegister.registerIcon("forestmods:nuggets/nugget" + nugget[i]);
		}
	}

	@Override
	public void getSubItems(Item id, CreativeTabs tab, List list) {
		for ( int i = 0; i < nugget.length; i++ ) {
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
		return Registry.setUnlocalizedItemName("nugget." + itemstack.getItemDamage(), "fd");
	}
}
