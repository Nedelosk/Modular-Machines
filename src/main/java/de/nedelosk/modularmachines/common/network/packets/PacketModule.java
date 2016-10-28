package de.nedelosk.modularmachines.common.network.packets;

import java.io.DataInputStream;
import java.io.IOException;

import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.IModulePage;
import de.nedelosk.modularmachines.api.modules.network.DataInputStreamMM;
import de.nedelosk.modularmachines.api.modules.network.DataOutputStreamMM;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraft.entity.player.EntityPlayer;

public abstract class PacketModule extends PacketModularHandler {

	protected int index;
	protected String pageId;

	public PacketModule() {
	}

	public PacketModule(IModuleState module) {
		this(module.getModular().getHandler(), module.getIndex(), null);
		IModulePage currentPage = module.getModular().getCurrentPage();
		if (currentPage.getModuleState().getIndex() == module.getIndex()) {
			pageId = currentPage.getPageID();
		}
	}

	public PacketModule(IModularHandler handler, int index, String pageId) {
		super(handler);
		this.index = index;
		this.pageId = pageId;
	}

	public IModuleState getModule(EntityPlayer player) {
		return getModule(getModularHandler(player));
	}

	public IModuleState getModule(IModularHandler handler) {
		if (handler == null || handler.getModular() == null) {
			return null;
		}
		return handler.getModular().getModule(index);
	}

	@Override
	public void readData(DataInputStreamMM data) throws IOException {
		super.readData(data);
		index = data.readInt();
		if (data.readBoolean()) {
			pageId = DataInputStream.readUTF(data);
		}
	}

	@Override
	protected void writeData(DataOutputStreamMM data) throws IOException {
		super.writeData(data);
		data.writeInt(index);
		data.writeBoolean(pageId != null);
		if (pageId != null) {
			data.writeUTF(pageId);
		}
	}
}
