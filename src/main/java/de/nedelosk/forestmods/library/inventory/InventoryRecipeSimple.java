package de.nedelosk.forestmods.library.inventory;

import de.nedelosk.forestmods.library.recipes.IRecipeInventory;

public class InventoryRecipeSimple extends InventorySimple implements IRecipeInventory {

	private int inputs;
	private int outputs;

	public InventoryRecipeSimple(int size, String name, int stackLimit, int inputs, int outputs) {
		super(size, name, stackLimit);

		this.inputs = inputs;
		this.outputs = outputs;
	}

	@Override
	public int getInputs() {
		return inputs;
	}

	@Override
	public int getOutputs() {
		return outputs;
	}
}