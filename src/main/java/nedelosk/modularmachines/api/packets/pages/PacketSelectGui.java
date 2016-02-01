package nedelosk.modularmachines.api.packets.pages;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import nedelosk.forestcore.library.packets.PacketTileEntity;
import nedelosk.modularmachines.api.ModularMachinesApi;
import nedelosk.modularmachines.api.modular.basic.IModularInventory;
import nedelosk.modularmachines.api.modular.managers.IModularGuiManager;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.modules.gui.IModuleGui;
import nedelosk.modularmachines.api.packets.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PacketSelectGui extends PacketTileEntity<TileEntity> implements IMessageHandler<PacketSelectGui, IMessage> {

	public String UID;

	public PacketSelectGui() {
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		UID = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		ByteBufUtils.writeUTF8String(buf, UID);
	}

	public PacketSelectGui(TileEntity tile, IModuleGui gui) {
		super(tile);
		this.UID = gui.getUID();
	}

	public PacketSelectGui(TileEntity tile, String UID) {
		super(tile);
		this.UID = UID;
	}

	@Override
	public IMessage onMessage(PacketSelectGui message, MessageContext ctx) {
		World world;
		//if (ctx.side == Side.CLIENT) {
			//world = Minecraft.getMinecraft().theWorld;
		//} else {
			world = ctx.getServerHandler().playerEntity.worldObj;
		//}
		IModularTileEntity<IModularInventory> tile = (IModularTileEntity) message.getTileEntity(world);
		IModularGuiManager guiManager = tile.getModular().getGuiManager();
		guiManager.setCurrentGui(guiManager.getGui(message.UID));
		//if (ctx.side == Side.SERVER) {
			EntityPlayerMP entityPlayerMP = ctx.getServerHandler().playerEntity;
			//PacketHandler.INSTANCE.sendTo(message, entityPlayerMP);
			getWorld(ctx).markBlockForUpdate(message.x, message.y, message.z);
			ModularMachinesApi.handler.openGui(entityPlayerMP, 0, entityPlayerMP.worldObj, message.x, message.y, message.z);
		//}
		return null;
	}
}
