package nedelosk.modularmachines.common.modular.module.manager;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.module.Module;
import nedelosk.modularmachines.api.modular.module.manager.IModuleTankManager;
import nedelosk.modularmachines.client.gui.machine.GuiModularMachine;
import nedelosk.modularmachines.common.inventory.machine.ContainerModularMachine;
import nedelosk.modularmachines.common.modular.handler.FluidHandler;
import nedelosk.nedeloskcore.api.machines.IContainerBase;
import nedelosk.nedeloskcore.api.machines.IGuiBase;
import nedelosk.nedeloskcore.client.gui.widget.WidgetFluidTank;
import nedelosk.nedeloskcore.common.fluids.FluidTankNedelosk;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;

public class ModuleTankManager extends Module implements IModuleTankManager {

	public TankManager manager;
	
	public ModuleTankManager() {
	}
	
	@Override
	public void update(IModular modular) {
		super.update(modular);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		if(manager != null)
			manager.writeToNBT(nbt);
	}
	
	public void readFromNBT(NBTTagCompound nbt, IModular modular) {
		super.readFromNBT(nbt);
		
		if(modular.getFluidHandler() != null)
		{
			manager = new TankManager();
			manager.readFromNBT(nbt);
		}
	}
	
	@Override
	public void addSlots(IContainerBase container, IModular modular) {
	}

	@Override
	public void addButtons(IGuiBase gui, IModular modular) {
		
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addWidgets(IGuiBase gui, IModular modular) {
		if(modular.getFluidHandler() != null)
		{
			for(int i = 0;i < ((FluidHandler)modular.getFluidHandler()).tanks.length;i++)
			{
				FluidTankNedelosk tank = ((FluidHandler)modular.getFluidHandler()).tanks[i];
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
