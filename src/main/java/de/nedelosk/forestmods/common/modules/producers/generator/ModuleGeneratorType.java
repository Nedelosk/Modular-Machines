package de.nedelosk.forestmods.common.modules.producers.generator;

public class ModuleGeneratorType implements IModuleGeneratorType {

	protected final int energy;

	public ModuleGeneratorType(int energy) {
		this.energy = energy;
	}

	@Override
	public int getEnergy() {
		return energy;
	}
}
