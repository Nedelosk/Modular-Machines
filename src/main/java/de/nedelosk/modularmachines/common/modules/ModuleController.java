package de.nedelosk.modularmachines.common.modules;

import java.util.Collections;
import java.util.List;

import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandlerTileEntity;
import de.nedelosk.modularmachines.api.modules.EnumModuleSizes;
import de.nedelosk.modularmachines.api.modules.IModelInitHandler;
import de.nedelosk.modularmachines.api.modules.IModuleProperties;
import de.nedelosk.modularmachines.api.modules.Module;
import de.nedelosk.modularmachines.api.modules.controller.EnumRedstoneMode;
import de.nedelosk.modularmachines.api.modules.controller.IModuleControlled;
import de.nedelosk.modularmachines.api.modules.controller.IModuleController;
import de.nedelosk.modularmachines.api.modules.items.IModuleColoredItem;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.models.IModelHandler;
import de.nedelosk.modularmachines.api.modules.models.ModelHandler;
import de.nedelosk.modularmachines.api.modules.models.ModelHandlerDefault;
import de.nedelosk.modularmachines.api.modules.position.EnumModulePositions;
import de.nedelosk.modularmachines.api.modules.position.IModulePositioned;
import de.nedelosk.modularmachines.api.modules.position.IModulePostion;
import de.nedelosk.modularmachines.api.modules.properties.IModuleControllerProperties;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.common.config.Config;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketSyncModule;
import de.nedelosk.modularmachines.common.utils.Translator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModuleController extends Module implements IModuleController, IModulePositioned, IModuleColoredItem {

	public ModuleController() {
		super("controller");
	}

	@Override
	public void sendModuleUpdate(IModuleState state){
		IModularHandler handler = state.getModular().getHandler();
		if(handler instanceof IModularHandlerTileEntity){
			PacketHandler.sendToNetwork(new PacketSyncModule(handler, state), ((IModularHandlerTileEntity)handler).getPos(), (WorldServer) handler.getWorld());
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
		ResourceLocation loc = ModelHandler.getModelLocation(state.getContainer(), "controllers", getSize(state.getContainer()));
		return new ModelHandlerDefault("controllers", state.getContainer(), loc);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public List<IModelInitHandler> getInitModelHandlers(IModuleContainer container) {
		ResourceLocation loc = ModelHandler.getModelLocation(container, "controllers", getSize(container));
		return Collections.singletonList(new ModelHandlerDefault("controllers", container, loc));
	}

	@Override
	public boolean canWork(IModuleState controllerState, IModuleState moduleState) {
		if(moduleState.getModule() instanceof IModuleControlled){
			IModuleControlled controlled = (IModuleControlled) moduleState.getModule();
			EnumRedstoneMode mode = controlled.getModuleControl(moduleState).getRedstoneMode();
			boolean hasSignal = hasRedstoneSignal(controllerState.getModular().getHandler());
			return mode == EnumRedstoneMode.IGNORE || hasSignal && mode == EnumRedstoneMode.ON || !hasSignal && mode == EnumRedstoneMode.OFF;
		}
		return true;
	}

	@Override
	public int getColor(IModuleContainer container) {
		return 0x751818;
	}

	private boolean hasRedstoneSignal(IModularHandler handler) {
		if(handler instanceof IModularHandlerTileEntity){
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
		return new IModulePostion[]{EnumModulePositions.CASING};
	}

	@Override
	public EnumModuleSizes getSize(IModuleContainer container) {
		return EnumModuleSizes.LARGE;
	}

	@Override
	public int getAllowedComplexity(IModuleContainer container) {
		IModuleProperties properties = container.getProperties();
		if(properties instanceof IModuleControllerProperties){
			return ((IModuleControllerProperties) properties).getAllowedComplexity(container);
		}
		return Config.defaultAllowedControllerComplexity;
	}
}
