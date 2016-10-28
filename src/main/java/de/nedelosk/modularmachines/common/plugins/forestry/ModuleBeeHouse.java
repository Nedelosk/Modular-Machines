package de.nedelosk.modularmachines.common.plugins.forestry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModulePage;
import de.nedelosk.modularmachines.api.modules.ITickable;
import de.nedelosk.modularmachines.api.modules.containers.IModuleItemContainer;
import de.nedelosk.modularmachines.api.modules.handlers.IModuleContentHandler;
import de.nedelosk.modularmachines.api.modules.models.IModelHandler;
import de.nedelosk.modularmachines.api.modules.models.ModelHandlerDefault;
import de.nedelosk.modularmachines.api.modules.models.ModuleModelLoader;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storage.StorageModule;
import de.nedelosk.modularmachines.common.plugins.forestry.handlers.BeeHouseHandler;
import de.nedelosk.modularmachines.common.plugins.forestry.handlers.ModuleBeeListener;
import de.nedelosk.modularmachines.common.plugins.forestry.handlers.ModuleInventoryBeeHousing;
import de.nedelosk.modularmachines.common.plugins.forestry.pages.BeeHousePage;
import de.nedelosk.modularmachines.common.plugins.forestry.pages.FrameHousingPage;
import forestry.api.apiculture.DefaultBeeListener;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.apiculture.IBeekeepingLogic;
import forestry.apiculture.ApiaryBeeModifier;
import forestry.apiculture.BeehouseBeeModifier;
import forestry.apiculture.tiles.TileBeeHousingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModuleBeeHouse extends StorageModule implements ITickable {

	public final boolean isApiary;

	public ModuleBeeHouse(String name, boolean isApiary) {
		super(name);
		this.isApiary = isApiary;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModelHandler createModelHandler(IModuleState state) {
		IModuleItemContainer container = state.getContainer().getItemContainer();
		ResourceLocation location = ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), container.getMaterial().getName(), name, container.getSize());
		return new ModelHandlerDefault(location);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Map<ResourceLocation, ResourceLocation> getModelLocations(IModuleItemContainer container) {
		Map<ResourceLocation, ResourceLocation> locations = new HashMap<>();
		locations.put(ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), container.getMaterial().getName(), name, container.getSize()),
				ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), container.getMaterial().getName(), name, container.getSize()));
		return locations;
	}

	@Override
	public List<IModuleContentHandler> createHandlers(IModuleState state) {
		List<IModuleContentHandler> handlers = super.createHandlers(state);
		IModulePage page = state.getPage(BeeHousePage.class);
		handlers.add(new BeeHouseHandler(state, new ModuleInventoryBeeHousing(page.getInventory())));
		return handlers;
	}

	@Override
	public void onModularAssembled(IModuleState state) {
		BeeHouseHandler housing = state.getContentHandler(BeeHouseHandler.class);
		if (isApiary) {
			IModulePage page = state.getPage(FrameHousingPage.class);
			housing.init(new ApiaryBeeModifier(), new ModuleBeeListener(housing, housing.getBeeInventory(), page.getInventory()));
		} else {
			housing.init(new BeehouseBeeModifier(), new DefaultBeeListener());
		}
	}

	@Override
	public List<IModulePage> createPages(IModuleState state) {
		List<IModulePage> pages = super.createPages(state);
		pages.add(new BeeHousePage(name, state));
		if (isApiary) {
			pages.add(new FrameHousingPage("frame_housing", state));
		}
		return pages;
	}

	@Override
	public void updateServer(IModuleState<IModule> state, int tickCount) {
		BeeHouseHandler housing = state.getContentHandler(BeeHouseHandler.class);
		IBeekeepingLogic beeLogic = housing.getBeekeepingLogic();
		if (beeLogic.canWork()) {
			beeLogic.doWork();
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void updateClient(IModuleState<IModule> state, int tickCount) {
		IBeeHousing housing = state.getContentHandler(BeeHouseHandler.class);
		IBeekeepingLogic beeLogic = housing.getBeekeepingLogic();
		IModular modular = state.getModular();
		if (beeLogic.canDoBeeFX() && modular.updateOnInterval(4)) {
			beeLogic.doBeeFX();
			if (modular.updateOnInterval(50)) {
				TileBeeHousingBase.doPollenFX(housing.getWorldObj(), housing.getCoordinates().getX(), housing.getCoordinates().getY(), housing.getCoordinates().getZ());
			}
		}
	}
}
