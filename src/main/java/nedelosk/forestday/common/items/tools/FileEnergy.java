package nedelosk.forestday.common.items.tools;

import nedelosk.forestday.common.config.ForestdayConfig;
import nedelosk.forestday.common.registrys.ForestdayBlockRegistry;
import nedelosk.forestday.common.registrys.ForestdayItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FileEnergy extends ItemToolForestday {

	public static ForestdayBlockRegistry coreBlocks;
	
	protected int capacity;
	
	public FileEnergy(String uln, int capacity){
		super(uln, 0);
		this.setNoRepair();
		this.capacity = capacity;
	}
	
	@Override
	public boolean doesContainerItemLeaveCraftingGrid( ItemStack par1ItemStack )
	{
		return false;
	}
	
	public int getMaxEnergyExtract()
	{
		return 0;
	}
	
    @Override
    public int getDisplayDamage(ItemStack stack) {
        if (stack.stackTagCompound == null) return 1 + capacity;

        return 1 + capacity - stack.stackTagCompound.getInteger("Energy");
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return 1 + capacity;
    }

    @Override
    public boolean isDamaged(ItemStack stack) {
        return true;
    }
	
	 public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
	    {
	    	if(p_77648_1_.getTagCompound().getInteger("Energy") > getMaxEnergyExtract())
	    	{
	        if (!p_77648_2_.canPlayerEdit(p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_1_))
	        {
	            return false;
	        }
	        else
	        {
	            Block var11 = p_77648_3_.getBlock(p_77648_4_, p_77648_5_, p_77648_6_);
	            int var13 = var11.getDamageValue(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_);
	            
	            if (var11 == Blocks.log || var11 == Blocks.log2)
	            {
	                Block var12 = ForestdayBlockRegistry.trunkBig;
	                if (!p_77648_3_.isRemote)
	                {
	                    p_77648_3_.setBlock(p_77648_4_, p_77648_5_, p_77648_6_, var12);
	                    removeEnergy(p_77648_1_);
                    	if(p_77648_2_.capabilities.isCreativeMode)
                    	{
                            if(ForestdayConfig.activeCreativeDrop)
                            {
                         EntityItem bark = new EntityItem(p_77648_3_, p_77648_2_.posX, p_77648_2_.posY, p_77648_2_.posZ, new ItemStack(ForestdayItemRegistry.nature, 4, 0));
                         p_77648_3_.spawnEntityInWorld(bark);
                            }
                    	}
                    	else
                    	{
                            EntityItem bark = new EntityItem(p_77648_3_, p_77648_2_.posX, p_77648_2_.posY, p_77648_2_.posZ, new ItemStack(ForestdayItemRegistry.nature, 4, 0));
                            p_77648_3_.spawnEntityInWorld(bark);
                    	}
	                    return true;
	                }
	            else
	            {
	            	return false;
	            }
	            }
	            else if (var11 == ForestdayBlockRegistry.trunkBig)
	            {
	                Block var12 = ForestdayBlockRegistry.trunkMedium;        
	                if (!p_77648_3_.isRemote)
	                {
	                    p_77648_3_.setBlock(p_77648_4_, p_77648_5_, p_77648_6_, var12);
	                    removeEnergy(p_77648_1_);
                    	if(p_77648_2_.capabilities.isCreativeMode)
                    	{
                            if(ForestdayConfig.activeCreativeDrop)
                            {
                         EntityItem bark = new EntityItem(p_77648_3_, p_77648_2_.posX, p_77648_2_.posY, p_77648_2_.posZ, new ItemStack(ForestdayItemRegistry.nature, 2, 1));
                         p_77648_3_.spawnEntityInWorld(bark);
                            }
                    	}
                    	else
                    	{
                            EntityItem bark = new EntityItem(p_77648_3_, p_77648_2_.posX, p_77648_2_.posY, p_77648_2_.posZ, new ItemStack(ForestdayItemRegistry.nature, 2, 1));
                            p_77648_3_.spawnEntityInWorld(bark);
                    	}
	                    return true;
	                }
	            else
	            {
	            	return false;
	            }
	            }
	            else if (var11 == ForestdayBlockRegistry.trunkMedium)
	            {
	                Block var12 = ForestdayBlockRegistry.trunkSmall;
	                if (!p_77648_3_.isRemote)
	                {
	                    p_77648_3_.setBlock(p_77648_4_, p_77648_5_, p_77648_6_, var12);
	                    removeEnergy(p_77648_1_);
                    	if(p_77648_2_.capabilities.isCreativeMode)
                    	{
                            if(ForestdayConfig.activeCreativeDrop)
                            {
                         EntityItem bark = new EntityItem(p_77648_3_, p_77648_2_.posX, p_77648_2_.posY, p_77648_2_.posZ, new ItemStack(ForestdayItemRegistry.nature, 2, 1));
                         p_77648_3_.spawnEntityInWorld(bark);
                            }
                    	}
                    	else
                    	{
                            EntityItem bark = new EntityItem(p_77648_3_, p_77648_2_.posX, p_77648_2_.posY, p_77648_2_.posZ, new ItemStack(ForestdayItemRegistry.nature, 2, 1));
                            p_77648_3_.spawnEntityInWorld(bark);
                    	}
	                    return true;
	                }
	            else
	            {
	            	return false;
	            }
	            }
	            else if (var11 == ForestdayBlockRegistry.trunkSmall)
	            {
	                Block var12 = ForestdayBlockRegistry.trunkTiny;
	                if (!p_77648_3_.isRemote)
	                {
	                    p_77648_3_.setBlock(p_77648_4_, p_77648_5_, p_77648_6_, var12);
	                    removeEnergy(p_77648_1_);
                    	if(p_77648_2_.capabilities.isCreativeMode)
                    	{
                            if(ForestdayConfig.activeCreativeDrop)
                            {
                         EntityItem bark = new EntityItem(p_77648_3_, p_77648_2_.posX, p_77648_2_.posY, p_77648_2_.posZ, new ItemStack(ForestdayItemRegistry.nature, 2, 1));
                         p_77648_3_.spawnEntityInWorld(bark);
                            }
                    	}
                    	else
                    	{
                            EntityItem bark = new EntityItem(p_77648_3_, p_77648_2_.posX, p_77648_2_.posY, p_77648_2_.posZ, new ItemStack(ForestdayItemRegistry.nature, 2, 1));
                            p_77648_3_.spawnEntityInWorld(bark);
                    	}
	                    return true;
	                }
	                else
	                {
	                	return false;
	                }
	            }
	            else if (var11 == ForestdayBlockRegistry.trunkTiny)
	            {
	                Block var12 = Blocks.air;
	                if (!p_77648_3_.isRemote)
	                {

	                    p_77648_3_.setBlock(p_77648_4_, p_77648_5_, p_77648_6_, var12);
	                    removeEnergy(p_77648_1_);
                    	if(p_77648_2_.capabilities.isCreativeMode)
                    	{
                            if(ForestdayConfig.activeCreativeDrop)
                            {
                         EntityItem bark = new EntityItem(p_77648_3_, p_77648_2_.posX, p_77648_2_.posY, p_77648_2_.posZ, new ItemStack(ForestdayItemRegistry.nature, 2, 1));
                         p_77648_3_.spawnEntityInWorld(bark);
                            }
                    	}
                    	else
                    	{
                            EntityItem bark = new EntityItem(p_77648_3_, p_77648_2_.posX, p_77648_2_.posY, p_77648_2_.posZ, new ItemStack(ForestdayItemRegistry.nature, 2, 1));
                            p_77648_3_.spawnEntityInWorld(bark);
                    	}
	                    return true;
	                }
	            else
	            {
	            	return false;
	            }
	            }     
	            else
	            {
	                return false;
	            }
	        }
	    	}
	    	else
	    	{
	    		return false;
	    	}
	        
	    }
    
    protected int removeEnergy(ItemStack stack)
    {
    	return getMaxEnergyExtract();
    }
	
	public boolean hasContainerItem(ItemStack stack)
	{
		return true;
	}
	
	@Override
	public ItemStack getContainerItem(ItemStack stack)
	{
		ItemStack itemstack = stack.copy();
		removeEnergy(stack);
		itemstack.stackSize = 1;
		return itemstack;
	}

}
