package de.nedelosk.forestmods.api.producers;

import java.util.List;

import javax.annotation.Nonnull;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modular.IModularTileEntity;
import de.nedelosk.forestmods.api.modular.renderer.IRenderState;
import de.nedelosk.forestmods.api.modular.renderer.ISimpleRenderer;
import de.nedelosk.forestmods.api.modules.special.IModuleController;
import de.nedelosk.forestmods.api.producers.handlers.IModuleContentHandler;
import de.nedelosk.forestmods.api.producers.handlers.IModulePage;
import de.nedelosk.forestmods.api.producers.handlers.gui.IModuleGui;
import de.nedelosk.forestmods.api.producers.handlers.inventory.IModuleInventory;
import de.nedelosk.forestmods.api.producers.handlers.tank.IModuleTank;
import de.nedelosk.forestmods.api.utils.ModularException;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface IModule {

	void updateServer();

	void updateClient();

	void getHandlers(List<IModuleContentHandler> handlers);

	void createHandlers();

	@SideOnly(Side.CLIENT)
	IModuleGui createGui();

	@SideOnly(Side.CLIENT)
	ISimpleRenderer getRenderer(ModuleStack stack, IRenderState state);

	String getUnlocalizedName(ModuleStack stack);

	boolean transferInput(IModularTileEntity tile, EntityPlayer player, int slotID, Container container, ItemStack stackItem);

	ItemStack getDropItem(ModuleStack stack, IModular modular);

	@SideOnly(Side.CLIENT)
	void addTooltip(ModuleStack stack, List<String> tooltip);

	void addRequiredModules(@Nonnull List<Class<? extends IModule>> requiredModules);

	void canAssembleModular(IModular modular, ModuleStack stack, ModuleStack<IModuleController> controller, List<ModuleStack> modules) throws ModularException;

	void onModularAssembled(IModular modular, ModuleStack stack, ModuleStack<IModuleController> controller, List<ModuleStack> modules) throws ModularException;

	void onAddInModular(IModular modular, ModuleStack stack) throws ModularException;

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

	ModuleStack getModuleStack();
}
