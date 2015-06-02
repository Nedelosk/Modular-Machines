package nedelosk.forestday.common.items.base;

import nedelosk.forestday.common.core.TabForestday;
import nedelosk.forestday.common.registrys.ForestdayRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemForestday extends Item {
	
	private String unl;
	
	public ItemForestday(String unl, CreativeTabs tab, boolean autoTexture)
	{
		this.setCreativeTab(tab);
		this.setUnlocalizedName(unl);
		if(autoTexture)
		{
		this.setTextureName("forestday:" + unl);
		}
		this.unl = unl;
	}
	
	public ItemForestday(String unl, CreativeTabs tab)
	{
		this.setCreativeTab(tab);
		this.setUnlocalizedName(unl);
		this.unl = unl;
	}
	
    @Override
    public String getUnlocalizedName (ItemStack itemstack)
    {
        return ForestdayRegistry.setUnlocalizedItemName(unl);
    }
}
