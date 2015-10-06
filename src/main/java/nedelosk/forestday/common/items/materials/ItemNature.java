package nedelosk.forestday.common.items.materials;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestday.common.items.base.ItemForestday;
import nedelosk.nedeloskcore.common.core.registry.NCRegistry;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemNature extends ItemForestday {

	public String[] material = new String[] { "bark", "sawdust", "rubber", "resin", "peat", "mud", "dirt", "ash", "mortar", "starch", "starch", "hardened_starch" };
	@SideOnly(Side.CLIENT)
    public IIcon[] itemIcon;
	
	public ItemNature() {
		super(null);
		setHasSubtypes(true);
		setUnlocalizedName("nature");
	}
	
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons (IIconRegister iconRegister)
    {
        this.itemIcon = new IIcon[material.length];

        for (int i = 0; i < this.itemIcon.length; ++i)
        {
            this.itemIcon[i] = iconRegister.registerIcon("forestday:" + material[i]);
        }
    }
    
    @Override
    public void getSubItems (Item id, CreativeTabs tab, List list)
    {
        for (int i = 0; i < material.length; i++)
            list.add(new ItemStack(id, 1, i));
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIconFromDamage (int meta)
    {
        return itemIcon[meta];
    }
    
    @Override
    public String getUnlocalizedName (ItemStack itemstack)
    {
        return NCRegistry.setUnlocalizedItemName("nature." + itemstack.getItemDamage(), "fd");
    }

}
