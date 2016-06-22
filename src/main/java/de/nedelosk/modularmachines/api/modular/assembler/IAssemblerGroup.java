package de.nedelosk.modularmachines.api.modular.assembler;

import java.awt.Rectangle;
import java.util.List;
import java.util.Map;

import de.nedelosk.modularmachines.api.gui.IGuiBase;
import de.nedelosk.modularmachines.api.modular.AssemblerException;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IAssemblerGroup {

	Map<String, IAssemblerSlot> getSlots();

	IAssemblerSlot getControllerSlot();

	void setControllerSlot(IAssemblerSlot controllerSlot);

	IAssembler getAssembler();

	int getGroupID();
	
	IAssemblerSlot addSlot(IAssemblerSlot slot);
	
	void removeSlot(IAssemblerSlot slot);

	IAssemblerSlot getSlot(String slotName);

	@SideOnly(Side.CLIENT)
	void renderGroup(IGuiBase<IAssembler> gui, Rectangle pos);
}