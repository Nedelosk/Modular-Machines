package modularmachines.api.recipes;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public interface IModuleCrafterRecipe extends IRecipe {

	boolean matches(ModuleCraftingWrapper inv, World world);

	ItemStack getHolder();

	Object[] getInput();

	int getWidth();

	int getHeight();
}
