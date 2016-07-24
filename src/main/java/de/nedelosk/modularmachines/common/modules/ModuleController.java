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
import de.nedelosk.modularmachines.client.modules.ModelHandler;
import de.nedelosk.modularmachines.client.modules.ModelHandlerDefault;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModuleController extends Module implements IModuleController {

	public ModuleController() {
		super("controller", 0);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModelHandler createModelHandler(IModuleState state) {
		ResourceLocation loc = ModelHandler.getModelLocation(state.getContainer(), "controllers", state.getModule().getSize());
		return new ModelHandlerDefault("controllers", state.getContainer(), loc);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public List<IModelInitHandler> getInitModelHandlers(IModuleContainer container) {
		List<IModelInitHandler> handlers = new ArrayList<>();
		ResourceLocation loc = ModelHandler.getModelLocation(container, "controllers", container.getModule().getSize());
		handlers.add(new ModelHandlerDefault("controllers", container, loc));
		return handlers;
	}

	@Override
	public boolean canWork(IModuleState controllerState, IModuleState moduleState) {
		return true;
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
