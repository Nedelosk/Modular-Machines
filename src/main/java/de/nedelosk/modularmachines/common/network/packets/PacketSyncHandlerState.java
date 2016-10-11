package de.nedelosk.modularmachines.common.network.packets;

import java.io.IOException;

import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.network.DataInputStreamMM;
import de.nedelosk.modularmachines.api.modules.network.DataOutputStreamMM;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketSyncHandlerState extends PacketModularHandler implements IPacketClient, IPacketServer {

	private boolean isAssembled;

	public PacketSyncHandlerState() {
	}

	public PacketSyncHandlerState(IModularHandler handler, boolean isAssembled) {
		super(handler);
		this.isAssembled = isAssembled;
	}

	@Override
	public void readData(DataInputStreamMM data) throws IOException {
		super.readData(data);
		isAssembled = data.readBoolean();
	}

	@Override
	protected void writeData(DataOutputStreamMM data) throws IOException {
		super.writeData(data);
		data.writeBoolean(isAssembled);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void onPacketData(DataInputStreamMM data, EntityPlayer player) throws IOException {
		IModularHandler modularHandler = getModularHandler(player);
		if(modularHandler != null){
			if(isAssembled){
				if(modularHandler.getAssembler() != null){
					modularHandler.getAssembler().assemble(player);
				}
			}else{
				if(modularHandler.getModular() != null){
					modularHandler.getModular().disassemble(player);
				}
			}
		}
	}

	@Override
	public void onPacketData(DataInputStreamMM data, EntityPlayerMP player) throws IOException {
		IModularHandler modularHandler = getModularHandler(player);

		if(modularHandler != null){
			if(isAssembled){
				if(modularHandler.getAssembler() != null){
					modularHandler.getAssembler().assemble(player);
				}
			}else{
				if(modularHandler.getModular() != null){
					modularHandler.getModular().disassemble(player);
				}
			}
		}
	}

	@Override
	public PacketId getPacketId() {
		return PacketId.SYNC_HANDLER_STATE;
	}

}
