package modularmachines.common.blocks.tile;

import java.util.Collection;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.mojang.authlib.GameProfile;

import net.minecraftforge.common.capabilities.Capability;

import modularmachines.api.ILocatable;
import modularmachines.api.modules.ModuleRegistry;
import modularmachines.api.modules.assemblers.IAssembler;
import modularmachines.api.modules.logic.IModuleLogic;
import modularmachines.api.modules.logic.LogicComponent;
import modularmachines.api.modules.storages.EnumStoragePosition;
import modularmachines.api.modules.storages.IStoragePosition;
import modularmachines.common.modules.assembler.Assembler;
import modularmachines.common.modules.logic.ModuleLogic;

public class TileEntityMachine extends TileBase implements ILocatable{

	public EnumFacing facing;
	public GameProfile owner;
	public IAssembler assembler;
	public IModuleLogic logic;
	
	public TileEntityMachine() {
		List<IStoragePosition> positions = EnumStoragePosition.getValidPositions();
		this.assembler = new Assembler(this, positions);
		this.logic = new ModuleLogic(this, positions);
		this.facing = EnumFacing.NORTH;
	}
	
	@Override
	public void updateClient() {
		
	}

	@Override
	public void updateServer() {
		Collection<LogicComponent> components = logic.getComponents().values();
		for(LogicComponent component : components){
			component.update();
		}
	}
	
	@Override
	public NBTTagCompound getUpdateTag() {
		return writeToNBT(new NBTTagCompound());
	}
	
	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		super.handleUpdateTag(tag);
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
	public boolean isUsableByPlayer(EntityPlayer player) {
		return !isInvalid() && world.getTileEntity(pos) == this && player.getDistanceSqToCenter(pos) <= 64;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setShort("Facing", (short) facing.ordinal());
		if(owner != null) {
			NBTUtil.writeGameProfile(compound.getCompoundTag("Owner"), owner);
		}
		compound.setTag("Assembler", assembler.writeToNBT(new NBTTagCompound()));
		compound.setTag("Logic", logic.writeToNBT(new NBTTagCompound()));
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		facing = EnumFacing.VALUES[compound.getShort("Facing")];
		if(compound.hasKey("Owner")){
			owner = NBTUtil.readGameProfileFromNBT(compound.getCompoundTag("Owner"));
		}
		assembler.readFromNBT(compound.getCompoundTag("Assembler"));
		logic.readFromNBT(compound.getCompoundTag("Logic"));
	}
	
	public boolean isAssembled(){
		return logic != null && !logic.getStorages().isEmpty();
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == ModuleRegistry.ASSEMBLER || capability == ModuleRegistry.MODULE_LOGIC || super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == ModuleRegistry.ASSEMBLER){
			return ModuleRegistry.ASSEMBLER.cast(assembler);
		}else if(capability == ModuleRegistry.MODULE_LOGIC){
			return ModuleRegistry.MODULE_LOGIC.cast(logic);
		}
		return super.getCapability(capability, facing);
	}
	
	public IModuleLogic getLogic() {
		return logic;
	}
	
	public EnumFacing getFacing() {
		return facing;
	}
	
	public void setFacing(EnumFacing facing) {
		this.facing = facing;
	}
	
	public void setOwner(GameProfile owner) {
		this.owner = owner;
	}
	
	public GameProfile getOwner() {
		return owner;
	}
}
