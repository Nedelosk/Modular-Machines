package nedelosk.forestday.common.machines.brick.furnace.fluidheater;

import java.util.ArrayList;

import nedelosk.forestday.api.crafting.ICokeFurnaceRecipe;
import nedelosk.forestday.api.crafting.IFluidHeaterRecipe;
import nedelosk.forestday.api.crafting.IKilnRecipe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class FluidHeaterRecipeManager implements IFluidHeaterRecipe{

	private static ArrayList<FluidHeaterRecipe> recipes = new ArrayList();
	
	public static FluidHeaterRecipeManager instance;
	
	public void addRecipe(FluidStack input, FluidStack input2, FluidStack output, int burnTime)
	{
		recipes.add(new FluidHeaterRecipe( input, input2, output, burnTime));
	}
	
	public void addRecipe(ItemStack input, FluidStack input2, FluidStack output, int burnTime)
	{
		recipes.add(new FluidHeaterRecipe( input, input2, output, burnTime));
	}
	
	public void addRecipe(ItemStack input, FluidStack input2, ItemStack output, int burnTime)
	{
		recipes.add(new FluidHeaterRecipe( input, input2, output, burnTime));
	}
	
	public void addRecipe(FluidStack input, FluidStack output, int burnTime)
	{
		recipes.add(new FluidHeaterRecipe( input, output, burnTime));
	}
	public void addRecipe(ItemStack input, FluidStack output, int burnTime)
	{
		recipes.add(new FluidHeaterRecipe( input, output, burnTime));
	}
	
	public void addRecipe(FluidStack input, ItemStack output, int burnTime)
	{
		recipes.add(new FluidHeaterRecipe( input, output, burnTime));
	}
	
	public static FluidHeaterRecipe getRecipe(FluidStack input, FluidStack input2, ItemStack inputItem){
		for(FluidHeaterRecipe sr : FluidHeaterRecipeManager.recipes){
			if (sr.getInput() != null && input != null && sr.getInput().getFluid() == input.getFluid()){
				if(sr.getInput2() == null ||input2 != null && sr.getInput2().getFluid() == input2.getFluid())
				{
					if(sr.getInputItem() == null || sr.getInputItem().getItem() ==  inputItem.getItem())
					{
						return sr;
					}
				}
			}
			else if(sr.getInputItem() != null && sr.getInputItem().getItem() ==  inputItem.getItem())
			{
				if(sr.getInput2() == null || input2 != null && sr.getInput2().getFluid() == input2.getFluid())
				{
				return sr;
				}
			}
		}
		return null;
	}
	
	public static FluidHeaterRecipe getRecipe(FluidStack input){
		for(FluidHeaterRecipe sr : FluidHeaterRecipeManager.recipes){
			if (sr.getInput().getFluid() == input.getFluid()){
				return sr;
			}
		}
		return null;
	}
	
	public static ArrayList<FluidHeaterRecipe> getRecipes() {
		return recipes;
	}
	
	public static FluidHeaterRecipeManager getInstance() {
		return instance;
	}
	
}