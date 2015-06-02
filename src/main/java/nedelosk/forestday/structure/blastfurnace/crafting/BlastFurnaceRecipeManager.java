package nedelosk.forestday.structure.blastfurnace.crafting;

import java.util.ArrayList;
import java.util.List;

import nedelosk.forestday.api.crafting.IAirHeaterRecipe;
import nedelosk.forestday.api.crafting.IBlastFurnaceRecipe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class BlastFurnaceRecipeManager implements IBlastFurnaceRecipe {

	private static ArrayList<BlastFurnaceRecipe> recipes = new ArrayList();
	
	public static BlastFurnaceRecipeManager  instance;
	
	public void addRecipe(ItemStack[] input, FluidStack[] output, int minHeat, int maxHeat)
	{
		recipes.add(new BlastFurnaceRecipe(input, output, minHeat, maxHeat));
	}
	
	public static BlastFurnaceRecipe getRecipe(ItemStack[] input, int heat){
		for(BlastFurnaceRecipe sr : BlastFurnaceRecipeManager.recipes){
			if (input[0].getItem()== sr.getInput()[0].getItem() && input[1].getItem()== sr.getInput()[1].getItem() && input[2].getItem()== sr.getInput()[2].getItem() && input[3].getItem()== sr.getInput()[3].getItem() && input[0].getItemDamage() == sr.getInput()[0].getItemDamage() && input[1].getItemDamage()== sr.getInput()[1].getItemDamage() && input[2].getItemDamage() == sr.getInput()[2].getItemDamage() && input[3].getItemDamage() == sr.getInput()[3].getItemDamage() && heat < sr.getMaxHeat() && heat > sr.getMinHeat()){
				return sr;
			}
		}
		return null;
	}

	public static BlastFurnaceRecipeManager  getInstance() {
		return instance;
	}
	
	public static List<BlastFurnaceRecipe> getRecipe()
	{
		return recipes;
	}
	
}