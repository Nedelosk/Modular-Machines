package nedelosk.modularmachines.common.modular.module.tool.producer.fluids;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestday.api.FluidTankBasic;
import nedelosk.forestday.api.guis.IContainerBase;
import nedelosk.forestday.api.guis.IGuiBase;
import nedelosk.forestday.api.guis.Widget;
import nedelosk.forestday.client.gui.widget.WidgetFluidTank;
import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.machines.basic.IModularTileEntity;
import nedelosk.modularmachines.api.modular.machines.basic.SlotModular;
import nedelosk.modularmachines.api.modular.module.Modules;
import nedelosk.modularmachines.api.modular.module.basic.IModule;
import nedelosk.modularmachines.api.modular.module.basic.basic.handlers.FluidHandler;
import nedelosk.modularmachines.api.modular.module.tool.producer.IProducer;
import nedelosk.modularmachines.api.modular.module.tool.producer.basic.ProducerManager;
import nedelosk.modularmachines.api.modular.module.tool.producer.fluids.IProducerTankManager;
import nedelosk.modularmachines.api.modular.module.tool.producer.fluids.IProducerTank;
import nedelosk.modularmachines.api.modular.module.tool.producer.fluids.ITankManager;
import nedelosk.modularmachines.api.modular.module.tool.producer.inventory.IProducerInventory;
import nedelosk.modularmachines.api.modular.utils.ModuleRegistry;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.common.modular.module.tool.producer.fluids.manager.TankManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class ProducerTankManager extends ProducerManager implements IProducerTankManager {

	protected ITankManager manager;
	public int tankSlots;
	
	public ProducerTankManager() {
		super("TankManager");
		this.tankSlots = 3;
		this.manager = new TankManager();
	}
	
	public ProducerTankManager(int tankSlots) {
		super("TankManager");
		this.tankSlots = tankSlots;
		this.manager = new TankManager();
	}
	
	public ProducerTankManager(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super(nbt, modular, stack);
	}
	
	@SideOnly(cpw.mods.fml.relauncher.Side.CLIENT)
	@Override
	public void updateGui(IGuiBase base, int x, int y, IModular modular, ModuleStack stack) {
		List<Widget> widgets = base.getWidgetManager().getWidgets();
		for (Widget widget : widgets) {
			if (widget instanceof WidgetFluidTank) {
				int ID = ((WidgetFluidTank) widget).ID;
				((WidgetFluidTank) widget).tank = ((FluidHandler)modular.getManager().getFluidHandler()).getTank(ID);
			}
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) throws Exception{
		super.writeToNBT(nbt, modular, stack);
		manager.writeToNBT(nbt);
		nbt.setInteger("TankSlots", tankSlots);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) throws Exception{
		super.readFromNBT(nbt, modular, stack);
		manager = new TankManager();
		manager.readFromNBT(nbt);
		tankSlots = nbt.getInteger("TankSlots");
	}

	@Override
	@SideOnly(cpw.mods.fml.relauncher.Side.CLIENT)
	public void addWidgets(IGuiBase gui, IModular modular, ModuleStack stack) {
		if(modular.getManager().getFluidHandler() != null)
		{
			for(int i = 0;i < ((FluidHandler)modular.getManager().getFluidHandler()).tanks.length;i++)
			{
				FluidTankBasic tank = ((FluidHandler)modular.getManager().getFluidHandler()).tanks[i];
				WidgetFluidTank widget = new WidgetFluidTank(tank, 36 + i * 51, 18, i);
				manager.addWidgets(widget, gui);
			}
		}
	}
	
	@Override
	public ResourceLocation getCustomGui(IModular modular, ModuleStack stack) {
		return new ResourceLocation("modularmachines", "textures/gui/modular_tank_manager.png");
	}
	
	@Override
	public int getGuiTop(IModular modular, ModuleStack stack) {
		return 196;
	}
	
	@Override
	public boolean hasCustomInventoryName(ModuleStack stack) {
		return true;
	}

	@Override
	public List<Slot> addSlots(IContainerBase container, IModular modular, ModuleStack stack) {
		ArrayList<Slot> slots = new ArrayList<Slot>();
		slots.add(new SlotTank(modular.getMachine(), 0, 26, 87, stack, 0));
		slots.add(new SlotTank(modular.getMachine(), 1, 26 + 1 * 51, 87, stack, 1));
		slots.add(new SlotTank(modular.getMachine(), 2, 26 + 2 * 51, 87, stack, 2));
		return slots;
	}

	@Override
	public int getSizeInventory(ModuleStack stack) {
		return 3;
	}
	
	@Override
	public boolean onBuildModular(IModular modular, ModuleStack stack, List<String> moduleNames) {
		modular.getManager().setFluidHandler(new FluidHandler(modular));
		return super.onBuildModular(modular, stack, moduleNames);
	}

	@Override
	public int getColor() {
		return 0x1719A4;
	}
	
	@Override
	public ITankManager getManager() {
		return manager;
	}
	
	@Override
	public boolean transferInput(ModuleStack<IModule, IProducerInventory> stackModule, IModularTileEntity tile, EntityPlayer player, int slotID, Container container, ItemStack stackItem){
		ModuleStack<IModule, IProducer> stack = ModuleRegistry.getModuleItem(stackItem);
		if(stack != null && stack.getModule() == Modules.TANK && stack.getProducer() != null && stack.getProducer() instanceof IProducerTank){
			if(mergeItemStack(stackItem, 36, 39, false, container))
				return true;
		}
		return false;
	}
	
	public static class SlotTank extends SlotModular{

		protected int ID;
		
		public SlotTank(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_, ModuleStack stack, int ID) {
			super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_, stack);
			this.ID = ID;
		}
		
		@Override
		public void onPickupFromSlot(EntityPlayer  player, ItemStack stack) {
			super.onPickupFromSlot( player, stack);
			IModularTileEntity tileModular = (IModularTileEntity) inventory;
			IModular modular = tileModular.getModular();
			ModuleStack module = ModuleRegistry.getModuleItem(stack);
			if(module != null && module.getModule() != null && module.getProducer() != null && module.getProducer() instanceof IProducerTank){
				((IProducerTank)module.getProducer()).setStorageFluid(((FluidHandler)modular.getManager().getFluidHandler()).getTank(ID).getFluid(), module, stack);
				((FluidHandler)modular.getManager().getFluidHandler()).addTank(ID, null);
			}
		}
		
		@Override
		public void putStack(ItemStack stack) {
			super.putStack(stack);
			IModularTileEntity tileModular = (IModularTileEntity) inventory;
			IModular modular = tileModular.getModular();
			ModuleStack module = ModuleRegistry.getModuleItem(stack);
			if(module != null && module.getModule() != null && module.getProducer() != null && module.getProducer() instanceof IProducerTank){
				((FluidHandler)modular.getManager().getFluidHandler()).addTank(ID, (IProducerTank) module.getProducer());
				((FluidHandler)modular.getManager().getFluidHandler()).getTank(ID).setFluid(((IProducerTank)module.getProducer()).getStorageFluid(module, stack));
			}
		}
		
	}

}
