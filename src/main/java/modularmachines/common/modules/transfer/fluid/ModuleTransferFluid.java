package modularmachines.common.modules.transfer.fluid;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

import modularmachines.api.modules.IModuleStorage;
import modularmachines.api.modules.Module;
import modularmachines.common.modules.transfer.ITransferCycle;
import modularmachines.common.modules.transfer.ITransferHandlerWrapper;
import modularmachines.common.modules.transfer.ModuleTransfer;
import modularmachines.common.modules.transfer.ModuleTransferPage;
import modularmachines.common.modules.transfer.TransferWrapperModule;
import modularmachines.common.modules.transfer.TransferWrapperTileEntity;

public class ModuleTransferFluid extends ModuleTransfer<IFluidHandler>{

	public ModuleTransferFluid(IModuleStorage storage) {
		super(storage);
	}

	@Override
	public Class<IFluidHandler> getHandlerClass() {
		return IFluidHandler.class;
	}
	
	@Override
	public ITransferHandlerWrapper<IFluidHandler> getWrapper(NBTBase compound) {
		if(compound instanceof NBTTagShort){
			EnumFacing facing = EnumFacing.VALUES[((NBTTagShort) compound).getShort()];
			ITransferHandlerWrapper wrapper = tileWrappers.get(facing);
			if(wrapper == null){
				tileWrappers.put(facing, wrapper = createTileWrapper(facing));
			}
			return wrapper;
		}else{
			int index = ((NBTTagInt)compound).getInt();
			ITransferHandlerWrapper wrapper = moduleWrappers.get(index);
			if(wrapper == null){
				moduleWrappers.put(index, wrapper = createModuleWrapper(index));
			}
			return wrapper;
		}
	}
	
	@Override
	public NBTBase writeWrapper(ITransferHandlerWrapper<IFluidHandler> wrapper) {
		if(wrapper instanceof TransferWrapperTileEntity){
			TransferWrapperTileEntity tileWrapper = (TransferWrapperTileEntity) wrapper;
			return new NBTTagShort((short) tileWrapper.getFacing().ordinal());
		}else if(wrapper instanceof TransferWrapperModule){
			TransferWrapperModule moduleWrapper = (TransferWrapperModule) wrapper;
			return new NBTTagInt(moduleWrapper.getIndex());
		}
		return new NBTTagInt(0);
	}

	@Override
	public ITransferHandlerWrapper<IFluidHandler> createModuleWrapper(int moduleIndex) {
		return new TransferWrapperModule<>(this, moduleIndex);
	}

	@Override
	public ITransferHandlerWrapper<IFluidHandler> createTileWrapper(EnumFacing facing) {
		return new TransferWrapperTileEntity(this, facing);
	}
	
	@Override
	public IFluidHandler getHandler(ITransferHandlerWrapper<IFluidHandler> wrapper) {
		if(wrapper instanceof TransferWrapperTileEntity){
			TransferWrapperTileEntity tileWrapper = (TransferWrapperTileEntity) wrapper;
			TileEntity tile = tileWrapper.getTileEntity();
			EnumFacing facing = tileWrapper.getFacing();
			return tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing.getOpposite());
		}else if(wrapper instanceof TransferWrapperModule){
			TransferWrapperModule moduleWrapper = (TransferWrapperModule) wrapper;
			Module module = moduleWrapper.getModule();
			return module.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
		}
		return null;
	}

	@Override
	protected ModuleTransferPage createPage(ModuleTransfer<IFluidHandler> parent, int index) {
		return new ModuleTransferFluidPage(this, index);
	}

	@Override
	public ITransferCycle<IFluidHandler> getCycle(NBTTagCompound compound) {
		return new FluidTransferCycle(this, compound);
	}

}
