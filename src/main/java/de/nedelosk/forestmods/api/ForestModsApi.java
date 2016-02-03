package de.nedelosk.forestmods.api;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import de.nedelosk.forestmods.api.internal.DummyInternalMethodHandler;
import de.nedelosk.forestmods.api.internal.IInternalMethodHandler;
import de.nedelosk.forestmods.api.multiblocks.IAirHeatingPlantRecipe;
import de.nedelosk.forestmods.api.multiblocks.IBlastFurnaceRecipe;
import net.minecraftforge.fluids.Fluid;

public class ForestModsApi {

	public static IInternalMethodHandler handler = new DummyInternalMethodHandler();
	public static IBlastFurnaceRecipe blastFurnace;
	public static IAirHeatingPlantRecipe airHeatingPlant;
	public static final ArrayList<Fluid> airHeatingPlantGas = Lists.newArrayList();
	public static final HashMap<Fluid, Integer> fermenterFluid = Maps.newHashMap();

	public static void addAirHeatingPlantGas(Fluid fluid) {
		airHeatingPlantGas.add(fluid);
	}
}
