package nedelosk.modularmachines.common.producers.fluids;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestday.api.FluidTankBasic;
import nedelosk.forestday.api.guis.IContainerBase;
import nedelosk.forestday.api.guis.IGuiBase;
import nedelosk.forestday.api.guis.Widget;
import nedelosk.forestday.client.gui.widget.WidgetFluidTank;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.handlers.FluidHandler;
import nedelosk.modularmachines.api.modular.inventory.SlotModular;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.Modules;
import nedelosk.modularmachines.api.producers.IProducer;
import nedelosk.modularmachines.api.producers.fluids.IProducerTank;
import nedelosk.modularmachines.api.producers.fluids.ITankManager;
import nedelosk.modularmachines.api.producers.fluids.ITankManager.TankMode;
import nedelosk.modularmachines.api.producers.inventory.IProducerInventory;
import nedelosk.modularmachines.api.producers.managers.ProducerManager;
import nedelosk.modularmachines.api.producers.managers.fluids.IProducerTankManager;
import nedelosk.modularmachines.api.utils.ModuleRegistry;
import nedelosk.modularmachines.api.utils.ModuleStack;
import nedelosk.modularmachines.common.producers.fluids.manager.TankManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class ProducerTankManager extends ProducerManager implements IProducerTankManager {

	public FluidTankBasic[] tanks;
	protected ITankManager manager;
	public int tankSlots;
	
	public ProducerTankManager() {
		super("TankManager");
		this.tankSlots = 2;
		this.tanks = new FluidTankBasic[tankSlots];
		this.manager = createManager();
	}
	
	public ProducerTankManager(int tankSlots) {
		super("TankManager");
		this.tankSlots = tankSlots;
		this.tanks = new FluidTankBasic[tankSlots];
		this.manager = createManager();
	}
	
	public ProducerTankManager(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super(nbt, modular, stack);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) throws Exception{
		super.writeToNBT(nbt, modular, stack);
		manager.writeToNBT(nbt);
		nbt.setInteger("TankSlots", tankSlots);
		NBTTagList list = new NBTTagList();
		int[] tankCapacitys = new int[tanks.length];
		for (int i = 0; i < tanks.length; i++) {
			if (tanks[i] != null) {
				NBTTagCompound nbtTag = new NBTTagCompound();
				tanks[i].writeToNBT(nbtTag);
				tankCapacitys[i] = tanks[i].getCapacity();
				nbtTag.setShort("Position", (short) i);
				list.appendTag(nbtTag);
			}
		}
		nbt.setTag("Tanks", list);
		nbt.setIntArray("TankCapacitys", tankCapacitys);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) throws Exception{
		super.readFromNBT(nbt, modular, stack);
		manager = new TankManager();
		manager.readFromNBT(nbt);
		tankSlots = nbt.getInteger("TankSlots");
		tanks = new FluidTankBasic[tankSlots];
		NBTTagList list = nbt.getTagList("Tanks", 10);
		int[] tankCapacitys = nbt.getIntArray("TankCapacitys");
		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound nbtTag = list.getCompoundTagAt(i);
			short position = nbtTag.getShort("Position");
			tanks[position] = new FluidTankBasic(tankCapacitys[position]);
			tanks[position].readFromNBT(nbtTag);
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
		for(int ID = 0;ID < tankSlots;ID++){
			slots.add(new SlotTank(modular.getMachine(), ID, 26 + ID * 51, 87, stack, ID));
		}
		return slots;
	}

	@Override
	public int getSizeInventory(ModuleStack stack) {
		return tankSlots;
	}
	
	@Override
	public boolean onBuildModular(IModular modular, ModuleStack stack, List<String> moduleNames) {
		if(modular.getManager().getFluidHandler() == null)
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
	public ITankManager createManager() {
		return new TankManager();
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
	
	@Override
	@SideOnly(cpw.mods.fml.relauncher.Side.CLIENT)
	public void addWidgets(IGuiBase gui, IModular modular, ModuleStack stack) {
		if(modular.getManager().getFluidHandler() != null)
		{
			for(int id = 0;id < tankSlots;id++)
			{
				FluidTankBasic tank = getTank(id);
				WidgetFluidTank widget = new WidgetFluidTank(tank, 36 + id * 51, 18, id);
				manager.addWidgets(widget, gui);
			}
		}
	}
	
	@SideOnly(cpw.mods.fml.relauncher.Side.CLIENT)
	@Override
	public void updateGui(IGuiBase base, int x, int y, IModular modular, ModuleStack stack) {
		List<Widget> widgets = base.getWidgetManager().getWidgets();
		for (Widget widget : widgets) {
			if (widget instanceof WidgetFluidTank) {
				int ID = ((WidgetFluidTank) widget).ID;
				((WidgetFluidTank) widget).tank = getTank(ID);
			}
		}
	}
	
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill, ModuleStack stack, IModular modular) {
		if (!doFill || resource == null)
			return 0;
		if (manager != null) {
			for(int i = 0;i < tanks.length;i++){
				FluidTankBasic tank = tanks[i];
				if(tank.isFull())
					continue;
				if(!tank.isEmpty()){
					if(resource.getFluid() != tank.getFluid().getFluid())
						continue;
				}
				ModuleStack stackT = modular.getFluidProducers().get(manager.getProducers()[i]);
				if(!(stack == null) && !stack.equals(stackT))
					continue;
				if(!(manager.getTankModes()[i] == TankMode.INPUT))
					continue;
				if(from == manager.getDirections()[i] || from == ForgeDirection.UNKNOWN || manager.getDirections()[i] == ForgeDirection.UNKNOWN){
					return tanks[i].fill(resource, doFill);
				}else
					continue;	
			}
		}
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain, ModuleStack stack, IModular modular) {
		if (!doDrain || resource == null)
			return null;
		if (manager != null) {
			for(int i = 0;i < tanks.length;i++){
				FluidTankBasic tank = tanks[i];
				if(tank.isEmpty())
					continue;
				if(resource.getFluid() != tank.getFluid().getFluid())
					continue;
				ModuleStack stackT = modular.getFluidProducers().get(manager.getProducers()[i]);
				if(!(stack == null) && !stack.equals(stackT))
					continue;
				if(!(manager.getTankModes()[i] == TankMode.OUTPUT))
					continue;
				if(from == manager.getDirections()[i] || from == ForgeDirection.UNKNOWN || manager.getDirections()[i] == ForgeDirection.UNKNOWN){
					return tanks[i].drain(resource, doDrain);
				}else
					continue;	
			}
		}
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain, ModuleStack stack, IModular modular) {
		if (!doDrain || maxDrain < 0)
			return null;
		if (manager != null) {
			for(int i = 0;i < tanks.length;i++){
				FluidTankBasic tank = tanks[i];
				if(tank.isEmpty())
					continue;
				if(tank.getFluid().amount < 0)
					continue;
				ModuleStack stackT = modular.getFluidProducers().get(manager.getProducers()[i]);
				if(!(stack == null) && !stack.equals(stackT))
					continue;
				if(from == manager.getDirections()[i] || from == ForgeDirection.UNKNOWN || manager.getDirections()[i] == ForgeDirection.UNKNOWN){
					return tanks[i].drain(maxDrain, doDrain);
				}else
					continue;	
			}
		}
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid, ModuleStack stack, IModular modular) {
		return true;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid, ModuleStack stack, IModular modular) {
		return true;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from, ModuleStack stack, IModular modular) {
		ArrayList<FluidTankInfo> infos = new ArrayList<>();
		for (int i = 0; i < tanks.length; i++) {
			if (tanks[i] != null)
				infos.add(tanks[i].getInfo());
		}
		return infos.toArray(new FluidTankInfo[infos.size()]);
	}
	
	public void addTank(int id, IProducerTank tank) {
		if(tank == null)
			tanks[id] = null;
		else
			tanks[id] = new FluidTankBasic(tank.getCapacity());
	}
	
	@Override
	public List<FluidTankBasic> getTanks(IModular modular, ModuleStack producer, TankMode mode) {
		ArrayList<FluidTankBasic> tanksL = Lists.newArrayList();
		if(producer != null){
			if(modular.getFluidProducers() == null || modular.getFluidProducers().isEmpty() || !modular.getFluidProducers().contains(producer))
				return tanksL;
			for(int ID = 0;ID < tanks.length;ID++){
				FluidTankBasic tank = tanks[ID];
				if(tank != null){
					if(mode == manager.getTankModes()[ID]){
						if(manager.getProducers()[ID] == modular.getFluidProducers().indexOf(producer))
							tanksL.add(tank);
					}
				}
			}
		}	
		return tanksL;
	}

	public FluidTankBasic getTank(int id) {
		return tanks[id];
	}
	
	public FluidTankBasic[] getTanks() {
		return tanks;
	}
	
	public class SlotTank extends SlotModular{

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
				((IProducerTank)module.getProducer()).setStorageFluid(getTank(ID).getFluid(), module, stack);
				addTank(ID, null);
			}
		}
		
		@Override
		public void putStack(ItemStack stack) {
			super.putStack(stack);
			IModularTileEntity tileModular = (IModularTileEntity) inventory;
			IModular modular = tileModular.getModular();
			ModuleStack module = ModuleRegistry.getModuleItem(stack);
			if(module != null && module.getModule() != null && module.getProducer() != null && module.getProducer() instanceof IProducerTank){
				addTank(ID, (IProducerTank) module.getProducer());
				getTank(ID).setFluid(((IProducerTank)module.getProducer()).getStorageFluid(module, stack));
			}
		}
		
	}

}
