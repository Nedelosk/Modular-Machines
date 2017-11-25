package modularmachines.common.network.packets;

import javax.annotation.Nullable;
import java.io.IOException;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.container.IModuleContainer;
import modularmachines.api.modules.container.IModuleGuiLogic;
import modularmachines.api.modules.pages.PageComponent;
import modularmachines.common.network.PacketBufferMM;

public abstract class PacketModule extends PacketLocatable<IModuleContainer> {
	
	protected int index;
	
	public PacketModule() {
	}
	
	public PacketModule(IModuleGuiLogic logic) {
		this(logic.getCurrentModule(), logic.getCurrentComponent());
	}
	
	public PacketModule(IModule module) {
		this(module.getContainer(), module.getIndex());
	}
	
	public PacketModule(IModule module, PageComponent component) {
		this(module.getContainer(), module.getIndex());
	}
	
	public PacketModule(IModuleContainer provider, int index) {
		super(provider);
		this.index = index;
	}
	
	@Override
	protected void writeData(PacketBufferMM data) throws IOException {
		super.writeData(data);
		data.writeInt(index);
	}
	
	@Nullable
	protected static IModule getModule(@Nullable IModuleContainer provider, PacketBufferMM data) {
		if (provider == null) {
			return null;
		}
		return provider.getModule(data.readInt());
	}
	
	@Nullable
	protected static PageComponent getComponent(@Nullable IModuleContainer provider, PacketBufferMM data) {
		IModule module = getModule(provider, data);
		if (module == null) {
			return null;
		}
		return module.getComponent(data.readInt());
	}
}
