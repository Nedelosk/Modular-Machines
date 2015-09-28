package nedelosk.modularmachines.common.core;

import java.util.ArrayList;
import java.util.HashMap;

import nedelosk.modularmachines.api.basic.machine.part.MachineMaterial;
import nedelosk.modularmachines.api.basic.machine.part.MaterialType;

public class MMRegistry {

    public static ArrayList<MachineMaterial> toolMaterials = new ArrayList();
	
    public static void addMachineMaterial(MaterialType type, String materialName, int harvestLevel, int reinforced, String style, int primaryColor)
    {
    	MachineMaterial mat = new MachineMaterial(type, materialName, harvestLevel, reinforced, style, primaryColor);
        if (!toolMaterials.contains(mat))
        {
            toolMaterials.add(mat);
        }
        else
            throw new IllegalArgumentException("[MM] Material is already occupied by " + mat.materialName);
    }
    
    public static void addMachineMaterial(MachineMaterial material)
    {
        if (!toolMaterials.contains(material))
        {
            toolMaterials.add(material);
        }
        else
            throw new IllegalArgumentException("[MM] Material is already occupied by " + material.materialName);
    }
    
}
