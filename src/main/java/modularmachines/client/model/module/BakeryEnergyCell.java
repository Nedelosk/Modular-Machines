package modularmachines.client.model.module;

import com.google.common.collect.ImmutableList;

import java.util.Collection;

import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.components.handlers.IEnergyHandlerComponent;
import modularmachines.api.modules.model.IModelInfo;
import modularmachines.api.modules.model.IModelList;
import modularmachines.common.core.Constants;

public class BakeryEnergyCell extends BakeryBase {
	public BakeryEnergyCell(String... locations) {
		super(locations);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void bakeModel(IModule module, IModelInfo modelInfo, IModelList modelList) {
		super.bakeModel(module, modelInfo, modelList);
		IEnergyHandlerComponent energyStorage = module.getComponent(IEnergyHandlerComponent.class);
		if (energyStorage == null) {
			return;
		}
		int scaledEnergy = energyStorage.getScaledEnergyStored(8);
		modelList.add(new ResourceLocation(Constants.MOD_ID, "module/cells/meter_" + scaledEnergy));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Collection<ResourceLocation> getDependencies() {
		return ImmutableList.of(
				modelLocations.get(0),
				new ResourceLocation(Constants.MOD_ID, "module/cells/meter_0"),
				new ResourceLocation(Constants.MOD_ID, "module/cells/meter_1"),
				new ResourceLocation(Constants.MOD_ID, "module/cells/meter_2"),
				new ResourceLocation(Constants.MOD_ID, "module/cells/meter_3"),
				new ResourceLocation(Constants.MOD_ID, "module/cells/meter_4"),
				new ResourceLocation(Constants.MOD_ID, "module/cells/meter_5"),
				new ResourceLocation(Constants.MOD_ID, "module/cells/meter_6"),
				new ResourceLocation(Constants.MOD_ID, "module/cells/meter_7"),
				new ResourceLocation(Constants.MOD_ID, "module/cells/meter_8"));
	}
}
