package modularmachines.common.blocks.tile;

import java.util.List;

import modularmachines.api.ILocatable;
import modularmachines.api.modules.ModuleRegistry;
import modularmachines.api.modules.assemblers.IAssembler;
import modularmachines.api.modules.logic.IModuleLogic;
import modularmachines.api.modules.storages.EnumStoragePosition;
import modularmachines.api.modules.storages.IStoragePosition;
import modularmachines.common.modules.assembler.Assembler;
import modularmachines.common.modules.logic.ModuleLogic;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;

public class TileEntityMachine extends TileBase implements ILocatable{

	public EnumFacing facing;
	public IAssembler assembler;
	public IModuleLogic logic;
	
	public TileEntityMachine() {
		List<IStoragePosition> positions = EnumStoragePosition.getValidPositions();
		this.assembler = new Assembler(this, positions);
		this.logic = new ModuleLogic(this, positions);
	}
	
	@Override
	public void updateClient() {
		
	}

	@Override
	public void updateServer() {
		
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
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setShort("Facing", (short) facing.ordinal());
		compound.setTag("Assembler", assembler.writeToNBT(new NBTTagCompound()));
		compound.setTag("Logic", logic.writeToNBT(new NBTTagCompound()));
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		facing = EnumFacing.VALUES[compound.getShort("Facing")];
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
	
	public EnumFacing getFacing() {
		return facing;
	}
	
	public void setFacing(EnumFacing facing) {
		this.facing = facing;
	}

}
