package nedelosk.forestday.common.items.materials;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestday.common.core.registry.FRegistry;
import nedelosk.forestday.common.items.base.ItemForest;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemNugget extends ItemForest {

	public static String[] nuggets = new String[] { "Copper", "Tin", "Silver", "Lead", "Nickel", "Iron" };
	public String[] nugget;
	@SideOnly(Side.CLIENT)
    public IIcon[] itemIcon;
	String modID;
	
	public ItemNugget(String[] ingot, String modID) {
		super(null, CreativeTabs.tabMaterials);
		this.nugget = ingot;
		this.modID = modID;
		setHasSubtypes(true);
		setUnlocalizedName("nugget");
	}
	
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons (IIconRegister iconRegister)
    {
        this.itemIcon = new IIcon[nugget.length];

        for (int i = 0; i < this.itemIcon.length; ++i)
        {
            this.itemIcon[i] = iconRegister.registerIcon(modID + ":nuggets/nugget" + nugget[i]);
        }
    }
    
    @Override
    public void getSubItems (Item id, CreativeTabs tab, List list)
    {
        for (int i = 0; i < nugget.length; i++)
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
        return FRegistry.setUnlocalizedItemName("nugget." + modID + "." + itemstack.getItemDamage(), "fd");
    }

}
