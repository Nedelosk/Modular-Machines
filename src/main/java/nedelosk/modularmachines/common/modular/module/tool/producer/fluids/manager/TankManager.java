package nedelosk.modularmachines.common.modular.module.tool.producer.fluids.manager;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestday.api.guis.IGuiBase;
import nedelosk.forestday.api.guis.Widget;
import nedelosk.forestday.client.gui.widget.WidgetFluidTank;
import nedelosk.modularmachines.api.modular.module.tool.producer.fluids.ITankManager;
import nedelosk.modularmachines.client.gui.widget.WidgetFluidTankDirection;
import nedelosk.modularmachines.client.gui.widget.WidgetFluidTankFilter;
import nedelosk.modularmachines.client.gui.widget.WidgetFluidTankPriority;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class TankManager implements ITankManager {

	protected int[] prioritys = new int[3];
	protected ForgeDirection[] directions = new ForgeDirection[]{ ForgeDirection.UNKNOWN, ForgeDirection.UNKNOWN, ForgeDirection.UNKNOWN };
	protected Fluid[] filters = new Fluid[3];

	@Override
	@SideOnly(Side.CLIENT)
	public void addWidgets(Widget widget, IGuiBase gui) {
		if (widget != null && widget instanceof WidgetFluidTank) {
			WidgetFluidTank tank = (WidgetFluidTank) widget;
			gui.getWidgetManager().add(tank);
			gui.getWidgetManager().add(new WidgetFluidTankFilter(tank.posX - 22, tank.posY, tank.ID, filters[tank.ID]));
			gui.getWidgetManager().add(new WidgetFluidTankPriority(tank.posX - 22, tank.posY + 21, tank.ID, prioritys[tank.ID]));
			gui.getWidgetManager().add(new WidgetFluidTankDirection(tank.posX - 22, tank.posY + 42, tank.ID, directions[tank.ID]));
		}
	}

	@Override
	public int[] getPrioritys() {
		return prioritys;
	}

	@Override
	public void setPrioritys(int[] prioritys) {
		this.prioritys = prioritys;
	}

	@Override
	public void setPriority(int priority, int ID) {
		this.prioritys[ID] = priority;
	}

	@Override
	public ForgeDirection[] getDirections() {
		return directions;
	}

	@Override
	public void setDirections(ForgeDirection[] directions) {
		this.directions = directions;
	}

	@Override
	public void setDirection(ForgeDirection direction, int ID) {
		this.directions[ID] = direction;
	}

	@Override
	public Fluid[] getFilters() {
		return filters;
	}

	@Override
	public void setFilters(Fluid[] filters) {
		this.filters = filters;
	}

	@Override
	public void setFilter(Fluid filter, int ID) {
		this.filters[ID] = filter;
	}

	// NBT
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		NBTTagList nbtFilter = nbt.getTagList("Filter", 10);
		for (int i = 0; i < nbtFilter.tagCount(); i++) {
			NBTTagCompound nbtTag = nbtFilter.getCompoundTagAt(i);
			if (nbtTag.hasKey("ID"))
				filters[i] = FluidRegistry.getFluid(nbtTag.getInteger("ID"));
		}

		NBTTagList nbtPrioritys = nbt.getTagList("Priority", 10);
		for (int i = 0; i < nbtPrioritys.tagCount(); i++) {
			NBTTagCompound nbtTag = nbtPrioritys.getCompoundTagAt(i);
			prioritys[i] = nbtTag.getInteger("priority");
		}

		NBTTagList nbtDirections = nbt.getTagList("Direction", 10);
		for (int i = 0; i < nbtDirections.tagCount(); i++) {
			NBTTagCompound nbtTag = nbtDirections.getCompoundTagAt(i);
			directions[i] = ForgeDirection.values()[nbtTag.getInteger("direction")];
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		NBTTagList nbtFilter = new NBTTagList();
		for (Fluid filter : filters) {
			NBTTagCompound nbtTag = new NBTTagCompound();
			if (filter != null)
				nbtTag.setInteger("ID", filter.getID());
			nbtFilter.appendTag(nbtTag);
		}
		nbt.setTag("Filter", nbtFilter);

		NBTTagList nbtPrioritys = new NBTTagList();
		for (int priority : prioritys) {
			NBTTagCompound nbtTag = new NBTTagCompound();
			if (priority != 0)
				nbtTag.setInteger("priority", priority);
			nbtPrioritys.appendTag(nbtTag);
		}
		nbt.setTag("Priority", nbtPrioritys);

		NBTTagList nbtDirections = new NBTTagList();
		for (ForgeDirection direction : directions) {
			NBTTagCompound nbtTag = new NBTTagCompound();
			if (direction != null)
				nbtTag.setInteger("direction", direction.ordinal());
			nbtDirections.appendTag(nbtTag);
		}
		nbt.setTag("Direction", nbtDirections);
	}
}
