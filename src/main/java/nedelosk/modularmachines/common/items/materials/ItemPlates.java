package nedelosk.modularmachines.common.items.materials;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.common.items.ModularItem;
import nedelosk.nedeloskcore.common.core.registry.NRegistry;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemPlates extends ModularItem {

	public static String[] plats = new String[] { "Iron", "Gold", "Copper", "Tin", "Silver", "Lead", "Bronze", "Invar", "Plastic", "Niobium", "Tantalum", "Aluminium" };
	public String[] name;
	@SideOnly(Side.CLIENT)
    public IIcon[] itemIcon;
	public String uln;
	
	public ItemPlates(String[] names, String uln) {
		super("plate" + uln);
		this.uln = uln;
		this.name = names;
		setHasSubtypes(true);
	}
	
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons (IIconRegister iconRegister)
    {
        this.itemIcon = new IIcon[name.length];

        for (int i = 0; i < this.itemIcon.length; ++i)
        {
            this.itemIcon[i] = iconRegister.registerIcon("modularmachines:plates/plate" + name[i]);
        }
    }
    
    @Override
    public void getSubItems (Item id, CreativeTabs tab, List list)
    {
        for (int i = 0; i < name.length; i++)
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
        return NRegistry.setUnlocalizedItemName("plate" + uln + "." + itemstack.getItemDamage(), "mm");
    }

}
