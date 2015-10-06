package nedelosk.modularmachines.common.core;

import static net.minecraft.util.EnumChatFormatting.AQUA;
import static net.minecraft.util.EnumChatFormatting.DARK_GRAY;
import static net.minecraft.util.EnumChatFormatting.DARK_RED;
import static net.minecraft.util.EnumChatFormatting.GOLD;
import static net.minecraft.util.EnumChatFormatting.GRAY;
import static net.minecraft.util.EnumChatFormatting.GREEN;
import static net.minecraft.util.EnumChatFormatting.RED;
import static net.minecraft.util.EnumChatFormatting.WHITE;
import static net.minecraft.util.EnumChatFormatting.YELLOW;

import java.util.ArrayList;

import nedelosk.modularmachines.api.materials.Material;
import nedelosk.modularmachines.api.materials.MaterialType;
import nedelosk.modularmachines.common.materials.EnergyConductsState;
import nedelosk.modularmachines.common.materials.EnergyStorageState;
import nedelosk.modularmachines.common.materials.MachineState;

public class MMRegistry {

    public static ArrayList<Material> materials = new ArrayList();
	
    public static Material addMachineMaterial(MaterialType type, String materialName, int harvestLevel, int reinforced, String style, int primaryColor, String oreDict, int tier, int engineSpeed)
    {
    	Material mat = new Material(type, materialName, style, primaryColor, oreDict).addStats(new MachineState(harvestLevel, reinforced, tier, engineSpeed));
        if (!materials.contains(mat))
        {
            materials.add(mat);
            return mat;
        }
        else
            throw new IllegalArgumentException("[MM] Material is already occupied by " + mat.materialName);
    }
    
    public static Material addMaterial(Material material)
    {
        if (!materials.contains(material))
        {
            materials.add(material);
            return material;
        }
        else
            throw new IllegalArgumentException("[MM] Material is already occupied by " + material.materialName);
    }
    
    public static Material Wood = MMRegistry.addMachineMaterial(MaterialType.WOOD, "Wood", 1, 0, YELLOW.toString(), 0x755821, "Wood", 1, 600);
    public static Material Stone = MMRegistry.addMachineMaterial(MaterialType.STONE, "Stone", 1, 0, GRAY.toString(), 0x7F7F7F, "Stone", 1, 650);
    public static Material Iron = MMRegistry.addMachineMaterial(MaterialType.METAL_Custom, "Iron", 2, 1, WHITE.toString(), 0xDADADA, "Iron", 1, 500).addStats(new EnergyConductsState(100));
    public static Material Flint = MMRegistry.addMachineMaterial(MaterialType.CUSTOM, "Flint", 1, 0, DARK_GRAY.toString(), 0x484848, "Flint", 1, 700);
    public static Material Bone = MMRegistry.addMachineMaterial(MaterialType.CUSTOM, "Bone", 1, 0, YELLOW.toString(), 0xEDEBCA, "Bone", 1, 700);
    public static Material Netherrack = MMRegistry.addMachineMaterial(MaterialType.STONE, "Netherrack", 2, 0, DARK_RED.toString(), 0x833238, "Netherrack", 1, 650);
    public static Material Tin = MMRegistry.addMachineMaterial(MaterialType.METAL, "Tin", 1, 1, WHITE.toString(), 0xCACECF, "Tin", 1, 550);
    public static Material Copper = MMRegistry.addMachineMaterial(MaterialType.METAL, "Copper", 1, 0, RED.toString(), 0xCC6410, "Copper", 1, 575).addStats(new EnergyConductsState(580));
    public static Material Bronze = MMRegistry.addMachineMaterial(MaterialType.METAL, "Bronze", 2, 1, GOLD.toString(), 0xCA9956, "Bronze", 1, 500);
    public static Material Steel = MMRegistry.addMachineMaterial(MaterialType.METAL, "Steel", 4, 2, GRAY.toString(), 0xA0A0A0, "Steel", 3, 375).addStats(new EnergyConductsState(14));
    public static Material Niobium = MMRegistry.addMachineMaterial(MaterialType.METAL, "Niobium", 3, 2, YELLOW.toString(), 0xD5BA7D, "Niobium", 2, 450);
    public static Material Tantalum = MMRegistry.addMachineMaterial(MaterialType.METAL, "Tantalum", 3, 2, GRAY.toString(), 0xBEB9AF, "Tantalum", 2, 450);
    public static Material Silver = MMRegistry.addMachineMaterial(MaterialType.METAL, "Silver", 1, 0, WHITE.toString(), 0xD4E3E6, "Silver", 2, 500).addStats(new EnergyConductsState(610));
    public static Material Plastic = MMRegistry.addMachineMaterial(MaterialType.CUSTOM, "Plastic", 1, 0, WHITE.toString(), 0xD4E3E6, "Plastic", 2, 600);
    public static Material Diamond = MMRegistry.addMachineMaterial(MaterialType.CRYTAL, "Diamond", 0, 0, AQUA.toString(), 0x34C0E6, "Diamond", 2, 350).addStats(new EnergyStorageState(250000));
    public static Material Emerald = MMRegistry.addMachineMaterial(MaterialType.CRYTAL, "Emerald", 0, 0, GREEN.toString(), 0x35AB1B, "Emerald", 2, 325).addStats(new EnergyStorageState(200000));
    public static Material Ruby = MMRegistry.addMachineMaterial(MaterialType.CRYTAL, "Ruby", 0, 0, RED.toString(), 0xD0122B, "Ruby", 1, 450).addStats(new EnergyStorageState(100000));
    public static Material Cobalt;
    public static Material Ardite;
    public static Material Manyullyn;
    public static Material Alumite;
    public static Material Pig_Iron;
    
}
