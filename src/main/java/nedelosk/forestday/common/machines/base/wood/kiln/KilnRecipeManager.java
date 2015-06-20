package nedelosk.forestday.common.machines.base.wood.kiln;

import java.util.ArrayList;
import java.util.List;

import nedelosk.forestday.api.crafting.IKilnRecipe;
import nedelosk.forestday.common.machines.base.saw.SawRecipe;
import nedelosk.forestday.common.machines.base.wood.workbench.WorkbenchRecipe;
import net.minecraft.item.ItemStack;

public class KilnRecipeManager implements IKilnRecipe{

	private static ArrayList<KilnRecipe> recipes = new ArrayList();
	
	public static KilnRecipeManager instance;
	
	public void addRecipe(ItemStack input1, ItemStack input2, ItemStack output1, ItemStack output2)
	{
		recipes.add(new KilnRecipe(input1, input2, output1, output2));
	}
	
	public void addRecipe(String input1, ItemStack input2, ItemStack output1, ItemStack output2)
	{
		recipes.add(new KilnRecipe(input1, input2, output1, output2));
	}
	
	public void addRecipe(ItemStack input1, String input2, ItemStack output1, ItemStack output2)
	{
		recipes.add(new KilnRecipe(input1, input2, output1, output2));
	}
	
	public void addRecipe(String input1, String input2, ItemStack output1, ItemStack output2)
	{
		recipes.add(new KilnRecipe(input1, input2, output1, output2));
	}
	
	public static KilnRecipe getRecipe(ItemStack input1, ItemStack input2){
		for(KilnRecipe sr : KilnRecipeManager.recipes){
			if (sr.getInput1().getItem() == input1.getItem() && sr.getInput2().getItem() == input2.getItem() && sr.getInput1().getItemDamage() == input1.getItemDamage() && sr.getInput2().getItemDamage() == input2.getItemDamage()){
				return sr;
			}
		}
		return null;
	}
	
	public static ArrayList<KilnRecipe> getRecipes() {
		return recipes;
	}
	
	public static KilnRecipeManager getInstance() {
		return instance;
	}
	
}