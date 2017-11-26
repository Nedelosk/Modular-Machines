package modularmachines.api.modules.components;

import net.minecraft.util.math.AxisAlignedBB;

import modularmachines.api.modules.IModule;

/**
 * A factory to create module components.
 */
public interface IModuleComponentFactory {
	/**
	 * Creates and adds a bounding box component to the module.
	 *
	 * @param module      The module
	 * @param boundingBox The bounding box that should be added to the module.
	 * @return The created bounding box component.
	 */
	IBoundingBoxComponent addBoundingBox(IModule module, AxisAlignedBB boundingBox);
	
	/**
	 * Creates and adds a item handler component to the module.
	 *
	 * @param module The module
	 * @return The created item handler component.
	 */
	IItemHandlerComponent addItemHandler(IModule module);
	
	/**
	 * Creates and adds a fluid handler component to the module.
	 *
	 * @param module The module
	 * @return The created fluid handler component.
	 */
	IFluidHandlerComponent addFluidHandler(IModule module);
}
