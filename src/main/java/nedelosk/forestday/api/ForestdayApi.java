/**
 * (C) 2015 Nedelosk
 */
package nedelosk.forestday.api;

import java.util.ArrayList;
import java.util.HashMap;

import nedelosk.forestday.api.multiblocks.IMultiblock;
import nedelosk.forestday.api.multiblocks.MultiblockPattern;

public class ForestDayApi {
	
	private static HashMap<String, ArrayList<MultiblockPattern>> mutiblockPatterns = new HashMap<String, ArrayList<MultiblockPattern>>();
	private static HashMap<String, IMultiblock> mutiblocks = new HashMap<String, IMultiblock>();
	
	public static void addMultiblockPattern(String mutliblockName, MultiblockPattern pattern)
	{
		ArrayList<MultiblockPattern> patterens = new ArrayList<MultiblockPattern>();
		if(	mutiblockPatterns.get(mutliblockName) != null)
			patterens.addAll(mutiblockPatterns.get(mutliblockName));
		patterens.add(pattern);
		mutiblockPatterns.put(mutliblockName, patterens);
	}
	
	public static void addMultiblockPattern(String mutliblockName, ArrayList<MultiblockPattern> pattern)
	{
		ArrayList<MultiblockPattern> patterens = new ArrayList<MultiblockPattern>();
		if(	mutiblockPatterns.get(mutliblockName) != null)
			patterens.addAll(mutiblockPatterns.get(mutliblockName));
		patterens.addAll(pattern);
		mutiblockPatterns.put(mutliblockName, patterens);
	}
	
	public static void registerMuliblock(String mutliblockName, IMultiblock multiblock)
	{
		mutiblocks.put(mutliblockName, multiblock);
	}
	
	public static <M extends IMultiblock> M getMutiblock(String mutliblockName) {
		return (M) mutiblocks.get(mutliblockName);
	}
	
	public static HashMap<String, IMultiblock> getMutiblocks() {
		return mutiblocks;
	}
	
	public static ArrayList<MultiblockPattern> getMutiblockPatterns(String mutliblockName) {
		return mutiblockPatterns.get(mutliblockName);
	}
	
	public static HashMap<String, ArrayList<MultiblockPattern>> getMutiblockPatterns() {
		return mutiblockPatterns;
	}
	
}
