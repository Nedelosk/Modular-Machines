package de.nedelosk.modularmachines.common.plugins.cofh;

import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import de.nedelosk.modularmachines.api.energy.IEnergyInterface;
import de.nedelosk.modularmachines.api.energy.IEnergyType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class EnergyTypeRF implements IEnergyType<Long> {

	@Override
	public IEnergyInterface getInterface(IBlockAccess world, BlockPos pos, EnumFacing facing) {
		TileEntity tileEntity = world.getTileEntity(pos);
		if(tileEntity != null){
			IEnergyProvider provider = null;
			IEnergyReceiver receiver = null;
			IEnergyHandler handler = null;
			if(tileEntity instanceof IEnergyProvider){
				provider = (IEnergyProvider) tileEntity;
			}
			if(tileEntity instanceof IEnergyReceiver){
				receiver = (IEnergyReceiver) tileEntity;
			}
			if(tileEntity instanceof IEnergyHandler){
				handler = (IEnergyHandler) tileEntity;
			}
			if(provider != null || receiver != null || handler != null){
				return new EnergyInterfaceRF(receiver, provider, handler, facing);
			}
		}
		return null;
	}

	@Override
	public String getFullName() {
		return "readstone.flux";
	}

	@Override
	public String getShortName() {
		return "rf";
	}
}
