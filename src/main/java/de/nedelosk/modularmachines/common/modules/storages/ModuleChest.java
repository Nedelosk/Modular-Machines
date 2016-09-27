package de.nedelosk.modularmachines.common.modules.storages;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularAssembler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandlerTileEntity;
import de.nedelosk.modularmachines.api.modules.EnumModuleSizes;
import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.models.IModelHandler;
import de.nedelosk.modularmachines.api.modules.models.ModelHandlerDefault;
import de.nedelosk.modularmachines.api.modules.models.ModuleModelLoader;
import de.nedelosk.modularmachines.api.modules.position.EnumModulePositions;
import de.nedelosk.modularmachines.api.modules.position.EnumStoragePositions;
import de.nedelosk.modularmachines.api.modules.position.IModulePositioned;
import de.nedelosk.modularmachines.api.modules.position.IModulePostion;
import de.nedelosk.modularmachines.api.modules.position.IStoragePosition;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storage.BasicStoragePage;
import de.nedelosk.modularmachines.api.modules.storage.IStorage;
import de.nedelosk.modularmachines.api.modules.storage.IStoragePage;
import de.nedelosk.modularmachines.api.modules.storage.Storage;
import de.nedelosk.modularmachines.api.modules.storage.StorageModule;
import de.nedelosk.modularmachines.common.modules.pages.ChestPage;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketSyncModule;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModuleChest extends StorageModule implements IModulePositioned {

	public ModuleChest(String name) {
		super(name);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModelHandler createModelHandler(IModuleState state) {
		IModuleContainer container = state.getContainer();
		return new ModelHandlerDefault(ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), container.getMaterial().getName(), name, getSize(container)));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Map<ResourceLocation, ResourceLocation> getModelLocations(IModuleContainer container) {
		return Collections.singletonMap(ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), container.getMaterial().getName(), name, getSize(container)), ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), "default", name, getSize(container)));
	}

	@Override
	protected IStoragePosition[] getPositions(IModuleContainer container) {
		return new IStoragePosition[] {EnumStoragePositions.LEFT, EnumStoragePositions.RIGHT, EnumStoragePositions.BACK};
	}

	@Override
	public void sendModuleUpdate(IModuleState state){
		IModularHandler handler = state.getModular().getHandler();
		if(handler instanceof IModularHandlerTileEntity){
			PacketHandler.sendToNetwork(new PacketSyncModule(handler, state), ((IModularHandlerTileEntity)handler).getPos(), (WorldServer) handler.getWorld());
		}
	}

	@Override
	public List<IModulePage> createPages(IModuleState state) {
		List<IModulePage> pages = super.createPages(state);
		pages.add(new ChestPage(state));
		return pages;
	}

	@Override
	public IStorage createStorage(IModuleState state, IModular modular, IStoragePosition position) {
		return new Storage(modular, position, state);
	}

	@Override
	public IStoragePage createPage(IModularAssembler assembler, IModular modular, IStorage storage, IModuleState state, IStoragePosition position) {
		if(storage != null){
			return new BasicStoragePage(assembler, storage);
		}
		return new BasicStoragePage(assembler, position);
	}
	
	@Override
	public IModulePostion[] getValidPositions(IModuleContainer container) {
		return new IModulePostion[]{EnumModulePositions.CASING};
	}
}