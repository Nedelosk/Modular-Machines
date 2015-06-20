package nedelosk.forestday.common.items.materials;

import java.util.List;

import nedelosk.forestday.api.Tabs;
import nedelosk.forestday.common.items.base.ItemForestday;
import nedelosk.nedeloskcore.common.core.registry.NRegistry;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCampfire extends ItemForestday {
	
	@SideOnly(Side.CLIENT)
    public IIcon[] itemIcon;
	
	public String[] textures;
	public String itemName;
	
	public ItemCampfire(String[] textures, String itemName) {
		super(null, Tabs.tabForestdayItems);
		setHasSubtypes(true);
		setUnlocalizedName("campfire." + itemName);
		this.textures = textures;
		this.itemName = itemName;
	}
	
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons (IIconRegister iconRegister)
    {
        this.itemIcon = new IIcon[textures.length];

        for (int i = 0; i < this.itemIcon.length; ++i)
        {
            this.itemIcon[i] = iconRegister.registerIcon("forestday:campfire/" + textures[i]);
        }
    }
    
    @Override
    public void getSubItems (Item id, CreativeTabs tab, List list)
    {
        for (int i = 0; i < textures.length; i++)
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
        return NRegistry.setUnlocalizedItemName("campfire." + itemName + "." + itemstack.getItemDamage(), "fd");
    }

}
