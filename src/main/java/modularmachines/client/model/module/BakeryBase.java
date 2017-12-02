package modularmachines.client.model.module;

import com.google.common.collect.ImmutableList;

import java.util.Collection;

import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.model.IModelInfo;
import modularmachines.api.modules.model.IModelList;
import modularmachines.api.modules.model.IModuleModelBakery;

@SideOnly(Side.CLIENT)
public class BakeryBase implements IModuleModelBakery {
	protected final ImmutableList<ResourceLocation> modelLocations;
	
	public BakeryBase(ResourceLocation... modelLocations) {
		this.modelLocations = ImmutableList.copyOf(modelLocations);
	}
	
	@Override
	public void bakeModel(IModule module, IModelInfo modelInfo, IModelList modelList) {
		for (ResourceLocation location : modelLocations) {
			modelList.add(location);
		}
	}
	
	@Override
	public Collection<ResourceLocation> getDependencies() {
		return modelLocations;
	}
}
