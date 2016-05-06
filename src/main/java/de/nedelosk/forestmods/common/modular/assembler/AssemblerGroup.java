package de.nedelosk.forestmods.common.modular.assembler;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.client.gui.GuiModularAssembler;
import de.nedelosk.forestmods.library.gui.GuiBase;
import de.nedelosk.forestmods.library.gui.IGuiBase;
import de.nedelosk.forestmods.library.modular.assembler.IAssembler;
import de.nedelosk.forestmods.library.modular.assembler.IAssemblerGroup;
import de.nedelosk.forestmods.library.modular.assembler.IAssemblerSlot;
import de.nedelosk.forestmods.library.utils.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class AssemblerGroup implements IAssemblerGroup {

	public final IAssembler assembler;
	public IAssemblerSlot controllerSlot;
	public int groupID;
	public final List<IAssemblerSlot> slots = new ArrayList();

	public AssemblerGroup(IAssembler assembler, int groupID) {
		this.assembler = assembler;
		this.groupID = groupID;
		assembler.addGroup(this);
	}

	@Override
	public List<IAssemblerSlot> getSlots() {
		return slots;
	}

	@Override
	public IAssemblerSlot getControllerSlot() {
		return controllerSlot;
	}

	@Override
	public void setControllerSlot(IAssemblerSlot controllerSlot) {
		this.controllerSlot = controllerSlot;
		addSlot(controllerSlot);
	}

	@Override
	public IAssembler getAssembler() {
		return assembler;
	}

	@Override
	public IAssemblerSlot addSlot(IAssemblerSlot slot) {
		slots.add(slot);
		return slot;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void renderGroup(IGuiBase<IAssembler> gui, Rectangle pos) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int guiLeft = gui.getGuiLeft();
		int guiTop = gui.getGuiTop();
		RenderUtil.bindTexture(GuiModularAssembler.assemblerOverlay);

		gui.getGui().drawTexturedModalRect(pos.x, pos.y, 18, 36, 18, 18);
		if(controllerSlot != null && controllerSlot.getStack() != null){
			gui.drawItemStack(controllerSlot.getStack(), pos.x + 1, pos.y + 1);
		}
	}

	@Override
	public int getGroupID() {
		return groupID;
	}
}
