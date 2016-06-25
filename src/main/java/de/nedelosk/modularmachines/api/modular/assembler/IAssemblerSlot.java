package de.nedelosk.modularmachines.api.modular.assembler;

import java.awt.Rectangle;

import de.nedelosk.modularmachines.api.gui.IGuiBase;
import de.nedelosk.modularmachines.api.modules.IModule;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IAssemblerSlot {

	/**
	 * @return A Integer between 0 and 10.
	 */
	int getXPos();

	/**
	 * @return A Integer between 0 and 9.
	 */
	int getYPos();

	IAssemblerGroup getGroup();

	IAssemblerSlot[] getParents();

	int getIndex();

	String getUID();

	boolean isActive();

	void update(EntityPlayer player, boolean moveItem);

	ItemStack getStack();

	Class<? extends IModule> getModuleClass();

	void onDeleteSlot(EntityPlayer player, boolean moveItem);

	@SideOnly(Side.CLIENT)
	void renderSlot(IGuiBase<IAssembler> gui, Rectangle pos);

	@SideOnly(Side.CLIENT)
	void renderPaths(IGuiBase<IAssembler> gui);

	boolean onStatusChange(boolean isActive);

	boolean canInsertItem(ItemStack stack);

}
