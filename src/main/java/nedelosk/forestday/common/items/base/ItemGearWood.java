package nedelosk.forestday.common.items.base;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestcore.library.core.Registry;
import nedelosk.forestcore.library.items.ItemForest;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemGearWood extends ItemForest {

	@SideOnly(Side.CLIENT)
	public IIcon[] woodIcon;

	public ItemGearWood() {
		super(null, CreativeTabs.tabMaterials);
		setHasSubtypes(true);
		setUnlocalizedName("gearWood");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister iconRegister) {
		this.woodIcon = new IIcon[6];
		for ( int i = 0; i < 6; i++ ) {
			this.woodIcon[i] = iconRegister.registerIcon("forestday:gears/wood." + i);
		}
	}

	@Override
	public void getSubItems(Item id, CreativeTabs tab, List list) {
		for ( int i = 0; i < 6; i++ ) {
			list.add(new ItemStack(id, 1, i));
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconFromDamage(int meta) {
		return woodIcon[meta];
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return Registry.setUnlocalizedItemName("gear.wood." + itemstack.getItemDamage(), "fd");
	}
}
