package modularmachines.common.modules;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modular.AssemblerException;
import modularmachines.api.modular.IModular;
import modularmachines.api.modular.IModularAssembler;
import modularmachines.api.modular.handlers.IModularHandler;
import modularmachines.api.modular.handlers.IModularHandlerTileEntity;
import modularmachines.api.modules.IModuleProperties;
import modularmachines.api.modules.Module;
import modularmachines.api.modules.ModuleManager;
import modularmachines.api.modules.containers.IModuleColoredItem;
import modularmachines.api.modules.containers.IModuleContainer;
import modularmachines.api.modules.containers.IModuleItemContainer;
import modularmachines.api.modules.controller.EnumRedstoneMode;
import modularmachines.api.modules.controller.IModuleControlled;
import modularmachines.api.modules.controller.IModuleController;
import modularmachines.api.modules.models.IModelHandler;
import modularmachines.api.modules.models.ModelHandlerDefault;
import modularmachines.api.modules.models.ModuleModelLoader;
import modularmachines.api.modules.position.EnumModulePositions;
import modularmachines.api.modules.position.IModulePositioned;
import modularmachines.api.modules.position.IModulePostion;
import modularmachines.api.modules.properties.IModuleControllerProperties;
import modularmachines.api.modules.state.IModuleState;
import modularmachines.api.modules.storage.IStorage;
import modularmachines.common.config.Config;
import modularmachines.common.modules.pages.MainPage;
import modularmachines.common.network.PacketHandler;
import modularmachines.common.network.packets.PacketSyncModule;
import modularmachines.common.utils.Translator;

public class ModuleController extends Module implements IModuleController, IModulePositioned, IModuleColoredItem {

	public ModuleController() {
		super("controller");
	}

	@Override
	public void sendModuleUpdate(IModuleState state) {
		IModularHandler handler = state.getModular().getHandler();
		if (handler instanceof IModularHandlerTileEntity) {
			PacketHandler.sendToNetwork(new PacketSyncModule(state), ((IModularHandlerTileEntity) handler).getPos(), (WorldServer) handler.getWorld());
		}
	}

	@Override
	public void addTooltip(List<String> tooltip, ItemStack stack, IModuleContainer container) {
		super.addTooltip(tooltip, stack, container);
		tooltip.add(Translator.translateToLocal("mm.module.allowed.complexity") + getAllowedComplexity(container));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModelHandler createModelHandler(IModuleState state) {
		IModuleItemContainer container = state.getContainer().getItemContainer();
		return new ModelHandlerDefault(ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), container.getMaterial().getName(), "controllers", container.getSize()));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Map<ResourceLocation, ResourceLocation> getModelLocations(IModuleItemContainer container) {
		return Collections.singletonMap(ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), container.getMaterial().getName(), "controllers", container.getSize()),
				ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), "default", "controllers", container.getSize()));
	}

	@Override
	public boolean canWork(IModuleState controllerState, IModuleState moduleState) {
		if (moduleState.getModule() instanceof IModuleControlled) {
			IModuleControlled controlled = (IModuleControlled) moduleState.getModule();
			EnumRedstoneMode mode = controlled.getModuleControl(moduleState).getRedstoneMode();
			boolean hasSignal = hasRedstoneSignal(controllerState.getModular().getHandler());
			return mode == EnumRedstoneMode.IGNORE || hasSignal && mode == EnumRedstoneMode.ON || !hasSignal && mode == EnumRedstoneMode.OFF;
		}
		return true;
	}

	@Override
	public void assembleModule(IModularAssembler assembler, IModular modular, IStorage storage, IModuleState state) throws AssemblerException {
		if (modular.getModules(IModuleController.class).size() > 1) {
			throw new AssemblerException(Translator.translateToLocal("modular.assembler.error.too.many.controllers"));
		}
	}

	@Override
	public void onModularAssembled(IModuleState state) {
		if (ModuleManager.getModulesWithPages(state.getModular()).isEmpty()) {
			state.addPage(new MainPage(null, state));
		}
	}

	@Override
	public int getColor(IModuleContainer container) {
		return 0x751818;
	}

	private boolean hasRedstoneSignal(IModularHandler handler) {
		if (handler instanceof IModularHandlerTileEntity) {
			IModularHandlerTileEntity tile = (IModularHandlerTileEntity) handler;
			for (EnumFacing direction : EnumFacing.VALUES) {
				BlockPos side = tile.getPos().offset(direction);
				EnumFacing dir = direction.getOpposite();
				World world = tile.getWorld();
				if (world.getRedstonePower(side, dir) > 0 || world.getStrongPower(side, dir) > 0) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public IModulePostion[] getValidPositions(IModuleContainer container) {
		return new IModulePostion[] { EnumModulePositions.CASING };
	}

	@Override
	public int getAllowedComplexity(IModuleContainer container) {
		IModuleProperties properties = container.getProperties();
		if (properties instanceof IModuleControllerProperties) {
			return ((IModuleControllerProperties) properties).getAllowedComplexity(container);
		}
		return Config.defaultAllowedControllerComplexity;
	}
}
