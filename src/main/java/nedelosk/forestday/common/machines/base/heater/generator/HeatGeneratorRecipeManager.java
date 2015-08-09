package nedelosk.forestday.common.machines.base.heater.generator;

import java.util.ArrayList;
import java.util.List;

import nedelosk.forestday.api.crafting.BurningMode;
import nedelosk.forestday.api.crafting.IBurnRecipe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class HeatGeneratorRecipeManager implements IBurnRecipe{

	private static List<HeatGeneratorRecipe> recipes = new ArrayList();
	
	public static HeatGeneratorRecipeManager instance;
	
	@Override
	public void addRecipe(ItemStack input, ItemStack output, int burnTime, BurningMode mode)
	{
		recipes.add(new HeatGeneratorRecipe(input, output, burnTime, mode));
	}
	
	@Override
	public void addRecipe(String input, ItemStack output, int burnTime, BurningMode mode)
	{
		recipes.add(new HeatGeneratorRecipe(input, output, burnTime, mode));
	}
	
	public static HeatGeneratorRecipe getRecipe(ItemStack input){
		for(HeatGeneratorRecipe sr : HeatGeneratorRecipeManager.recipes){
			if(sr.getInput1() != null)
			{
			if (sr.getInput1().getItem() == input.getItem() && sr.getInput1().getItemDamage() == input.getItemDamage() && ItemStack.areItemStackTagsEqual(input, sr.getInput1())){
				return sr;
			}
			}
			if(sr.getOredictInput() != null)
			{
			List<ItemStack> list = OreDictionary.getOres(sr.getOredictInput());
			ItemStack stackI = input.copy();
			stackI.stackSize = 1;
			for(ItemStack stack : list)
			{
				if(input.getItem() == stack.getItem() && input.getItemDamage() == stack.getItemDamage())
				{
					return sr;
				}
			}
			}
		}
		return null;
	}
	
	public static HeatGeneratorRecipeManager getInstance() {
		return instance;
	}
	
	public static List<HeatGeneratorRecipe> getRecipes() {
		return recipes;
	}
	
}