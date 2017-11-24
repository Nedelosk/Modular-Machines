package modularmachines.common.modules.container;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

import net.minecraftforge.common.capabilities.Capability;

import modularmachines.api.ILocatable;
import modularmachines.api.components.IComponent;
import modularmachines.api.components.IComponentTag;
import modularmachines.api.modules.IModule;
import modularmachines.api.modules.IModuleHandler;
import modularmachines.api.modules.container.ContainerComponent;
import modularmachines.api.modules.container.IModuleContainer;
import modularmachines.api.modules.positions.IModulePosition;
import modularmachines.common.modules.ModuleHandler;

public enum EmptyModuleContainer implements IModuleContainer {
	INSTANCE;
	
	@Nullable
	@Override
	public IModule getModule(int index) {
		return null;
	}
	
	@Override
	public int generateIndex(IModule module) {
		return 0;
	}
	
	@Override
	public Collection<IModule> getModules() {
		return Collections.emptyList();
	}
	
	@Nullable
	@Override
	public RayTraceResult collisionRayTrace(BlockPos pos, Vec3d start, Vec3d end) {
		return null;
	}
	
	@Override
	public boolean onActivated(EntityPlayer player, EnumHand hand, RayTraceResult hit) {
		return false;
	}
	
	@Override
	public void onClick(EntityPlayer player, RayTraceResult hit) {
	
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
	
	}
	
	@Override
	public void sendToClient() {
	
	}
	
	@Override
	public void markForDeletion() {
	
	}
	
	@Override
	public boolean isMarkedForDeletion() {
		return false;
	}
	
	@Override
	public ILocatable getLocatable() {
		return null;
	}
	
	@Override
	public Collection<ContainerComponent> getComponents() {
		return Collections.emptyList();
	}
	
	@Override
	public <T extends ContainerComponent> T addComponent(T component) {
		return component;
	}
	
	@Override
	public void addComponent(ContainerComponent... components) {
	
	}
	
	@Override
	public Class<?>[] getComponentInterfaces(Class<? extends ContainerComponent> componentClass) {
		return new Class[0];
	}
	
	@Override
	public boolean hasComponent(Class<?> componentClass) {
		return false;
	}
	
	@Nullable
	@Override
	public <T extends IComponent> T getComponent(Class<T> componentClass) {
		return null;
	}
	
	@Nullable
	@Override
	public <T> T getInterface(Class<T> interfaceClass) {
		return null;
	}
	
	@Override
	public <T> Collection<T> getInterfaces(Class<T> interfaceClass) {
		return Collections.emptyList();
	}
	
	@Nullable
	@Override
	public <T> T getComponent(IComponentTag tag) {
		return null;
	}
	
	@Override
	public <T> Collection<T> getComponents(IComponentTag tag) {
		return Collections.emptyList();
	}
	
	@Override
	public boolean hasComponent(IComponentTag tag) {
		return false;
	}
	
	@Override
	public void writeData(PacketBuffer data) {
	
	}
	
	@Override
	public void readData(PacketBuffer data) throws IOException {
	
	}
	
	
	@Override
	public IModuleHandler getHandler() {
		return new ModuleHandler(this);
	}
	
	@Nullable
	@Override
	public IModulePosition getPosition(RayTraceResult hit) {
		return null;
	}
	
	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
		return false;
	}
	
	@Nullable
	@Override
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
		return null;
	}
	
}
