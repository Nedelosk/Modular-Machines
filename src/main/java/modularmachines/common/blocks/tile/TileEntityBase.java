package modularmachines.common.blocks.tile;

import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public abstract class TileEntityBase extends TileEntity implements ITickable {
	
	@Override
	public void update() {
		if (world.isRemote) {
			updateClient();
		} else {
			updateServer();
		}
	}
	
	public abstract void updateClient();
	
	public abstract void updateServer();
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(pos, 0, getUpdateTag());
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
		world.markBlockRangeForRenderUpdate(pos, pos);
		handleUpdateTag(packet.getNbtCompound());
	}
}
