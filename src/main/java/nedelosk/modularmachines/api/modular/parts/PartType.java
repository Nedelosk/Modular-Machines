package nedelosk.modularmachines.api.modular.parts;

import com.google.common.collect.ImmutableSet;

import nedelosk.modularmachines.api.modular.material.IMachine;
import nedelosk.modularmachines.api.modular.material.Material;
import net.minecraft.item.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class PartType {

  public final Set neededPart = new HashSet<IMachine>();
  private final String[] neededTypes;

  public PartType(IMachine part, String... statIDs) {
	  neededPart.add(part);
	  neededTypes = statIDs;
  }

  public boolean isValid(ItemStack stack) {
    if(stack == null || stack.getItem() == null) {
      return false;
    }

    if(!(stack.getItem() instanceof IMachineComponent) && !(stack.getItem() instanceof IMachinePart)) {
      return false;
    }
    
    if(stack.getItem() instanceof IMachinePart){

    	IMachinePart machinePart = (IMachinePart) stack.getItem();
	    return isValid(machinePart);
    }
    else{
	    IMachineComponent machineComponent = (IMachineComponent) stack.getItem();
	    return isValid(machineComponent, machineComponent.getMaterial(stack));
    }
  }

  public boolean isValid(IMachineComponent part, Material material) {
    return isValidItem(part) && isValidMaterial(material);
  }
  
  public boolean isValid(IMachinePart part) {
	    return isValidItem(part);
  }
  
  public boolean isValidMaterial(Material material) {
	    for(String type : neededTypes) {
	      if(!material.hasStats(type)) {
	        return false;
	      }
	    }

	    return true;
  }

  public boolean isValidItem(IMachineComponent part) {
    return neededPart.contains(part);
  }
  
  public boolean isValidItem(IMachinePart part) {
	    return neededPart.contains(part);
  }

  public Set<IMachineComponent> getPossibleParts() {
    return ImmutableSet.copyOf(neededPart);
  }

  public static class MachinePartType extends PartType {

    public MachinePartType(IMachineComponent part) {
      super(part, "Machine");
    }
  }
}
