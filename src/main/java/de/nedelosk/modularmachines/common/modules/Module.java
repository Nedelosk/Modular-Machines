package de.nedelosk.modularmachines.common.modules;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularHandler;
import de.nedelosk.modularmachines.api.modular.IModularLogic;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.IModuleModelHandler;
import de.nedelosk.modularmachines.api.modules.handlers.IModuleContentHandler;
import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventory;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventoryBuilder;
import de.nedelosk.modularmachines.api.modules.handlers.tank.IModuleTank;
import de.nedelosk.modularmachines.api.modules.handlers.tank.IModuleTankBuilder;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.state.ModuleState;
import de.nedelosk.modularmachines.common.modules.handlers.inventorys.ModuleInventoryBuilder;
import de.nedelosk.modularmachines.common.modules.handlers.tanks.ModuleTankBuilder;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;

public abstract class Module extends IForgeRegistryEntry.Impl<IModule> implements IModule {

	@Override
	public int getComplexity(IModuleState state) {
		return 1;
	}

	@Override
	public String getUnlocalizedName() {
		return "module." + getRegistryName().getResourcePath() + ".name";
	}

	@Override
	public ItemStack getDropItem(IModuleState state) {
		return state.getContainer().getItemStack();
	}

	@Override
	public List<IModuleContentHandler> createContentHandlers(IModuleState state){
		List<IModuleContentHandler> handlers = Lists.newArrayList();
		IModuleInventory inv = createInventory(state);
		if(inv != null){
			handlers.add(inv);
		}
		IModuleTank tank = createTank(state);
		if(tank != null){
			handlers.add(tank);
		}
		return handlers;
	}

	protected IModuleInventory createInventory(IModuleState state) {
		IModuleInventoryBuilder invBuilder = new ModuleInventoryBuilder();
		invBuilder.setModular(state.getModular());
		invBuilder.setModuleState(state);
		if (state.getPages() != null) {
			for(IModulePage page : state.getPages()) {
				page.createInventory(invBuilder);
			}
		}
		if(!invBuilder.isEmpty()){
			return invBuilder.build();
		}
		return null;
	}

	protected IModuleTank createTank(IModuleState state) {
		IModuleTankBuilder tankBuilder = new ModuleTankBuilder();
		tankBuilder.setModular(state.getModular());
		tankBuilder.setModuleState(state);
		if (state.getPages() != null) {
			for(IModulePage page : state.getPages()) {
				page.createTank(tankBuilder);
			}
		}
		if(!tankBuilder.isEmpty()){
			return tankBuilder.build();
		}
		return null;
	}

	@Override
	public void updateServer(IModuleState state) {
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void updateClient(IModuleState state) {
	}

	@Override
	public boolean transferInput(IModularHandler tile, IModuleState state, EntityPlayer player, int slotID, Container container, ItemStack stackItem) {
		return false;
	}

	@Override
	public IModuleState createState(IModular modular, IModuleContainer container) {
		return new ModuleState(modular, this, container);
	}

	@Override
	public IModuleState loadStateFromItem(IModuleState state, ItemStack stack) {
		return state;
	}

	@Override
	public IModulePage[] createPages(IModuleState state) {
		return null;
	}

	@Override
	public List<IModularLogic> createLogic(IModuleState state) {
		return Collections.emptyList();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModuleModelHandler getInitModelHandler(IModuleContainer container) {
		return null;
	}

	@Override
	public IModuleModelHandler getModelHandler(IModuleState state) {
		return getInitModelHandler(state.getContainer());
	}

	@Override
	public boolean assembleModule(IItemHandler itemHandler, IModular modular, IModuleState state) {
		return true;
	}
}
