package modularmachines.client.model.module;

import com.google.common.collect.ImmutableList;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.model.IModelInfo;
import modularmachines.api.modules.model.IModelList;
import modularmachines.api.modules.model.IModuleModelBakery;
import modularmachines.common.core.Constants;

public class BakeryBase implements IModuleModelBakery {
	protected final ImmutableList<ResourceLocation> modelLocations;
	
	public BakeryBase(ResourceLocation... modelLocations) {
		this.modelLocations = ImmutableList.copyOf(modelLocations);
	}
	
	public BakeryBase(String... locations) {
		this.modelLocations = ImmutableList.copyOf(Arrays.stream(locations)
				.map(path -> new ResourceLocation(Constants.MOD_ID, path))
				.collect(Collectors.toList()));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void bakeModel(IModule module, IModelInfo modelInfo, IModelList modelList) {
		for (ResourceLocation location : modelLocations) {
			modelList.add(location);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Collection<ResourceLocation> getDependencies() {
		return modelLocations;
	}
}
