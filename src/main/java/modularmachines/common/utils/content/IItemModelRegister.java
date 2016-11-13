package modularmachines.common.utils.content;

import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.client.model.ModelManager;

public interface IItemModelRegister {

	@SideOnly(Side.CLIENT)
	void registerItemModels(Item item, ModelManager manager);
}
