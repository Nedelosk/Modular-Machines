package modularmachines.api.modules;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

import modularmachines.api.ILocatable;
import modularmachines.api.ILocatableSource;
import modularmachines.api.modules.listeners.IModuleListener;
import modularmachines.api.modules.logic.LogicComponent;

public interface IModuleContainer extends ILocatableSource, IModuleListener, IModuleProvider {
	
	void addComponent(String identifier, LogicComponent component);
	
	@Nullable
	<T extends LogicComponent> T getComponent(String identifier);
	
	Map<String, LogicComponent> getComponents();
	
	ILocatable getLocatable();
	
	@Nullable
	Module getModule(int index);
	
	Collection<Module> getModules();
	
	@Nullable
	RayTraceResult collisionRayTrace(BlockPos pos, Vec3d start, Vec3d end);
	
	default AxisAlignedBB getBoundingBox() {
		return Block.FULL_BLOCK_AABB;
	}
	
	boolean insertModule(ItemStack itemStack, RayTraceResult rayTraceResult, boolean simulate);
	
	List<ItemStack> extractModule(RayTraceResult rayTraceResult, boolean simulate);
	
	@Override
	default IModuleContainer getContainer() {
		return this;
	}
	
	@Nullable
	default EnumFacing getFacing() {
		return null;
	}
	
	default void setFacing(EnumFacing facing) {
	}
}
