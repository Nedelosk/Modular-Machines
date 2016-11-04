package modularmachines.common.modules;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import modularmachines.api.modular.IModular;
import modularmachines.api.modular.handlers.IModularHandler;
import modularmachines.api.modular.handlers.IModularHandlerTileEntity;
import modularmachines.api.modules.IModule;
import modularmachines.api.modules.IModuleModuleCleaner;
import modularmachines.api.modules.IModulePage;
import modularmachines.api.modules.ModuleManager;
import modularmachines.api.modules.containers.IModuleColoredItem;
import modularmachines.api.modules.containers.IModuleContainer;
import modularmachines.api.modules.containers.IModuleItemProvider;
import modularmachines.api.modules.controller.ModuleControlled;
import modularmachines.api.modules.handlers.IModuleContentHandler;
import modularmachines.api.modules.handlers.inventory.IModuleInventory;
import modularmachines.api.modules.position.EnumModulePositions;
import modularmachines.api.modules.position.IModulePositioned;
import modularmachines.api.modules.position.IModulePostion;
import modularmachines.api.modules.state.IModuleState;
import modularmachines.common.modules.pages.CleanerPage;
import modularmachines.common.modules.pages.ControllerPage;
import modularmachines.common.network.PacketHandler;
import modularmachines.common.network.packets.PacketSyncModule;
import net.minecraft.item.ItemStack;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModuleModuleCleaner extends ModuleControlled implements IModuleModuleCleaner, IModulePositioned, IModuleColoredItem {

	public ModuleModuleCleaner(String name) {
		super(name);
	}

	@Override
	public IModulePostion[] getValidPositions(IModuleContainer container) {
		return new IModulePostion[] { EnumModulePositions.CASING };
	}

	@Override
	public int getComplexity(IModuleContainer container) {
		return 1;
	}

	@Override
	public List<IModulePage> createPages(IModuleState state) {
		List<IModulePage> pages = super.createPages(state);
		pages.add(new CleanerPage(state));
		return pages;
	}

	@Override
	public void updateServer(IModuleState<IModule> state, int tickCount) {
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void updateClient(IModuleState<IModule> state, int tickCount) {
	}

	@Override
	public void cleanModule(IModuleState state) {
		IModuleInventory inventory = state.getPage(CleanerPage.class).getInventory();
		ItemStack stack = inventory.getStackInSlot(0);
		if (stack != null) {
			IModuleItemProvider provider = stack.getCapability(ModuleManager.MODULE_PROVIDER_CAPABILITY, null);
			if (provider != null) {
				Iterator<IModuleState> moduleStates = provider.iterator();
				while (moduleStates.hasNext()) {
					IModuleState moduleState = moduleStates.next();
					if (moduleState != null) {
						for(IModuleContentHandler handler : moduleState.getAllContentHandlers()) {
							if (handler.isCleanable()) {
								handler.cleanHandler(state);
							}
						}
					}
					if (moduleState.getModule().isClean(moduleState)) {
						moduleStates.remove();
					}
				}
			}
		}
	}

	@Override
	public void sendModuleUpdate(IModuleState state) {
		IModularHandler handler = state.getModular().getHandler();
		if (handler instanceof IModularHandlerTileEntity) {
			PacketHandler.sendToNetwork(new PacketSyncModule(state), ((IModularHandlerTileEntity) handler).getPos(), (WorldServer) handler.getWorld());
		}
	}

	@Override
	public int getColor(IModuleContainer container) {
		return 0x2E5D0E;
	}

	@Override
	public List<IModuleState> getUsedModules(IModuleState state) {
		List<IModuleState> modules = new ArrayList<>();
		IModular modular = state.getModular();
		for(IModuleState moduleState : modular.getModules()) {
			for(IModuleContentHandler handler : moduleState.getAllContentHandlers()) {
				if (handler != null && handler.isCleanable()) {
					modules.add(moduleState);
					break;
				}
			}
		}
		return modules;
	}

	@Override
	protected IModulePage getControllerPage(IModuleState state) {
		return new ControllerPage(state);
	}
}
