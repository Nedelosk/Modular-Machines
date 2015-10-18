package nedelosk.modularmachines.common.modular.module.tool.producer.energy;

import java.util.ArrayList;
import java.util.Vector;
import cofh.api.energy.EnergyStorage;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.machines.basic.IModularTileEntity;
import nedelosk.modularmachines.api.modular.machines.basic.SlotModular;
import nedelosk.modularmachines.api.modular.module.basic.IModule;
import nedelosk.modularmachines.api.modular.module.tool.producer.energy.IProducerBattery;
import nedelosk.modularmachines.api.modular.module.tool.producer.energy.IProducerCapacitor;
import nedelosk.modularmachines.api.modular.module.tool.producer.inventory.ProducerInventory;
import nedelosk.modularmachines.api.modular.utils.ModularUtils;
import nedelosk.modularmachines.api.modular.utils.ModuleRegistry;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.common.modular.machines.modular.handlers.EnergyHandler;
import nedelosk.nedeloskcore.api.machines.IContainerBase;
import nedelosk.nedeloskcore.api.machines.IGuiBase;
import nedelosk.nedeloskcore.api.machines.Widget;
import nedelosk.nedeloskcore.client.gui.widget.WidgetEnergyBar;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ProducerBattery extends ProducerInventory implements IProducerBattery {

	public EnergyStorage storage;
	
    private int batteryCapacity;
    private int speedModifier;
    private int energyModifier;
	
	public ProducerBattery(String modifier, EnergyStorage storage) {
		super(modifier);
		this.storage = storage;
	}
	
	public ProducerBattery(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super(nbt, modular, stack);
	}
	
	@Override
	public void update(IModular modular, ModuleStack stack) {
		if(batteryCapacity == 0)
			batteryCapacity = getStorage(stack).getMaxEnergyStored();
		int energyModifier = 0;
		int speedModifier = 0;
		if(ModularUtils.getModuleCapacitor(modular) != null){
			for(IModule module : ModularUtils.getModuleCapacitor(modular))
			{
				IProducerCapacitor capacitor = (IProducerCapacitor) module;
				if(capacitor.canWork(modular))
				{
					energyModifier = energyModifier + capacitor.getEnergyModifier();
					speedModifier = speedModifier + capacitor.getSpeedModifier();
				}
			}
			this.speedModifier = speedModifier;
			this.energyModifier = energyModifier;
			int capacity = batteryCapacity * energyModifier / 100;
			if(((EnergyHandler)modular.getManager().getEnergyHandler()).getStorage().getMaxEnergyStored() != capacity)
				((EnergyHandler)modular.getManager().getEnergyHandler()).getStorage().setCapacity(batteryCapacity + (batteryCapacity * energyModifier / 100));
		}
		else{
			if(((EnergyHandler)modular.getManager().getEnergyHandler()).getStorage().getMaxEnergyStored() != batteryCapacity){
				((EnergyHandler)modular.getManager().getEnergyHandler()).getStorage().setEnergyStored(batteryCapacity);
				((EnergyHandler)modular.getManager().getEnergyHandler()).getStorage().setCapacity(batteryCapacity);
			}
		}
	}
	
	
	//Gui
	@SideOnly(Side.CLIENT)
	@Override
	public void addWidgets(IGuiBase gui, IModular modular, ModuleStack stack) {
		gui.getWidgetManager().add(new WidgetEnergyBar(((EnergyHandler)modular.getManager().getEnergyHandler()).getStorage(), 20, 8));
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public boolean hasCustomInventoryName(ModuleStack stack) {
		return true;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void updateGui(IGuiBase base, int x, int y, IModular modular, ModuleStack stack) {
		for(Widget widget : (ArrayList<Widget>) base.getWidgetManager().getWidgets()){
			if(widget instanceof WidgetEnergyBar){
				(( WidgetEnergyBar)widget).storage = ((EnergyHandler)modular.getManager().getEnergyHandler()).getStorage();
			}
		}
	}
	
	//Inventory
	@Override
	public ArrayList<Slot> addSlots(IContainerBase container, IModular modular, ModuleStack stack) {
		ArrayList<Slot> list = new ArrayList<Slot>();
		list.add(new SlotCapacitor(modular.getMachine(), 0, 143, 17, stack));
		list.add(new SlotCapacitor(modular.getMachine(), 1, 143, 35, stack));
		list.add(new SlotCapacitor(modular.getMachine(), 2, 143, 53, stack));
		return list;
	}

	@Override
	public int getSizeInventory(ModuleStack stack) {
		return 3;
	}
	
	//NBT
	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super.writeToNBT(nbt, modular, stack);
		
		if(storage == null){
			NBTTagCompound nbtTag = new NBTTagCompound();
			storage.writeToNBT(nbtTag);
			nbtTag.setInteger("Capacity", storage.getMaxEnergyStored());
			nbtTag.setInteger("MaxReceive", storage.getMaxReceive());
			nbtTag.setInteger("MaxExtract", storage.getMaxExtract());
			nbt.setTag("Storage", nbtTag);
		}
		nbt.setInteger("BatteryCapacity", batteryCapacity);
		nbt.setInteger("speedModifier", speedModifier);
		nbt.setInteger("energyModifier", energyModifier);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super.readFromNBT(nbt, modular, stack);
		if(nbt.hasKey("Storage")){
			NBTTagCompound nbtTag = nbt.getCompoundTag("Storage");
			storage = new EnergyStorage(nbtTag.getInteger("Capacity"), nbtTag.getInteger("MaxReceive"), nbtTag.getInteger("MaxExtract"));
			storage.readFromNBT(nbtTag);
		}
		batteryCapacity = nbt.getInteger("BatteryCapacity");
		speedModifier = nbt.getInteger("speedModifier");
		energyModifier = nbt.getInteger("energyModifier");
	}
	
	@Override
	public EnergyStorage getStorage(ModuleStack stack) {
		return storage;
	}
	
	@Override
	public int getSpeedModifier() {
		return speedModifier;
	}
	
	public static class SlotCapacitor extends SlotModular{

		public SlotCapacitor(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_, ModuleStack stack) {
			super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_, stack);
		}
		
		@Override
		public boolean isItemValid(ItemStack stack) {
			if(ModuleRegistry.getModuleItem(stack) != null && ModuleRegistry.getModuleItem(stack).getProducer() != null && ModuleRegistry.getModuleItem(stack).getProducer() instanceof IProducerCapacitor)
				return true;
			return false;
		}
		
		@Override
		public void onSlotChanged() {
			super.onSlotChanged();
			if(((IModularTileEntity)inventory).getModular().getModules().get("Capacitor") == null || ((IModularTileEntity)inventory).getModular().getModules().get("Capacitor").size() < 3){
				Vector<ModuleStack> modules = new Vector<ModuleStack>(3);
				for(int i = 0;i < 3;i++){
					modules.add(null);
				}
				((IModularTileEntity)inventory).getModular().getModules().put("Capacitor", modules);
			}
			Vector<ModuleStack> modules = ((IModularTileEntity)inventory).getModular().getModules().get("Capacitor");
			if(getStack() == null){
				if(modules.get(getSlotIndex()) != null){
					modules.set(getSlotIndex(), null);
				}
			}else{
				if(ModuleRegistry.getModuleItem(getStack()) != null && ModuleRegistry.getModuleItem(getStack()).getProducer() != null && ModuleRegistry.getModuleItem(getStack()).getProducer() instanceof IProducerCapacitor){
					((IModularTileEntity)inventory).getModular().getModules().get("Capacitor").set(getSlotIndex(), ModuleRegistry.getModuleItem(getStack()));
				}
			}
		}
		
	}

}
