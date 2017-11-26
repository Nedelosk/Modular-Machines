package modularmachines.common.blocks.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.common.capabilities.Capability;

import modularmachines.api.ILocatable;
import modularmachines.api.modules.IModule;
import modularmachines.api.modules.ModuleManager;
import modularmachines.api.modules.container.IModuleContainer;
import modularmachines.common.modules.ModuleCapabilities;
import modularmachines.common.modules.container.components.EnergyManager;
import modularmachines.common.modules.container.components.FluidManager;
import modularmachines.common.modules.container.components.HeatComponent;
import modularmachines.common.modules.container.components.ItemManager;

public class TileEntityModuleContainer extends TileEntityBase implements ILocatable {
	
	public EnumFacing facing;
	public IModuleContainer moduleContainer;
	
	public TileEntityModuleContainer() {
		this.moduleContainer = ModuleManager.factory.createContainer(this);
		this.facing = EnumFacing.NORTH;
		moduleContainer.addComponent(new EnergyManager());
		moduleContainer.addComponent(new HeatComponent());
		moduleContainer.addComponent(new ItemManager());
		moduleContainer.addComponent(new FluidManager());
	}
	
	@Override
	public void updateClient() {
	
	}
	
	@Override
	public void updateServer() {
		moduleContainer.update();
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
		for (IModule module : moduleContainer.getModules()) {
			module.updateFacing();
		}
	}
}