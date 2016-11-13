package modularmachines.common.modules.pages;

import java.util.Locale;

import net.minecraft.util.text.translation.I18n;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.ModulePage;
import modularmachines.api.modules.state.IModuleState;

public class MainPage<M extends IModule> extends ModulePage<M> {

	protected final String tabTitle;

	public MainPage(String title, IModuleState<M> module) {
		super("MainPage", title, module);
		this.tabTitle = null;
	}

	public MainPage(String title, String tabTitle, IModuleState<M> module) {
		super("MainPage", title, module);
		this.tabTitle = tabTitle;
	}

	@Override
	public String getTabTitle() {
		if (tabTitle == null || tabTitle.isEmpty()) {
			return super.getTabTitle();
		}
		return I18n.translateToLocal("module.page." + tabTitle.toLowerCase(Locale.ENGLISH) + ".name");
	}
}
