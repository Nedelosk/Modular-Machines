package de.nedelosk.modularmachines.common.network.packets;

import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandlerTileEntity;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.state.IModuleStateClient;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketSyncModule extends PacketModule implements IMessageHandler<PacketSyncModule, IMessage> {

	private NBTTagCompound nbt;

	public PacketSyncModule() {
	}

	public PacketSyncModule(IModularHandler handler, IModuleState module) {
		this(handler, module.getIndex());
	}

	public PacketSyncModule(IModularHandler handler, int index) {
		super(handler, index);
		this.nbt = handler.getModular().getModule(index).serializeNBT();
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		nbt = ByteBufUtils.readTag(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		ByteBufUtils.writeTag(buf, nbt);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IMessage onMessage(PacketSyncModule message, MessageContext ctx) {
		World world = Minecraft.getMinecraft().theWorld;
		IModularHandler handler = message.getModularHandler(ctx);
		if (handler == null || handler.getModular() == null) {
			return null;
		}
		IModuleState moduleState = handler.getModular().getModule(message.index);
		moduleState.deserializeNBT(message.nbt);

		if(moduleState.getModule().needHandlerReload((IModuleStateClient) moduleState)){
			((IModuleStateClient)moduleState).getModelHandler().setNeedReload(true);
			if(handler instanceof IModularHandlerTileEntity){
				BlockPos pos = ((IModularHandlerTileEntity) handler).getPos();
				world.markBlockRangeForRenderUpdate(pos, pos);
			}
		}
		return null;
	}
}
