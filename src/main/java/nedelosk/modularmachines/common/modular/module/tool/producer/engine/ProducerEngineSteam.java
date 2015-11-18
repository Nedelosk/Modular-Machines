package nedelosk.modularmachines.common.modular.module.tool.producer.engine;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.api.recipes.IRecipeManager;
import nedelosk.modularmachines.api.recipes.RecipeInput;
import nedelosk.modularmachines.common.modular.utils.RecipeManagerEnergy;
import nedelosk.modularmachines.common.modular.utils.RecipeManagerSteam;
import net.minecraft.nbt.NBTTagCompound;

public class ProducerEngineSteam extends ProducerEngine{

	public ProducerEngineSteam(String modifier, int speedModifier) {
		super(modifier, speedModifier, "Steam");
	}

	public ProducerEngineSteam(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super(nbt, modular, stack);
	}

	@Override
	public int getMaterialModifier(ModuleStack stack) {
		return 2;
	}

	@Override
	public IRecipeManager creatRecipeManager(IModular modular, String recipeName, int materialModifier, RecipeInput[] inputs) {
		return new RecipeManagerSteam(modular, recipeName, materialModifier, inputs);
	}

	@Override
	public IRecipeManager creatRecipeManager() {
		return new RecipeManagerSteam();
	}
	
}
