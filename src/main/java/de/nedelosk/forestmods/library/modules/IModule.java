package de.nedelosk.forestmods.library.modules;

import java.util.List;

import javax.annotation.Nonnull;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.library.modular.IModular;
import de.nedelosk.forestmods.library.modular.IModularTileEntity;
import de.nedelosk.forestmods.library.modular.ModularException;
import de.nedelosk.forestmods.library.modular.renderer.IRenderState;
import de.nedelosk.forestmods.library.modular.renderer.ISimpleRenderer;
import de.nedelosk.forestmods.library.modules.handlers.IModuleContentHandler;
import de.nedelosk.forestmods.library.modules.handlers.IModulePage;
import de.nedelosk.forestmods.library.modules.handlers.inventory.IModuleInventory;
import de.nedelosk.forestmods.library.modules.handlers.tank.IModuleTank;
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
	ISimpleRenderer getRenderer(IRenderState state);

	@SideOnly(Side.CLIENT)
	String getFilePath();


	/**
	 * Only for modules with inventory.
	 */
	boolean transferInput(IModularTileEntity tile, EntityPlayer player, int slotID, Container container, ItemStack stackItem);

	/**
	 * @return The item that the module drop.
	 */
	ItemStack getDropItem();

	void addRequiredModules(@Nonnull List<Class<? extends IModule>> requiredModules);

	void canAssembleModular(IModuleController controller) throws ModularException;

	void onModularAssembled(IModuleController controller) throws ModularException;

	void loadDataFromItem(ItemStack stack);

	String getName(IModuleContainer container);

	void writeToNBT(NBTTagCompound nbt, IModular modular);

	void readFromNBT(NBTTagCompound nbt, IModular modular);

	IModuleInventory getInventory();

	IModuleTank getTank();

	IModulePage[] getPages();

	IModular getModular();

	String getUnlocalizedName();

	@SideOnly(Side.CLIENT)
	void addTooltip(List<String> tooltip);

	/**
	 * @return The ModuleContainer of the module.
	 */
	IModuleContainer getModuleContainer();
}
