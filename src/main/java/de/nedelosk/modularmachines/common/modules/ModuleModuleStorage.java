package de.nedelosk.modularmachines.common.modules;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularAssembler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandlerTileEntity;
import de.nedelosk.modularmachines.api.modules.EnumModuleSizes;
import de.nedelosk.modularmachines.api.modules.IModelInitHandler;
import de.nedelosk.modularmachines.api.modules.IModuleProperties;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.models.IModelHandler;
import de.nedelosk.modularmachines.api.modules.position.EnumStoragePositions;
import de.nedelosk.modularmachines.api.modules.position.IStoragePosition;
import de.nedelosk.modularmachines.api.modules.properties.IModuleModuleStorageProperties;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storage.IStorage;
import de.nedelosk.modularmachines.api.modules.storage.IStoragePage;
import de.nedelosk.modularmachines.api.modules.storage.StorageModule;
import de.nedelosk.modularmachines.api.modules.storage.module.IDefaultModuleStorage;
import de.nedelosk.modularmachines.api.modules.storage.module.IModuleModuleStorage;
import de.nedelosk.modularmachines.api.modules.storage.module.ModuleStorage;
import de.nedelosk.modularmachines.api.modules.storage.module.ModuleStoragePage;
import de.nedelosk.modularmachines.client.model.ModelHandlerDrawer;
import de.nedelosk.modularmachines.common.config.Config;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketSyncModule;
import de.nedelosk.modularmachines.common.utils.Translator;
import net.minecraft.item.ItemStack;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModuleModuleStorage extends StorageModule implements IModuleModuleStorage<ModuleStorage> {

	public ModuleModuleStorage() {
		super("moduleStorage");
	}

	@Override
	public void addTooltip(List<String> tooltip, ItemStack stack, IModuleContainer container) {
		tooltip.add(Translator.translateToLocal("mm.module.allowed.complexity") + getAllowedComplexity(container));
		super.addTooltip(tooltip, stack, container);
	}

	@Override
	public void sendModuleUpdate(IModuleState state){
		IModularHandler handler = state.getModular().getHandler();
		if(handler instanceof IModularHandlerTileEntity){
			PacketHandler.sendToNetwork(new PacketSyncModule(handler, state), ((IModularHandlerTileEntity)handler).getPos(), (WorldServer) handler.getWorld());
		}
	}

	protected ModelHandlerDrawer createModelHandler(IModuleContainer container){
		return new ModelHandlerDrawer(container);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModelHandler createModelHandler(IModuleState state) {
		return createModelHandler(state.getContainer());
	}

	@SideOnly(Side.CLIENT)
	@Override
	public List<IModelInitHandler> getInitModelHandlers(IModuleContainer container) {
		List<IModelInitHandler> handlers = new ArrayList<>();
		handlers.add(createModelHandler(container));
		return handlers;
	}

	@Override
	public int getAllowedComplexity(IModuleContainer container) {
		IModuleProperties properties = container.getProperties();
		if(properties instanceof IModuleModuleStorageProperties){
			return ((IModuleModuleStorageProperties)properties).getAllowedComplexity(container);
		}
		return Config.defaultAllowedStorageComplexity;
	}

	@Override
	public boolean isValidForPosition(IStoragePosition position, IModuleContainer container) {
		IModuleProperties properties = container.getProperties();
		if(properties instanceof IModuleModuleStorageProperties){
			return ((IModuleModuleStorageProperties)properties).isValidForPosition(position, container);
		}
		return false;
	}

	@Override
	public ModuleStorage createStorage(IModuleState state, IModular modular, IStoragePosition position) {
		return new ModuleStorage(modular, position, state, EnumModuleSizes.LARGE, true);
	}

	@Override
	public IStoragePage createPage(IModularAssembler assembler, IModular modular, IStorage storage, IModuleState state, IStoragePosition position) {
		if(storage instanceof IDefaultModuleStorage){
			return new ModuleStoragePage(assembler, (IDefaultModuleStorage) storage);
		}
		return new ModuleStoragePage(assembler, EnumModuleSizes.LARGE, position);
	}

	@Override
	public EnumStoragePositions getCurrentPosition(IModuleState state) {
		return null;
	}
}
