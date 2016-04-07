package de.nedelosk.forestmods.api.modules;

import java.util.List;

import javax.annotation.Nonnull;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modular.IModularTileEntity;
import de.nedelosk.forestmods.api.modular.renderer.IRenderState;
import de.nedelosk.forestmods.api.modular.renderer.ISimpleRenderer;
import de.nedelosk.forestmods.api.modules.handlers.IModuleContentHandler;
import de.nedelosk.forestmods.api.modules.handlers.IModulePage;
import de.nedelosk.forestmods.api.modules.handlers.gui.IModuleGui;
import de.nedelosk.forestmods.api.modules.handlers.inventory.IModuleInventory;
import de.nedelosk.forestmods.api.modules.handlers.tank.IModuleTank;
import de.nedelosk.forestmods.api.modules.special.IModuleController;
import de.nedelosk.forestmods.api.utils.ModularException;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface IModule {

	void updateServer();

	void updateClient();

	/**
	 * Add the content handlers of the module to the list.
	 */
	void getContentHandlers(List<IModuleContentHandler> handlers);

	/**
	 * Create the content of the module.
	 */
	void createContentHandlers();

	boolean isHandlerDisabled(String handlerType);

	@SideOnly(Side.CLIENT)
	IModuleGui createGui();

	@SideOnly(Side.CLIENT)
	ISimpleRenderer getRenderer(ModuleStack stack, IRenderState state);

	@SideOnly(Side.CLIENT)
	String getFilePath(ModuleStack stack);

	/**
	 * @return The unlocalized name for the module.
	 */
	String getUnlocalizedName(ModuleStack stack);

	boolean transferInput(IModularTileEntity tile, EntityPlayer player, int slotID, Container container, ItemStack stackItem);

	/**
	 * @return The item that the module drop.
	 */
	ItemStack getDropItem();

	/**
	 * To add a tooltip to the item of the module.
	 */
	@SideOnly(Side.CLIENT)
	void addTooltip(ModuleStack stack, List<String> tooltip);

	void addRequiredModules(@Nonnull List<Class<? extends IModule>> requiredModules);

	void canAssembleModular(ModuleStack<IModuleController> controller) throws ModularException;

	void onModularAssembled(ModuleStack<IModuleController> controller) throws ModularException;

	void onAddInModular(IModular modular, ModuleStack stack);

	String getName();

	void writeToNBT(NBTTagCompound nbt, IModular modular);

	void readFromNBT(NBTTagCompound nbt, IModular modular);

	IModuleInventory getInventory();

	IModuleTank getTank();

	@SideOnly(Side.CLIENT)
	IModuleGui getGui();

	IModulePage[] getPages();

	void setCurrentPage(int newPage);

	IModulePage getCurrentPage();

	IModular getModular();

	/**
	 * @return The ModuleStack of the module.
	 */
	ModuleStack getModuleStack();
}
