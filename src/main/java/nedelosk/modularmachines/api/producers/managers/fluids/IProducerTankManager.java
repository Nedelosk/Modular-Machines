package nedelosk.modularmachines.api.producers.managers.fluids;

import java.util.List;

import nedelosk.forestday.api.FluidTankBasic;
import nedelosk.forestday.api.guis.IGuiBase;
import nedelosk.forestday.api.guis.Widget;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.producers.fluids.IProducerTank;
import nedelosk.modularmachines.api.producers.fluids.ITankData;
import nedelosk.modularmachines.api.producers.gui.IProducerGuiWithButtons;
import nedelosk.modularmachines.api.producers.gui.IProducerGuiWithWidgets;
import nedelosk.modularmachines.api.producers.managers.IProducerManager;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

public interface IProducerTankManager extends IProducerGuiWithWidgets, IProducerGuiWithButtons, IProducerManager {
	
	void addTank(int id, IProducerTank tank);

	ITankData getData(int id);
	
	List<ITankData> getDatas(IModular modular, ModuleStack producer, TankMode mode);
	
	ITankData[] getDatas();
	
	void addWidgets(Widget tank, IGuiBase<IModularTileEntity<IModular>> gui);
	
	public static enum TankMode{
		INPUT, OUTPUT
	}
	
	int getMaxTabs();
	
	int getTab();
	
	void setTab(int tab);
	
	int fill(ForgeDirection from, FluidStack resource, boolean doFill, ModuleStack stack, IModular modular);

	FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain, ModuleStack stack, IModular modular);

	FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain, ModuleStack stack, IModular modular);

	boolean canFill(ForgeDirection from, Fluid fluid, ModuleStack stack, IModular modular);

	boolean canDrain(ForgeDirection from, Fluid fluid, ModuleStack stack, IModular modular);

	FluidTankInfo[] getTankInfo(ForgeDirection from, ModuleStack stack, IModular modular);

}
