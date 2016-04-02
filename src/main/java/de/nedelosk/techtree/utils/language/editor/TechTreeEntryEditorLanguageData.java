package de.nedelosk.techtree.utils.language.editor;

import java.util.HashMap;

import de.nedelosk.techtree.api.language.ILanguageData;
import de.nedelosk.techtree.utils.language.TechTreeEntryLanguageData;

public class TechTreeEntryEditorLanguageData implements ILanguageData {

	public TechTreeEntryLanguageData defaultData;
	public HashMap<String, TechTreeEntryLanguageData> languageData;

	public TechTreeEntryEditorLanguageData(TechTreeEntryLanguageData defaultData, HashMap<String, TechTreeEntryLanguageData> languageData) {
		this.defaultData = defaultData;
		this.languageData = languageData;
	}
}
