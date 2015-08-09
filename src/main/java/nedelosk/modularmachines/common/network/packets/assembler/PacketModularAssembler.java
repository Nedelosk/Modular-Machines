package nedelosk.modularmachines.common.network.packets.assembler;

import io.netty.buffer.ByteBuf;
import nedelosk.modularmachines.api.modular.module.ModuleEntry;
import nedelosk.modularmachines.common.ModularMachines;
import nedelosk.modularmachines.common.blocks.tile.TileModularAssembler;
import nedelosk.modularmachines.common.inventory.ContainerModularAssembler;
import nedelosk.modularmachines.common.inventory.ContainerModularAssemblerSlot;
import nedelosk.modularmachines.common.network.packets.saver.ModularSaveModule;
import nedelosk.nedeloskcore.common.network.packets.PacketTileEntity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.DimensionManager;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketModularAssembler extends PacketTileEntity<TileModularAssembler> implements IMessageHandler<PacketModularAssembler, IMessage> {

	public ModuleEntry entry;
	
	public PacketModularAssembler() {
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		if(buf.readBoolean())
		{
		NBTTagCompound nbt = ByteBufUtils.readTag(buf);
			entry = new ModuleEntry(nbt, getTileEntity(DimensionManager.getWorld(0)));
		}
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		buf.writeBoolean(entry != null);
		if(entry != null){
		NBTTagCompound nbt = new NBTTagCompound();
		entry.writeToNBT(nbt);
		ByteBufUtils.writeTag(buf, nbt);
		}
	}

	public PacketModularAssembler(TileModularAssembler tile, ModuleEntry entry) {
		super(tile);
		this.entry = entry;
	}
	
	public PacketModularAssembler(TileModularAssembler tile) {
		super(tile);
	}
	
	@Override
	public IMessage onMessage(PacketModularAssembler message, MessageContext ctx) {
	    	if(message.entry != null)
	    	{
			TileModularAssembler tile = message.getTileEntity(ctx.getServerHandler().playerEntity.worldObj);
			
			EntityPlayerMP entityPlayerMP = ctx.getServerHandler().playerEntity;
			if(entityPlayerMP.getExtendedProperties(ModularSaveModule.class.getName()) != null)
				((ModularSaveModule)entityPlayerMP.getExtendedProperties(ModularSaveModule.class.getName())).entry = message.entry;
			else
				entityPlayerMP.registerExtendedProperties(ModularSaveModule.class.getName(), new ModularSaveModule(message.entry));
			entityPlayerMP.openGui(ModularMachines.instance, 1, entityPlayerMP.worldObj, message.x, message.y, message.z);
	    	}
	    	else
	    	{
	    		TileModularAssembler tile = message.getTileEntity(ctx.getServerHandler().playerEntity.worldObj);
				
	    		
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
