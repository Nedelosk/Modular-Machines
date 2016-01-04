package nedelosk.modularmachines.common.producers.engine;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.producers.engine.ProducerEngine;
import nedelosk.modularmachines.api.recipes.IRecipeManager;
import nedelosk.modularmachines.api.recipes.RecipeInput;
import nedelosk.modularmachines.api.utils.ModuleStack;
import nedelosk.modularmachines.common.modular.utils.RecipeManagerEnergy;
import net.minecraft.nbt.NBTTagCompound;

public class ProducerEngineEnergy extends ProducerEngine {

	public ProducerEngineEnergy(String modifier, int speedModifier) {
		super(modifier, speedModifier, "Energy");
	}

	public ProducerEngineEnergy(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super(nbt, modular, stack);
	}

	@Override
	public int getMaterialModifier(ModuleStack stack) {
		return 1;
	}

	@Override
	public IRecipeManager creatRecipeManager(IModular modular, String recipeName, int materialModifier,
			RecipeInput[] inputs, Object... craftingModifier) {
		return new RecipeManagerEnergy(modular, recipeName, materialModifier, inputs, craftingModifier);
	}

	@Override
	public IRecipeManager creatRecipeManager() {
		return new RecipeManagerEnergy();
	}

}
