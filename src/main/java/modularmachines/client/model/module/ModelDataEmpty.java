package modularmachines.client.model.module;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.energy.CapabilityEnergy;

import modularmachines.api.ILocatable;
import modularmachines.api.modules.IModule;
import modularmachines.api.modules.IModuleData;
import modularmachines.api.modules.model.IModelProperty;
import modularmachines.api.modules.model.IModuleModelState;
import modularmachines.api.modules.positions.EnumCasingPositions;
import modularmachines.common.core.Constants;

/**
 * The model data of a empty module.
 */
public class ModelDataEmpty extends ModelData {
	
	private enum Properties implements IModelProperty {
		ENERGY
	}
	
	public static void addModelData(IModuleData data) {
		ModelDataEmpty model = new ModelDataEmpty();
		model.add(Properties.ENERGY, new ResourceLocation(Constants.MOD_ID, "module/energy_input"));
		data.setModel(model);
	}
	
	@Override
	public IModuleModelState createState(IModule module) {
		ModuleModelState moduleModelState = new ModuleModelState();
		if (!(module.getPosition() instanceof EnumCasingPositions)) {
			moduleModelState.set(Properties.ENERGY, false);
			return moduleModelState;
		}
		ILocatable locatable = module.getContainer().getLocatable();
		World world = locatable.getWorldObj();
		EnumFacing facing = module.getFacing();
		BlockPos pos = locatable.getCoordinates().offset(facing);
		TileEntity tileEntity = world.getTileEntity(pos);
		moduleModelState.set(Properties.ENERGY, tileEntity != null && tileEntity.hasCapability(CapabilityEnergy.ENERGY, facing.getOpposite()));
		return moduleModelState;
	}
}
