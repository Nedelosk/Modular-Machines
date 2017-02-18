package modularmachines.common.modules.logic;

import java.util.HashMap;
import java.util.Map;

import modularmachines.api.modules.logic.LogicComponent;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModelComponent extends LogicComponent {
	
	@SideOnly(Side.CLIENT)
	protected final Map<Integer, IBakedModel> models = new HashMap<>();

	@SideOnly(Side.CLIENT)
	public void setModel(int index, IBakedModel model) {
		models.put(index, model);
	}
	
	@SideOnly(Side.CLIENT)
	public IBakedModel getModel(int index) {
		return models.get(index);
	}
	
}
