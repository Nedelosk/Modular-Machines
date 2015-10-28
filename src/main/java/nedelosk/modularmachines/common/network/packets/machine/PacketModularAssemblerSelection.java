package nedelosk.modularmachines.common.network.packets.machine;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.Point;

import com.google.common.collect.Lists;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import nedelosk.forestday.common.inventory.ContainerBase;
import nedelosk.forestday.common.network.packets.PacketTileEntity;
import nedelosk.modularmachines.api.modular.machines.basic.AssemblerMachineInfo;
import nedelosk.modularmachines.client.gui.assembler.GuiModularAssembler;
import nedelosk.modularmachines.common.blocks.tile.TileModularAssembler;
import nedelosk.modularmachines.common.inventory.assembler.ContainerModularAssembler;
import nedelosk.modularmachines.common.network.packets.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.world.WorldServer;

public class PacketModularAssemblerSelection extends PacketTileEntity<TileModularAssembler>
		implements IMessageHandler<PacketModularAssemblerSelection, IMessage> {

	public int activeSlots;
	public AssemblerMachineInfo info;

	public PacketModularAssemblerSelection() {
	}

	public PacketModularAssemblerSelection(TileModularAssembler assembler, AssemblerMachineInfo info, int activeSlots) {
		super(assembler);
		this.activeSlots = activeSlots;
		this.info = info;
	}

	@Override
	public IMessage onMessage(PacketModularAssemblerSelection message, MessageContext ctx) {

		if (ctx.side == Side.CLIENT)
			message.handleClientSide(ctx.getClientHandler());
		else
			message.handleServerSide(ctx.getServerHandler());
		return null;
	}

	public void handleClientSide(NetHandlerPlayClient netHandler) {
		Container container = Minecraft.getMinecraft().thePlayer.openContainer;
		if (container instanceof ContainerModularAssembler) {
			((ContainerModularAssembler) container).setToolSelection(info, activeSlots);
			if (Minecraft.getMinecraft().currentScreen instanceof GuiModularAssembler) {
				((GuiModularAssembler) Minecraft.getMinecraft().currentScreen).onToolSelectionPacket(this);
			}
		}
	}

	public void handleServerSide(NetHandlerPlayServer netHandler) {
		Container container = netHandler.playerEntity.openContainer;
		if (container instanceof ContainerModularAssembler) {
			((ContainerModularAssembler) container).setToolSelection(info, activeSlots);

			WorldServer server = netHandler.playerEntity.getServerForPlayer();
			for (EntityPlayer player : (ArrayList<EntityPlayer>) server.playerEntities) {
				if (player == netHandler.playerEntity)
					continue;
				if (player.openContainer instanceof ContainerModularAssembler) {
					if (((ContainerBase) container).sameGui((ContainerBase) player.openContainer)) {
						((ContainerModularAssembler) player.openContainer).setToolSelection(info, activeSlots);
						PacketHandler.INSTANCE.sendTo(this, (EntityPlayerMP) player);
					}
				}
			}
		}
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		if (buf.readBoolean()) {
			info = new AssemblerMachineInfo();
			if (buf.readBoolean()) {
				info.machine = ByteBufUtils.readItemStack(buf);
			}
			List<Point> positions = Lists.newArrayList();
			int points = buf.readShort();
			if (points > 0) {
				for (int i = 0; i < points; i++) {
					positions.add(new Point(buf.readInt(), buf.readInt()));
				}
			}
			info.positions = positions;

		}

		activeSlots = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(info != null);
		if (info != null) {
			buf.writeBoolean(info.machine != null);
			if (info.machine != null) {
				ByteBufUtils.writeItemStack(buf, info.machine);
			}

			if (info.positions != null) {
				buf.writeShort(info.positions.size());
				for (Point point : info.positions) {
					if (point != null) {
						buf.writeInt(point.getX());
						buf.writeInt(point.getY());
					}
				}
			} else
				buf.writeShort(0);
		}

		buf.writeInt(activeSlots);
	}

}
