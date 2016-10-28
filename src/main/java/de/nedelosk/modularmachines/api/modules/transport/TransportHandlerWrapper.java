package de.nedelosk.modularmachines.api.modules.transport;

import de.nedelosk.modularmachines.api.modules.IModulePage;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public class TransportHandlerWrapper<H> implements ITransportHandlerWrapper<H> {

	protected final H handler;
	protected final IModulePage modulePage;
	protected final IModuleState moduleState;
	protected final TileEntity tileEntity;
	protected final EnumFacing facing;

	public TransportHandlerWrapper(H handler, IModuleState moduleState, IModulePage modulePage) {
		this(handler, moduleState, modulePage, null, null);
	}

	public TransportHandlerWrapper(H handler, TileEntity tileEntity, EnumFacing facing) {
		this(handler, null, null, tileEntity, facing);
	}

	private TransportHandlerWrapper(H handler, IModuleState moduleState, IModulePage modulePage, TileEntity tileEntity, EnumFacing facing) {
		this.handler = handler;
		this.moduleState = moduleState;
		this.modulePage = modulePage;
		this.tileEntity = tileEntity;
		this.facing = facing;
	}

	@Override
	public H getHandler() {
		return handler;
	}

	@Override
	public IModulePage getPage() {
		return modulePage;
	}

	@Override
	public IModuleState getModuleState() {
		return moduleState;
	}

	@Override
	public EnumFacing getFacing() {
		return facing;
	}

	@Override
	public TileEntity getTileEntity() {
		return tileEntity;
	}
}
