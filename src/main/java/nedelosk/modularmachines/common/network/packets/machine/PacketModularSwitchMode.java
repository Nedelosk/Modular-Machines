package nedelosk.modularmachines.common.network.packets.machine;

import io.netty.buffer.ByteBuf;
import nedelosk.forestday.common.network.packets.PacketTileEntity;
import nedelosk.modularmachines.api.modular.module.basic.IModule;
import nedelosk.modularmachines.api.modular.module.basic.basic.IMachineMode;
import nedelosk.modularmachines.api.modular.module.tool.producer.machine.IProducerMachine;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.api.modular.utils.ModuleUtils;
import nedelosk.modularmachines.common.ModularMachines;
import nedelosk.modularmachines.common.blocks.tile.TileModular;
import nedelosk.modularmachines.common.modular.module.tool.producer.machine.ProducerMachineRecipeMode;
import net.minecraft.entity.player.EntityPlayerMP;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketModularSwitchMode extends PacketTileEntity<TileModular> implements IMessageHandler<PacketModularSwitchMode, IMessage> {

	public int mode;

	public PacketModularSwitchMode() {
		super();
	}
	
	public PacketModularSwitchMode(TileModular tile, IMachineMode mode) {
		super(tile);
		this.mode = mode.ordinal();
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		mode = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		buf.writeInt(mode);
	}

	@Override
	public IMessage onMessage(PacketModularSwitchMode message, MessageContext ctx) {
		try{
			TileModular tile = message.getTileEntity(ctx.getServerHandler().playerEntity.worldObj);
			if(tile.getModular() != null){
				ModuleStack<IModule, IProducerMachine> machineStack = ModuleUtils.getModuleStackMachine(tile.getModular());
				if(machineStack != null){
					ProducerMachineRecipeMode machine = (ProducerMachineRecipeMode) machineStack.getProducer();
					machine.mode = machine.getModeClass().getEnumConstants()[message.mode];
					getWorld(ctx).markBlockForUpdate(message.x, message.y, message.z);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
