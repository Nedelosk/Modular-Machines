package de.nedelosk.modularmachines.common.modular.handlers;

import java.util.List;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularAssembler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandlerTileEntity;
import de.nedelosk.modularmachines.api.modules.position.IStoragePosition;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergyTile;
import ic2.api.info.Info;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;

public class ModularHandlerTileEntity extends ModularHandler implements IModularHandlerTileEntity<IModular, IModularAssembler, NBTTagCompound> {

	protected TileEntity tileEntity;
	protected EnumFacing facing;
	protected boolean addedToEnet;

	public ModularHandlerTileEntity(TileEntity tileEntity, List<IStoragePosition> positions) {
		super(tileEntity.getWorld(), positions);

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

	@Override
	public void updateServer() {
		if(!addedToEnet && modular != null && modular.getEnergyBuffer() != null){
			onLoaded();
		}
		super.updateServer();
	}

	protected void onLoaded() {
		if(Loader.isModLoaded("IC2")){
			onLoadedIC2();
		}
	}

	@Override
	public void invalidate(){
		if(addedToEnet && modular != null && modular.getEnergyBuffer() != null && Loader.isModLoaded("IC2")){
			invalidateIC2();
		}
	}

	@Optional.Method(modid = "IC2")
	protected void onLoadedIC2(){
		if (!world.isRemote && Info.isIc2Available() && tileEntity instanceof IEnergyTile) {

			MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent((IEnergyTile) tileEntity));
			IBlockState state = world.getBlockState(getPos());
			world.notifyBlockOfStateChange(getPos(), state.getBlock());

			addedToEnet = true;
		}
	}

	@Optional.Method(modid = "IC2")
	protected void invalidateIC2(){
		if (Info.isIc2Available() && tileEntity instanceof IEnergyTile) {
			MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent((IEnergyTile) tileEntity));

			IBlockState state = world.getBlockState(getPos());
			world.notifyBlockOfStateChange(getPos(), state.getBlock());

			addedToEnet = false;
		}
	}
}
