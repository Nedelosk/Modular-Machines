package modularmachines.common.modules;

import net.minecraft.util.math.AxisAlignedBB;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.components.IBoundingBoxComponent;
import modularmachines.api.modules.components.IFluidHandlerComponent;
import modularmachines.api.modules.components.IGuiFactory;
import modularmachines.api.modules.components.IIOComponent;
import modularmachines.api.modules.components.IInteractionComponent;
import modularmachines.api.modules.components.IItemHandlerComponent;
import modularmachines.api.modules.components.IModuleComponentFactory;
import modularmachines.common.modules.components.BoundingBoxComponent;
import modularmachines.common.modules.components.FluidHandlerComponent;
import modularmachines.common.modules.components.GuiComponent;
import modularmachines.common.modules.components.IOComponent;
import modularmachines.common.modules.components.ItemHandlerComponent;

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
