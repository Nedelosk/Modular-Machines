package de.nedelosk.modularmachines.common.modules;

import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.IModuleController;
import de.nedelosk.modularmachines.api.modules.IModuleState;
import de.nedelosk.modularmachines.api.modules.models.IModelHandler;
import de.nedelosk.modularmachines.client.modules.ModelHandlerDefault;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModuleController extends Module implements IModuleController {

	public final int allowedComplexity;
	public final int allowedModuleComplexity;
	public final int allowedToolComplexity;
	public final int allowedDriveComplexity;

	public ModuleController(int allowedComplexity, int allowedModuleComplexity, int allowedToolComplexity, int allowedDriveComplexity) {
		super("controller", 0);
		this.allowedComplexity = allowedComplexity;
		this.allowedModuleComplexity = allowedModuleComplexity;
		this.allowedToolComplexity = allowedToolComplexity;
		this.allowedDriveComplexity = allowedDriveComplexity;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModelHandler getInitModelHandler(IModuleContainer container) {
		return new ModelHandlerDefault(new ResourceLocation("modularmachines:module/controllers/" + container.getMaterial().getName()));
	}

	@Override
	public int getAllowedComplexity(IModuleState state) {
		return allowedComplexity;
	}
}
