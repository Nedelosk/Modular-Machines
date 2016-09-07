package de.nedelosk.modularmachines.common.modules.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import de.nedelosk.modularmachines.api.ModularMachinesApi;
import de.nedelosk.modularmachines.api.energy.HeatLevel;
import de.nedelosk.modularmachines.api.energy.IHeatSource;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandlerTileEntity;
import de.nedelosk.modularmachines.api.modules.EnumModulePosition;
import de.nedelosk.modularmachines.api.modules.EnumWallType;
import de.nedelosk.modularmachines.api.modules.IModelInitHandler;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModuleProperties;
import de.nedelosk.modularmachines.api.modules.controller.IModuleController;
import de.nedelosk.modularmachines.api.modules.controller.ModuleControlled;
import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventory;
import de.nedelosk.modularmachines.api.modules.handlers.tank.FluidTankAdvanced;
import de.nedelosk.modularmachines.api.modules.handlers.tank.IModuleTank;
import de.nedelosk.modularmachines.api.modules.integration.IModuleJEI;
import de.nedelosk.modularmachines.api.modules.items.IModuleColored;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.models.IModelHandler;
import de.nedelosk.modularmachines.api.modules.models.ModelHandler;
import de.nedelosk.modularmachines.api.modules.models.ModelHandlerDefault;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.tools.IModuleTool;
import de.nedelosk.modularmachines.api.modules.tools.properties.IModuleBoilerProperties;
import de.nedelosk.modularmachines.common.core.FluidManager;
import de.nedelosk.modularmachines.common.modules.pages.BoilerPage;
import de.nedelosk.modularmachines.common.modules.pages.ControllerPage;
import de.nedelosk.modularmachines.common.modules.tools.jei.ModuleCategoryUIDs;
import de.nedelosk.modularmachines.common.modules.tools.jei.ModuleJeiPlugin;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketSyncHeatBuffer;
import de.nedelosk.modularmachines.common.network.packets.PacketSyncModule;
import de.nedelosk.modularmachines.common.utils.ModuleUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModuleBoiler extends ModuleControlled implements IModuleTool, IModuleColored, IModuleJEI, IModuleBoilerProperties {

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
			PacketHandler.sendToNetwork(new PacketSyncModule(handler, state), ((IModularHandlerTileEntity)handler).getPos(), (WorldServer) handler.getWorld());
		}
	}

	@Override
	public EnumModulePosition getPosition(IModuleContainer container) {
		return EnumModulePosition.SIDE;
	}

	@Override
	public EnumWallType getWallType(IModuleState state) {
		return EnumWallType.WINDOW;
	}

	@Override
	public String[] getJEIRecipeCategorys(IModuleContainer container) {
		return new String[]{ModuleCategoryUIDs.BOILER};
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
					if (heatSource.getHeatStored() >= ModularMachinesApi.BOILING_POINT){
						int waterCost = (heatLevel.getIndex() - 1) * getWaterPerWork(state);
						if (waterCost <= 0){
							return;
						}

						FluidStack water = tankWater.drainInternal(waterCost * 15, false);
						if (water == null){
							return;
						}

						waterCost = Math.min(waterCost, water.amount);
						FluidStack steam = new FluidStack(FluidManager.Steam, ModularMachinesApi.STEAM_PER_UNIT_WATER / 2 * waterCost);
						steam.amount = tankSteam.fillInternal(new FluidStack(FluidManager.Steam, ModularMachinesApi.STEAM_PER_UNIT_WATER / 2 * waterCost), false);

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
	public List<IModelInitHandler> getInitModelHandlers(IModuleContainer container) {
		List<IModelInitHandler> handlers = new ArrayList<>();
		handlers.add(new ModelHandlerDefault("boilers", container, ModelHandler.getModelLocation(container, "boilers", getSize(container))));
		return handlers;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModelHandler createModelHandler(IModuleState state) {
		return new ModelHandlerDefault("boilers", state.getContainer(), ModelHandler.getModelLocation(state.getContainer(), "boilers", getSize(state.getContainer())));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public ResourceLocation getWindowLocation(IModuleContainer container) {
		return ModelHandler.getModelLocation(container, "windows",  getSize(container));
	}

	@Override
	public void openJEI(IModuleState state){
		if(this instanceof IModuleJEI){
			Loader.instance();
			if(Loader.isModLoaded("JEI")){
				ModuleJeiPlugin.jeiRuntime.getRecipesGui().showCategories(Arrays.asList(((IModuleJEI)this).getJEIRecipeCategorys(state.getContainer())));
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
