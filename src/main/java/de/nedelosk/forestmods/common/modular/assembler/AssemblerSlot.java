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
import de.nedelosk.forestmods.library.modules.IModule;
import de.nedelosk.forestmods.library.utils.RenderUtil;
import de.nedelosk.forestmods.library.utils.WorldUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class AssemblerSlot implements IAssemblerSlot {

	public final int xPos;
	public final int yPos;
	public final int index;
	public final IAssemblerGroup group;
	public final Class<? extends IModule> moduleClass;
	public final IAssemblerSlot[] parents;
	public boolean isActive;

	public AssemblerSlot(IAssemblerGroup group, int xPos, int yPos, int index, Class<? extends IModule> moduleClass, IAssemblerSlot... parents) {
		this.group = group;
		this.xPos = xPos;
		this.yPos = yPos;
		this.index = index;
		this.moduleClass = moduleClass;
		this.parents = parents;
		this.isActive = parents == null || parents.length == 0;
	}
	
	@Override
	public void testActivity(EntityPlayer player) {
		if(getStack() == null){
			isActive = false;
			return;
		}
		if(parents != null && parents.length > 0){
			for(IAssemblerSlot slot : parents){
				if(!slot.isActive() || slot.getStack() == null){
					onDeleteSlot(player);
					if(isActive){
						isActive = false;
					}
					return;
				}
			}
		}
		isActive = true;
	}
	
	@Override
	public boolean isActive() {
		return isActive;
	}

	@Override
	public int getXPos() {
		return xPos;
	}

	@Override
	public int getYPos() {
		return yPos;
	}

	@Override
	public IAssemblerGroup getGroup() {
		return group;
	}

	@Override
	public ItemStack getStack() {
		return group.getAssembler().getStack(index);
	}

	@Override
	public int getIndex() {
		return index;
	}

	@Override
	public Class<? extends IModule> getModuleClass() {
		return moduleClass;
	}

	@Override
	public IAssemblerSlot[] getParents() {
		return parents;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void renderSlot(IGuiBase<IAssembler> gui, Rectangle pos) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int guiLeft = gui.getGuiLeft();
		int guiTop = gui.getGuiTop();
		RenderUtil.bindTexture(GuiModularAssembler.assemblerOverlay);

		gui.getGui().drawTexturedModalRect(pos.x, pos.y, 0, 36, 18, 18);
		if(getStack() != null){
			gui.drawItemStack(getStack(), pos.x + 1, pos.y + 1);
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void renderPaths(IGuiBase<IAssembler> gui) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		RenderUtil.bindTexture(GuiModularAssembler.assemblerOverlay);
		int guiLeft = gui.getGuiLeft();
		int guiTop = gui.getGuiTop();
		
		List<IAssemblerSlot> slots = new ArrayList();
		testSlots: for(IAssemblerSlot slot : group.getSlots()){
			for(IAssemblerSlot parent : slot.getParents()){
				if(parent.getIndex() == index && !slot.equals(this)){
					slots.add(slot);
					continue testSlots;
				}
			}
		}

		if(!slots.isEmpty()){
			int xOffset = 0;
			
			if(isActive){
				xOffset = 108;
			}else{
				xOffset = 162;
			}
			
			for(IAssemblerSlot otherSlot : slots){
				int currentX = getXPos();
				int currentY = getYPos();
				if(otherSlot.getYPos() > currentY){
					currentY++;
				}else{
					currentY--;
				}
				while(otherSlot.getYPos() != currentY){
					gui.getGui().drawTexturedModalRect(guiLeft + 38 + currentX * 18, guiTop + 8 + currentY * 18, 36+ xOffset, 0, 18, 18);
					if(otherSlot.getYPos() > currentY){
						currentY++;
					}else{
						currentY--;
					}
				}
				if(otherSlot.getYPos() != yPos && otherSlot.getXPos() != xPos){
					if(otherSlot.getXPos() > xPos){
						if(otherSlot.getYPos() > yPos){
							gui.getGui().drawTexturedModalRect(guiLeft + 38 + currentX * 18, guiTop + 8 + currentY * 18, xOffset, 18, 18, 18);
						}else{
							gui.getGui().drawTexturedModalRect(guiLeft + 38 + currentX * 18, guiTop + 8 + currentY * 18, xOffset, 0, 18, 18);
						}
					}else{
						if(otherSlot.getYPos() > yPos){
							gui.getGui().drawTexturedModalRect(guiLeft + 38 + currentX * 18, guiTop + 8 + currentY * 18, 18+ xOffset, 18, 18, 18);
						}else{
							gui.getGui().drawTexturedModalRect(guiLeft + 38 + currentX * 18, guiTop + 8 + currentY * 18, 18+ xOffset, 0, 18, 18);
						}
					}
				}
				while(otherSlot.getXPos() != currentX){
					if(otherSlot.getXPos() < currentX){
						currentX--;
					}else{
						currentX++;
					}
					gui.getGui().drawTexturedModalRect(guiLeft + 38 + currentX * 18, guiTop + 8 + currentY * 18, 36 + xOffset, 18, 18, 18);
				}
			}
		}
	}

	@Override
	public void onDeleteSlot(EntityPlayer player, boolean moveItem) {
		if(moveItem && getStack() != null){
			ItemStack stack = getStack().copy();
			if(!player.inventory.addItemStackToInventory(stack)){
				WorldUtil.dropItem(player, stack);
			}
			group.getAssembler().setStack(index, null);
		}
	}
}
