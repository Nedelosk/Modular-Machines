package nedelosk.modularmachines.api.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestcore.library.packets.PacketTileEntity;
import nedelosk.modularmachines.api.modular.basic.IModularDefault;
import nedelosk.modularmachines.api.modular.managers.IModularGuiManager;
import nedelosk.modularmachines.api.modular.managers.IModularInventoryManager;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PacketClientManagers extends PacketTileEntity<TileEntity> implements IMessageHandler<PacketClientManagers, IMessage> {

	public PacketClientManagers() {
	}

	public PacketClientManagers(TileEntity tile) {
		super(tile);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IMessage onMessage(PacketClientManagers message, MessageContext ctx) {
		World world = Minecraft.getMinecraft().theWorld;
		IModularTileEntity<IModularDefault> tile = (IModularTileEntity) message.getTileEntity(world);
		IModularInventoryManager inventoryManager = tile.getModular().getInventoryManager();
		IModularGuiManager guiManager = tile.getModular().getGuiManager();
		inventoryManager.searchForInventorys();
		guiManager.searchForGuis();
		return null;
	}
}
