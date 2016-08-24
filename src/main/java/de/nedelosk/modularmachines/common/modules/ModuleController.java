package de.nedelosk.modularmachines.common.modules;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandlerTileEntity;
import de.nedelosk.modularmachines.api.modules.IModelInitHandler;
import de.nedelosk.modularmachines.api.modules.Module;
import de.nedelosk.modularmachines.api.modules.items.IModuleColored;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.models.IModelHandler;
import de.nedelosk.modularmachines.api.modules.models.ModelHandler;
import de.nedelosk.modularmachines.api.modules.models.ModelHandlerDefault;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storaged.EnumModulePosition;
import de.nedelosk.modularmachines.api.modules.storaged.EnumModuleSize;
import de.nedelosk.modularmachines.api.modules.storaged.IModuleController;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketSyncModule;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModuleController extends Module implements IModuleController, IModuleColored {

	protected final int allowedComplexity;

	public ModuleController(int allowedComplexity) {
		super("controller");
		this.allowedComplexity = allowedComplexity;
	}

	@Override
	public void sendModuleUpdate(IModuleState state){
		IModularHandler handler = state.getModular().getHandler();
		if(handler instanceof IModularHandlerTileEntity){
			PacketHandler.sendToNetwork(new PacketSyncModule(handler, state), ((IModularHandlerTileEntity)handler).getPos(), (WorldServer) handler.getWorld());
		}
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
	public int getComplexity(IModuleContainer container) {
		return 1;
	}

	@Override
	public int getAllowedComplexity(IModuleContainer container) {
		return allowedComplexity;
	}
}
