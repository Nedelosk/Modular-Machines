package de.nedelosk.forestmods.library.recipes;

import net.minecraft.inventory.IInventory;

public interface IRecipeInventory extends IInventory{

	int getInputs();

	int getOutputs();
}
