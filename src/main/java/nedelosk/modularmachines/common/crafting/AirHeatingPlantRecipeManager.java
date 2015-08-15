package nedelosk.modularmachines.common.crafting;

import java.util.ArrayList;
import java.util.List;

import nedelosk.modularmachines.api.multiblocks.IAirHeatingPlantRecipe;
import net.minecraftforge.fluids.FluidStack;

public class AirHeatingPlantRecipeManager implements IAirHeatingPlantRecipe {

	private static ArrayList<AirHeatingPlantRecipe> recipes = new ArrayList();
	
	public static AirHeatingPlantRecipeManager instance;
	
	public static void addRecipe(AirHeatingPlantRecipe recipe)
	{
		recipes.add(recipe);
	}
	
	public static void removeRecipe(AirHeatingPlantRecipe recipe)
	{
		recipes.remove(recipe);
	}
	
	public static void addAllRecipes(List<AirHeatingPlantRecipe> recipe)
	{
		recipes.addAll(recipe);
	}
	
	@Override
	public void addRecipe(int burnTime, FluidStack input, FluidStack output)
	{
		recipes.add(new AirHeatingPlantRecipe(burnTime, input, output));
	}
	
	public static boolean isFluidInput(FluidStack stack)
	{
		for(AirHeatingPlantRecipe sr : AirHeatingPlantRecipeManager.recipes){
			if(stack.getFluid() == sr.getInput().getFluid())
				return true;
		}
		return false;
	}
	
	public static AirHeatingPlantRecipe getRecipe(FluidStack stack){
		for(AirHeatingPlantRecipe sr : AirHeatingPlantRecipeManager.recipes){
			if(stack.getFluid() == sr.getInput().getFluid() && sr.getInput().amount <= stack.amount)
				return sr;
		}
		return null;
	}
	
	public static ArrayList<AirHeatingPlantRecipe> getRecipes() {
		return recipes;
	}
	
	public static AirHeatingPlantRecipeManager getInstance() {
		return instance;
	}
	
}