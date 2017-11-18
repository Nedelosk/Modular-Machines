package modularmachines.common.blocks.tile;

import com.google.common.base.Preconditions;

import javax.annotation.Nullable;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import com.mojang.authlib.GameProfile;

import net.minecraftforge.common.capabilities.Capability;

import modularmachines.api.ILocatable;
import modularmachines.api.modules.EnumModulePositions;
import modularmachines.api.modules.IModuleContainer;
import modularmachines.api.modules.IModuleHandler;
import modularmachines.api.modules.IModulePosition;
import modularmachines.api.modules.IModuleProvider;
import modularmachines.api.modules.IModuleType;
import modularmachines.api.modules.Module;
import modularmachines.api.modules.ModuleHelper;
import modularmachines.api.modules.ModuleRegistry;
import modularmachines.api.modules.containers.IModuleDataContainer;
import modularmachines.api.modules.logic.LogicComponent;
import modularmachines.common.modules.ModuleHandler;
import modularmachines.common.modules.logic.EnergyStorageComponent;
import modularmachines.common.modules.logic.HeatComponent;
import modularmachines.common.modules.logic.ModelComponent;
import modularmachines.common.modules.logic.UpdateComponent;

public class TileModuleStorage extends TileBase implements ILocatable, IModuleContainer {
	
	private final Map<String, LogicComponent> componentMap;
	private final BitSet validIndexes;
	public EnumFacing facing;
	public GameProfile owner;
	public ModuleHandler moduleHandler;
	
	public TileModuleStorage() {
		this.moduleHandler = new ModuleHandler(this, EnumModulePositions.CASING);
		this.facing = EnumFacing.NORTH;
		this.componentMap = new LinkedHashMap<>();
		this.validIndexes = new BitSet();
		addComponent(LogicComponent.ENERGY, new EnergyStorageComponent());
		addComponent(LogicComponent.UPDATE, new UpdateComponent());
		addComponent(LogicComponent.HEAT, new HeatComponent());
		addComponent(LogicComponent.MODEL, new ModelComponent());
	}
	
	@Override
	public void updateClient() {
	
	}
	
	@Override
	public void updateServer() {
		for(LogicComponent component : componentMap.values()){
			component.update();
		}
	}
	
	@Override
	public NBTTagCompound getUpdateTag() {
		return writeToNBT(new NBTTagCompound());
	}
	
	@Override
	public BlockPos getCoordinates() {
		return pos;
	}
	
	@Override
	public World getWorldObj() {
		return world;
	}
	
	@Override
	public void markLocatableDirty() {
		markDirty();
	}
	
	@Override
	public void markBlockUpdate() {
		IBlockState blockState = world.getBlockState(pos);
		world.notifyBlockUpdate(pos, blockState, blockState, 0);
	}
	
	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return !isInvalid() && world.getTileEntity(pos) == this && player.getDistanceSqToCenter(pos) <= 64;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setShort("Facing", (short) facing.ordinal());
		moduleHandler.writeToNBT(compound);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		facing = EnumFacing.VALUES[compound.getShort("Facing")];
		validIndexes.clear();
		moduleHandler.readFromNBT(compound);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == ModuleRegistry.MODULE_CONTAINER || super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == ModuleRegistry.MODULE_CONTAINER){
			return ModuleRegistry.MODULE_CONTAINER.cast(this);
		}
		return super.getCapability(capability, facing);
	}
	
	public EnumFacing getFacing() {
		return facing;
	}
	
	public void setFacing(EnumFacing facing) {
		this.facing = facing;
	}
	
	@Override
	public void addComponent(String identifier, LogicComponent component) {
		Preconditions.checkNotNull(component, "Can't have a null logic component!");
		component.setProvider(this);
		this.componentMap.put(identifier, component);
	}
	
	@Override
	public Map<String, LogicComponent> getComponents() {
		return this.componentMap;
	}
	
	@Override
	@Nullable
	public <T extends LogicComponent> T getComponent(String identifier) {
		return (T) this.componentMap.get(identifier);
	}
	
	public boolean hasComponent(String identifier) {
		return this.componentMap.containsKey(identifier);
	}
	
	@Override
	public ILocatable getLocatable() {
		return this;
	}
	
	@Override
	public IModuleHandler getHandler() {
		return moduleHandler;
	}
	
	@Override
	public Module getModule(int index) {
		return getModules().stream().filter(m -> m.getIndex() == index).findAny().orElse(null);
	}
	
	@Override
	public Collection<Module> getModules() {
		Set<Module> modules = new HashSet<>();
		moduleHandler.getModules().forEach(m -> addToList(modules, m));
		return modules;
	}
	
	private void addToList(Set<Module> modules, Module module){
		modules.add(module);
		if(!(module instanceof IModuleProvider)){
			return;
		}
		IModuleProvider provider = (IModuleProvider) module;
		IModuleHandler moduleHandler = provider.getHandler();
		moduleHandler.getModules().forEach(m -> addToList(modules, m));
	}
	
	@Override
	public AxisAlignedBB getBoundingBox() {
		AxisAlignedBB boundingBox = null;
		for(Module module : getModules()){
			AxisAlignedBB alignedBB = module.getCollisionBox();
			if(alignedBB == null){
				continue;
			}
			if(boundingBox == null){
				boundingBox = alignedBB;
				continue;
			}
			boundingBox = boundingBox.union(alignedBB);
		}
		return boundingBox == null ? Block.FULL_BLOCK_AABB : boundingBox;
	}
	
	@Override
	public RayTraceResult collisionRayTrace(BlockPos pos, Vec3d start, Vec3d end) {
		Vec3d startVec = start.subtract((double)pos.getX(), (double)pos.getY(), (double)pos.getZ());
		Vec3d endVec = end.subtract((double)pos.getX(), (double)pos.getY(), (double)pos.getZ());
		Collection<Module> modules = getModules();
		if(modules.isEmpty()){
			RayTraceResult old = Block.FULL_BLOCK_AABB.calculateIntercept(startVec, endVec);
			if(old == null){
				return null;
			}
			return new RayTraceResult(old.hitVec.addVector((double)pos.getX(), (double)pos.getY(), (double)pos.getZ()), old.sideHit, pos);
		}
		return modules
				.stream()
				.map(m -> Pair.of(m, m.collisionRayTrace(startVec, endVec)))
				.filter(p -> p.getValue() != null)
				.min(Comparator.comparingDouble(hit -> hit.getValue().hitVec.squareDistanceTo(startVec)))
				.map(p -> {
					RayTraceResult old = p.getValue();
					RayTraceResult result = new RayTraceResult(old.hitVec.addVector((double)pos.getX(), (double)pos.getY(), (double)pos.getZ()), old.sideHit, pos);
					result.subHit = p.getKey().getIndex();
					result.hitInfo = old;
					return result;
				})
				.orElse(null);
	}
	
	@Override
	public boolean insertModule(ItemStack itemStack, RayTraceResult rayTraceResult) {
		if(itemStack.isEmpty()){
			return false;
		}
		IModuleDataContainer dataContainer = ModuleHelper.getContainerFromItem(itemStack);
		if(dataContainer == null){
			return false;
		}
		if(rayTraceResult.subHit == -1){
			return insertModule(this, itemStack, dataContainer, rayTraceResult);
		}
		Module module = getModule(rayTraceResult.subHit);
		if(!(module instanceof IModuleProvider)){
			return false;
		}
		IModuleProvider provider = (IModuleProvider) module;
		return insertModule(provider, itemStack, dataContainer, rayTraceResult);
	}
	
	private boolean insertModule(IModuleProvider provider, ItemStack itemStack, IModuleDataContainer dataContainer, RayTraceResult rayTraceResult){
		IModulePosition position = provider.getPosition(rayTraceResult);
		if(position == null) {
			return false;
		}
		return provider.getHandler().insertModule(position, dataContainer, itemStack);
	}
	
	@Override
	public IModulePosition getPosition(RayTraceResult hit) {
		return EnumModulePositions.CASING;
	}
	
	@Override
	public Collection<Module> getModules(IModuleType type) {
		return Collections.emptySet();
	}
	
	@Override
	public void onModuleRemoved(Module module) {
		validIndexes.clear(module.getIndex());
	}
	
	@Override
	public void onModuleAdded(Module module) {
		int index = validIndexes.nextClearBit(0);
		module.setIndex(index);
		validIndexes.set(index);
	}
}