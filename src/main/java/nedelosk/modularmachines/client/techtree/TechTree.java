package nedelosk.modularmachines.client.techtree;

import nedelosk.modularmachines.common.ModularMachines;
import nedelosk.nedeloskcore.utils.IOUtils;

public class TechTree {
	
    public static String readCustomTechTreeEntrys() {
        return IOUtils.readOrWrite(ModularMachines.configFolder.getPath(), "CustomTechTreeEntrys", customTechTreeEntryInstructions);
    }
    
    public static String readCustomTechTreeEntryPages() {
        return IOUtils.readOrWrite(ModularMachines.configFolder.getPath(), "CustomTechTreeEntryPages", customTechTreeEntryPagesInstructions);
    }
    
    private static final String customTechTreeEntryInstructions = 
    		"";
    
    private static final String customTechTreeEntryPagesInstructions = 
    		"";
	
}
