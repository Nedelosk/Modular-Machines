package de.nedelosk.modularmachines.common.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import com.google.common.collect.Lists;

import de.nedelosk.modularmachines.api.ModularMachinesApi;
import de.nedelosk.modularmachines.api.modular.AssemblerException;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularAssembler;
import de.nedelosk.modularmachines.api.modules.IModelInitHandler;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModuleProperties;
import de.nedelosk.modularmachines.api.modules.handlers.ICleanableModuleContentHandler;
import de.nedelosk.modularmachines.api.modules.handlers.IModuleContentHandler;
import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventory;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventoryBuilder;
import de.nedelosk.modularmachines.api.modules.handlers.tank.IModuleTank;
import de.nedelosk.modularmachines.api.modules.handlers.tank.IModuleTankBuilder;
import de.nedelosk.modularmachines.api.modules.integration.IModuleJEI;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.items.IModuleProvider;
import de.nedelosk.modularmachines.api.modules.models.IModelHandler;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.state.IModuleStateClient;
import de.nedelosk.modularmachines.api.modules.state.ModuleState;
import de.nedelosk.modularmachines.api.modules.state.ModuleStateClient;
import de.nedelosk.modularmachines.api.modules.storage.IModuleStorage;
import de.nedelosk.modularmachines.api.modules.storaged.EnumModuleSize;
import de.nedelosk.modularmachines.api.modules.storaged.EnumWallType;
import de.nedelosk.modularmachines.common.items.ItemModule;
import de.nedelosk.modularmachines.common.modules.handlers.inventorys.ModuleInventoryBuilder;
import de.nedelosk.modularmachines.common.modules.handlers.tanks.ModuleTankBuilder;
import de.nedelosk.modularmachines.common.modules.storaged.tools.jei.ModuleJeiPlugin;
import de.nedelosk.modularmachines.common.utils.Translator;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class Module extends IForgeRegistryEntry.Impl<IModule> implements IModule {

	protected final String name;

	public Module(String name) {
		this.name = name;
	}

	@Override
	public int getComplexity(IModuleContainer container) {
		IModuleProperties properties = container.getProperties();
		if(properties == null){
			return -1;
		}
		return properties.getComplexity(container);
	}

	@Override
	public EnumModuleSize getSize(IModuleContainer container) {
		IModuleProperties properties = container.getProperties();
		if(properties == null){
			return null;
		}
		return properties.getSize(container);
	}

	@Override
	public String getDisplayName(IModuleContainer container) {
		return container.getMaterial().getLocalizedName() + " " + Translator.translateToLocal(container.getUnlocalizedName());
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addTooltip(List<String> tooltip, ItemStack stack, IModuleContainer container) {
		if(!(container.getItemStack().getItem() instanceof ItemModule)){
			tooltip.add(Translator.translateToLocal("mm.module.tooltip.name") + container.getDisplayName());
		}
		if(getSize(container) != null){
			tooltip.add(Translator.translateToLocal("mm.module.tooltip.size") + getSize(container).getLocalizedName());
		}
		if(getComplexity(container) >= 0){
			tooltip.add(Translator.translateToLocal("mm.module.tooltip.complexity") + getComplexity(container));
		}
		if(getPosition(container) != null){
			tooltip.add(Translator.translateToLocal("mm.module.tooltip.position") + Translator.translateToLocal("module.storage." + getPosition(container).getName() + ".name"));
		}
		IModuleProvider provider = stack.getCapability(ModularMachinesApi.MODULE_PROVIDER_CAPABILITY, null);
		if(provider != null && provider.hasState()){
			if(!GuiScreen.isShiftKeyDown()){
				tooltip.add(TextFormatting.WHITE.toString() + TextFormatting.ITALIC + Translator.translateToLocal("mm.tooltip.holdshift"));
			}else{
				IModuleState state = provider.createState(null);
				for(IModuleContentHandler handler : (List<IModuleContentHandler>)state.getContentHandlers()){
					handler.addToolTip(tooltip, stack, state);
				}
			}
		}
	}

	@Override
	public boolean isClean(IModuleState state) {
		for(IModuleContentHandler handler : (List<IModuleContentHandler>)state.getContentHandlers()){
			if(handler instanceof ICleanableModuleContentHandler){
				if(!((ICleanableModuleContentHandler) handler).isEmpty()){
					return false;
				}
			}
		}
		return true;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public ResourceLocation getWindowLocation(IModuleContainer container) {
		return new ResourceLocation("modularmachines:module/" + container.getMaterial().getName().toLowerCase(Locale.ENGLISH)+ "/windows/" + getSize(container).getName());
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
		return state.getContainer().getItemStack().copy();
	}

	@Override
	public List<IModuleContentHandler> createHandlers(IModuleState state){
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
	public IModuleState createState(IModular modular, IModuleContainer container) {
		if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT){
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
	public void assembleModule(IModularAssembler assembler, IModular modular, IModuleStorage storage, IModuleState state) throws AssemblerException {
	}

	public void openJEI(IModuleState state){
		if(this instanceof IModuleJEI){
			Loader.instance();
			if(Loader.isModLoaded("JEI")){
				ModuleJeiPlugin.jeiRuntime.getRecipesGui().showCategories(Arrays.asList(((IModuleJEI)this).getJEIRecipeCategorys(state.getContainer())));
			}
		}
	}
}
