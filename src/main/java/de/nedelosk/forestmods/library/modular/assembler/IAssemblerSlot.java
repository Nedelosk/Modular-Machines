package de.nedelosk.forestmods.library.modular.assembler;

import java.awt.Rectangle;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.library.gui.IGuiBase;
import de.nedelosk.forestmods.library.modules.IModule;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

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

	String getName();

	boolean isActive();

	void update(EntityPlayer player, boolean moveItem);

	ItemStack getStack();

	Class<? extends IModule> getModuleClass();

	void onDeleteSlot(EntityPlayer player, boolean moveItem);

	@SideOnly(Side.CLIENT)
	void renderSlot(IGuiBase<IAssembler> gui, Rectangle pos);

	@SideOnly(Side.CLIENT)
	void renderPaths(IGuiBase<IAssembler> gui);

}
