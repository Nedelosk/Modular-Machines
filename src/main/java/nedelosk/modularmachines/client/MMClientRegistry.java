package nedelosk.modularmachines.client;

import java.util.HashMap;

import nedelosk.modularmachines.api.modular.parts.IMachinePart;
import nedelosk.modularmachines.common.machines.assembler.AssemblerMachineInfo;

public class MMClientRegistry {

	private static final HashMap<IMachinePart, AssemblerMachineInfo> assemblerInfos = new HashMap<IMachinePart, AssemblerMachineInfo>();
	
    public static void addAssemblerInfo(IMachinePart item, AssemblerMachineInfo info)
    {
    	assemblerInfos.put(item, info);
    }
    
    public static AssemblerMachineInfo getAssemblerInfo(IMachinePart item)
    {
        return assemblerInfos.get(item);
    }
	
}
