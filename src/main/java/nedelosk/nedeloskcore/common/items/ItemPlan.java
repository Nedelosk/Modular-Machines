package nedelosk.nedeloskcore.common.items;

import java.util.List;

import nedelosk.forestday.common.machines.iron.plan.PlanEnum;
import nedelosk.nedeloskcore.api.NCoreApi;
import nedelosk.nedeloskcore.api.crafting.OreStack;
import nedelosk.nedeloskcore.api.plan.IPlan;
import nedelosk.nedeloskcore.api.plan.IPlanEnum;
import nedelosk.nedeloskcore.common.NedelsokCore;
import nedelosk.nedeloskcore.common.blocks.tile.TilePlan;
import nedelosk.nedeloskcore.common.core.registry.NRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ItemPlan extends Item implements IPlan {
	
	public ItemPlan() {
		setCreativeTab(CreativeTabs.tabMisc);
		setUnlocalizedName(NRegistry.setUnlocalizedItemName("plan", "nc"));
		setTextureName("nedeloskcore:plan");
		setHarvestLevel("axe", 0);
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List listItems) {
		for(IPlanEnum plan : NCoreApi.plans)
		{
			listItems.add(NCoreApi.setItemPlan(item, plan));
		}
	}
	
	@Override
	public ItemStack[] getInput(ItemStack stack, int stage) {
		if(stack.getTagCompound() == null)
			return null;
		NBTTagList nbtTagList = stack.getTagCompound().getTagList("input" + stage, 10);
		ItemStack[] stacks = new ItemStack[nbtTagList.tagCount()];
		for(int i = 0; i < nbtTagList.tagCount(); i++)
		{
			NBTTagCompound nbt = nbtTagList.getCompoundTagAt(i);
			stacks[i] = ItemStack.loadItemStackFromNBT(nbt);
		}
		return stacks;
	}
	 
	 @Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z,int p_77648_7_, float p_77648_8_, float p_77648_9_,float p_77648_10_) {
	    if (!player.canPlayerEdit(x, z, y, p_77648_7_, stack))
	    {
	        return false;
	    }
	    else if(!world.isRemote)
	    {
	    	TileEntity planTile = world.getTileEntity(x, y, z);
	    	  if(world.getBlock(x, y, z) != null && world.getBlock(x, y, z) != Blocks.air && world.getBlock(x, y, z) != NRegistry.planBlock)
	    	  {
	    		  if(world.getBlock(x, y + 1, z) == Blocks.air)
	    		  {
	    			  if(!(world.setBlock(x, y + 1, z, NRegistry.planBlock)))
	    					  return  false;
	    			  if(!(world.getTileEntity(x, y + 1, z) instanceof TilePlan))
	    			  {
	    				  world.setBlockToAir(x, y + 1, z);
	    				  return false;
	    			  }
	    			  ((TilePlan)world.getTileEntity(x, y + 1, z)).setPlan(stack);
	    			  world.playSoundEffect((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), NRegistry.planBlock.stepSound.func_150496_b(), (NRegistry.planBlock.stepSound.getVolume() + 1.0F) / 2.0F, NRegistry.planBlock.stepSound.getPitch() * 0.8F);
	    			  stack.stackSize--;
	    			  world.markBlockForUpdate(x, y + 1, z);
	    			  return true;
	    		  }
	    	  }
	    }
		return false;
	}

	@Override
	public OreStack[] getInputOre(ItemStack stack, int stage) {
		if(stack.getTagCompound() == null)
			return null;
		NBTTagList nbtTagList = stack.getTagCompound().getTagList("inputOre" + stage, 10);
		OreStack[] stacks = new OreStack[nbtTagList.tagCount()];
		for(int i = 0; i < nbtTagList.tagCount(); i++)
		{
			NBTTagCompound nbt = nbtTagList.getCompoundTagAt(i);
			stacks[i] = OreStack.loadOreStackFromNBT(nbt);
		}
		return stacks;
	}

	@Override
	public ItemStack getOutput(ItemStack stack) {
		if(stack.getTagCompound() == null)
			return null;
		NBTTagCompound nbt = stack.getTagCompound().getCompoundTag("Output");
		return ItemStack.loadItemStackFromNBT(nbt);
	}

	@Override
	public int getBuildingStages(ItemStack stack) {
		if(stack.getTagCompound() == null)
			return 0;
		return stack.getTagCompound().getInteger("stages");
	}

}
