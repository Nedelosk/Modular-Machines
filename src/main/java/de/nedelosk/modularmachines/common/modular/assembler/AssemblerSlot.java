package de.nedelosk.modularmachines.common.modular.assembler;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import de.nedelosk.modularmachines.api.gui.IGuiBase;
import de.nedelosk.modularmachines.api.modular.assembler.IAssembler;
import de.nedelosk.modularmachines.api.modular.assembler.IAssemblerGroup;
import de.nedelosk.modularmachines.api.modular.assembler.IAssemblerSlot;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.ModuleManager;
import de.nedelosk.modularmachines.client.gui.GuiModularAssembler;
import de.nedelosk.modularmachines.common.utils.RenderUtil;
import de.nedelosk.modularmachines.common.utils.WorldUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AssemblerSlot implements IAssemblerSlot {

	public final int xPos;
	public final int yPos;
	public final int index;
	public final IAssemblerGroup group;
	public final Class<? extends IModule> moduleClass;
	public final IAssemblerSlot[] parents;
	public boolean isActive;
	public String UID;

	public AssemblerSlot(IAssemblerGroup group, int xPos, int yPos, int index, String UID, Class<? extends IModule> moduleClass, IAssemblerSlot... parents) {
		this.group = group;
		this.xPos = xPos;
		this.yPos = yPos;
		this.index = index;
		this.UID = UID;
		this.moduleClass = moduleClass;
		this.parents = parents;
		this.isActive = parents == null || parents.length == 0;
	}

	@Override
	public void update(EntityPlayer player, boolean moveItem) {
		if(getStack() == null){
			if(isActive){
				isActive = onStatusChange(false);
			}
			return;
		}
		IModule module = ModuleManager.getContainerFromItem(getStack()).getModule();
		if(parents != null && parents.length > 0){
			for(IAssemblerSlot slot : parents){
				if(!slot.isActive() || slot.getStack() == null){
					onDeleteSlot(player, moveItem);
					if(isActive){
						isActive = onStatusChange(false);
					}
					if(module != null){
						module.updateSlots(this);
					}
					return;
				}
			}
		}
		if(!isActive){
			isActive = onStatusChange(true);
		}
		if(module != null){
			module.updateSlots(this);
		}
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

	@Override
	public String getUID() {
		return UID;
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
		testSlots: for(IAssemblerSlot slot : group.getSlots().values()){
			for(IAssemblerSlot parent : slot.getParents()){
				if(parent.getUID().equals(UID) && !slot.equals(this)){
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

	@Override
	public boolean onStatusChange(boolean isActive) {
		if(getStack() != null){
			IModuleContainer container = ModuleManager.getContainerFromItem(getStack());
			return container.getModule().onStatusChange(this, isActive);	
		}
		return isActive;
	}

	@Override
	public boolean canInsertItem(ItemStack stack) {
		IModuleContainer container = ModuleManager.getContainerFromItem(stack);
		if(container == null){
			return false;
		}
		return moduleClass.isAssignableFrom(container.getModule().getClass());
	}
}
