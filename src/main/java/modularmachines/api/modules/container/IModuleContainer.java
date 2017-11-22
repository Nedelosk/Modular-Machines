package modularmachines.api.modules.container;

import javax.annotation.Nullable;
import java.util.Collection;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

import net.minecraftforge.common.capabilities.ICapabilityProvider;

import modularmachines.api.ILocatableSource;
import modularmachines.api.components.IComponentProvider;
import modularmachines.api.modules.IModuleProvider;
import modularmachines.api.modules.Module;
import modularmachines.api.modules.listeners.IModuleListener;

public interface IModuleContainer extends ILocatableSource, IModuleListener, IModuleProvider, ICapabilityProvider, IComponentProvider<ContainerComponent> {
	
	@Nullable
	Module getModule(int index);
	
	Collection<Module> getModules();
	
	@Nullable
	RayTraceResult collisionRayTrace(BlockPos pos, Vec3d start, Vec3d end);
	
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
	
	void sendModuleToClient(Module module);
	
	void sendToClient();
}
