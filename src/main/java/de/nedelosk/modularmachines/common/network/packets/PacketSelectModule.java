package de.nedelosk.modularmachines.common.network.packets;

import java.io.IOException;

import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.network.DataInputStreamMM;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.common.core.ModularMachines;
import de.nedelosk.modularmachines.common.inventory.ContainerModular;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketSetSlot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketSelectModule extends PacketModule implements IPacketClient, IPacketServer {

	public PacketSelectModule() {
	}

	public PacketSelectModule(IModuleState module) {
		super(module);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void onPacketData(DataInputStreamMM data, EntityPlayer player) throws IOException {
		IModularHandler modularHandler = getModularHandler(player);
		IModuleState moduleState = getModule(modularHandler);
		if (modularHandler.getModular() != null && modularHandler.isAssembled()) {
			modularHandler.getModular().setCurrentModule(moduleState);
		}
	}

	@Override
	public void onPacketData(DataInputStreamMM data, EntityPlayerMP player) throws IOException {
		IModularHandler modularHandler = getModularHandler(player);
		BlockPos pos = getPos(modularHandler);
		if (modularHandler.getModular() != null && modularHandler.isAssembled()) {
			modularHandler.getModular().setCurrentModule(getModule(modularHandler));
		}
		WorldServer server = player.getServerWorld();
		PacketHandler.sendToNetwork(this, pos, server);
		for(EntityPlayer otherPlayer : server.playerEntities) {
			if (otherPlayer.openContainer instanceof ContainerModular) {
				ContainerModular assembler = (ContainerModular) otherPlayer.openContainer;
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
		return PacketId.SELECT_MODULE;
	}
}
