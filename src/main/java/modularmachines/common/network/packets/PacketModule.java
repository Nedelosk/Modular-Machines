package modularmachines.common.network.packets;

import java.io.IOException;

import modularmachines.api.modules.IModuleGuiLogic;
import modularmachines.api.modules.IModuleLogic;
import modularmachines.api.modules.Module;
import modularmachines.api.modules.pages.ModulePage;
import modularmachines.common.containers.ContainerModuleLogic;
import modularmachines.common.network.PacketBufferMM;

public abstract class PacketModule extends PacketLocatable<IModuleLogic> {

	protected int index;
	protected int pageIndex;

	public PacketModule() {
	}
	
	public PacketModule(ContainerModuleLogic container) {
		this(container.getGuiLogic());
	}
	
	public PacketModule(IModuleGuiLogic logic) {
		this(logic.getCurrentModule(), logic.getCurrentPage());
	}
	
	public PacketModule(Module module) {
		this(module.getLogic(), module.getIndex(), -1);
	}

	public PacketModule(Module module, ModulePage page) {
		this(module.getLogic(), module.getIndex(), page.getIndex());
	}

	public PacketModule(IModuleLogic logic, int index, int pageIndex) {
		super(logic);
		this.index = index;
		this.pageIndex = pageIndex;
	}
	
	@Override
	protected void writeData(PacketBufferMM data) throws IOException {
		super.writeData(data);
		data.writeInt(index);
		data.writeInt(pageIndex);
	}

	protected static Module getModule(IModuleLogic logic, PacketBufferMM data) {
		if (logic == null) {
			return null;
		}
		return logic.getModule(data.readInt());
	}
	
	protected static ModulePage getPage(IModuleLogic logic, PacketBufferMM data) {
		Module module = getModule(logic, data);
		if(module == null){
			return null;
		}
		return module.getPage(data.readInt());
	}
}
