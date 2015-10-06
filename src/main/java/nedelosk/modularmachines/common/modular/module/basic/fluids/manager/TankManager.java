package nedelosk.modularmachines.common.modular.module.basic.fluids.manager;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.client.gui.widget.WidgetFluidTankDirection;
import nedelosk.modularmachines.client.gui.widget.WidgetFluidTankFilter;
import nedelosk.modularmachines.client.gui.widget.WidgetFluidTankPriority;
import nedelosk.nedeloskcore.api.INBTTagable;
import nedelosk.nedeloskcore.api.machines.IGuiBase;
import nedelosk.nedeloskcore.client.gui.widget.WidgetFluidTank;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class TankManager implements INBTTagable {
	
	public int[] prioritys = new int[3];
	public ForgeDirection[] directions = new ForgeDirection[3];
	public Fluid[] filters = new Fluid[3];
	 
	public TankManager() {
	}
	
	public void update()
	{
	}
	
	@SideOnly(Side.CLIENT)
	public void addWidgets(WidgetFluidTank widget, IGuiBase gui){
		if(widget != null)
		{
			gui.getWidgetManager().add(widget);
			gui.getWidgetManager().add(new WidgetFluidTankFilter(widget.posX - 22, widget.posY, widget.ID, filters[widget.ID]));
			gui.getWidgetManager().add(new WidgetFluidTankPriority(widget.posX - 22, widget.posY + 21, widget.ID, prioritys[widget.ID]));
			gui.getWidgetManager().add(new WidgetFluidTankDirection(widget.posX - 22, widget.posY + 42, widget.ID, directions[widget.ID]));
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		NBTTagList nbtFilter = nbt.getTagList("Filter", 10);
		for(int i = 0;i < nbtFilter.tagCount();i++)
		{
			NBTTagCompound nbtTag = nbtFilter.getCompoundTagAt(i);
			if(nbtTag.hasKey("ID"))
				filters[i] = FluidRegistry.getFluid(nbtTag.getInteger("ID"));
		}
		
		NBTTagList nbtPrioritys = nbt.getTagList("Priority", 10);
		for(int i = 0;i < nbtPrioritys.tagCount();i++)
		{
			NBTTagCompound nbtTag = nbtPrioritys.getCompoundTagAt(i);
			prioritys[i] = nbtTag.getInteger("priority");
		}
		
		NBTTagList nbtDirections = nbt.getTagList("Direction", 10);
		for(int i = 0;i < nbtDirections.tagCount();i++)
		{
			NBTTagCompound nbtTag = nbtDirections.getCompoundTagAt(i);
			directions[i] = ForgeDirection.values()[nbtTag.getInteger("direction")];
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		NBTTagList nbtFilter = new NBTTagList();
		for(Fluid filter : filters)
		{
			NBTTagCompound nbtTag = new NBTTagCompound();
			if(filter != null)
				nbtTag.setInteger("ID", filter.getID());
			nbtFilter.appendTag(nbtTag);
		}
		nbt.setTag("Filter", nbtFilter);
		
		NBTTagList nbtPrioritys = new NBTTagList();
		for(int priority : prioritys)
		{
			NBTTagCompound nbtTag = new NBTTagCompound();
			if(priority != 0)
				nbtTag.setInteger("priority", priority);
			nbtPrioritys.appendTag(nbtTag);
		}
		nbt.setTag("Priority", nbtPrioritys);
		
		NBTTagList nbtDirections = new NBTTagList();
		for(ForgeDirection direction : directions)
		{
			NBTTagCompound nbtTag = new NBTTagCompound();
			if(direction != null)
				nbtTag.setInteger("direction", direction.ordinal());
			nbtDirections.appendTag(nbtTag);
		}
		nbt.setTag("Direction", nbtDirections);
	}
}
