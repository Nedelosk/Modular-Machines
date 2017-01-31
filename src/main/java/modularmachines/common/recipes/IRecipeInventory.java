package modularmachines.api.recipes;

import net.minecraftforge.items.IItemHandler;

public interface IRecipeInventory extends IItemHandler {

	int getInputs();

	int getOutputs();
}
