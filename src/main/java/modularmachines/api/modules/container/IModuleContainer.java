package modularmachines.api.modules.container;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

import net.minecraftforge.common.capabilities.ICapabilityProvider;

import modularmachines.api.ILocatable;
import modularmachines.api.ILocatableSource;
import modularmachines.api.components.IComponentProvider;
import modularmachines.api.modules.IModule;
import modularmachines.api.modules.IModuleProvider;

/**
 * Implement this interface as a capability which should handle modules.
 * <p>
 * You can use {@link modularmachines.api.modules.IModuleFactory#createContainer(ILocatable)} to create an instance of
 * this or you can use your own implementation.
 */
public interface IModuleContainer extends ILocatableSource, IModuleProvider, ICapabilityProvider, IComponentProvider<ContainerComponent> {
	
	/**
	 * @param index The internal index of the module. It is generated out of the position of the module and the
	 *              positions of the parents.
	 * @return The module that has the given internal index.
	 */
	@Nullable
	IModule getModule(int index);
	
	int generateIndex(IModule module);
	
	@Nullable
	RayTraceResult collisionRayTrace(BlockPos pos, Vec3d start, Vec3d end);
	
	/**
	 * @return A bounding box that contains all bounding boxes of the modules.
	 */
	default AxisAlignedBB getBoundingBox() {
		return Block.FULL_BLOCK_AABB;
	}
	
	boolean onActivated(EntityPlayer player, EnumHand hand, RayTraceResult hit);
	
	void onClick(EntityPlayer player, RayTraceResult hit);
	
	@Override
	default IModuleContainer getContainer() {
		return this;
	}
	
	NBTTagCompound writeToNBT(NBTTagCompound compound);
	
	void readFromNBT(NBTTagCompound compound);
	
	/**
	 * Sends the data of all {@link modularmachines.api.components.INetworkComponent} to the client.
	 */
	void sendToClient();
	
	/**
	 * Mark the block that contains for deletion in the next tick. Called if the last module of the {@link #getHandler()}
	 * was removed and the container dose not contain any modules anymore.
	 */
	void markForDeletion();
	
	boolean isMarkedForDeletion();
}
