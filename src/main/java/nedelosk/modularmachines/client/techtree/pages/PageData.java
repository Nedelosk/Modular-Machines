package nedelosk.modularmachines.client.techtree.pages;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import cpw.mods.fml.common.registry.LanguageRegistry;
import nedelosk.modularmachines.api.techtree.TechTreeCategories;
import nedelosk.modularmachines.api.techtree.TechTreeEntry;
import nedelosk.modularmachines.api.techtree.TechTreePage;
import nedelosk.modularmachines.api.techtree.TechTreePage.PageType;
import nedelosk.modularmachines.common.ModularMachines;

public class PageData {
	
	public static HashMap<String, String> pages = new HashMap<>();

	public static void readDocument(String categoryName, String language)
	{
		File file = new File(ModularMachines.configFolder.getPath() + "/techtree/" + language, "data_entrys_" + categoryName + ".xml");
		try{
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		if(file.exists()){
			boolean hasChance = false;
			InputStream inputStream = new FileInputStream(file);
            Document doc = dBuilder.parse(inputStream);
            doc.getDocumentElement().normalize();

            Element category = doc.getDocumentElement();
            TechTreeEntry[] entrys = TechTreeCategories.getEntryList(categoryName).entrys.values().toArray(new TechTreeEntry[TechTreeCategories.getEntryList(categoryName).entrys.values().size()]);
			for(int i = 0;i < TechTreeCategories.getEntryList(categoryName).entrys.values().size();i++)
			{
				TechTreeEntry entry = entrys[i];
				NodeList nodeListEntry = doc.getElementsByTagName(entry.key);
				if(nodeListEntry.item(0) != null && nodeListEntry != null)
				{
				Node nodeEntry = nodeListEntry.item(0);
				Element eEntry = (Element) nodeEntry;
				if(eEntry.getElementsByTagName("Name") != null)
				{
					NodeList nodeListName = eEntry.getElementsByTagName("Name");
					Node nodeName = nodeListName.item(0);
					Element eName = (Element) nodeName;
					if(eName != null)
					{
					if((eName.getTextContent().isEmpty() || eName.getTextContent().equals(entry.getName())))
					{
						createPageDataName(entry, eName, doc, language);
						hasChance = true;
					}
					else
					{
					pages.put(entry.getName(), eName.getTextContent());
					}
					}
					else
					{
						createPageDataNameNEW(entry, eEntry, doc, language);
						hasChance = true;
					}
				}
				else
				{
					createPageDataNameNEW(entry, eEntry, doc, language);
					hasChance = true;
				}
				if(eEntry.getElementsByTagName("Text") != null)
				{
					NodeList nodeListText = eEntry.getElementsByTagName("Text");
					Node nodeText = nodeListText.item(0);
					Element eText = (Element) nodeText;
					if(eText != null)
					{
					if((eText.getTextContent().isEmpty() || eText.getTextContent().equals(entry.getText())))
					{
						createPageDataText(entry, eText, doc, language);
						hasChance = true;
					}
					else
					{
					pages.put(entry.getText(), eText.getTextContent());
					}
					}
					else
					{
						createPageDataTextNEW(entry, eEntry, doc, language);
						hasChance = true;
					}
				}
				else
				{
					createPageDataTextNEW(entry, eEntry, doc, language);
					hasChance = true;
				}
				if(entry.getPages() != null)
				{
				for(int p = 0;p < entry.getPages().length;p++)
				{
					if(entry.getPages()[p] != null)
					{
						TechTreePage page = entry.getPages()[p];
						if(page.type == PageType.IMAGE || page.type == PageType.TEXT || page.type == PageType.TEXT_CONCEALED)
						{
							NodeList nodeListPage = eEntry.getElementsByTagName(page.text);
							if(nodeListPage != null)
							{
							Node nodePage = nodeListPage.item(0);
							Element nodeE = (Element)nodePage;
							if(nodeE != null)
							{
							if((nodeE.getTextContent().isEmpty() || nodeE.getTextContent().equals(page.text)))
							{
								createPageDataPageText(page.text, p, entry.key, nodeE, language);
								hasChance = true;
							}
							else
							{
							pages.put(page.text, ((Element)nodePage).getTextContent());
							}
							}
							else
							{
								createPageDataPage(page.text, p, entry.key, eEntry, doc, language);
								hasChance = true;
							}
							}
							else
							{
								createPageDataPage(page.text, p, entry.key, eEntry, doc, language);
								hasChance = true;
							}
						}
					}
				}
				}
				}
				else
				{
					createPageDataEntry(entry, category, doc, language);
					hasChance = true;
				}
			}
            inputStream.close();
			if(hasChance)
			{
				OutputStream outputStream = new FileOutputStream(file);
				TransformerFactory tFactory = TransformerFactory.newInstance();
				Transformer t = tFactory.newTransformer();
				t.setOutputProperty(OutputKeys.INDENT, "yes");
				t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
				DOMSource s = new DOMSource(doc);
				StreamResult sr = new StreamResult(outputStream);
				t.transform(s, sr);
	            outputStream.close();
			}
		}
		else
		{
			if (file.getParentFile() != null)
            {
                file.getParentFile().mkdirs();
            }
			if(!file.createNewFile())
				throw new IOException();
			OutputStream outputStream = new FileOutputStream(file);
			Document doc = dBuilder.newDocument();
			Element category = doc.createElement(categoryName);
			doc.appendChild(category);
			for(TechTreeEntry entry : TechTreeCategories.getEntryList(categoryName).entrys.values())
			{
				createPageDataEntry(entry, category, doc,  language);
			}
			
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer t = tFactory.newTransformer();
			t.setOutputProperty(OutputKeys.INDENT, "yes");
			t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			DOMSource s = new DOMSource(doc);
			StreamResult sr = new StreamResult(outputStream);
			t.transform(s, sr);
			outputStream.close();
		}
		}
		catch(ParserConfigurationException | SAXException | IOException | TransformerException e)
		{
			ModularMachines.class.getName();
		}
	}
	
	private static void createPageDataPage(String pageText, int pageID, String entryKey, Element element, Document doc, String lang)
	{
		Element pageElement = doc.createElement(pageText);
		String text = null;
		String l = LanguageRegistry.instance().getStringLocalization(pageText, lang);
		if(!LanguageRegistry.instance().getStringLocalization(pageText, lang).isEmpty())
			text = LanguageRegistry.instance().getStringLocalization(pageText, lang);
		else if(!LanguageRegistry.instance().getStringLocalization(pageText, "en_US").isEmpty())
			text = LanguageRegistry.instance().getStringLocalization(pageText, "en_US");
		else
			text = pageText;
		pages.put(pageText, text);
		pageElement.setTextContent(text);
		element.appendChild(pageElement);
	}
	
	private static void createPageDataPageText(String pageText, int pageID, String entryKey, Element pageElement, String lang)
	{
		String text = null;
		String l = LanguageRegistry.instance().getStringLocalization(pageText, lang);
		if(!LanguageRegistry.instance().getStringLocalization(pageText, lang).isEmpty())
			text = LanguageRegistry.instance().getStringLocalization(pageText, lang);
		else if(!LanguageRegistry.instance().getStringLocalization(pageText, "en_US").isEmpty())
			text = LanguageRegistry.instance().getStringLocalization(pageText, "en_US");
		else
			text = pageText;
		pages.put(pageText, text);
		pageElement.setTextContent(text);
	}
	
	private static void createPageDataTextNEW(TechTreeEntry entry,  Element element, Document doc, String language)
	{
		Element textElement = doc.createElement("Text");
		String text = null;
		String l = LanguageRegistry.instance().getStringLocalization(entry.getText(), language);
		if(!LanguageRegistry.instance().getStringLocalization(entry.getText(), language).isEmpty())
			text = LanguageRegistry.instance().getStringLocalization(entry.getText(), language);
		else if(!LanguageRegistry.instance().getStringLocalization(entry.getText(), "en_US").isEmpty())
			text = LanguageRegistry.instance().getStringLocalization(entry.getText(), "en_US");
		else
			text = entry.getText();
		pages.put(entry.getText(), text);
		textElement.setTextContent(text);
		element.appendChild(textElement);
	}
	
	private static void createPageDataText(TechTreeEntry entry,  Element element, Document doc, String language)
	{
		String text = null;
		String l = LanguageRegistry.instance().getStringLocalization(entry.getText(), language);
		if(!LanguageRegistry.instance().getStringLocalization(entry.getText(), language).isEmpty())
			text = LanguageRegistry.instance().getStringLocalization(entry.getText(), language);
		else if(!LanguageRegistry.instance().getStringLocalization(entry.getText(), "en_US").isEmpty())
			text = LanguageRegistry.instance().getStringLocalization(entry.getText(), "en_US");
		else
			text = entry.getText();
		pages.put(entry.getText(), text);
		element.setTextContent(text);
	}
	
	private static void createPageDataNameNEW(TechTreeEntry entry,  Element element, Document doc, String language)
	{
		Element textElement = doc.createElement("Name");
		String text = null;
		String l = LanguageRegistry.instance().getStringLocalization(entry.getName(), language);
		if(!LanguageRegistry.instance().getStringLocalization(entry.getName(), language).isEmpty())
			text = LanguageRegistry.instance().getStringLocalization(entry.getName(), language);
		else if(!LanguageRegistry.instance().getStringLocalization(entry.getName(), "en_US").isEmpty())
			text = LanguageRegistry.instance().getStringLocalization(entry.getName(), "en_US");
		else
			text = entry.getName();
		pages.put(entry.getName(), text);
		textElement.setTextContent(text);
		element.appendChild(textElement);
	}
	
	private static void createPageDataName(TechTreeEntry entry,  Element element, Document doc, String language)
	{
		String text = null;
		String l = LanguageRegistry.instance().getStringLocalization(entry.getName(), language);
		if(!LanguageRegistry.instance().getStringLocalization(entry.getName(), language).isEmpty())
			text = LanguageRegistry.instance().getStringLocalization(entry.getName(), language);
		else if(!LanguageRegistry.instance().getStringLocalization(entry.getName(), "en_US").isEmpty())
			text = LanguageRegistry.instance().getStringLocalization(entry.getName(), "en_US");
		else
			text = entry.getName();
		pages.put(entry.getName(), text);
		element.setTextContent(text);
	}
	
	private static void createPageDataEntry(TechTreeEntry entry,  Element element, Document doc, String language)
	{
		Element entryElement = doc.createElement(entry.key);
		createPageDataNameNEW(entry, entryElement, doc, language);
		createPageDataTextNEW(entry, entryElement, doc, language);
		if(entry.getPages() != null)
		{
			for(int p = 0;p < entry.getPages().length;p++)
			{
				TechTreePage page = entry.getPages()[p];
				if(page.type == PageType.IMAGE || page.type == PageType.TEXT || page.type == PageType.TEXT_CONCEALED)
				{
					createPageDataPage(page.text, p, entry.key, entryElement, doc, language);
				}
			}
		}
		element.appendChild(entryElement);
	}
	
}
