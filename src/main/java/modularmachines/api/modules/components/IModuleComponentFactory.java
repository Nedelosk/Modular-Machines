package modularmachines.api.modules.components;

import net.minecraft.util.math.AxisAlignedBB;

import modularmachines.api.modules.IModule;

public interface IModuleComponentFactory {
	IBoundingBoxComponent addBoundingBox(IModule module, AxisAlignedBB boundingBox);
	
	IItemHandlerComponent addItemHandler(IModule module);
	
	IFluidHandlerComponent addFluidHandler(IModule module);
}
