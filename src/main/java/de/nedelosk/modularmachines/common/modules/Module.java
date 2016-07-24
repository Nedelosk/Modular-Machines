package de.nedelosk.modularmachines.common.modules;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import de.nedelosk.modularmachines.api.Translator;
import de.nedelosk.modularmachines.api.modular.AssemblerException;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularAssembler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.IModelInitHandler;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.handlers.IModuleContentHandler;
import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventory;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventoryBuilder;
import de.nedelosk.modularmachines.api.modules.handlers.tank.IModuleTank;
import de.nedelosk.modularmachines.api.modules.handlers.tank.IModuleTankBuilder;
import de.nedelosk.modularmachines.api.modules.models.IModelHandler;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.state.IModuleStateClient;
import de.nedelosk.modularmachines.api.modules.state.ModuleState;
import de.nedelosk.modularmachines.api.modules.state.ModuleStateClient;
import de.nedelosk.modularmachines.api.modules.storage.IPositionedModuleStorage;
import de.nedelosk.modularmachines.api.modules.storaged.EnumWallType;
import de.nedelosk.modularmachines.common.modules.handlers.inventorys.ModuleInventoryBuilder;
import de.nedelosk.modularmachines.common.modules.handlers.tanks.ModuleTankBuilder;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class Module extends IForgeRegistryEntry.Impl<IModule> implements IModule {

	protected final String name;
	protected final int complexity;

	public Module(String name, int complexity) {
		this.name = name;
		this.complexity = complexity;
	}

	@Override
	public int getComplexity(IModuleState state) {
		return complexity;
	}

	@Override
	public String getDisplayName(IModuleContainer container) {
		return container.getMaterial().getLocalizedName() + " " + Translator.translateToLocal(container.getUnlocalizedName());
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addTooltip(List<String> tooltip, IModuleContainer container) {
		tooltip.add(Translator.translateToLocal("mm.module.tooltip.size") + getSize().getLocalizedName());
		tooltip.add(Translator.translateToLocal("mm.module.tooltip.complexity") + complexity);
		if(getPosition(container) != null){
			tooltip.add(Translator.translateToLocal("mm.module.tooltip.position") + Translator.translateToLocal("module.storage." + getPosition(container).getName() + ".name"));
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public ResourceLocation getWindowLocation(IModuleContainer container) {
		return new ResourceLocation("modularmachines:module/windows/" + container.getMaterial().getName() + "_" + getSize().getName());
	}

	@Override
	public EnumWallType getWallType(IModuleState state) {
		return EnumWallType.NONE;
	}

	@Override
	public String getUnlocalizedName(IModuleContainer container) {
		return "module." + name + ".name";
	}

	@Override
	public String getDescription(IModuleContainer container) {
		return "module." + name + ".description";
	}

	@Override
	public ItemStack saveDataToItem(IModuleState state) {
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
		invBuilder.setModuleState(state);
		if (state.getPages() != null) {
			for(IModulePage page : (List<IModulePage>) state.getPages()) {
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

		tankBuilder.setModuleState(state);
		if (state.getPages() != null) {
			for(IModulePage page : (List<IModulePage>) state.getPages()) {
				page.createTank(tankBuilder);
			}
		}
		if(!tankBuilder.isEmpty()){
			return tankBuilder.build();
		}
		return null;
	}

	@Override
	public boolean transferInput(IModularHandler tile, IModuleState state, EntityPlayer player, int slotID, Container container, ItemStack stackItem) {
		return false;
	}

	@Override
	public IModuleState createState(IModular modular, IModuleContainer container) {
		if(FMLCommonHandler.instance().getSide() == Side.CLIENT){
			return createClientState(modular, container);
		}
		return new ModuleState(modular, container);
	}

	@SideOnly(Side.CLIENT)
	protected IModuleState createClientState(IModular modular, IModuleContainer container){
		return new ModuleStateClient(modular, container);
	}

	@Override
	public IModuleState loadStateFromItem(IModuleState state, ItemStack stack) {
		return state;
	}

	@Override
	public List<IModulePage> createPages(IModuleState state) {
		return new ArrayList<>();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public List<IModelInitHandler> getInitModelHandlers(IModuleContainer container) {
		return new ArrayList<>();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean needHandlerReload(IModuleStateClient state) {
		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModelHandler createModelHandler(IModuleState state) {
		return null;
	}

	@Override
	public void assembleModule(IModularAssembler assembler, IModular modular, IPositionedModuleStorage storage, IModuleState state) throws AssemblerException {
	}
}
