package nedelosk.forestday.common.items.base;

import nedelosk.forestday.api.Tabs;
import nedelosk.nedeloskcore.common.core.registry.NCRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemForestday extends Item {
	
	private String unl;
	private boolean hasMeta;
	
	public ItemForestday(String unl, boolean hasMeta)
	{
		this.setUnlocalizedName(unl);
		this.unl = unl;
		this.setHasSubtypes(hasMeta);
		this.setCreativeTab(Tabs.tabForestday);
		this.hasMeta = hasMeta;
	}
	
	public ItemForestday(String unl)
	{
		this.setCreativeTab(Tabs.tabForestday);
		this.setUnlocalizedName(unl);
		this.unl = unl;
	}
	
    @Override
    public String getUnlocalizedName (ItemStack itemstack)
    {
        return NCRegistry.setUnlocalizedItemName(unl + ((hasMeta) ? itemstack.getItemDamage() : ""), "fd");
    }
}
