package nedelosk.modularmachines.api.basic.machine.part;

import net.minecraft.util.StatCollector;

public class MachineMaterial
{
    public final MaterialType type;
    public final String materialName;
    public final int harvestLevel;
    public final int reinforced;
    public final String tipStyle;
    public final int primaryColor;

    public final String identifier;

    public MachineMaterial(MaterialType type, String name, int level, int reinforced, String style, int primaryColor)
    {
        this(type, name, "material." + name.toLowerCase().replaceAll(" ", ""), level, reinforced, style, primaryColor);
    }

    public MachineMaterial(MaterialType type, String name, String identifier, int level, int reinforced, String style, int primaryColor)
    {
    	this.type = type;
        this.materialName = name;
        this.harvestLevel = level;
        this.reinforced = reinforced;
        this.tipStyle = style;
        this.primaryColor = primaryColor;

        this.identifier = identifier;
    }

    public String name ()
    {
        return materialName;
    }

    public String localizedName()
    {
        return StatCollector.translateToLocal(identifier);
    }
    
    public String getIdentifier() {
		return identifier;
	}

    public int harvestLevel ()
    {
        return this.harvestLevel;
    }

    public int reinforced ()
    {
        return this.reinforced;
    }

    public String style ()
    {
        return this.tipStyle;
    }

    public int primaryColor ()
    {
        return this.primaryColor;
    }
    
    public MaterialType getType() {
		return type;
	}
}
