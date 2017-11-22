package modularmachines.common.inventory;

import net.minecraft.item.ItemStack;

import modularmachines.api.modules.IModule;
import modularmachines.common.utils.ContentContainer;

public class ItemContainer extends ContentContainer<ItemStack> {
	
	protected String backgroundTexture;
	
	public ItemContainer(int index, boolean isInput, IModule module) {
		super(index, isInput, module);
		set(ItemStack.EMPTY);
	}
	
	public ItemContainer(int index, boolean isInput, IModule module, String backgroundTexture) {
		this(index, isInput, module);
		this.backgroundTexture = backgroundTexture;
	}
	
	public String getBackgroundTexture() {
		return backgroundTexture;
	}
}
