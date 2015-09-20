package nedelosk.modularmachines.client.techtree.utils.language.editor;

import java.util.HashMap;
import nedelosk.modularmachines.api.basic.techtree.language.ILanguageData;
import nedelosk.modularmachines.client.techtree.utils.language.TechTreeEntryLanguageData;

public class TechTreeEntryEditorLanguageData implements ILanguageData {

	public TechTreeEntryLanguageData defaultData;
	public HashMap<String, TechTreeEntryLanguageData> languageData;
	
	public TechTreeEntryEditorLanguageData(TechTreeEntryLanguageData defaultData, HashMap<String, TechTreeEntryLanguageData> languageData) {
		this.defaultData = defaultData;
		this.languageData = languageData;
	}
	
}
