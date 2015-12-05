package nedelosk.modularmachines.common.producers.fluids.manager;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestday.api.guis.IGuiBase;
import nedelosk.forestday.api.guis.Widget;
import nedelosk.forestday.client.gui.widget.WidgetFluidTank;
import nedelosk.modularmachines.api.producers.fluids.ITankManager;
import nedelosk.modularmachines.client.gui.widget.WidgetDirection;
import nedelosk.modularmachines.client.gui.widget.WidgetProducer;
import nedelosk.modularmachines.client.gui.widget.WidgetTankMode;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.ForgeDirection;

public class TankManager implements ITankManager {

	protected int[] producers = new int[]{ 0, 0, 0};
	protected ForgeDirection[] directions = new ForgeDirection[]{ ForgeDirection.UNKNOWN, ForgeDirection.UNKNOWN, ForgeDirection.UNKNOWN };
	protected TankMode[] modes = new TankMode[]{ TankMode.OUTPUT, TankMode.OUTPUT, TankMode.OUTPUT };

	@Override
	@SideOnly(Side.CLIENT)
	public void addWidgets(Widget widget, IGuiBase gui) {
		if (widget != null && widget instanceof WidgetFluidTank) {
			WidgetFluidTank tank = (WidgetFluidTank) widget;
			gui.getWidgetManager().add(tank);
			gui.getWidgetManager().add(new WidgetTankMode(tank.posX - 22, tank.posY, modes[tank.ID], tank.ID));
			gui.getWidgetManager().add(new WidgetProducer(tank.posX - 22, tank.posY + 21, producers[tank.ID], tank.ID));
			gui.getWidgetManager().add(new WidgetDirection(tank.posX - 22, tank.posY + 42, directions[tank.ID], tank.ID));
		}
	}
	
	@Override
	public void setDirections(ForgeDirection[] directions) {
		this.directions = directions;
	}

	@Override
	public void setDirection(int ID, ForgeDirection direction) {
		this.directions[ID] = direction;
	}

	@Override
	public ForgeDirection[] getDirections() {
		return directions;
	}
	
	@Override
	public void setProducer(int tankID, int producer) {
		this.producers[tankID] = producer;
	}
	
	@Override
	public void setProducers(int[] producer) {
		this.producers = producer;
	}
	
	@Override
	public int[] getProducers() {
		return producers;
	}
	
	@Override
	public void setTankMode(int tankID, TankMode mode) {
		modes[tankID] = mode;
	}
	
	@Override
	public void setTankModes(TankMode[] modes) {
		this.modes = modes;
	}
	
	@Override
	public TankMode[] getTankModes() {
		return modes;
	}

	// NBT
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		NBTTagList nbtFilter = nbt.getTagList("Producers", 10);
		for (int i = 0; i < nbtFilter.tagCount(); i++) {
			NBTTagCompound nbtTag = nbtFilter.getCompoundTagAt(i);
			producers[i] = nbtTag.getInteger("Producer");
		}

		NBTTagList nbtPrioritys = nbt.getTagList("Modes", 10);
		for (int i = 0; i < nbtPrioritys.tagCount(); i++) {
			NBTTagCompound nbtTag = nbtPrioritys.getCompoundTagAt(i);
			modes[i] = TankMode.values()[nbtTag.getInteger("Mode")];
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
		for (int producer : producers) {
			NBTTagCompound nbtTag = new NBTTagCompound();
			nbtTag.setInteger("Producer", producer);
			nbtFilter.appendTag(nbtTag);
		}
		nbt.setTag("Producers", nbtFilter);

		NBTTagList nbtPrioritys = new NBTTagList();
		for (TankMode mode : modes) {
			NBTTagCompound nbtTag = new NBTTagCompound();
			if (mode != null)
				nbtTag.setInteger("Mode", mode.ordinal());
			nbtPrioritys.appendTag(nbtTag);
		}
		nbt.setTag("Modes", nbtPrioritys);

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
