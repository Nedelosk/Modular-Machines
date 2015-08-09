package nedelosk.modularmachines.client.techtree;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.common.ModularMachines;
import nedelosk.nedeloskcore.utils.IOUtils;
import net.minecraft.client.settings.KeyBinding;

public class TechTree {

	@SideOnly(Side.CLIENT)
	public static KeyBinding techTree = new KeyBinding("forest.mm.techtree.key", Keyboard.KEY_U, "forest.mm.techtree");
	
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
