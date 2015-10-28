package nedelosk.modularmachines.api;

import java.util.ArrayList;
import java.util.HashMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import nedelosk.modularmachines.api.multiblocks.IAirHeatingPlantRecipe;
import nedelosk.modularmachines.api.multiblocks.IBlastFurnaceRecipe;
import net.minecraftforge.fluids.Fluid;

public class ModularMachinesApi {

	public static IBlastFurnaceRecipe blastFurnace;

	public static IAirHeatingPlantRecipe airHeatingPlant;

	public static final ArrayList<Fluid> airHeatingPlantGas = Lists.newArrayList();

	public static final HashMap<Fluid, Integer> fermenterFluid = Maps.newHashMap();

	public static void addAirHeatingPlantGas(Fluid fluid) {
		airHeatingPlantGas.add(fluid);
	}

}
