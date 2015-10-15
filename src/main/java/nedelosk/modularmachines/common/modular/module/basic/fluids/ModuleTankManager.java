package nedelosk.modularmachines.common.modular.module.basic.fluids;

import java.util.ArrayList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.module.basic.basic.ModuleManager;
import nedelosk.modularmachines.api.modular.module.basic.fluids.IModuleFluidManager;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.common.modular.machines.modular.handlers.FluidHandler;
import nedelosk.modularmachines.common.modular.module.basic.fluids.manager.TankManager;
import nedelosk.nedeloskcore.api.machines.IContainerBase;
import nedelosk.nedeloskcore.api.machines.IGuiBase;
import nedelosk.nedeloskcore.client.gui.widget.WidgetFluidTank;
import nedelosk.nedeloskcore.common.fluids.FluidTankNedelosk;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public class ModuleTankManager extends ModuleManager implements IModuleFluidManager {

	public TankManager manager;
	
	public ModuleTankManager() {
		super();
	}
	
	public ModuleTankManager(ForgeDirection side) {
		super(side);
	}
	
	public ModuleTankManager(ForgeDirection side, String modifier) {
		super(side, modifier);
	}
	
	public ModuleTankManager(NBTTagCompound nbt) {
		super(nbt);
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
	
	@Override
	public boolean hasCustomInventoryName() {
		return true;
	}

	@Override
	public int getColor() {
		return 0;
	}

	@Override
	public ArrayList<Slot> addSlots(IContainerBase container, IModular modular, ModuleStack stack) {
		return new ArrayList<Slot>();
	}

	@Override
	public int getSizeInventory(ModuleStack stack) {
		return 3;
	}

}
