package modularmachines.common.modules.pages;

import java.util.Locale;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.ModuleManager;
import modularmachines.common.network.PacketHandler;
import modularmachines.common.utils.Translator;

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
