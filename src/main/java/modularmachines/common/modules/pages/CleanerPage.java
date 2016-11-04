package modularmachines.common.modules.pages;

import java.util.Locale;

import modularmachines.api.gui.Button;
import modularmachines.api.modules.IModule;
import modularmachines.api.modules.IModuleModuleCleaner;
import modularmachines.api.modules.ModuleManager;
import modularmachines.api.modules.handlers.filters.IContentFilter;
import modularmachines.api.modules.handlers.inventory.IModuleInventoryBuilder;
import modularmachines.api.modules.state.IModuleState;
import modularmachines.common.network.PacketHandler;
import modularmachines.common.network.packets.PacketModuleCleaner;
import modularmachines.common.utils.Translator;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CleanerPage extends MainPage<IModuleModuleCleaner> {

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
		if (gui != null) {
			gui.getButtonManager().add(new CleanerButton(gui.getButtonManager().getButtons().size(), gui.getGuiLeft() + 32, gui.getGuiTop() + 57));
		}
	}

	private class ItemFilterModule implements IContentFilter<ItemStack, IModule> {

		@Override
		public boolean isValid(int index, ItemStack content, IModuleState<IModule> module) {
			return ModuleManager.getContainerFromItem(content) != null;
		}
	}

	@SideOnly(Side.CLIENT)
	private class CleanerButton extends Button {

		public CleanerButton(int ID, int xPosition, int yPosition) {
			super(ID, xPosition, yPosition, 115, 20, Translator.translateToLocal("module.page." + title.toLowerCase(Locale.ENGLISH) + ".clean" + ".name"));
		}

		@Override
		public void onButtonClick() {
			PacketHandler.sendToServer(new PacketModuleCleaner(moduleState));
		}
	}
}
