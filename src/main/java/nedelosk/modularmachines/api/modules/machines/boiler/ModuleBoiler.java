package nedelosk.modularmachines.api.modules.machines.boiler;

import nedelosk.modularmachines.api.modules.machines.recipe.ModuleMachineRecipe;
import nedelosk.modularmachines.api.utils.ModuleStack;

public abstract class ModuleBoiler<S extends IModuleBoilerSaver> extends ModuleMachineRecipe<S> implements IModuleBoiler<S> {

	protected final int steam;
	protected final int water;
	protected final String textureName;

	public ModuleBoiler(String moduleUID, String textureName, int inputsItem, int outputsItem, int inputsFluid, int outputsFluid, int speed, int steam,
			int water) {
		super(moduleUID, inputsItem, outputsItem, inputsFluid, outputsFluid, speed);
		this.steam = steam;
		this.water = water;
		this.textureName = textureName;
	}

	@Override
	public boolean useFluids(ModuleStack stack) {
		return true;
	}

	@Override
	public String getFilePath(ModuleStack stack) {
		return "boiler/" + textureName;
	}

	@Override
	public S createSaver(ModuleStack stack) {
		return (S) new ModuleBoilerSaver();
	}
}
