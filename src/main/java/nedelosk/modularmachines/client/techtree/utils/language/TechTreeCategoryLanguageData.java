package nedelosk.modularmachines.client.techtree.utils.language;

import nedelosk.modularmachines.api.basic.techtree.language.ILanguageCategoryData;

public class TechTreeCategoryLanguageData implements ILanguageCategoryData {

	public String catogory;
	
	public TechTreeCategoryLanguageData(String catogory) {
		this.catogory = catogory;
	}
	
	@Override
	public String getCatogory() {
		return catogory;
	}
	
}
