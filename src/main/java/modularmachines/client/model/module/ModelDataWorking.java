package modularmachines.client.model.module;

import com.google.common.base.Function;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.tileentity.TileEntity;

import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.IModelState;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.Module;
import modularmachines.api.modules.ModuleData;
import modularmachines.api.modules.model.ModelLocation;
import modularmachines.api.modules.storages.IStorage;
import modularmachines.common.modules.IModuleWorking;

@SideOnly(Side.CLIENT)
public class ModelDataWorking extends ModelData {

	public static final String ON = "on";
	public static final String OFF = "off";

	public static void initModelData(ModuleData data, ModelLocation locationOn, ModelLocation locationOff){
		ModelDataWorking working = new ModelDataWorking();
		working.addLocation(ON, locationOn);
		working.addLocation(OFF, locationOff);
		data.addModel(TileEntity.class, working);
	}
	
	@Override
	public IBakedModel getModel(Module module, IStorage storage, IModelState modelState, VertexFormat format, Function bakedTextureGetter) {
		if(module instanceof IModuleWorking){
			IModuleWorking working = (IModuleWorking) module;
			if (working.isWorking()) {
				return ModelLoader.getModel(get(ON), format);
			} else {
				return ModelLoader.getModel(get(OFF), format);
			}
		}
		return ModelLoaderRegistry.getMissingModel().bake(modelState, format, bakedTextureGetter);
	}
}
