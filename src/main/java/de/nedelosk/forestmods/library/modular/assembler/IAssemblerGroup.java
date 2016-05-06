package de.nedelosk.forestmods.library.modular.assembler;

import java.awt.Rectangle;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.library.gui.IGuiBase;
import net.minecraft.entity.player.EntityPlayer;

public interface IAssemblerGroup {

	List<IAssemblerSlot> getSlots();

	IAssemblerSlot addSlot(IAssemblerSlot slot);

	IAssemblerSlot getControllerSlot();

	void setControllerSlot(IAssemblerSlot controllerSlot);

	IAssembler getAssembler();
	
	int getGroupID();

	@SideOnly(Side.CLIENT)
	void renderGroup(IGuiBase<IAssembler> gui, Rectangle pos);
}