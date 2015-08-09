package nedelosk.forestday.common.machines.base.furnace.coke;

import java.util.ArrayList;

import nedelosk.forestday.api.crafting.ICokeFurnaceRecipe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class CokeFurnaceRecipeManager implements ICokeFurnaceRecipe{

	private static ArrayList<CokeFurnaceRecipe> recipes = new ArrayList();
	
	public static CokeFurnaceRecipeManager instance;
	
	@Override
	public void addRecipe(ItemStack input, ItemStack output, ItemStack output1, ItemStack output2, FluidStack outputFluid)
	{
		recipes.add(new CokeFurnaceRecipe(input, output, output1, output2, outputFluid));
	}
	
	@Override
	public void addRecipe(String input, ItemStack output, ItemStack output1, ItemStack output2, FluidStack outputFluid)
	{
		recipes.add(new CokeFurnaceRecipe(input, output, output1, output2, outputFluid));
	}
	
	public static CokeFurnaceRecipe getRecipe(ItemStack input){
		for(CokeFurnaceRecipe sr : CokeFurnaceRecipeManager.recipes){
			if (sr.getInput().getItem() == input.getItem() && sr.getInput().getItemDamage() == input.getItemDamage()){
				return sr;
			}
		}
		return null;
	}
	
	public static ArrayList<CokeFurnaceRecipe> getRecipes() {
		return recipes;
	}
	
	public static CokeFurnaceRecipeManager getInstance() {
		return instance;
	}
	
}