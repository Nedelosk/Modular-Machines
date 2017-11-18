package modularmachines.common.modules.logic;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.renderer.block.model.IBakedModel;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.logic.LogicComponent;

public class ModelComponent extends LogicComponent {
	
	@SideOnly(Side.CLIENT)
	protected final Map<Integer, IBakedModel> models = new HashMap<>();
	
	@SideOnly(Side.CLIENT)
	public void setModel(int index, IBakedModel model) {
		models.put(index, model);
	}
	
	@Nullable
	@SideOnly(Side.CLIENT)
	public IBakedModel getModel(int index) {
		return models.get(index);
	}
	
}
