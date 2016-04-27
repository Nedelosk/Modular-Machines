package de.nedelosk.forestmods.library.items;

import de.nedelosk.forestmods.library.core.Registry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemForest extends Item {

	private String unl;
	private boolean hasMeta;

	public ItemForest(String unl, boolean hasMeta, CreativeTabs tab) {
		this.setUnlocalizedName(unl);
		this.unl = unl;
		this.setHasSubtypes(hasMeta);
		this.setCreativeTab(tab);
		this.hasMeta = hasMeta;
	}

	public ItemForest(String unl, CreativeTabs tab) {
		this.setCreativeTab(tab);
		this.setUnlocalizedName(unl);
		this.unl = unl;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return Registry.setUnlocalizedItemName(unl + ((hasMeta) ? itemstack.getItemDamage() : ""));
	}
}
