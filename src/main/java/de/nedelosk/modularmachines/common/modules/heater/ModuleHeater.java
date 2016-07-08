package de.nedelosk.modularmachines.common.modules.heater;

import de.nedelosk.modularmachines.api.modules.IModuleColored;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.IModuleModelHandler;
import de.nedelosk.modularmachines.api.modules.heater.IModuleHeater;
import de.nedelosk.modularmachines.client.modules.ModelHandlerDefault;
import de.nedelosk.modularmachines.common.modules.Module;
import net.minecraft.util.ResourceLocation;

public abstract class ModuleHeater extends Module implements IModuleHeater, IModuleColored {

	protected final int maxHeat;
	protected final int size;

	public ModuleHeater(int maxHeat, int size) {
		super();
		this.maxHeat = maxHeat;
		this.size = size;
	}

	@Override
	public IModuleModelHandler getInitModelHandler(IModuleContainer container) {
		return new ModelHandlerDefault(new ResourceLocation("modularmachines:module/heaters/" + container.getMaterial().getName() + (size == 0 ? "_small" : size == 1 ? "_middle" : "_large")));
	}

	@Override
	public int getMaxHeat() {
		return maxHeat;
	}

	@Override
	public int getSize() {
		return size;
	}
}