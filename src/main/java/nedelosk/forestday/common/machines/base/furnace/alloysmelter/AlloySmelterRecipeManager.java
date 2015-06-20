package nedelosk.forestday.common.machines.base.furnace.alloysmelter;

import java.util.ArrayList;
import java.util.List;

import nedelosk.forestday.api.crafting.BurningMode;
import nedelosk.forestday.api.crafting.IAlloySmelterRecipe;
import nedelosk.forestday.api.crafting.IBurnRecipe;
import nedelosk.forestday.common.machines.base.wood.workbench.WorkbenchRecipe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class AlloySmelterRecipeManager implements IAlloySmelterRecipe{

	private static List<AlloySmelterRecipe> recipes = new ArrayList();
	
	public static AlloySmelterRecipeManager instance;
	
	public void addRecipe(ItemStack input, int burnTime, ItemStack... output)
	{
		add(new AlloySmelterRecipe(input, burnTime, output));
	}
	
	public void addRecipe(ItemStack input, FluidStack inputFluid, int burnTime, ItemStack... output)
	{
		add(new AlloySmelterRecipe(input, inputFluid, burnTime, output));
	}
	
	public void addRecipe(ItemStack input, FluidStack inputFluid, int burnTime, int energy, ItemStack... output)
	{
		add(new AlloySmelterRecipe(input, inputFluid, burnTime, energy, output));
	}
	
	public void addRecipe(ItemStack input, int burnTime, int energy, ItemStack... output)
	{
		add(new AlloySmelterRecipe(input, burnTime, energy, output));
	}
	
	public void add(AlloySmelterRecipe recipe)
	{
		recipes.add(recipe);
	}
	
	public static AlloySmelterRecipe getRecipe(ItemStack input, FluidStack inputFluid){
		for(AlloySmelterRecipe sr : recipes){
			if (sr.input.getItem() == input.getItem() && sr.input.getItemDamage() == input.getItemDamage() && (sr.inputFluid == null || inputFluid.getFluid() == sr.inputFluid.getFluid())){
				return sr;
			}
		}
		return null;
	}
	
	public static void removeRecipe(AlloySmelterRecipe recipe)
	{
		recipes.remove(recipe);
	}
	
	public static void addAllRecipes(List<AlloySmelterRecipe> recipe)
	{
		recipes.addAll(recipe);
	}
	
	public static List<AlloySmelterRecipe> removeRecipes(ItemStack stack)
	{
		List<AlloySmelterRecipe> list = new ArrayList();
		for(AlloySmelterRecipe recipe : recipes)
		{
			int outputs = 0;
			for(int i = 0; i < recipe.output.length;i++)
			{
			if(recipe.output[i].getItem() == stack.getItem() && recipe.output[i].getItemDamage() == stack.getItemDamage() && ItemStack.areItemStackTagsEqual(recipe.output[i], stack))
			{
				outputs++;
			}
			}
			
			if(outputs ==  recipe.output.length)
			{
				 list.add(recipe);
			}
		}
		return list;
	}
	
}