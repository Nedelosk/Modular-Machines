package de.nedelosk.forestmods.common.producers;

import de.nedelosk.forestmods.api.recipes.IRecipe;
import de.nedelosk.forestmods.api.recipes.IRecipeHandler;
import de.nedelosk.forestmods.api.recipes.IRecipeJsonSerializer;
import de.nedelosk.forestmods.api.recipes.IRecipeNBTSerializer;

public class ProducerRecipeHandler implements IRecipeHandler {

	private final ProducerAdvanced producer;

	public ProducerRecipeHandler(ProducerAdvanced producer) {
		this.producer = producer;
	}

	@Override
	public IRecipeJsonSerializer getJsonSerialize() {
		return producer;
	}

	@Override
	public IRecipeNBTSerializer getNBTSerialize() {
		return producer;
	}

	@Override
	public String getRecipeCategory() {
		return producer.getRecipeCategory();
	}

	@Override
	public Class<? extends IRecipe> getRecipeClass() {
		return producer.getRecipeClass();
	}
}
