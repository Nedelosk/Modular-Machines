package de.nedelosk.forestmods.common.network.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestcore.network.PacketTileEntity;
import de.nedelosk.forestmods.api.modular.basic.IModularDefault;
import de.nedelosk.forestmods.api.modular.managers.IModularGuiManager;
import de.nedelosk.forestmods.api.modular.managers.IModularInventoryManager;
import de.nedelosk.forestmods.api.modular.tile.IModularTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PacketSyncManagers extends PacketTileEntity<TileEntity> implements IMessageHandler<PacketSyncManagers, IMessage> {

	public PacketSyncManagers() {
	}

	public PacketSyncManagers(TileEntity tile) {
		super(tile);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IMessage onMessage(PacketSyncManagers message, MessageContext ctx) {
		World world = Minecraft.getMinecraft().theWorld;
		IModularTileEntity<IModularDefault> tile = (IModularTileEntity) message.getTileEntity(world);
		IModularInventoryManager inventoryManager = tile.getModular().getInventoryManager();
		IModularGuiManager guiManager = tile.getModular().getGuiManager();
		inventoryManager.addInventorys();
		guiManager.addGuis();
		return null;
	}
}
