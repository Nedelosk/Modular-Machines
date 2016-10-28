package de.nedelosk.modularmachines.api.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import de.nedelosk.modularmachines.api.modular.AssemblerException;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularAssembler;
import de.nedelosk.modularmachines.api.modular.assembler.SlotAssembler;
import de.nedelosk.modularmachines.api.modular.assembler.SlotAssemblerStorage;
import de.nedelosk.modularmachines.api.modules.containers.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.containers.IModuleItemContainer;
import de.nedelosk.modularmachines.api.modules.containers.IModuleItemProvider;
import de.nedelosk.modularmachines.api.modules.containers.IModuleProvider;
import de.nedelosk.modularmachines.api.modules.handlers.IModuleContentHandler;
import de.nedelosk.modularmachines.api.modules.models.IModelHandler;
import de.nedelosk.modularmachines.api.modules.position.IModulePositioned;
import de.nedelosk.modularmachines.api.modules.position.IModulePostion;
import de.nedelosk.modularmachines.api.modules.position.IStoragePosition;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.state.IModuleStateClient;
import de.nedelosk.modularmachines.api.modules.state.ModuleState;
import de.nedelosk.modularmachines.api.modules.state.ModuleStateClient;
import de.nedelosk.modularmachines.api.modules.storage.IStorage;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Module extends IForgeRegistryEntry.Impl<IModule> implements IModule {

	protected final String name;

	public Module(String name) {
		this.name = name;
	}

	@Override
	public int getComplexity(IModuleContainer container) {
		IModuleProperties properties = container.getProperties();
		if (properties == null) {
			return -1;
		}
		return properties.getComplexity(container);
	}

	@Override
	public void sendModuleUpdate(IModuleState state) {
	}

	@Override
	public String getDisplayName(IModuleContainer container) {
		return I18n.translateToLocal(container.getUnlocalizedName());
	}

	protected boolean showMaterial(IModuleContainer container) {
		return true;
	}

	protected boolean showName(IModuleContainer container) {
		return !ModuleManager.hasDefaultStack(container.getItemContainer());
	}

	protected boolean showComplexity(IModuleContainer container) {
		return getComplexity(container) >= 0;
	}

	protected boolean showPosition(IModuleContainer container) {
		IModulePostion[] positions = ((IModulePositioned) this).getValidPositions(container);
		return positions != null && positions.length > 0;
	}

	protected boolean showSize(IModuleContainer container) {
		return true;
	}

	protected boolean showProvider(IModuleContainer container, List<String> providerTip) {
		return !providerTip.isEmpty();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addTooltip(List<String> tooltip, ItemStack stack, IModuleContainer container) {
		if (showMaterial(container)) {
			tooltip.add(I18n.translateToLocal("mm.module.tooltip.material") + container.getItemContainer().getMaterial().getLocalizedName());
		}
		if (showName(container)) {
			tooltip.add(I18n.translateToLocal("mm.module.tooltip.name") + container.getDisplayName());
		}
		if (showSize(container)) {
			tooltip.add(I18n.translateToLocal("mm.module.tooltip.size") + container.getItemContainer().getSize().getLocalizedName());
		}
		if (showComplexity(container)) {
			tooltip.add(I18n.translateToLocal("mm.module.tooltip.complexity") + getComplexity(container));
		}
		if (this instanceof IModulePositioned) {
			if (showPosition(container)) {
				IModulePositioned module = (IModulePositioned) this;
				tooltip.add(I18n.translateToLocal("mm.module.tooltip.position") + Arrays.toString(module.getValidPositions(container)).replace("[", "").replace("]", ""));
			}
		}
		List<String> providerTip = new ArrayList<>();
		addProviderTooltip(providerTip, stack, container);
		if (showProvider(container, providerTip)) {
			if (!GuiScreen.isShiftKeyDown()) {
				tooltip.add(TextFormatting.WHITE.toString() + TextFormatting.ITALIC + I18n.translateToLocal("mm.tooltip.holdshift"));
			} else {
				tooltip.addAll(providerTip);
			}
		}
	}

	protected void addProviderTooltip(List<String> tooltip, ItemStack stack, IModuleContainer container) {
		IModuleItemProvider itemProvider = stack.getCapability(ModuleManager.MODULE_PROVIDER_CAPABILITY, null);
		if (itemProvider != null && !itemProvider.isEmpty()) {
			for(IModuleState state : itemProvider) {
				if (state.getModule().equals(this)) {
					for(IModuleContentHandler handler : state.getAllContentHandlers()) {
						handler.addToolTip(tooltip, stack, state);
					}
				}
			}
		}
	}

	@Override
	public boolean isClean(IModuleState state) {
		for(IModuleContentHandler handler : state.getAllContentHandlers()) {
			if (handler.isCleanable() && !handler.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public ResourceLocation getWindowLocation(IModuleItemContainer container) {
		return null;
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
	public List<IModuleContentHandler> createHandlers(IModuleState state) {
		return Lists.newArrayList();
	}

	@Override
	public IModuleState createState(IModuleProvider provider, IModuleContainer container) {
		return createDefaultState(provider, container);
	}

	protected final IModuleState createDefaultState(IModuleProvider provider, IModuleContainer container) {
		IModuleState state;
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
			state = createClientState(provider, container);
		} else {
			state = new ModuleState(provider, container);
		}
		return state;
	}

	@SideOnly(Side.CLIENT)
	protected IModuleState createClientState(IModuleProvider provider, IModuleContainer container) {
		return new ModuleStateClient(provider, container);
	}

	@Override
	public IModuleState loadStateFromItem(IModuleState state, ItemStack stack) {
		return state;
	}

	@Override
	public void saveDataToItem(ItemStack itemStack, IModuleState state) {
	}

	@Override
	public List<IModulePage> createPages(IModuleState state) {
		return new ArrayList<>();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Map<ResourceLocation, ResourceLocation> getModelLocations(IModuleItemContainer container) {
		return Collections.emptyMap();
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
	public void assembleModule(IModularAssembler assembler, IModular modular, IStorage storage, IModuleState state) throws AssemblerException {
	}

	@Override
	public boolean isValid(IModularAssembler assembler, IStoragePosition position, ItemStack stack, SlotAssembler slot, SlotAssemblerStorage storageSlot) {
		return true;
	}

	@Override
	public void onModularAssembled(IModuleState state) {
	}
}
