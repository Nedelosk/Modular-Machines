package de.nedelosk.forestmods.common.core;

import de.nedelosk.forestmods.library.core.Registry;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.Fluid;

public class FluidManager {

	public static Fluid White_Pig_Iron;
	public static Fluid Gray_Pig_Iron;
	public static Fluid Steel;
	public static Fluid Slag;
	public static Fluid Gas_Blastfurnace;
	public static Fluid Air_Hot;
	public static Fluid Air;
	public static Fluid Steam;

	public static void registerFluids() {
		White_Pig_Iron = Registry.registerFluid("white_pig_iron", 1500, Material.lava, true, false).setDensity(3000).setViscosity(6000);
		Gray_Pig_Iron = Registry.registerFluid("gray_pig_iron", 1500, Material.lava, true, false).setDensity(3000).setViscosity(6000);
		Steel = Registry.registerFluid("steel", 1500, Material.lava, true, false).setDensity(3000).setViscosity(6000);
		Slag = Registry.registerFluid("slag", 100, Material.lava, true, false).setDensity(3000).setViscosity(6000);
		Gas_Blastfurnace = Registry.registerFluid("gas_blastfurnace", 200, Material.water, true, true);
		Air_Hot = Registry.registerFluid("air_hot", 750, Material.lava, true, true);
		Air = Registry.registerFluid("air", 0, Material.water, true, true);
		Steam = Registry.registerFluid("steam", 500, Material.lava, true, true);
	}
}
