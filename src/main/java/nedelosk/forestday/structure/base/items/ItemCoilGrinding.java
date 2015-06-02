package nedelosk.forestday.structure.base.items;

import java.util.List;

import javax.swing.text.html.HTML.Tag;

import nedelosk.forestday.api.Tabs;
import nedelosk.forestday.api.structure.coils.ICoilItem;
import nedelosk.forestday.common.items.base.ItemForestday;
import nedelosk.forestday.common.registrys.ForestdayRegistry;
import nedelosk.forestday.structure.base.blocks.BlockCoilGrinding;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCoilGrinding extends ItemForestday implements ICoilItem {

	public String[] textures = new String[] { "stone", "iron", "brown_alloy", "steel", "steel_dark", "diamond" };
	
	@SideOnly(Side.CLIENT)
    public IIcon[] itemIcon;
	
	public ItemCoilGrinding() {
		super(null, Tabs.tabForestdayItems);
		setHasSubtypes(true);
	}
	
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons (IIconRegister iconRegister)
    {
        this.itemIcon = new IIcon[textures.length];

        for (int i = 0; i < this.itemIcon.length; ++i)
        {
            this.itemIcon[i] = iconRegister.registerIcon("forestday:coils/grinding/" + textures[i]);
        }
    }
    
    @Override
    public void getSubItems (Item id, CreativeTabs tab, List list)
    {
        for (int i = 0; i < textures.length; i++)
        {
        	ItemStack stack = new ItemStack(id, 1, i);
        	stack.setTagCompound(new NBTTagCompound());
        	stack.getTagCompound().setInteger("Damage", BlockCoilGrinding.coilMaxSharpness[i]);
            list.add(stack);
        }
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
        return ForestdayRegistry.setUnlocalizedItemName("coil.grinding." + itemstack.getItemDamage());
    }
    
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_) {
		int meta = stack.getItemDamage();

			list.add(StatCollector.translateToLocal("forestday.tooltip.roughness") + stack.getTagCompound().getInteger("Damage"));
	}
	
    @Override
    public int getDisplayDamage(ItemStack stack) {
        if (stack.stackTagCompound == null) return 1 + BlockCoilGrinding.coilMaxSharpness[stack.getItemDamage()];

        return 1 + BlockCoilGrinding.coilMaxSharpness[stack.getItemDamage()] - stack.stackTagCompound.getInteger("Damage");
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return 1 + BlockCoilGrinding.coilMaxSharpness[stack.getItemDamage()];
    }

    @Override
    public boolean isDamaged(ItemStack stack) {
        return true;
    }

	@Override
	public int getCoilSharpness(ItemStack stack) {
		return stack.getTagCompound().getInteger("Damage");
	}

	@Override
	public int addCoilSharpness(ItemStack stack, int damageAdd) {
        NBTTagCompound tags = stack.getTagCompound();
        if (tags == null || !tags.hasKey("Damage"))
        {
            return 0;
        }
        int damage = tags.getInteger("Damage");

        int energyExtracted = Math.min(damage, damageAdd);
        
        damage -= energyExtracted;
        tags.setInteger("Damage", damage);
        return energyExtracted;
	}

	@Override
	public int sharpCoil(ItemStack stack, int sharpness) {
        NBTTagCompound tags = stack.getTagCompound();
		if(canSharp(stack))
		{
			int damage = tags.getInteger("Damage");
			tags.setInteger("Damage", damage + sharpness);
			return sharpness;
		}
		return 0;
	}
	
	public boolean canSharp(ItemStack stack)
	{
		if(stack.getTagCompound().getInteger("Damage") >= BlockCoilGrinding.coilMaxSharpness[stack.getItemDamage()])
		{
			return false;
		}
		else
		{
			return true;
		}
	}

}
