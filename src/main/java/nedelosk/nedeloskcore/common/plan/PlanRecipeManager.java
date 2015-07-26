package nedelosk.nedeloskcore.common.plan;

import java.util.ArrayList;
import java.util.List;

import nedelosk.nedeloskcore.api.crafting.IPlanRecipe;
import nedelosk.nedeloskcore.api.plan.PlanRecipe;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class PlanRecipeManager implements IPlanRecipe{

	public static ArrayList<PlanRecipe> recipes = new ArrayList();
	
	public static PlanRecipeManager instance;
	
	public static void addRecipe(PlanRecipe recipe)
	{
		recipes.add(recipe);
	}
	
	public static void removeRecipe(PlanRecipe recipe)
	{
		recipes.remove(recipe);
	}
	
	public static void addAllRecipes(List<PlanRecipe> recipe)
	{
		recipes.addAll(recipe);
	}
	
	public static List<PlanRecipe> removeRecipes(ItemStack stack)
	{
		List<PlanRecipe> list = new ArrayList();
		for(PlanRecipe recipe : recipes)
		{
			if(recipe.outputBlock.getItem() == stack.getItem() && recipe.outputBlock.getItemDamage() == stack.getItemDamage())
			{
				list.add(recipe);
			}
		}
		return list;
	}
	
	public void add(ItemStack outputBlock, int stages, ItemStack[]... input) {
		recipes.add(new PlanRecipe(outputBlock, stages, input));
	}
	
	public void add(ItemStack updateBlock, ItemStack outputBlock, int stages, ItemStack[]... input) {
		recipes.add(new PlanRecipe(updateBlock, outputBlock, stages, input));
	}
	
	public static  ArrayList<PlanRecipe> getRecipe(Block block){
		 ArrayList<PlanRecipe> recipe = new  ArrayList<PlanRecipe>();
		for(PlanRecipe sr : recipes)
		{
			Block blockNew = null;
			Block blockNew2 = null;
			if(sr.updateBlock != null)
			blockNew = Block.getBlockFromItem(sr.updateBlock.getItem());
			if(sr.outputBlock != null)
			blockNew2 = Block.getBlockFromItem(sr.outputBlock.getItem());
			
			if(blockNew != null)
			{
			if(blockNew == block)
			{
				recipe.add(sr);
			}
			}
			
			if(blockNew2 != null)
			{
			if(blockNew2 == block)
			{
				recipe.add(sr);
			}
			}
		}
		return recipe;
	}
	
	public static ArrayList<Block> getBlocks()
	{
		ArrayList<Block> list = new ArrayList<Block>();
		for(PlanRecipe sr : recipes){
			Block block = null;
			if(sr.outputBlock != null)
			block = Block.getBlockFromItem(sr.outputBlock.getItem());
			if(sr.updateBlock != null)
			block = Block.getBlockFromItem(sr.updateBlock.getItem());
			
			if(block != null && !list.contains(block))
			{
				list.add(block);
			}
		}
		return list;
	}
	
}