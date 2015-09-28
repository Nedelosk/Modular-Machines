package nedelosk.modularmachines.common.machines.assembler;

import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import nedelosk.modularmachines.api.basic.machine.part.IMachinePart;

public class AssemblerRegistry {

	private static final Set<IMachinePart> assemblerRecipes = Sets.newHashSet();
	
    public static void registerAssemblerRecipes(IMachinePart part)
    {
    	assemblerRecipes.add(part);
    }
    
    public static Set<IMachinePart> getAssemblerRecipes()
    {
        return ImmutableSet.copyOf(assemblerRecipes);
    }
	
}
