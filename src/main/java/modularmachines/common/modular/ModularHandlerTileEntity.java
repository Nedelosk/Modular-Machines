package modularmachines.common.modular;

import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergyTile;
import ic2.api.info.Info;
import modularmachines.api.modular.handlers.IModularHandlerTileEntity;
import modularmachines.api.modular.handlers.ModularHandler;
import modularmachines.api.modules.position.StoragePositions;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;

public class ModularHandlerTileEntity<K> extends ModularHandler<K> implements IModularHandlerTileEntity<NBTTagCompound, K> {

	protected TileEntity tileEntity;
	protected EnumFacing facing;
	protected boolean addedToEnet;

	public ModularHandlerTileEntity(StoragePositions<K> positions) {
		super(null, positions);
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = super.serializeNBT();
		if (facing != null) {
			nbt.setInteger("facing", facing.ordinal());
		}
		return nbt;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		if (nbt.hasKey("facing")) {
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
	public void setTile(TileEntity tileEntity) {
		this.tileEntity = tileEntity;
		if (tileEntity != null && tileEntity.getWorld() != null) {
			setWorld(tileEntity.getWorld());
		}
	}

	@Override
	public TileEntity getTile() {
		return tileEntity;
	}

	@Override
	public void updateServer() {
		super.updateServer();
		if (!addedToEnet && getModular() != null && getModular().getEnergyBuffer() != null && Loader.isModLoaded("IC2")) {
			onLoadedIC2();
		}
	}

	@Override
	public void invalidate() {
		if (addedToEnet && getModular() != null && getModular().getEnergyBuffer() != null && Loader.isModLoaded("IC2")) {
			invalidateIC2();
		}
	}

	@Optional.Method(modid = "IC2")
	protected void onLoadedIC2() {
		if (!getWorld().isRemote && Info.isIc2Available() && getTile() instanceof IEnergyTile) {
			MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent((IEnergyTile) getTile()));
			IBlockState state = getWorld().getBlockState(getPos());
			getWorld().notifyBlockOfStateChange(getPos(), state.getBlock());
			addedToEnet = true;
		}
	}

	@Optional.Method(modid = "IC2")
	protected void invalidateIC2() {
		if (Info.isIc2Available() && getTile() instanceof IEnergyTile) {
			MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent((IEnergyTile) getTile()));
			IBlockState state = getWorld().getBlockState(getPos());
			getWorld().notifyBlockOfStateChange(getPos(), state.getBlock());
			addedToEnet = false;
		}
	}
}
