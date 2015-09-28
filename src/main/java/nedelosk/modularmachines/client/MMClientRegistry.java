package nedelosk.modularmachines.client;

import java.util.HashMap;

import nedelosk.modularmachines.api.basic.machine.part.IMachinePart;
import nedelosk.modularmachines.client.gui.assembler.AssemblerMachineInfo;

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
