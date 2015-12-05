package nedelosk.modularmachines.api.producers.managers.fluids;

import java.util.List;

import nedelosk.forestday.api.FluidTankBasic;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.producers.fluids.IProducerTank;
import nedelosk.modularmachines.api.producers.fluids.ITankManager;
import nedelosk.modularmachines.api.producers.fluids.ITankManager.TankMode;
import nedelosk.modularmachines.api.producers.gui.IProducerGuiWithWidgets;
import nedelosk.modularmachines.api.producers.managers.IProducerManager;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public interface IProducerTankManager extends IProducerGuiWithWidgets, IProducerManager {

	ITankManager getManager();
	
	ITankManager createManager();
	
	void addTank(int id, IProducerTank tank);

	FluidTankBasic getTank(int id);
	
	List<FluidTankBasic> getTanks(IModular modular, ModuleStack producer, TankMode mode);
	
	FluidTankBasic[] getTanks();
	
	int fill(ForgeDirection from, FluidStack resource, boolean doFill, ModuleStack stack, IModular modular);

	FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain, ModuleStack stack, IModular modular);

	FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain, ModuleStack stack, IModular modular);

	boolean canFill(ForgeDirection from, Fluid fluid, ModuleStack stack, IModular modular);

	boolean canDrain(ForgeDirection from, Fluid fluid, ModuleStack stack, IModular modular);

	FluidTankInfo[] getTankInfo(ForgeDirection from, ModuleStack stack, IModular modular);

}
