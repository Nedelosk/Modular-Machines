package de.nedelosk.modularmachines.common.modules;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.modularmachines.api.modules.IModelInitHandler;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.models.IModelHandler;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storaged.EnumModuleSize;
import de.nedelosk.modularmachines.api.modules.storaged.EnumPosition;
import de.nedelosk.modularmachines.api.modules.storaged.IModuleController;
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
	public IModelHandler createModelHandler(IModuleState state) {
		return new ModelHandlerDefault(new ResourceLocation("modularmachines:module/controllers/" + state.getContainer().getMaterial().getName()));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public List<IModelInitHandler> getInitModelHandlers(IModuleContainer container) {
		List<IModelInitHandler> handlers = new ArrayList<>();
		handlers.add(new ModelHandlerDefault(new ResourceLocation("modularmachines:module/controllers/" + container.getMaterial().getName())));
		return handlers;
	}

	@Override
	public int getAllowedComplexity(IModuleState state) {
		return allowedComplexity;
	}

	@Override
	public boolean canWork(IModuleState controllerState, IModuleState moduleState) {
		return true;
	}

	@Override
	public EnumPosition getCurrentPosition(IModuleState state) {
		return EnumPosition.INTERNAL;
	}

	@Override
	public boolean canUseFor(EnumPosition position, IModuleContainer container) {
		return position == EnumPosition.INTERNAL;
	}

	@Override
	public EnumPosition getPosition(IModuleContainer container) {
		return EnumPosition.INTERNAL;
	}

	@Override
	public EnumModuleSize getSize() {
		return EnumModuleSize.LARGE;
	}
}
