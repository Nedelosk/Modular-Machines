package nedelosk.nedeloskcore.common.book;

import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import nedelosk.forestbotany.common.core.ForestBotany;
import nedelosk.nedeloskcore.api.book.BookCategory;
import nedelosk.nedeloskcore.api.book.BookEntry;
import net.minecraft.client.Minecraft;

import org.w3c.dom.Document;

public class BookDatas {

	public static Map<String, BookData> bookdatas = new HashMap();
	
	public static BookData getBookData(String key)
	{
		return bookdatas.get(key);
	}
	
	public static BookEntry getEntry(String key)
	{
		Collection books = bookdatas.values();
		for(Object book : books)
		{
			Collection cats = ((BookData)book).map.values();
			for(Object cat : cats)
			{
				Collection entrys = ((BookCategory)cat).entrys.values();
				for(Object entry : entrys)
				{
					if(((BookEntry)entry).key.equals(key))
					{
						return (BookEntry)entry;
					}
				}
			}
		}
		return null;
	}
	
	public static void registerBookData(BookData data)
	{
		bookdatas.put(data.tag, data);
	}
	
	public static void addEntry(BookEntry entry)
	{
		BookData data = getBookData(entry.bookdata);
		BookCategory c = data.getCategory(entry.category);
		if(c == null)
		{
			return;
		}
		c.entrys.put(entry.key, entry);
	}
	
    public static Document crops;

    public static void readManuals() {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        String CurrentLanguage = Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage().getLanguageCode();

        Document crops_cl = readManual("/assets/forestbotany/book/crops_" + CurrentLanguage + ".xml", dbFactory);

        crops = crops_cl != null ? crops_cl : readManual("/assets/forestbotany/book/crops_en_US.xml", dbFactory);

    }

    public static Document readManual(String location, DocumentBuilderFactory dbFactory) {
        try {
            InputStream stream = ForestBotany.class.getResourceAsStream(location);
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(stream);
            doc.getDocumentElement().normalize();
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	
}
