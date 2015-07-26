package nedelosk.nedeloskcore.common.items;

import java.util.List;

import nedelosk.nedeloskcore.api.NCoreApi;
import nedelosk.nedeloskcore.api.crafting.OreStack;
import nedelosk.nedeloskcore.api.plan.IPlan;
import nedelosk.nedeloskcore.api.plan.PlanRecipe;
import nedelosk.nedeloskcore.common.blocks.tile.TilePlan;
import nedelosk.nedeloskcore.common.core.NedelsokCore;
import nedelosk.nedeloskcore.common.core.registry.NRegistry;
import nedelosk.nedeloskcore.common.inventory.InventoryPlanningTool;
import nedelosk.nedeloskcore.common.plan.PlanRecipeManager;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.mojang.realmsclient.gui.ChatFormatting;

public class ItemPlan extends Item implements IPlan {
	
	public IIcon[] itemIcon = new IIcon[3];
	
	public ItemPlan() {
		setCreativeTab(CreativeTabs.tabMisc);
		setHarvestLevel("axe", 0);
		setHasSubtypes(true);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return NRegistry.setUnlocalizedItemName("plan", "nc") + "." + stack.getItemDamage();
	}
	
	@Override
	public void registerIcons(IIconRegister IIconRegister) {
		itemIcon[0] = IIconRegister.registerIcon("nedeloskcore:plan");
		itemIcon[1] = IIconRegister.registerIcon("nedeloskcore:blueprint");
		itemIcon[2] = IIconRegister.registerIcon("nedeloskcore:planning_tool");
	}
	
	@Override
	public IIcon getIconFromDamage(int meta) {
		return itemIcon[meta];
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List listItems) {
		for(PlanRecipe plan : PlanRecipeManager.recipes)
		{
			listItems.add(NCoreApi.setItemPlan(item, plan));
		}
		listItems.add(new ItemStack(item, 1, 1));
		listItems.add(new ItemStack(item, 1, 2));
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_) {
		if(stack.getItemDamage() == 0)
			list.add(ChatFormatting.ITALIC + getOutput(stack).getDisplayName());
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
		if(stack.getItemDamage() == 0)
		{
	    if (!player.canPlayerEdit(x, z, y, p_77648_7_, stack))
	    {
	        return false;
	    }
	    else if(!world.isRemote)
	    {
	    	if(GuiScreen.isShiftKeyDown()){
	    	}
	    	else if(getUpdateBlock(stack) == null)
	    	{
	    	  if(world.getBlock(x, y, z) != null && world.getBlock(x, y, z) != Blocks.air && world.getBlock(x, y, z) != NRegistry.planBlock)
	    	  {
	    		  if(world.getBlock(x, y + 1, z) == Blocks.air)
	    		  {
	    			  if(getUpdateBlock(stack) == null)
	    			  {
	    			  if(!(world.setBlock(x, y + 1, z, NRegistry.planBlock)))
	    					  return  false;
	    			  if(!(world.getTileEntity(x, y + 1, z) instanceof TilePlan))
	    			  {
	    				  world.setBlockToAir(x, y + 1, z);
	    				  return false;
	    			  }
	    			  ((TilePlan)world.getTileEntity(x, y + 1, z)).setPlan(stack.getTagCompound());
	    			  world.playSoundEffect((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), NRegistry.planBlock.stepSound.func_150496_b(), (NRegistry.planBlock.stepSound.getVolume() + 1.0F) / 2.0F, NRegistry.planBlock.stepSound.getPitch() * 0.8F);
	    			  world.markBlockForUpdate(x, y + 1, z);
	    			  return true;
	    			  }
	    		  }
	    	  }
	    	}
	    }
		}
		else if(stack.getItemDamage() == 2)
		{
	    if (!player.canPlayerEdit(x, z, y, p_77648_7_, stack))
	    {
	        return false;
	    }
	    else if(!world.isRemote)
	    { 
	    	if(GuiScreen.isShiftKeyDown()){
	    	InventoryPlanningTool toolInv = new InventoryPlanningTool(stack);
	    	if(toolInv.getStackInSlot(0) != null)
	    	{
		    	if(toolInv.getStackInSlot(0).getItem() != null)
		    	{
		    		ItemStack stackInv = toolInv.getStackInSlot(0);
		    		if(getUpdateBlock(stackInv) != null && world.getBlock(x, y, z) != null && world.getBlock(x, y, z) == Block.getBlockFromItem(getUpdateBlock(stackInv).getItem()) && world.getBlockMetadata(x, y, z) == getUpdateBlock(stackInv).getItemDamage())
		    		{
		  	    	  if(world.getBlock(x, y, z) != null && world.getBlock(x, y, z) != Blocks.air)
			    	  {
		  	    		  NBTTagCompound nbt = stack.getTagCompound().getTagList("slots", 10).getCompoundTagAt(0).getCompoundTag("tag");
			    			  if(!(world.setBlock(x, y, z, NRegistry.planBlock)))
			    					  return  false;
			    			  if(!(world.getTileEntity(x, y, z) instanceof TilePlan))
			    			  {
			    				  world.setBlockToAir(x, y, z);
			    				  return false;
			    			  }
			    			  ((TilePlan)world.getTileEntity(x, y, z)).setPlan(stack.getTagCompound().getTagList("slots", 10).getCompoundTagAt(0).getCompoundTag("tag"));
			    			  world.playSoundEffect((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), NRegistry.planBlock.stepSound.func_150496_b(), (NRegistry.planBlock.stepSound.getVolume() + 1.0F) / 2.0F, NRegistry.planBlock.stepSound.getPitch() * 0.8F);
			    			  world.markBlockForUpdate(x, y, z);
			    			  return true;
			    	  }
		    		}
		    	}
	    	}
	    	}
	    }
		}
		
		return false;
	}
	 
	 @Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		 if(stack.getItemDamage() == 0)
		 {
			 if(GuiScreen.isShiftKeyDown()){
					stack.setItemDamage(1);
					stack.stackTagCompound = null;
			 }
		 }
		 if(stack.getItemDamage() == 1)
		 {
		 if(!GuiScreen.isShiftKeyDown()){
				player.openGui(NedelsokCore.instance, 1, world, 0, 0, 0);
		 }
		 }
		 else if(stack.getItemDamage() == 2)
		 {
			 if(!GuiScreen.isShiftKeyDown()){
					player.openGui(NedelsokCore.instance, 2, world, 0, 0, 0);
			 } 
		 }
		return stack;
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
	
	public ItemStack getUpdateBlock(ItemStack stack) {
		if(stack.getTagCompound() == null)
			return null;
		NBTTagCompound nbt = stack.getTagCompound().getCompoundTag("UpdateBlock");
		return ItemStack.loadItemStackFromNBT(nbt);
	}

	@Override
	public int getBuildingStages(ItemStack stack) {
		if(stack.getTagCompound() == null)
			return 0;
		return stack.getTagCompound().getInteger("stages");
	}

}
