package nedelosk.forestday.common.items.materials;

import java.util.List;

import nedelosk.forestday.api.Tabs;
import nedelosk.forestday.common.items.base.ItemForestday;
import nedelosk.nedeloskcore.common.core.registry.NRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemCampfire extends ItemForestday {
	
	public String[] textures;
	public String itemName;
	
	public ItemCampfire(String[] textures, String itemName) {
		super(null, Tabs.tabForestdayItems);
		setHasSubtypes(true);
		setUnlocalizedName("campfire." + itemName);
		this.textures = textures;
		this.itemName = itemName;
	}
    
    @Override
    public void getSubItems (Item id, CreativeTabs tab, List list)
    {
        for (int i = 0; i < textures.length; i++)
            list.add(new ItemStack(id, 1, i));
    }
    
    @Override
    public String getUnlocalizedName (ItemStack itemstack)
    {
        return NRegistry.setUnlocalizedItemName("campfire." + itemName + "." + itemstack.getItemDamage(), "fd");
    }

}
