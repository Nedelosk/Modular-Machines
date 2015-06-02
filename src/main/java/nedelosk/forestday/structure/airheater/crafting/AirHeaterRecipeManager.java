package nedelosk.forestday.structure.airheater.crafting;

import java.util.ArrayList;
import java.util.List;

import nedelosk.forestday.api.crafting.IAirHeaterRecipe;
import net.minecraftforge.fluids.FluidStack;

public class AirHeaterRecipeManager implements IAirHeaterRecipe {

	private static ArrayList<AirHeaterRecipe> recipes = new ArrayList();
	
	public static AirHeaterRecipeManager  instance;
	
	public void addRecipe(FluidStack input, FluidStack output, int minHeat, int maxHeat)
	{
		recipes.add(new AirHeaterRecipe(input, output, minHeat, maxHeat));
	}
	
	public static AirHeaterRecipe getRecipe(FluidStack input, int heat){
		for(AirHeaterRecipe sr : AirHeaterRecipeManager.recipes){
			if (input.getFluid() == sr.getInput().getFluid() && heat < sr.getMaxHeat() && heat > sr.getMinHeat()){
				return sr;
			}
		}
		return null;
	}

	public static AirHeaterRecipeManager  getInstance() {
		return instance;
	}
	
	public static List<AirHeaterRecipe> getRecipe()
	{
		return recipes;
	}
	
}