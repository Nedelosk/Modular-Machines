package de.nedelosk.modularmachines.client.statemapper;

import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;

public class BlankStateMapper extends StateMapperBase{

	@Override
	public Map<IBlockState, ModelResourceLocation> putStateModelLocations(Block blockIn) {
		return mapStateModelLocations;
	}

	@Override
	protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
		return null;
	}
}
