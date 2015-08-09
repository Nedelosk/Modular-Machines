package nedelosk.nedeloskcore.common.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestday.common.items.base.ItemForestday;
import nedelosk.nedeloskcore.common.core.registry.NRegistry;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemIngot extends ItemForestday {

	public static String[] ingots = new String[] { "Copper", "Tin", "Silver", "Lead", "Nickel" };
	public String[] ingot;
	@SideOnly(Side.CLIENT)
    public IIcon[] itemIcon;
	public String modID;
	
	public ItemIngot(String[] ingot, String modID) {
		super(null, CreativeTabs.tabMaterials);
		this.ingot = ingot;
		this.modID = modID;
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
            this.itemIcon[i] = iconRegister.registerIcon(modID + ":ingots/ingot" + ingot[i]);
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
        return NRegistry.setUnlocalizedItemName("ingot." + modID + "." + itemstack.getItemDamage(), "nc");
    }

}
