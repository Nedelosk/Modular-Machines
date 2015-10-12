package nedelosk.modularmachines.api.materials;

import java.util.Collection;
import java.util.Map;

import com.google.common.collect.Maps;

import nedelosk.modularmachines.api.materials.stats.IMaterialStats;
import net.minecraft.util.StatCollector;

public class Material
{
    public final MaterialType type;
    public final String materialName;
    public final String tipStyle;
    public final int primaryColor;

    public final String identifier;
    public final String oreDict;
    
    protected final Map<String, IMaterialStats> stats = Maps.newLinkedHashMap();

    public Material(MaterialType type, String name, String style, int primaryColor, String oreDict)
    {
        this(type, name, name.toLowerCase().replaceAll(" ", ""), style, primaryColor, oreDict);
    }

    public Material(MaterialType type, String name, String identifier, String style, int primaryColor, String oreDict)
    {
    	this.type = type;
        this.materialName = name;
        this.tipStyle = style;
        this.primaryColor = primaryColor;

        this.identifier = identifier;
        this.oreDict = oreDict;
    }

    public String name ()
    {
        return materialName;
    }

    public String localizedName()
    {
        return StatCollector.translateToLocal("material." + identifier);
    }
    
    public String getOreDict() {
		return oreDict;
	}
    
    public String getIdentifier() {
		return identifier;
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
    
    public Material addStats(IMaterialStats materialStats) {
        this.stats.put(materialStats.getIdentifier(), materialStats);
        return this;
    }
    
    private IMaterialStats getStatsSafe(String identifier) {
        if(identifier == null || identifier.isEmpty()) {
          return null;
        }

        for(IMaterialStats stat : stats.values()) {
          if(identifier.equals(stat.getIdentifier())) {
            return stat;
          }
        }

        return null;
    }
    
    @SuppressWarnings("unchecked")
    public <S extends IMaterialStats> S getStats(String identifier) {
      return (S) getStatsSafe(identifier);
    }

    public Collection<IMaterialStats> getAllStats() {
      return stats.values();
    }

    public boolean hasStats(String identifier) {
      return getStats(identifier) != null;
    }
}
