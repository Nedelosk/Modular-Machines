package de.nedelosk.modularmachines.common.modules;

import java.awt.Color;
import java.util.List;
import java.util.Locale;

import de.nedelosk.modularmachines.api.ModularMachinesApi;
import de.nedelosk.modularmachines.api.gui.Button;
import de.nedelosk.modularmachines.api.gui.IContainerBase;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModuleModuleCleaner;
import de.nedelosk.modularmachines.api.modules.IModuleTickable;
import de.nedelosk.modularmachines.api.modules.handlers.IContentFilter;
import de.nedelosk.modularmachines.api.modules.handlers.IModuleContentHandler;
import de.nedelosk.modularmachines.api.modules.handlers.ICleanableModuleContentHandler;
import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventory;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventoryBuilder;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.slots.SlotModule;
import de.nedelosk.modularmachines.api.modules.items.IModuleColored;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.items.IModuleProvider;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storage.IAddableModuleStorage;
import de.nedelosk.modularmachines.api.modules.storaged.EnumModuleSize;
import de.nedelosk.modularmachines.api.modules.storaged.EnumPosition;
import de.nedelosk.modularmachines.common.modules.handlers.ModulePage;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketModuleClean;
import de.nedelosk.modularmachines.common.utils.Translator;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModuleModuleCleaner extends Module implements IModuleModuleCleaner, IModuleColored{

	public ModuleModuleCleaner(String name, int complexity) {
		super(name, complexity);
	}

	@Override
	public EnumPosition getPosition(IModuleContainer container) {
		return EnumPosition.INTERNAL;
	}

	@Override
	public EnumModuleSize getSize() {
		return EnumModuleSize.SMALL;
	}
	
	@Override
	public List<IModulePage> createPages(IModuleState state) {
		List<IModulePage> pages = super.createPages(state);
		pages.add(new ModuleClearerPage("Basic", state));
		return pages;
	}

	@Override
	public void updateServer(IModuleState<IModule> state, int tickCount) {
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void updateClient(IModuleState<IModule> state, int tickCount) {
	}
	
	@Override
	public void cleanModule(IModuleState state) {
		IModuleInventory inventory = (IModuleInventory) state.getContentHandler(IModuleInventory.class);
		ItemStack stack = inventory.getStackInSlot(0);
		if(stack != null){
			IModuleProvider provider = stack.getCapability(ModularMachinesApi.MODULE_PROVIDER_CAPABILITY, null);
			IModuleState moduleState = provider.createState(null);
			for(IModuleContentHandler handler : (List<IModuleContentHandler>)moduleState.getContentHandlers()){
				if(handler instanceof ICleanableModuleContentHandler){
					((ICleanableModuleContentHandler)handler).cleanHandler(state);
				}
			}
			provider.setState(null);
		}
	}
	
	public class ModuleClearerPage extends ModulePage{

		public ModuleClearerPage(String pageID, IModuleState module) {
			super(pageID, "cleaner", module);
		}
		
		public ModuleClearerPage(String pageID, String title, IModuleState module) {
			super(pageID, title, module);
		}
		
		@Override
		public void createInventory(IModuleInventoryBuilder invBuilder) {
			invBuilder.addInventorySlot(true, 80, 35, new ItemFilterModule());
		}
		
		@Override
		public void createSlots(IContainerBase container, List modularSlots) {
			modularSlots.add(new SlotModule(state, 0));
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void addButtons() {
			super.addButtons();
			if(gui != null){
				gui.getButtonManager().add(new CleanerButton(gui.getButtonManager().getButtons().size(), gui.getGuiLeft() + 32, gui.getGuiTop() + 57));
			}
		}
		
		private class CleanerButton extends Button{
			public CleanerButton(int ID, int xPosition, int yPosition) {
				super(ID, xPosition, yPosition, 115, 20, Translator.translateToLocal("module.page." + title.toLowerCase(Locale.ENGLISH) + ".clean" + ".name"));
			}
			
			@Override
			public void onButtonClick() {
				PacketHandler.INSTANCE.sendToServer(new PacketModuleClean(state));
			}
			
		}
		
	}
	
	private class ItemFilterModule implements IContentFilter<ItemStack, IModule>{
		@Override
		public boolean isValid(int index, ItemStack content, IModuleState<IModule> module) {
			return ModularMachinesApi.getContainerFromItem(content) != null;
		}
	}

	@Override
	public int getColor() {
		return Color.GREEN.getRGB();
	}
}
