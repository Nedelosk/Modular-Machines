/**
 * (C) 2015 Nedelosk
 */
package nedelosk.nedeloskcore.api.book;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nedelosk.nedeloskcore.api.NCoreApi;
import nedelosk.nedeloskcore.common.book.BookData;
import nedelosk.nedeloskcore.common.book.BookDatas;
import nedelosk.nedeloskcore.common.book.note.Note;
import net.minecraft.util.StatCollector;

public class BookEntry {

	public final String key;
	public final String bookdata;
	public final String category;
	public BookLevel level;
	public String[] parents = null;
	public final Knowledge[] knowledges;

	public Note[] notes = null;
	
	public BookEntry(String unlocalizedName, String category, BookLevel level, String bookdata, Knowledge... knowledges) {
		this.key = unlocalizedName;
		this.category = category;
		this.level = level;
		this.knowledges = knowledges;
		this.bookdata = bookdata;
	}
	
	public BookEntry(String unlocalizedName, String categoryName, BookLevel level, String bookdata) {
		this.key = unlocalizedName;
		this.category = categoryName;
		this.level = level;
		this.knowledges = new Knowledge[1];
		this.knowledges[0] = NCoreApi.basicKnowledge;
		this.bookdata = bookdata;
	}
	
	public BookEntry(String unlocalizedName, String categoryName, String bookdata, Knowledge... knowledges) {
		this.key = unlocalizedName;
		this.category = categoryName;
		this.level = NCoreApi.traineeLevel;
		this.knowledges = knowledges;
		this.bookdata = bookdata;
	}
	
	public BookEntry(String unlocalizedName, String categoryName, String bookdata) {
		this.key = unlocalizedName;
		this.bookdata = bookdata;
		this.category = categoryName;
		this.level = NCoreApi.traineeLevel;
		this.knowledges = new Knowledge[1];
		this.knowledges[0] = NCoreApi.basicKnowledge;
	}
	
	public BookEntry registerEntry()
	{
		BookDatas.addEntry(this);
		return this;
	}

	public String getUnlocalizedName() {
		return "nc.book.entry." + key;
	}

	public BookEntry setNote(Note... notes) {
		this.notes = notes;

		return this;
	}
	
	public String getKey() {
		return key;
	}
	
	public Knowledge[] getKnowledges() {
		return knowledges;
	}
	
}