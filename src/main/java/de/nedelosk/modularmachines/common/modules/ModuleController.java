package de.nedelosk.modularmachines.common.modules;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandlerTileEntity;
import de.nedelosk.modularmachines.api.modules.EnumModulePosition;
import de.nedelosk.modularmachines.api.modules.EnumModuleSize;
import de.nedelosk.modularmachines.api.modules.IModelInitHandler;
import de.nedelosk.modularmachines.api.modules.IModuleController;
import de.nedelosk.modularmachines.api.modules.IModuleControllerProperties;
import de.nedelosk.modularmachines.api.modules.IModuleProperties;
import de.nedelosk.modularmachines.api.modules.Module;
import de.nedelosk.modularmachines.api.modules.items.IModuleColored;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.models.IModelHandler;
import de.nedelosk.modularmachines.api.modules.models.ModelHandler;
import de.nedelosk.modularmachines.api.modules.models.ModelHandlerDefault;
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

public class ModuleController extends Module implements IModuleController, IModuleColored {

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
		List<IModelInitHandler> handlers = new ArrayList<>();
		ResourceLocation loc = ModelHandler.getModelLocation(container, "controllers", getSize(container));
		handlers.add(new ModelHandlerDefault("controllers", container, loc));
		return handlers;
	}

	@Override
	public boolean canWork(IModuleState controllerState, IModuleState moduleState) {
		return !hasRedstoneSignal(controllerState.getModular().getHandler());
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
	public EnumModulePosition getPosition(IModuleContainer container) {
		return EnumModulePosition.INTERNAL;
	}

	@Override
	public EnumModuleSize getSize(IModuleContainer container) {
		return EnumModuleSize.LARGE;
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
