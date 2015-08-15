package nedelosk.modularmachines.common.config;

import nedelosk.modularmachines.api.basic.techtree.TechPointTypes;
import nedelosk.modularmachines.api.basic.techtree.TechTreeCategories;
import nedelosk.modularmachines.api.basic.techtree.TechTreeEntry;
import nedelosk.modularmachines.api.basic.techtree.TechTreePage;
import nedelosk.modularmachines.api.basic.techtree.TechTreePage.PageType;
import nedelosk.modularmachines.client.techtree.TechTree;
import nedelosk.nedeloskcore.utils.IOUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class TechTreeConfigsReader {
	
    public static void readCustomTechTreeEntrys() {
            String[] entrysRawData = IOUtils.getLinesArrayFromData(TechTree.readCustomTechTreeEntrys());
            for(int i=0;i<entrysRawData.length;i++) {
                String[] entryData = IOUtils.getData(entrysRawData[i]);
                TechTreeEntry entry = null;
                //0: entry name
                //1: category
                //2: techpoints
                //3: techpoint Type
                //4: col
                //5: row
                //6: iconStack
                //7 - ...: parents
                if(entryData.length < 7)
                	continue;
                ItemStack iconStack = IOUtils.getStack(entryData[6]);
                TechPointTypes type = null;
                for(TechPointTypes typeI : TechPointTypes.values())
                	if(typeI.name().equals(entryData[3]))
                		type = typeI;
                if(TechTreeCategories.getEntryList(entryData[1]) == null)
                	TechTreeCategories.registerCategory(entryData[1], new ResourceLocation("thaumcraft", "textures/items/thaumonomiconcheat.png"), new ResourceLocation("modularmachines", "textures/gui/gui_techtreeback.png"));
                if(iconStack != null && type != null)
                	entry = new TechTreeEntry(entryData[0], entryData[1], Integer.parseInt(entryData[2]), type, Integer.parseInt(entryData[4]), Integer.parseInt(entryData[5]), iconStack);
                if(entryData.length >= 8)
                {
                	if(entryData.length > 8)
                	{
                	String[] parents = new String[entryData.length - 7];
                	int index = 0;
                	for(int a = 7; a < entryData.length;a++)
                	{
                		parents[index] = entryData[a];
                		index++;
                	}
                	entry.setParents(parents);
                	}
                	else
                	{
                	entry.setParents(entryData[7]);
                	}
                }
                
                if(entry != null)
                	TechTreeCategories.addEntry(entry);
      
            }
    }
    
    public static void readCustomTechTreeEntryPages() {
        String[] pagesRawData = IOUtils.getLinesArrayFromData(TechTree.readCustomTechTreeEntryPages());
        for(int i=0;i<pagesRawData.length;i++) {
            String[] pageData = IOUtils.getData(pagesRawData[i]);
            TechTreePage page = null;
            //0: entry name
            //1: pagetype
            //2 - ...:	pagemodifier
            if(pageData[1].equals(PageType.TEXT.name()))
            	page = new TechTreePage(pageData[2]);
            else
            {
            	
            }
            if(page != null && TechTreeCategories.getEntry(pageData[0]) != null)
            {
            	TechTreeEntry entry = TechTreeCategories.getEntry(pageData[0]);
            	if(entry.getPages() == null)
            	{
            		entry.setPages(page);
            	}
            	else
            	{
            		TechTreePage[] pages = entry.getPages();
            		TechTreePage[] pagesNew = new TechTreePage[pages.length + 1];
            		for(int a = 0;a < pages.length;a++)
            			pagesNew[a] = pages[a];
            		pagesNew[pages.length] = page;
            		entry.setPages(pagesNew);
            	}
            }
  
        }
}
	
}
