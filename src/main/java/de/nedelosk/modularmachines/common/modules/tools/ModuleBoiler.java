package de.nedelosk.modularmachines.common.modules.tools;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

import de.nedelosk.modularmachines.api.energy.HeatLevel;
import de.nedelosk.modularmachines.api.energy.HeatManager;
import de.nedelosk.modularmachines.api.energy.IHeatSource;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandlerTileEntity;
import de.nedelosk.modularmachines.api.modules.EnumWallType;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModuleProperties;
import de.nedelosk.modularmachines.api.modules.containers.IModuleColoredItem;
import de.nedelosk.modularmachines.api.modules.containers.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.containers.IModuleItemContainer;
import de.nedelosk.modularmachines.api.modules.controller.IModuleController;
import de.nedelosk.modularmachines.api.modules.controller.ModuleControlled;
import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventory;
import de.nedelosk.modularmachines.api.modules.handlers.tank.FluidTankAdvanced;
import de.nedelosk.modularmachines.api.modules.handlers.tank.IModuleTank;
import de.nedelosk.modularmachines.api.modules.integration.IModuleJEI;
import de.nedelosk.modularmachines.api.modules.models.IModelHandler;
import de.nedelosk.modularmachines.api.modules.models.ModelHandlerDefault;
import de.nedelosk.modularmachines.api.modules.models.ModuleModelLoader;
import de.nedelosk.modularmachines.api.modules.position.EnumModulePositions;
import de.nedelosk.modularmachines.api.modules.position.IModulePositioned;
import de.nedelosk.modularmachines.api.modules.position.IModulePostion;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.tools.IModuleTool;
import de.nedelosk.modularmachines.api.modules.tools.properties.IModuleBoilerProperties;
import de.nedelosk.modularmachines.common.core.FluidManager;
import de.nedelosk.modularmachines.common.modules.pages.BoilerPage;
import de.nedelosk.modularmachines.common.modules.pages.ControllerPage;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketSyncHeatBuffer;
import de.nedelosk.modularmachines.common.network.packets.PacketSyncModule;
import de.nedelosk.modularmachines.common.plugins.jei.CategoryUIDs;
import de.nedelosk.modularmachines.common.plugins.jei.JeiPlugin;
import de.nedelosk.modularmachines.common.utils.ModuleUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModuleBoiler extends ModuleControlled implements IModuleTool, IModulePositioned, IModuleColoredItem, IModuleJEI, IModuleBoilerProperties {

	public ModuleBoiler() {
		super("boiler");
	}

	@Override
	public int getWaterPerWork(IModuleState state) {
		IModuleProperties properties = state.getModuleProperties();
		if(properties instanceof IModuleBoilerProperties){
			return ((IModuleBoilerProperties) properties).getWaterPerWork(state);
		}
		return 0;
	}

	@Override
	public void sendModuleUpdate(IModuleState state){
		IModularHandler handler = state.getModular().getHandler();
		if(handler instanceof IModularHandlerTileEntity){
			PacketHandler.sendToNetwork(new PacketSyncHeatBuffer(handler), ((IModularHandlerTileEntity)handler).getPos(), (WorldServer) handler.getWorld());
			PacketHandler.sendToNetwork(new PacketSyncModule(state), ((IModularHandlerTileEntity)handler).getPos(), (WorldServer) handler.getWorld());
		}
	}

	@Override
	public IModulePostion[] getValidPositions(IModuleContainer container) {
		return new IModulePostion[]{EnumModulePositions.SIDE};
	}

	@Override
	public EnumWallType getWallType(IModuleState state) {
		return EnumWallType.WINDOW;
	}

	@Override
	public String[] getJEIRecipeCategorys(IModuleContainer container) {
		return new String[]{CategoryUIDs.BOILER};
	}

	@Override
	public void updateServer(IModuleState state, int tickCount) {
		IModular modular = state.getModular();
		Random rand = modular.getHandler().getWorld().rand;
		IModulePage page = state.getPage(BoilerPage.class);
		IModuleInventory inventory = page.getInventory();
		IModuleTank tank = page.getTank();
		FluidTankAdvanced tankWater = tank.getTank(0);
		FluidTankAdvanced tankSteam = tank.getTank(1);
		boolean needUpdate = false;
		IModuleState<IModuleController> controller = modular.getModule(IModuleController.class);

		if(modular.updateOnInterval(20)){
			if(inventory != null){
				ModuleUtil.tryEmptyContainer(0, 1, inventory, tank.getTank(0));
				ModuleUtil.tryFillContainer(2, 3, inventory, tank.getTank(1));
			}
		}
		if(modular.updateOnInterval(10)){
			if(controller == null || controller.getModule() == null || controller.getModule().canWork(controller, state)){
				IHeatSource heatSource = modular.getHeatSource();
				HeatLevel heatLevel = heatSource.getHeatLevel();

				FluidStack waterStack = tankWater.getFluid();
				if(!tankWater.isEmpty() && waterStack != null && waterStack.amount > 0 && !tankSteam.isFull()){
					if (heatSource.getHeatStored() >= HeatManager.BOILING_POINT){
						int waterCost = (heatLevel.getIndex() - 1) * getWaterPerWork(state);
						if (waterCost <= 0){
							return;
						}

						FluidStack water = tankWater.drainInternal(waterCost * 15, false);
						if (water == null){
							return;
						}

						waterCost = Math.min(waterCost, water.amount);
						FluidStack steam = new FluidStack(FluidManager.Steam, HeatManager.STEAM_PER_UNIT_WATER / 2 * waterCost);
						steam.amount = tankSteam.fillInternal(new FluidStack(FluidManager.Steam, HeatManager.STEAM_PER_UNIT_WATER / 2 * waterCost), false);

						if(steam.amount > 0){
							tankWater.drainInternal(waterCost * 15, true);
							tankSteam.fillInternal(steam, true);
							sendModuleUpdate(state);
						}
					}
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void updateClient(IModuleState<IModule> state, int tickCount) {
	}

	@Override
	public List<IModulePage> createPages(IModuleState state) {
		List<IModulePage> pages = super.createPages(state);
		pages.add(new BoilerPage(state));
		return pages;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModelHandler createModelHandler(IModuleState state) {
		IModuleItemContainer container = state.getContainer().getItemContainer();
		return new ModelHandlerDefault(ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), container.getMaterial().getName(), "boilers", container.getSize()));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Map<ResourceLocation, ResourceLocation> getModelLocations(IModuleItemContainer container) {
		return Collections.singletonMap(ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), container.getMaterial().getName(), "boilers", container.getSize()), ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), "default", "boilers", container.getSize()));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public ResourceLocation getWindowLocation(IModuleItemContainer container) {
		return ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), container.getMaterial().getName(), "windows",  container.getSize());
	}

	@Override
	public void openJEI(IModuleState state){
		if(this instanceof IModuleJEI){
			Loader.instance();
			if(Loader.isModLoaded("JEI")){
				JeiPlugin.jeiRuntime.getRecipesGui().showCategories(Arrays.asList(((IModuleJEI)this).getJEIRecipeCategorys(state.getContainer())));
			}
		}
	}

	@Override
	public int getColor(IModuleContainer container) {
		return 0xA287C1;
	}

	@Override
	protected IModulePage getControllerPage(IModuleState state) {
		return new ControllerPage(state);
	}
}
