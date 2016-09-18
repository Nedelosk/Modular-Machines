package de.nedelosk.modularmachines.common.modules.pages;

import java.util.Locale;

import de.nedelosk.modularmachines.api.gui.Button;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModuleModuleCleaner;
import de.nedelosk.modularmachines.api.modules.ModuleManager;
import de.nedelosk.modularmachines.api.modules.handlers.filters.IContentFilter;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventoryBuilder;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketModuleClean;
import de.nedelosk.modularmachines.common.utils.Translator;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CleanerPage extends MainPage<IModuleModuleCleaner>{

	public CleanerPage(IModuleState<IModuleModuleCleaner> module) {
		super("cleaner", module);
	}

	@Override
	public void createInventory(IModuleInventoryBuilder invBuilder) {
		invBuilder.addInventorySlot(true, 80, 35, new ItemFilterModule());
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addButtons() {
		super.addButtons();
		if(gui != null){
			gui.getButtonManager().add(new CleanerButton(gui.getButtonManager().getButtons().size(), gui.getGuiLeft() + 32, gui.getGuiTop() + 57));
		}
	}

	private class ItemFilterModule implements IContentFilter<ItemStack, IModule>{
		@Override
		public boolean isValid(int index, ItemStack content, IModuleState<IModule> module) {
			return ModuleManager.getContainerFromItem(content) != null;
		}
	}

	@SideOnly(Side.CLIENT)
	private class CleanerButton extends Button{
		public CleanerButton(int ID, int xPosition, int yPosition) {
			super(ID, xPosition, yPosition, 115, 20, Translator.translateToLocal("module.page." + title.toLowerCase(Locale.ENGLISH) + ".clean" + ".name"));
		}

		@Override
		public void onButtonClick() {
			PacketHandler.INSTANCE.sendToServer(new PacketModuleClean(moduleState));
		}

	}

}
