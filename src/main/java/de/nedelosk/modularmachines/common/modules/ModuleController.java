package de.nedelosk.modularmachines.common.modules;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.IModuleController;
import de.nedelosk.modularmachines.api.modules.IModuleDrive;
import de.nedelosk.modularmachines.api.modules.IModuleModelHandler;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.tool.IModuleTool;
import de.nedelosk.modularmachines.client.modules.ModelHandlerDefault;
import net.minecraft.util.ResourceLocation;

public class ModuleController extends Module implements IModuleController {

	public final int allowedComplexity;
	public final int allowedModuleComplexity;
	public final int allowedToolComplexity;
	public final int allowedDriveComplexity;

	public ModuleController(int allowedComplexity, int allowedModuleComplexity, int allowedToolComplexity, int allowedDriveComplexity) {
		this.allowedComplexity = allowedComplexity;
		this.allowedModuleComplexity = allowedModuleComplexity;
		this.allowedToolComplexity = allowedToolComplexity;
		this.allowedDriveComplexity = allowedDriveComplexity;
	}

	@Override
	public IModuleModelHandler getInitModelHandler(IModuleContainer container) {
		return new ModelHandlerDefault(new ResourceLocation("modularmachines:module/controllers/" + container.getMaterial().getName()));
	}

	@Override
	public int getAllowedModuleComplexity(Class<? extends IModule> moduleClass, IModuleState state) {
		if(IModuleTool.class.isAssignableFrom(moduleClass)){
			return allowedToolComplexity;
		}else if(IModuleDrive.class.isAssignableFrom(moduleClass)){
			return allowedDriveComplexity;
		}
		return allowedModuleComplexity;
	}

	@Override
	public int getAllowedComplexity(IModuleState state) {
		return allowedComplexity;
	}
}
