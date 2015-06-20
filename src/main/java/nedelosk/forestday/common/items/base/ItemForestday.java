package nedelosk.forestday.common.items.base;

import nedelosk.forestday.common.core.TabForestday;
import nedelosk.forestday.common.registrys.FRegistry;
import nedelosk.nedeloskcore.common.core.registry.NRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemForestday extends Item {
	
	private String unl;
	private boolean hasMeta;
	
	public ItemForestday(String unl, CreativeTabs tab, boolean hasMeta)
	{
		this.setCreativeTab(tab);
		this.setUnlocalizedName(unl);
		this.unl = unl;
		this.setHasSubtypes(hasMeta);
		this.hasMeta = hasMeta;
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
        return NRegistry.setUnlocalizedItemName(unl + ((hasMeta) ? itemstack.getItemDamage() : ""), "fd");
    }
}
