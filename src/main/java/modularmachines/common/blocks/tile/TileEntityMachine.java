package modularmachines.common.blocks.tile;

import java.util.List;

import modularmachines.api.ILocatable;
import modularmachines.api.modules.IModuleLogic;
import modularmachines.api.modules.ModuleManager;
import modularmachines.api.modules.assemblers.IAssembler;
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
		List<IStoragePosition> positions = EnumStoragePosition.getPositions();
		this.assembler = new Assembler(this, positions);
		this.logic = new ModuleLogic(this);
	}
	
	@Override
	public void updateClient() {
		
	}

	@Override
	public void updateServer() {
		
	}

	@Override
	public BlockPos getCoordinates() {
		return getPos();
	}

	@Override
	public World getWorldObj() {
		return world;
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
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == ModuleManager.ASSEMBLER || capability == ModuleManager.MODULE_LOGIC || super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == ModuleManager.ASSEMBLER){
			return ModuleManager.ASSEMBLER.cast(assembler);
		}else if(capability == ModuleManager.MODULE_LOGIC){
			return ModuleManager.MODULE_LOGIC.cast(logic);
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
