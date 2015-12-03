package nedelosk.modularmachines.common.core;

import java.util.HashMap;

import com.google.common.collect.Maps;

import nedelosk.modularmachines.api.modular.assembler.AssemblerMachineInfo;

public class MMRegistry {

	private static final HashMap<String, AssemblerMachineInfo> assemblerInfos = Maps.newHashMap();

	public static void addAssemblerInfo(String modularName, AssemblerMachineInfo info) {
		assemblerInfos.put(modularName, info);
	}

	public static AssemblerMachineInfo getAssemblerInfo(String modularName) {
		return assemblerInfos.get(modularName);
	}

	public static HashMap<String, AssemblerMachineInfo> getAssemblerInfos() {
		return assemblerInfos;
	}

}
