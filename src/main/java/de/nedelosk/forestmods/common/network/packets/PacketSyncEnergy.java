package de.nedelosk.forestmods.common.network.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestcore.network.PacketTileEntity;
import de.nedelosk.forestmods.api.modular.tile.IModularTileEntity;
import de.nedelosk.forestmods.api.modules.storage.battery.IModuleBatterySaver;
import de.nedelosk.forestmods.api.utils.ModularUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PacketSyncEnergy extends PacketTileEntity<TileEntity> implements IMessageHandler<PacketSyncEnergy, IMessage> {

	private int energy;

	public PacketSyncEnergy() {
	}

	public <T extends TileEntity & IModularTileEntity> PacketSyncEnergy(T tile) {
		super(tile);
		this.energy = ModularUtils.getBatteryStack(tile.getModular()).getSaver().getStorage().getEnergyStored();
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		energy = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		buf.writeInt(energy);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IMessage onMessage(PacketSyncEnergy message, MessageContext ctx) {
		World world = Minecraft.getMinecraft().theWorld;
		TileEntity tile = message.getTileEntity(world);
		if (tile != null && ((TileEntity & IModularTileEntity) tile).getModular() != null
				&& ModularUtils.getBatteryStack(((TileEntity & IModularTileEntity) tile).getModular()) != null) {
			IModuleBatterySaver saver = ModularUtils.getBatteryStack(((TileEntity & IModularTileEntity) tile).getModular()).getSaver();
			saver.getStorage().setEnergyStored(message.energy);
		}
		return null;
	}
}
