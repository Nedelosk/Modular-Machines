package nedelosk.forestday.common.registrys;

import nedelosk.forestbotany.api.botany.book.BookPlantEntry;
import nedelosk.forestbotany.api.genetics.plants.IPlant;
import nedelosk.forestbotany.common.genetics.PlantManager;
import nedelosk.forestbotany.common.genetics.templates.crop.CropChromosome;
import nedelosk.forestbotany.common.genetics.templates.crop.CropManager;
import nedelosk.nedeloskcore.api.book.BookCategory;
import nedelosk.nedeloskcore.api.book.BookEntry;
import nedelosk.nedeloskcore.common.book.BookData;
import nedelosk.nedeloskcore.common.book.BookDatas;
import nedelosk.nedeloskcore.common.book.note.NoteText;
import net.minecraft.util.ResourceLocation;

public class ForestdayEntryRegistry {

	public static BookData lumberjackData;
	public static BookCategory woodCategory;
	public static BookEntry basicWood;
	public static BookEntry basicAxe;
	public static BookEntry basicWorkbanch;
	
	public static BookData plantData;
	
	public static void preInit()
	{
		registerBookData();
	}
	
	public static void registerBookData()
	{
		lumberjackData = new BookData("lumberjack");
		
		//Basics
		lumberjackData.registerCategory("basic", new ResourceLocation("textures/items/iron_axe.png"));
		BookDatas.registerBookData(lumberjackData);
		basicWood = new BookEntry("baseWood", "basic", "lumberjack").setNote(new NoteText("baseWood", 0, lumberjackData), new NoteText("baseWood", 1, lumberjackData)).registerEntry();
		basicAxe = new BookEntry("baseAxe", "basic", "lumberjack").setNote(new NoteText("baseAxe", 0, lumberjackData)).registerEntry();
		basicWorkbanch = new BookEntry("baseWorkbanch", "basic", lumberjackData.tag).setNote(new NoteText("baseWorkbanch", 0, lumberjackData)).registerEntry();
		
		plantData = new BookData("plants");
		
		plantData.registerCategory("plants", new ResourceLocation("forestbotany", "textures/gui/book/manual_crop/category/crops.png"));
		BookDatas.registerBookData(plantData);
		
		for(IPlant plant : PlantManager.cropManager.getTemplates())
		{
			BookEntry entry = new BookPlantEntry("plant" + plant.getGenome().getActiveAllele(CropChromosome.PLANT).getUID().replace("fb.plant.crop", ""), "plants", "plants", plant, BookDatas.crops).registerEntry();
		}
		
	}
	
}
