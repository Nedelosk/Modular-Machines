package de.nedelosk.modularmachines.common.network.packets;

import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import io.netty.buffer.ByteBuf;

public abstract class PacketModule extends PacketModularHandler {

	protected int index;

	public PacketModule() {
	}

	public PacketModule(IModularHandler handler, IModuleState module) {
		this(handler, module.getIndex());
	}

	public PacketModule(IModularHandler handler, int index) {
		super(handler);
		this.index = index;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		index = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		buf.writeInt(index);
	}
}
