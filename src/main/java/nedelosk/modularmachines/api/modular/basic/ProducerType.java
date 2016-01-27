package nedelosk.modularmachines.api.modular.basic;

import nedelosk.modularmachines.api.modular.basic.container.module.IModuleContainer;
import nedelosk.modularmachines.api.modular.basic.container.module.ISingleModuleContainer;

public enum ProducerType {
	MACHINE(ISingleModuleContainer.class), MANAGER(ISingleModuleContainer.class), ENGINE(ISingleModuleContainer.class), CUSTOM(IModuleContainer.class);

	private final Class<? extends IModuleContainer> clazz;

	private ProducerType(Class<? extends IModuleContainer> clazz) {
		this.clazz = clazz;
	}

	public Class<? extends IModuleContainer> getClazz() {
		return clazz;
	}
}
