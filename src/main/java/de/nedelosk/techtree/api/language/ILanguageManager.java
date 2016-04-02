package de.nedelosk.techtree.api.language;

import java.io.File;
import java.io.IOException;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.techtree.api.TechTreeCategoryList;
import de.nedelosk.techtree.api.TechTreeEntry;

@SideOnly(Side.CLIENT)
public interface ILanguageManager {

	ILanguageEntryData getTranslateData(TechTreeEntry entry);

	ILanguageData getTranslateData(String key);

	ILanguageCategoryData getTranslateData(TechTreeCategoryList category);

	void checkLanguage();

	void updateLanguage();

	void readLanguageData();

	void readCategorysLanguageData(File categorysFile) throws IOException;

	void readCategoryLanguageData(TechTreeCategoryList category, File categorysFile) throws IOException;

	ILanguageEntryData readEntryLanguageData(TechTreeEntry entry, File categoryFile) throws IOException;

	void writeLanguageData();

	void writeCategorysLanguageData(File categorysFile) throws IOException;

	void writeCategoryListLanguageData(TechTreeCategoryList category, File categorysFile) throws IOException;

	void writeCategoryLanguageData(TechTreeCategoryList category, File categorysFile) throws IOException;

	void writeEntryLanguageData(TechTreeEntry entry, File categoryFile) throws IOException;
}
