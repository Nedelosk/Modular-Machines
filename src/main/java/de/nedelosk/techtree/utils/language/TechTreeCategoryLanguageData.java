package de.nedelosk.techtree.utils.language;

import de.nedelosk.techtree.api.language.ILanguageCategoryData;

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
