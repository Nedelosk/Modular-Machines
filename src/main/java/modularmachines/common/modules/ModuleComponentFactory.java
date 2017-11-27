package modularmachines.common.modules;

import net.minecraft.util.math.AxisAlignedBB;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.components.IGuiFactory;
import modularmachines.api.modules.components.IModuleComponentFactory;
import modularmachines.api.modules.components.block.IBoundingBoxComponent;
import modularmachines.api.modules.components.block.IInteractionComponent;
import modularmachines.api.modules.components.handlers.IFluidHandlerComponent;
import modularmachines.api.modules.components.handlers.IIOComponent;
import modularmachines.api.modules.components.handlers.IItemHandlerComponent;
import modularmachines.common.modules.components.block.BoundingBoxComponent;
import modularmachines.common.modules.components.block.GuiComponent;
import modularmachines.common.modules.components.handlers.FluidHandlerComponent;
import modularmachines.common.modules.components.handlers.IOComponent;
import modularmachines.common.modules.components.handlers.ItemHandlerComponent;

public enum ModuleComponentFactory implements IModuleComponentFactory {
	INSTANCE;
	
	@Override
	public IBoundingBoxComponent addBoundingBox(IModule module, AxisAlignedBB boundingBox) {
		return module.addComponent(new BoundingBoxComponent(boundingBox));
	}
	
	@Override
	public IItemHandlerComponent addItemHandler(IModule module) {
		if (!module.hasComponent(IIOComponent.class)) {
			addIO(module);
		}
		return module.addComponent(new ItemHandlerComponent());
	}
	
	@Override
	public IFluidHandlerComponent addFluidHandler(IModule module) {
		if (!module.hasComponent(IIOComponent.class)) {
			addIO(module);
		}
		return module.addComponent(new FluidHandlerComponent());
	}
	
	@Override
	public IIOComponent addIO(IModule module) {
		return module.addComponent(new IOComponent());
	}
	
	@Override
	public IInteractionComponent addGui(IModule module, IGuiFactory guiFactory) {
		return module.addComponent(new GuiComponent(guiFactory));
	}
}
