package modularmachines.common.modules.tools;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.energy.HeatLevel;
import modularmachines.api.energy.HeatManager;
import modularmachines.api.energy.IHeatSource;
import modularmachines.api.modular.IModular;
import modularmachines.api.modular.handlers.IModularHandler;
import modularmachines.api.modular.handlers.IModularHandlerTileEntity;
import modularmachines.api.modules.EnumWallType;
import modularmachines.api.modules.IModule;
import modularmachines.api.modules.IModulePage;
import modularmachines.api.modules.IModuleProperties;
import modularmachines.api.modules.containers.IModuleColoredItem;
import modularmachines.api.modules.containers.IModuleContainer;
import modularmachines.api.modules.containers.IModuleItemContainer;
import modularmachines.api.modules.controller.IModuleController;
import modularmachines.api.modules.controller.ModuleControlled;
import modularmachines.api.modules.handlers.inventory.IModuleInventory;
import modularmachines.api.modules.handlers.tank.FluidTankAdvanced;
import modularmachines.api.modules.handlers.tank.IModuleTank;
import modularmachines.api.modules.integration.IModuleJEI;
import modularmachines.api.modules.models.IModelHandler;
import modularmachines.api.modules.models.ModelHandlerDefault;
import modularmachines.api.modules.models.ModuleModelLoader;
import modularmachines.api.modules.position.EnumModulePositions;
import modularmachines.api.modules.position.IModulePositioned;
import modularmachines.api.modules.position.IModulePostion;
import modularmachines.api.modules.state.IModuleState;
import modularmachines.api.modules.tools.IModuleTool;
import modularmachines.api.modules.tools.properties.IModuleBoilerProperties;
import modularmachines.common.core.managers.FluidManager;
import modularmachines.common.modules.pages.BoilerPage;
import modularmachines.common.modules.pages.ControllerPage;
import modularmachines.common.network.PacketHandler;
import modularmachines.common.network.packets.PacketSyncHeatBuffer;
import modularmachines.common.network.packets.PacketSyncModule;
import modularmachines.common.plugins.jei.CategoryUIDs;
import modularmachines.common.plugins.jei.JeiPlugin;
import modularmachines.common.utils.ModuleUtil;

public class ModuleBoiler extends ModuleControlled implements IModuleTool, IModulePositioned, IModuleColoredItem, IModuleJEI, IModuleBoilerProperties {

	public ModuleBoiler() {
		super("boiler");
	}

	@Override
	public int getWaterPerWork(IModuleState state) {
		IModuleProperties properties = state.getModuleProperties();
		if (properties instanceof IModuleBoilerProperties) {
			return ((IModuleBoilerProperties) properties).getWaterPerWork(state);
		}
		return 0;
	}

	@Override
	public void sendModuleUpdate(IModuleState state) {
		IModularHandler handler = state.getModular().getHandler();
		if (handler instanceof IModularHandlerTileEntity) {
			PacketHandler.sendToNetwork(new PacketSyncHeatBuffer(handler), ((IModularHandlerTileEntity) handler).getPos(), (WorldServer) handler.getWorld());
			PacketHandler.sendToNetwork(new PacketSyncModule(state), ((IModularHandlerTileEntity) handler).getPos(), (WorldServer) handler.getWorld());
		}
	}

	@Override
	public IModulePostion[] getValidPositions(IModuleContainer container) {
		return new IModulePostion[] { EnumModulePositions.SIDE };
	}

	@Override
	public EnumWallType getWallType(IModuleState state) {
		return EnumWallType.WINDOW;
	}

	@Override
	public String[] getJEIRecipeCategorys(IModuleContainer container) {
		return new String[] { CategoryUIDs.BOILER };
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
		if (modular.updateOnInterval(20)) {
			if (inventory != null) {
				ModuleUtil.tryEmptyContainer(0, 1, inventory, tank.getTank(0));
				ModuleUtil.tryFillContainer(2, 3, inventory, tank.getTank(1));
			}
		}
		if (modular.updateOnInterval(10)) {
			if (controller == null || controller.getModule() == null || controller.getModule().canWork(controller, state)) {
				IHeatSource heatSource = modular.getHeatSource();
				HeatLevel heatLevel = heatSource.getHeatLevel();
				FluidStack waterStack = tankWater.getFluid();
				if (!tankWater.isEmpty() && waterStack != null && waterStack.amount > 0 && !tankSteam.isFull()) {
					if (heatSource.getHeatStored() >= HeatManager.BOILING_POINT) {
						int waterCost = (heatLevel.getIndex() - 1) * getWaterPerWork(state);
						if (waterCost <= 0) {
							return;
						}
						FluidStack water = tankWater.drainInternal(waterCost * 15, false);
						if (water == null) {
							return;
						}
						waterCost = Math.min(waterCost, water.amount);
						FluidStack steam = new FluidStack(FluidManager.STEAM, HeatManager.STEAM_PER_UNIT_WATER / 2 * waterCost);
						steam.amount = tankSteam.fillInternal(new FluidStack(FluidManager.STEAM, HeatManager.STEAM_PER_UNIT_WATER / 2 * waterCost), false);
						if (steam.amount > 0) {
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
		return Collections.singletonMap(ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), container.getMaterial().getName(), "boilers", container.getSize()),
				ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), "default", "boilers", container.getSize()));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public ResourceLocation getWindowLocation(IModuleItemContainer container) {
		return ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), container.getMaterial().getName(), "windows", container.getSize());
	}

	@Override
	public void openJEI(IModuleState state) {
		if (this instanceof IModuleJEI) {
			Loader.instance();
			if (Loader.isModLoaded("JEI")) {
				JeiPlugin.jeiRuntime.getRecipesGui().showCategories(Arrays.asList(((IModuleJEI) this).getJEIRecipeCategorys(state.getContainer())));
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
