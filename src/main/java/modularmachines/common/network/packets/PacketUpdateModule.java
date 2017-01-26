package modularmachines.common.network.packets;

import java.io.IOException;

import modularmachines.api.modules.IModuleLogic;
import modularmachines.api.modules.Module;
import modularmachines.api.modules.pages.ModulePage;
import modularmachines.common.network.IStreamable;
import modularmachines.common.network.PacketBufferMM;
import modularmachines.common.network.PacketId;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketUpdateModule extends PacketModule{

	public PacketUpdateModule() {
	}
	
	public PacketUpdateModule(Module module) {
		super(module);
	}

	public PacketUpdateModule(Module module, ModulePage page) {
		super(module, page);
	}
	
	@Override
	protected void writeData(PacketBufferMM data) throws IOException {
		super.writeData(data);
		if (index > 0) {
			Module module = source.getModule(index);
			if (pageIndex > 0) {
				ModulePage page = module.getPage(pageIndex);
				if (page instanceof IStreamable) {
					((IStreamable) page).writeData(data);
				}
			} else {
				if (module instanceof IStreamable) {
					((IStreamable) module).writeData(data);
				}
			}
		}
	}
	
	public static final class Handler implements IPacketHandlerClient{

		@SideOnly(Side.CLIENT)
		@Override
		public void onPacketData(PacketBufferMM data, EntityPlayer player) throws IOException {
			int index = data.readInt();
			if (index > 0) {
				IModuleLogic logic = PacketLocatable.getLogic(data, player.world);
				Module module = logic.getModule(index);
				int pageIndex = data.readInt();
				if (pageIndex > 0) {
					ModulePage page = module.getPage(pageIndex);
					if (page instanceof IStreamable) {
						((IStreamable) page).readData(data);
					}
				} else {
					if (module instanceof IStreamable) {
						((IStreamable) module).readData(data);
					}
				}
			}
		}
	}

	@Override
	public PacketId getPacketId() {
		return PacketId.UPDATE_MODULE;
	}
}
