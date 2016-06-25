package de.nedelosk.modularmachines.common.modular.assembler;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import de.nedelosk.modularmachines.api.gui.IGuiBase;
import de.nedelosk.modularmachines.api.modular.assembler.IAssembler;
import de.nedelosk.modularmachines.api.modular.assembler.IAssemblerGroup;
import de.nedelosk.modularmachines.api.modular.assembler.IAssemblerSlot;
import de.nedelosk.modularmachines.client.gui.GuiModularAssembler;
import de.nedelosk.modularmachines.common.utils.RenderUtil;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AssemblerGroup implements IAssemblerGroup {

	public final IAssembler assembler;
	public IAssemblerSlot controllerSlot;
	public int groupID;
	public final Map<String, IAssemblerSlot> slots = new HashMap<>();

	public AssemblerGroup(IAssembler assembler, int groupID) {
		this.assembler = assembler;
		this.groupID = groupID;
		assembler.addGroup(this);
	}

	@Override
	public Map<String, IAssemblerSlot> getSlots() {
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
	public IAssemblerSlot getSlot(String slotUID) {
		return slots.get(slotUID);
	}

	@Override
	public IAssemblerSlot addSlot(IAssemblerSlot slot) {
		slots.put(slot.getUID(), slot);
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
	
	@Override
	public void removeSlot(IAssemblerSlot slot) {
		if(slot != null){
			if(slots.containsKey(slot.getUID())){
				slots.remove(slot.getUID());
			}
		}
	}
}
