package nedelosk.modularmachines.common.producers.machines.generator;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.producers.machines.generator.IProducerGenerator;
import nedelosk.modularmachines.api.producers.machines.recipe.ProducerMachineRecipe;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ProducerGenerator extends ProducerMachineRecipe implements IProducerGenerator {

	protected int fuel;
	protected int fuelTotal;
	protected int energy;
	protected String name;
	
	public ProducerGenerator(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super(nbt, modular, stack);
	}

	public ProducerGenerator(String name, int inputs, int outputs, int speed, int energy) {
		super("Generator" + name, inputs, outputs, speed);
		this.energy = energy;
		this.name = name;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) throws Exception {
		super.writeToNBT(nbt, modular, stack);
		
		nbt.setInteger("Fuel", fuel);
		nbt.setInteger("FuelTotal", fuelTotal);
		nbt.setInteger("Energy", energy);
		nbt.setString("Name", name);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) throws Exception {
		super.readFromNBT(nbt, modular, stack);
		
		fuel = nbt.getInteger("Fuel");
		fuelTotal = nbt.getInteger("FuelTotal");
		energy = nbt.getInteger("Energy");
		name = nbt.getString("Name");
	}
	
	@Override
	public String getFilePath(ModuleStack stack) {
		return "generator/" + name;
	}

}
