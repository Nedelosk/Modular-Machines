package de.nedelosk.forestmods.common.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.common.core.TabModularMachines;
import de.nedelosk.forestmods.library.core.Registry;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemMetaModular extends ItemModular {

	public String[] names;
	@SideOnly(Side.CLIENT)
	public IIcon[] itemIcon;
	public String uln;

	public ItemMetaModular(String uln, String[] names) {
		super(uln);
		setHasSubtypes(true);
		setCreativeTab(TabModularMachines.tabModules);
		this.names = names;
		this.uln = uln;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister iconRegister) {
		this.itemIcon = new IIcon[names.length];
		for(int i = 0; i < this.itemIcon.length; ++i) {
			this.itemIcon[i] = iconRegister.registerIcon("forestmods:" + uln + "/" + names[i]);
		}
	}

	@Override
	public void getSubItems(Item id, CreativeTabs tab, List list) {
		for(int i = 0; i < names.length; i++) {
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
		return Registry.setUnlocalizedItemName(uln + "." + itemstack.getItemDamage());
	}
}
