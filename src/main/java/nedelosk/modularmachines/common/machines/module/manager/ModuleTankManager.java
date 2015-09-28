package nedelosk.modularmachines.common.machines.module.manager;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.basic.machine.modular.IModular;
import nedelosk.modularmachines.api.basic.machine.module.ModuleGui;
import nedelosk.modularmachines.api.basic.machine.module.manager.IModuleTankManager;
import nedelosk.modularmachines.common.machines.handler.FluidHandler;
import nedelosk.nedeloskcore.api.machines.IGuiBase;
import nedelosk.nedeloskcore.client.gui.widget.WidgetFluidTank;
import nedelosk.nedeloskcore.common.fluids.FluidTankNedelosk;
import net.minecraft.nbt.NBTTagCompound;

public class ModuleTankManager extends ModuleGui implements IModuleTankManager {

	public TankManager manager;
	
	public ModuleTankManager() {
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		if(manager != null)
			manager.writeToNBT(nbt);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular) {
		super.readFromNBT(nbt);
		if(modular.getManager().getFluidHandler() != null)
		{
			manager = new TankManager();
			manager.readFromNBT(nbt);
		}
	}

	@Override
	public void addButtons(IGuiBase gui, IModular modular) {
		
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addWidgets(IGuiBase gui, IModular modular) {
		if(modular.getManager().getFluidHandler() != null)
		{
			for(int i = 0;i < ((FluidHandler)modular.getManager().getFluidHandler()).tanks.length;i++)
			{
				FluidTankNedelosk tank = ((FluidHandler)modular.getManager().getFluidHandler()).tanks[i];
				WidgetFluidTank widget = new WidgetFluidTank(tank, 36 + i * 51, 10, i);
				manager.addWidgets(widget, gui);
			}
		}
	}

	@Override
	public String getModuleName() {
		return "TankManager";
	}

}
