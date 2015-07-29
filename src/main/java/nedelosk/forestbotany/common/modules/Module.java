package nedelosk.forestbotany.common.modules;

import java.io.File;

import cpw.mods.fml.common.Loader;
import nedelosk.forestbotany.common.core.registrys.ItemRegistry;
import nedelosk.forestbotany.common.genetics.PlantManager;
import nedelosk.forestbotany.common.genetics.templates.tree.TreeManager;
import nedelosk.forestbotany.common.items.ItemSapling;
import nedelosk.nedeloskcore.common.core.NedeloskCore;

public class Module {

	public static File configFolderNedelosk = new File(Loader.instance().getConfigDir(), "Nedelosk_Core");
	public static File configFolder = new File(configFolderNedelosk, "forest-botany");
	
	public void preInit(){}
	
	public void init(){}
	
	public void postInit(){}
	
}
