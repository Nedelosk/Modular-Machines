package nedelosk.forestday.common.items.materials;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestday.api.Tabs;
import nedelosk.forestday.common.core.TabForestday;
import nedelosk.forestday.common.items.base.ItemForestday;
import nedelosk.forestday.common.registrys.FRegistry;
import nedelosk.nedeloskcore.common.core.registry.NRegistry;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;

public class ItemIngot extends ItemForestday {

	public String[] ingot = new String[] { "copper", "tin", "bronze", "redstone_alloy", "dark_alloy", "steel", "forest_steel", "enderium" };
	@SideOnly(Side.CLIENT)
    public IIcon[] itemIcon;
	
	public ItemIngot() {
		super(null, Tabs.tabForestdayItems);
		setHasSubtypes(true);
		setUnlocalizedName("ingot");
	}
	
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons (IIconRegister iconRegister)
    {
        this.itemIcon = new IIcon[ingot.length];

        for (int i = 0; i < this.itemIcon.length; ++i)
        {
            this.itemIcon[i] = iconRegister.registerIcon("forestday:ingots/" + ingot[i]);
        }
    }
    
    @Override
    public void getSubItems (Item id, CreativeTabs tab, List list)
    {
        for (int i = 0; i < ingot.length; i++)
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
        return NRegistry.setUnlocalizedItemName("ingot." + itemstack.getItemDamage(), "fd");
    }

}
