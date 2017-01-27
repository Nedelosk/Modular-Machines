package modularmachines.common.utils.content;

import modularmachines.client.model.ModelManager;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IItemModelRegister {

	@SideOnly(Side.CLIENT)
	void registerItemModels(Item item, ModelManager manager);
}
