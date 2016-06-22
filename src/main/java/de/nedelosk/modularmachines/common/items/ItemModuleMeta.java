package de.nedelosk.modularmachines.common.items;

import java.util.List;

import de.nedelosk.modularmachines.common.core.Registry;
import de.nedelosk.modularmachines.common.core.TabModularMachines;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemModuleMeta extends Item {

	public String[] names;
	@SideOnly(Side.CLIENT)
	public IIcon[] itemIcon;
	public String uln;

	public ItemModuleMeta(String uln, String[] names) {
		setCreativeTab(TabModularMachines.tabForestMods);
		setUnlocalizedName(uln);
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
		return Registry.setUnlocalizedItemName(uln + "_" + names[itemstack.getItemDamage()]);
	}
}
