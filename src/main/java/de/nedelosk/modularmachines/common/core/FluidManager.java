package de.nedelosk.modularmachines.common.core;

import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.Fluid;

public class FluidManager {

	public static Fluid Steam;

	public static void registerFluids() {
		Steam = Registry.registerFluid("steam", 500, Material.LAVA, true, true);
	}
}
