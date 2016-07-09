package de.nedelosk.modularmachines.common.modules.heater;

import de.nedelosk.modularmachines.api.modules.IModuleColored;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.IModuleModelHandler;
import de.nedelosk.modularmachines.api.modules.heater.IModuleHeater;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.client.modules.ModelHandlerDefault;
import de.nedelosk.modularmachines.client.modules.ModelHandlerInit;
import de.nedelosk.modularmachines.common.modules.Module;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ModuleHeater extends Module implements IModuleHeater, IModuleColored {

	protected final int maxHeat;
	protected final int size;

	public ModuleHeater(int maxHeat, int size) {
		super();
		this.maxHeat = maxHeat;
		this.size = size;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public IModuleModelHandler getInitModelHandler(IModuleContainer container) {
		ResourceLocation[] locations = new ResourceLocation[2];
		locations[0] = new ResourceLocation("modularmachines:module/heaters/" + container.getMaterial().getName() + (size == 0 ? "_small" : size == 1 ? "_middle" : "_large") + "_off");
		locations[1] = new ResourceLocation("modularmachines:module/heaters/" + container.getMaterial().getName() + (size == 0 ? "_small" : size == 1 ? "_middle" : "_large") + "_on");
		return new ModelHandlerInit(locations);
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