package de.nedelosk.modularmachines.common.items;

import java.util.List;

import de.nedelosk.forestmods.library.Tabs;
import de.nedelosk.modularmachines.common.core.Registry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemToolParts extends ItemForest {

	public String[] parts = new String[] { "file_handle", "file_head_stone", "file_head_iron", "file_head_diamond", "knife_handle", "knife_head", "cutter_head",
			"cutter_handle", "adze_head", "adze_head_long", "adze_handle", "adze_handle_long" };
	@SideOnly(Side.CLIENT)
	public IIcon[] itemIcon;

	public ItemToolParts() {
		super(null, Tabs.tabForestMods);
		setHasSubtypes(true);
		setUnlocalizedName("tool.parts");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister iconRegister) {
		this.itemIcon = new IIcon[parts.length];
		for(int i = 0; i < this.itemIcon.length; ++i) {
			this.itemIcon[i] = iconRegister.registerIcon("forestmods:toolparts/" + parts[i]);
		}
	}

	@Override
	public void getSubItems(Item id, CreativeTabs tab, List list) {
		for(int i = 0; i < parts.length; i++) {
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
		return Registry.setUnlocalizedItemName("parts." + itemstack.getItemDamage());
	}
}
