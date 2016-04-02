package de.nedelosk.forestmods.common.modules.basic;

import de.nedelosk.forestmods.api.modules.casing.IModuleCasing;
import de.nedelosk.forestmods.api.producers.IModule;

public class ModuleCasingType extends ModuleType<IModuleCasing, IModuleSaver> implements IModuleCasingType {

	private final int heat;
	private final int resistance;
	private final int hardness;

	public ModuleCasingType(String name, Material material, IModule<IModuleCasing, IModuleSaver, IModuleType> producer, int heat, int resistance,
			int hardness) {
		super(name, material, producer);
		this.heat = heat;
		this.hardness = hardness;
		this.resistance = resistance;
	}

	@Override
	public int getMaxHeat() {
		return 0;
	}

	@Override
	public int getResistance() {
		return 0;
	}

	@Override
	public int getHardness() {
		return 0;
	}
}
