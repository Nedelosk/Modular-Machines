package modularmachines.common.network.packets;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketSetSlot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modular.IModularAssembler;
import modularmachines.api.modular.handlers.IModularHandler;
import modularmachines.api.modules.network.DataInputStreamMM;
import modularmachines.api.modules.network.DataOutputStreamMM;
import modularmachines.api.modules.position.IStoragePosition;
import modularmachines.common.core.ModularMachines;
import modularmachines.common.inventory.ContainerAssembler;
import modularmachines.common.network.PacketHandler;

public class PacketSelectAssemblerPosition extends PacketModularHandler implements IPacketClient, IPacketServer {

	public int position;

	@Override
	public void readData(DataInputStreamMM data) throws IOException {
		super.readData(data);
		position = data.readShort();
	}

	@Override
	protected void writeData(DataOutputStreamMM data) throws IOException {
		super.writeData(data);
		data.writeShort(position);
	}

	public PacketSelectAssemblerPosition() {
	}

	public PacketSelectAssemblerPosition(IModularHandler handler, IStoragePosition position) {
		super(handler);
		IModularAssembler assembler = handler.getAssembler();
		this.position = assembler.getIndex(position);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void onPacketData(DataInputStreamMM data, EntityPlayer player) throws IOException {
		IModularHandler modularHandler = getModularHandler(player);
		if (modularHandler != null && modularHandler.getAssembler() != null && !modularHandler.isAssembled()) {
			IModularAssembler assembler = modularHandler.getAssembler();
			assembler.setSelectedPosition(assembler.getStoragePositions().get(position));
		}
	}

	@Override
	public void onPacketData(DataInputStreamMM data, EntityPlayerMP player) throws IOException {
		IModularHandler modularHandler = getModularHandler(player);
		BlockPos pos = getPos(modularHandler);
		if (modularHandler.getAssembler() != null && !modularHandler.isAssembled()) {
			IModularAssembler assembler = modularHandler.getAssembler();
			assembler.setSelectedPosition(assembler.getStoragePositions().get(position));
		}
		WorldServer server = player.getServerWorld();
		PacketHandler.sendToNetwork(this, pos, server);
		for (EntityPlayer otherPlayer : server.playerEntities) {
			if (otherPlayer.openContainer instanceof ContainerAssembler) {
				ContainerAssembler assembler = (ContainerAssembler) otherPlayer.openContainer;
				if (modularHandler == assembler.getHandler()) {
					ItemStack heldStack = null;
					if (otherPlayer.inventory.getItemStack() != null) {
						heldStack = otherPlayer.inventory.getItemStack();
						otherPlayer.inventory.setItemStack(null);
					}
					otherPlayer.openGui(ModularMachines.instance, 0, otherPlayer.worldObj, pos.getX(), pos.getY(), pos.getZ());
					if (heldStack != null) {
						otherPlayer.inventory.setItemStack(heldStack);
						((EntityPlayerMP) otherPlayer).connection.sendPacket(new SPacketSetSlot(-1, -1, heldStack));
					}
				}
			}
		}
	}

	@Override
	public PacketId getPacketId() {
		return PacketId.SELECT_ASSEMBLER_POSITION;
	}
}
