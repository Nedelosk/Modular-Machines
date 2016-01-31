package nedelosk.modularmachines.api.modules.managers.fluids;

import java.util.List;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modules.IModuleSaver;
import nedelosk.modularmachines.api.modules.fluids.TankData;
import nedelosk.modularmachines.api.modules.managers.IModuleManager;
import nedelosk.modularmachines.api.modules.storage.tanks.IModuleTank;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

public interface IModuleTankManager<S extends IModuleSaver> extends IModuleManager<S> {

	void addTank(int id, IModuleTank tank, ModuleStack stack);

	List<TankData> getDatas(IModular modular, ModuleStack producer, TankMode mode);

	int fill(ForgeDirection from, FluidStack resource, boolean doFill, ModuleStack stack, IModular modular, boolean canFillOutput);

	FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain, ModuleStack stack, IModular modular, boolean canDrainInput);

	FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain, ModuleStack stack, IModular modular, boolean canDrainInput);

	boolean canFill(ForgeDirection from, Fluid fluid, ModuleStack stack, IModular modular);

	boolean canDrain(ForgeDirection from, Fluid fluid, ModuleStack stack, IModular modular);

	FluidTankInfo[] getTankInfo(ForgeDirection from, ModuleStack stack, IModular modular);

	public static enum TankMode {
		INPUT, OUTPUT, NONE
	}
}
