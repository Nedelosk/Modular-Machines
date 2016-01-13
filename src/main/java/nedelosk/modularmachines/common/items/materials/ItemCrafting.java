package nedelosk.modularmachines.common.items.materials;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestcore.library.core.Registry;
import nedelosk.modularmachines.common.items.ModularItem;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemCrafting extends ModularItem {

	public static String[] items = new String[] { "iron_wire", "aluminium_coated_paper" };
	public String[] name;
	@SideOnly(Side.CLIENT)
	public IIcon[] itemIcon;
	public String uln;

	public ItemCrafting(String[] names, String uln) {
		super("crafting" + uln);
		this.uln = uln;
		this.name = names;
		setHasSubtypes(true);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister iconRegister) {
		this.itemIcon = new IIcon[name.length];
		for ( int i = 0; i < this.itemIcon.length; ++i ) {
			this.itemIcon[i] = iconRegister.registerIcon("modularmachines:crafting/" + name[i]);
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
		return Registry.setUnlocalizedItemName("crafting" + uln + "." + itemstack.getItemDamage(), "mm");
	}
}
