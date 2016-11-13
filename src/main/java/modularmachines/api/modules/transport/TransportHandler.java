package modularmachines.api.modules.transport;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import modularmachines.api.modular.handlers.IModularHandler;
import modularmachines.api.modular.handlers.IModularHandlerTileEntity;
import modularmachines.api.modules.IModulePage;
import modularmachines.api.modules.state.IModuleState;

public abstract class TransportHandler<H, T extends ITransportCycle<H>> implements ITransportHandler<H, T> {

	protected final int allowedComplexit;
	protected final Class<? extends H> handlerClass;

	public TransportHandler(Class<? extends H> handlerClass, int allowedComplexit) {
		this.allowedComplexit = allowedComplexit;
		this.handlerClass = handlerClass;
	}

	protected abstract H getHandler(TileEntity tileEntity, EnumFacing facing);

	@Override
	public List<ITransportHandlerWrapper<H>> getHandlers(IModuleState<IModuleTansport> moduleState) {
		List<ITransportHandlerWrapper<H>> handlers = new ArrayList<>();
		IModularHandler modularHandler = moduleState.getModular().getHandler();
		if (modularHandler instanceof IModularHandlerTileEntity) {
			IModularHandlerTileEntity tileHandler = (IModularHandlerTileEntity) modularHandler;
			BlockPos pos = tileHandler.getPos();
			World world = modularHandler.getWorld();
			for (EnumFacing facing : EnumFacing.VALUES) {
				TileEntity tileEntity = world.getTileEntity(pos.offset(facing));
				H handler = getHandler(tileEntity, facing);
				if (handler != null) {
					handlers.add(new TransportHandlerWrapper(handler, tileEntity, facing));
				}
			}
		}
		for (IModuleState state : modularHandler.getModular().getModules()) {
			{
				H handler = state.getContentHandler(getHandlerClass());
				if (handler != null) {
					handlers.add(new TransportHandlerWrapper(handler, state, null));
				}
			}
			for (IModulePage modulePage : (List<IModulePage>) state.getPages()) {
				if (modulePage != null) {
					H handler = modulePage.getContentHandler(getHandlerClass());
					if (handler != null) {
						handlers.add(new TransportHandlerWrapper(handler, state, modulePage));
					}
				}
			}
		}
		return handlers;
	}

	@Override
	public Class<? extends H> getHandlerClass() {
		return handlerClass;
	}

	@Override
	public int getAllowedComplexity() {
		return allowedComplexit;
	}
}
