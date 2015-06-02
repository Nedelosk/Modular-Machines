package nedelosk.forestbotany.api.botany.book;

import org.w3c.dom.Document;

import nedelosk.forestbotany.api.genetics.plants.IPlant;
import nedelosk.nedeloskcore.api.NCoreApi;
import nedelosk.nedeloskcore.api.book.BookEntry;
import nedelosk.nedeloskcore.api.book.BookLevel;
import nedelosk.nedeloskcore.api.book.Knowledge;

public class BookPlantEntry extends BookEntry {
	
	public final IPlant plant;
	public final Document doc;
	
	public BookPlantEntry(String unlocalizedName, String category, BookLevel level, String bookdata, IPlant plant, Document doc, Knowledge... knowledges) {
		super(unlocalizedName, category, level, bookdata);
		this.plant = plant;
		this.doc = doc;
	}
	
	public BookPlantEntry(String unlocalizedName, String categoryName, BookLevel level, String bookdata, IPlant plant, Document doc) {
		super(unlocalizedName, categoryName, level, bookdata);
		this.plant = plant;
		this.doc = doc;
	}
	
	public BookPlantEntry(String unlocalizedName, String categoryName, String bookdata, IPlant plant, Document doc, Knowledge... knowledges) {
		super(unlocalizedName, categoryName, bookdata, knowledges);
		this.plant = plant;
		this.doc = doc;
	}
	
	public BookPlantEntry(String unlocalizedName, String categoryName, String bookdata, IPlant plant, Document doc) {
		super(unlocalizedName, categoryName, bookdata);
		this.plant = plant;
		this.doc = doc;
	}

}
