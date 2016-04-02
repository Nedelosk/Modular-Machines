package de.nedelosk.techtree.utils.language;

import de.nedelosk.techtree.api.language.ILanguageEntryData;

public class TechTreeEntryLanguageData implements ILanguageEntryData {

	public TechTreeEntryLanguageData(String key, String text, String[] pages) {
		this.name = key;
		this.text = text;
		this.pages = pages;
	}

	public TechTreeEntryLanguageData() {
	}

	public String name;
	public String text;
	public String[] pages;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public String[] getPages() {
		return pages;
	}
}
