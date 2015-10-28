package nedelosk.modularmachines.api.modular.module.tool.producer.fluids;

import nedelosk.forestday.api.INBTTagable;
import nedelosk.forestday.api.guis.IGuiBase;
import nedelosk.forestday.api.guis.Widget;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;

public interface ITankManager extends INBTTagable {

	void addWidgets(Widget tank, IGuiBase gui);

	int[] getPrioritys();

	void setPrioritys(int[] prioritys);

	void setPriority(int priority, int ID);

	ForgeDirection[] getDirections();

	void setDirections(ForgeDirection[] directions);

	void setDirection(ForgeDirection direction, int ID);

	Fluid[] getFilters();

	void setFilters(Fluid[] filters);

	void setFilter(Fluid filter, int ID);

}
