package nedelosk.forestday.structure.base.items;

import java.util.List;

import nedelosk.forestday.api.Tabs;
import nedelosk.forestday.common.items.base.ItemForestday;
import nedelosk.forestday.common.registrys.ForestdayRegistry;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCoilHeat extends ItemForestday {

	public String[] coils = new String[] { "coil_red", "coil_blue", "coil_dark_blue", "coil_yellow", "coil_brown", "coil_green", "coil_copper", "coil_tin", "coil_bronze", "coil_iron", "coil_steel" };
	public int[] coilHeat = new int[] { 100, 200, 500, 1000, 2500, 5000, 55, 10, 45, 75, 90 };
	@SideOnly(Side.CLIENT)
    public IIcon[] itemIcon;
	
	public ItemCoilHeat() {
		super(null, Tabs.tabForestdayItems);
		setHasSubtypes(true);
	}
	
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons (IIconRegister iconRegister)
    {
        this.itemIcon = new IIcon[coils.length];

        for (int i = 0; i < this.itemIcon.length; ++i)
        {
            this.itemIcon[i] = iconRegister.registerIcon("forestday:coils/" + coils[i]);
        }
    }
    
    @Override
    public void getSubItems (Item id, CreativeTabs tab, List list)
    {
        for (int i = 0; i < coils.length; i++)
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
        return ForestdayRegistry.setUnlocalizedItemName("coil." + itemstack.getItemDamage());
    }
    
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_) {
		int meta = stack.getItemDamage();
		
			list.add(StatCollector.translateToLocal("forestday.tooltip.heat") + coilHeat[meta]);
	}
	
	public int getCoilHeat(int meta) {
		return coilHeat[meta];
	}

}
