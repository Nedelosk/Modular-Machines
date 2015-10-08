package nedelosk.nedeloskcore.common.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.nedeloskcore.common.core.registry.NCRegistry;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemGem extends ItemForest {

	public String[] gem = new String[] { "Ruby" };
	@SideOnly(Side.CLIENT)
    public IIcon[] itemIcon;
	
	public ItemGem() {
		super(null, CreativeTabs.tabMaterials);
		setHasSubtypes(true);
		setUnlocalizedName("gem");
	}
	
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons (IIconRegister iconRegister)
    {
        this.itemIcon = new IIcon[gem.length];

        for (int i = 0; i < this.itemIcon.length; ++i)
        {
            this.itemIcon[i] = iconRegister.registerIcon("nedeloskcore:gems/gem" + gem[i]);
        }
    }
    
    @Override
    public void getSubItems (Item id, CreativeTabs tab, List list)
    {
        for (int i = 0; i < gem.length; i++)
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
        return NCRegistry.setUnlocalizedItemName("gem." + itemstack.getItemDamage(), "nc");
    }

}
