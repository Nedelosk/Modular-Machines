package modularmachines.api.modules.containers;

import java.util.Collection;
import java.util.Set;

import com.google.common.collect.Sets;

import modularmachines.api.modules.ModuleData;
import net.minecraft.item.ItemStack;

public class ModuleContainer implements IModuleContainer {

	protected final ItemStack parent;
	protected final Set<ModuleData> datas;

	public ModuleContainer(ItemStack parent, ModuleData... datas) {
		this.parent = parent;
		this.datas = Sets.newHashSet(datas);
	}

	@Override
	public ItemStack getParent() {
		return parent;
	}

	@Override
	public Collection<ModuleData> getDatas() {
		return datas;
	}

	@Override
	public boolean matches(ItemStack stack) {
		if(stack == null) {
			return false;
		}
		return stack.getItem() == parent.getItem();
	}
}
