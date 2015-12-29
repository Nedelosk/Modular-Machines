package nedelosk.modularmachines.api.packets;

import io.netty.buffer.ByteBuf;
import nedelosk.forestcore.library.packets.PacketTileEntity;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.producers.machines.IProducerMachine;
import nedelosk.modularmachines.api.producers.machines.recipe.IProducerMachineRecipeMode;
import nedelosk.modularmachines.api.recipes.IMachineMode;
import nedelosk.modularmachines.api.utils.ModuleStack;
import nedelosk.modularmachines.api.utils.ModuleUtils;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketSwitchMode extends PacketTileEntity<TileEntity> implements IMessageHandler<PacketSwitchMode, IMessage> {

	public int mode;

	public PacketSwitchMode() {
		super();
	}
	
	public PacketSwitchMode(TileEntity tile, IMachineMode mode) {
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
	public IMessage onMessage(PacketSwitchMode message, MessageContext ctx) {
		try{
			IModularTileEntity<IModular> tile = (IModularTileEntity<IModular>) message.getTileEntity(ctx.getServerHandler().playerEntity.worldObj);
			if(tile.getModular() != null){
				ModuleStack<IModule, IProducerMachine> machineStack = ModuleUtils.getModuleStackMachine(tile.getModular());
				if(machineStack != null){
					IProducerMachineRecipeMode machine = (IProducerMachineRecipeMode) machineStack.getProducer();
					machine.setMode(machine.getModeClass().getEnumConstants()[message.mode]);
					getWorld(ctx).markBlockForUpdate(message.x, message.y, message.z);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
