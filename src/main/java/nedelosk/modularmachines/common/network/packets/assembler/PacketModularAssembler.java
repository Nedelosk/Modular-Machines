package nedelosk.modularmachines.common.network.packets.assembler;

import io.netty.buffer.ByteBuf;
import nedelosk.modularmachines.common.ModularMachines;
import nedelosk.modularmachines.common.blocks.tile.TileModularAssenbler;
import nedelosk.modularmachines.common.inventory.ContainerModularAssembler;
import nedelosk.modularmachines.common.inventory.ContainerModularAssemblerSlot;
import nedelosk.modularmachines.common.network.packets.saver.ModularSaveModule;
import nedelosk.nedeloskcore.common.network.packets.PacketTileEntity;
import net.minecraft.entity.player.EntityPlayerMP;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketModularAssembler extends PacketTileEntity<TileModularAssenbler> implements IMessageHandler<PacketModularAssembler, IMessage> {

	public int entryId;
	public boolean slotToAssembler;
	
	public PacketModularAssembler() {
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		entryId = buf.readInt();
		slotToAssembler = buf.readBoolean();
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		buf.writeInt(entryId);
		buf.writeBoolean(slotToAssembler);
	}

	public PacketModularAssembler(TileModularAssenbler tile, int entryId, boolean slotToAssembler) {
		super(tile);
		this.entryId = entryId;
		this.slotToAssembler = slotToAssembler;
	}
	
	public PacketModularAssembler(TileModularAssenbler tile) {
		super(tile);
		this.entryId = 0;
		this.slotToAssembler = true;
	}
	
	@Override
	public IMessage onMessage(PacketModularAssembler message, MessageContext ctx) {
	    	if(!message.slotToAssembler)
	    	{
			TileModularAssenbler tile = message.getTileEntity(ctx.getServerHandler().playerEntity.worldObj);
			
			EntityPlayerMP entityPlayerMP = ctx.getServerHandler().playerEntity;
			if(entityPlayerMP.getExtendedProperties(ModularSaveModule.class.getName()) != null)
				((ModularSaveModule)entityPlayerMP.getExtendedProperties(ModularSaveModule.class.getName())).entry = ((TileModularAssenbler)tile).getModuleEntry(tile.page, message.entryId);
			else
				entityPlayerMP.registerExtendedProperties(ModularSaveModule.class.getName(), new ModularSaveModule(((TileModularAssenbler)tile).getModuleEntry(tile.page, message.entryId)));
			entityPlayerMP.openGui(ModularMachines.instance, 1, entityPlayerMP.worldObj, message.x, message.y, message.z);
	    	}
	    	else
	    	{
	    		TileModularAssenbler tile = message.getTileEntity(ctx.getServerHandler().playerEntity.worldObj);
				
	    		
				if(ctx.getServerHandler().playerEntity.openContainer instanceof ContainerModularAssemblerSlot)
				{        
				EntityPlayerMP entityPlayerMP = ctx.getServerHandler().playerEntity;
				entityPlayerMP.getNextWindowId();
                entityPlayerMP.closeContainer();
                int windowId = entityPlayerMP.currentWindowId;
                entityPlayerMP.openContainer = new ContainerModularAssembler(tile, ctx.getServerHandler().playerEntity.inventory);
                entityPlayerMP.openContainer.windowId = windowId;
				}
	    	}
		
		return null;
	}

}
