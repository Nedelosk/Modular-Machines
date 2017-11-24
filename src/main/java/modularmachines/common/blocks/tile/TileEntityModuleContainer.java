package modularmachines.common.blocks.tile;

import java.util.LinkedHashMap;
import java.util.Map;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.mojang.authlib.GameProfile;

import net.minecraftforge.common.capabilities.Capability;

import modularmachines.api.ILocatable;
import modularmachines.api.modules.ModuleManager;
import modularmachines.api.modules.container.ContainerComponent;
import modularmachines.api.modules.container.IModuleContainer;
import modularmachines.common.modules.ModuleCapabilities;
import modularmachines.common.modules.ModuleHandler;
import modularmachines.common.modules.container.components.EnergyManager;
import modularmachines.common.modules.container.components.HeatComponent;
import modularmachines.common.modules.container.components.UpdateComponent;

public class TileEntityModuleContainer extends TileEntityBase implements ILocatable {
	
	private final Map<String, ContainerComponent> componentMap;
	public EnumFacing facing;
	public GameProfile owner;
	public ModuleHandler moduleHandler;
	public IModuleContainer moduleContainer;
	
	public TileEntityModuleContainer() {
		this.moduleContainer = ModuleManager.factory.createContainer(this);
		this.facing = EnumFacing.NORTH;
		this.componentMap = new LinkedHashMap<>();
		moduleContainer.addComponent(new EnergyManager());
		moduleContainer.addComponent(new UpdateComponent());
		moduleContainer.addComponent(new HeatComponent());
	}
	
	@Override
	public void updateClient() {
	
	}
	
	@Override
	public void updateServer() {
		if (moduleContainer.isMarkedForDeletion()) {
			world.setBlockToAir(pos);
		}
		for (ContainerComponent component : componentMap.values()) {
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
		moduleContainer.writeToNBT(compound);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		facing = EnumFacing.VALUES[compound.getShort("Facing")];
		moduleContainer.readFromNBT(compound);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == ModuleCapabilities.MODULE_CONTAINER || moduleContainer.hasCapability(capability, facing) || super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == ModuleCapabilities.MODULE_CONTAINER) {
			return ModuleCapabilities.MODULE_CONTAINER.cast(moduleContainer);
		} else {
			T t = moduleContainer.getCapability(capability, facing);
			if (t != null) {
				return t;
			}
		}
		return super.getCapability(capability, facing);
	}
	
	public EnumFacing getFacing() {
		return facing;
	}
	
	public void setFacing(EnumFacing facing) {
		this.facing = facing;
	}
}