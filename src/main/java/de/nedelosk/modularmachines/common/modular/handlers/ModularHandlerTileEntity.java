package de.nedelosk.modularmachines.common.modular.handlers;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandlerTileEntity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class ModularHandlerTileEntity extends ModularHandler implements IModularHandlerTileEntity<IModular, NBTTagCompound> {

	protected TileEntity tileEntity;
	protected EnumFacing facing;

	public ModularHandlerTileEntity(TileEntity tileEntity) {
		super(tileEntity.getWorld());

		this.tileEntity = tileEntity;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = super.serializeNBT();
		if(facing != null){
			nbt.setInteger("facing", facing.ordinal());
		}
		return nbt;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		if(nbt.hasKey("facing")){
			facing = EnumFacing.VALUES[nbt.getInteger("facing")];
		}
		super.deserializeNBT(nbt);
	}

	@Override
	public void markDirty() {
		tileEntity.markDirty();
	}

	@Override
	public EnumFacing getFacing() {
		return facing;
	}

	@Override
	public void setFacing(EnumFacing facing) {
		this.facing = facing;
	}

	@Override
	public BlockPos getPos() {
		return tileEntity.getPos();
	}
}
