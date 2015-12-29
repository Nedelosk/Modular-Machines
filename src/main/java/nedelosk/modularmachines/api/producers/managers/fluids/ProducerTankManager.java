package nedelosk.modularmachines.api.producers.managers.fluids;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestcore.library.FluidTankBasic;
import nedelosk.forestcore.library.gui.IGuiBase;
import nedelosk.forestcore.library.gui.Widget;
import nedelosk.forestcore.library.gui.WidgetFluidTank;
import nedelosk.forestcore.library.inventory.IContainerBase;
import nedelosk.modularmachines.api.client.gui.ButtonTabTankManager;
import nedelosk.modularmachines.api.client.widget.WidgetDirection;
import nedelosk.modularmachines.api.client.widget.WidgetProducer;
import nedelosk.modularmachines.api.client.widget.WidgetTankMode;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.handlers.FluidHandler;
import nedelosk.modularmachines.api.modular.inventory.SlotModular;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.Modules;
import nedelosk.modularmachines.api.producers.IProducer;
import nedelosk.modularmachines.api.producers.fluids.IProducerTank;
import nedelosk.modularmachines.api.producers.fluids.ITankData;
import nedelosk.modularmachines.api.producers.fluids.ITankData.TankMode;
import nedelosk.modularmachines.api.producers.fluids.TankData;
import nedelosk.modularmachines.api.producers.inventory.IProducerInventory;
import nedelosk.modularmachines.api.producers.managers.ProducerManager;
import nedelosk.modularmachines.api.utils.ModuleRegistry;
import nedelosk.modularmachines.api.utils.ModuleStack;
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

public class ProducerTankManager extends ProducerManager implements IProducerTankManager {

	protected TankData[] datas;
	protected int tankSlots;
	protected int tab;
	
	public ProducerTankManager() {
		super("TankManager");
		this.tankSlots = 2;
		this.datas = new TankData[tankSlots];
		this.tab = 0;
	}
	
	public ProducerTankManager(int tankSlots) {
		super("TankManager");
		this.tankSlots = tankSlots;
		this.datas = new TankData[tankSlots];
		this.tab = 0;
	}
	
	public ProducerTankManager(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super(nbt, modular, stack);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) throws Exception{
		super.writeToNBT(nbt, modular, stack);
		nbt.setInteger("TankSlots", tankSlots);
		nbt.setInteger("Tab", tab);
		NBTTagList list = new NBTTagList();
		for (int i = 0; i < datas.length; i++) {
			if (datas[i] != null) {
				NBTTagCompound nbtTag = new NBTTagCompound();
				datas[i].writeToNBT(nbtTag);
				nbtTag.setShort("Position", (short) i);
				list.appendTag(nbtTag);
			}
		}
		nbt.setTag("Datas", list);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) throws Exception{
		super.readFromNBT(nbt, modular, stack);
		tankSlots = nbt.getInteger("TankSlots");
		tab = nbt.getInteger("Tab");
		datas = new TankData[tankSlots];
		NBTTagList list = nbt.getTagList("Datas", 10);
		int[] tankCapacitys = nbt.getIntArray("TankCapacitys");
		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound nbtTag = list.getCompoundTagAt(i);
			short position = nbtTag.getShort("Position");
			datas[position] = new TankData();
			datas[position].readFromNBT(nbtTag);
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
		int i = 0;
		for(int ID = tab * 3;ID < (tab + 1) * 3;ID++){
			if(!(datas.length <= ID)){
				slots.add(new SlotTank(modular.getMachine(), ID, 37 + i * 51, 87, stack, ID));
				i++;
			}
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
	public boolean transferInput(ModuleStack<IModule, IProducerInventory> stackModule, IModularTileEntity tile, EntityPlayer player, int slotID, Container container, ItemStack stackItem){
		ModuleStack<IModule, IProducer> stack = ModuleRegistry.getModuleItem(stackItem);
		if(stack != null && stack.getModule() == Modules.TANK && stack.getProducer() != null && stack.getProducer() instanceof IProducerTank){
			if(mergeItemStack(stackItem, 36, container.inventorySlots.size(), false, container))
				return true;
		}
		return false;
	}
	
	@Override
	@SideOnly(cpw.mods.fml.relauncher.Side.CLIENT)
	public void addWidgets(IGuiBase gui, IModular modular, ModuleStack stack) {
		if(modular.getManager().getFluidHandler() != null)
		{
			int i = 0;
			for(int ID = tab * 3;ID < (tab + 1) * 3;ID++)
			{
				if(!(datas.length <= ID)){
					ITankData data = getData(ID);
					WidgetFluidTank widget = new WidgetFluidTank(data == null ? null : data.getTank(), 36 + i * 51, 18, ID);
					addWidgets(widget, gui);
					i++;
				}
			}
		}
	}
	
	@Override
	@SideOnly(cpw.mods.fml.relauncher.Side.CLIENT)
	public void addWidgets(Widget widget, IGuiBase<IModularTileEntity<IModular>> gui) {
		if (widget != null && widget instanceof WidgetFluidTank) {
			WidgetFluidTank tank = (WidgetFluidTank) widget;
			ITankData data = getData(tank.ID);
			gui.getWidgetManager().add(tank);
			gui.getWidgetManager().add(new WidgetTankMode(tank.posX - 22, tank.posY, data == null ? null : data.getMode(), tank.ID));
			if(gui.getTile().getModular().getFluidProducers() != null && !gui.getTile().getModular().getFluidProducers().isEmpty())
				gui.getWidgetManager().add(new WidgetProducer(tank.posX - 22, tank.posY + 21, data == null ? -1 : data.getProducer(), tank.ID));
			gui.getWidgetManager().add(new WidgetDirection(tank.posX - 22, tank.posY + 42, data == null ? null : data.getDirection(), tank.ID));
		}
	}
	
	@SideOnly(cpw.mods.fml.relauncher.Side.CLIENT)
	@Override
	public void addButtons(IGuiBase gui, IModular modular, ModuleStack stack) {
		int tabs = tankSlots / 3 + 1;
		for(int ID = 0;ID <	tabs;ID++){
			gui.getButtonManager().add(new ButtonTabTankManager(gui.getButtons().size() + gui.getButtonManager().getButtons().size(), ID > 4 ? 12 + gui.getGuiLeft() + (ID - 5) * 30 : 12 + gui.getGuiLeft() + ID * 30, ID > 4 ? 196 + gui.getGuiTop() : -19 + gui.getGuiTop(), stack, ID > 4 ? true : false, ID));
		}
	}
	
	@SideOnly(cpw.mods.fml.relauncher.Side.CLIENT)
	@Override
	public void updateGui(IGuiBase base, int x, int y, IModular modular, ModuleStack stack) {
		List<Widget> widgets = base.getWidgetManager().getWidgets();
		for (Widget widget : widgets) {
			if (widget instanceof WidgetFluidTank) {
				int ID = ((WidgetFluidTank) widget).ID;
				if(getData(ID) != null)
					((WidgetFluidTank) widget).tank = getData(ID).getTank();
				else 
					((WidgetFluidTank) widget).tank = null;
			}else if (widget instanceof WidgetDirection) {
				int ID = ((WidgetDirection) widget).ID;
				if(getData(ID) != null)
					((WidgetDirection) widget).direction = getData(ID).getDirection();
				else
					((WidgetDirection) widget).direction = null;
			} else if (widget instanceof WidgetProducer) {
				int ID = ((WidgetProducer) widget).ID;
				if(getData(ID) != null && getData(ID).getMode() != null && getData(ID).getMode() != TankMode.NONE)
					((WidgetProducer) widget).producer = getData(ID).getProducer();
				else
					((WidgetProducer) widget).producer = -1;
			} else if (widget instanceof WidgetTankMode) {
				int ID = ((WidgetTankMode) widget).ID;
				if(getData(ID) != null)
					((WidgetTankMode) widget).mode = getData(ID).getMode();
				else
					((WidgetTankMode) widget).mode = null;
			}
		}
	}
	
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill, ModuleStack stack, IModular modular) {
		if (resource == null)
			return 0;
		for(int i = 0;i < datas.length;i++){
			TankData data = datas[i];
			if(data == null || data.getTank().isFull())
				continue;
			if(!data.getTank().isEmpty()){
				if(resource.getFluid() != data.getTank().getFluid().getFluid())
					continue;
			}
			if(modular.getFluidProducers() != null && !modular.getFluidProducers().isEmpty()){
				ModuleStack stackT = modular.getFluidProducers().get(data.getProducer());
				if(!(stack == null) && !stack.equals(stackT))
					continue;
			}
			if(data.getMode() == TankMode.OUTPUT && stack != null || data.getMode() == TankMode.INPUT && stack == null){
				if(from == data.getDirection() || from == ForgeDirection.UNKNOWN || data.getDirection() == ForgeDirection.UNKNOWN){
					return data.getTank().fill(resource, doFill);
				}else
					continue;	
			}else
				continue;
		}
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain, ModuleStack stack, IModular modular) {
		if (resource == null)
			return null;
		for(int i = 0;i < datas.length;i++){
			TankData data = datas[i];
			if(data == null || data.getTank().isEmpty())
				continue;
			if(resource.getFluid() != data.getTank().getFluid().getFluid())
				continue;
			if(modular.getFluidProducers() != null && !modular.getFluidProducers().isEmpty()){
				ModuleStack stackT = modular.getFluidProducers().get(data.getProducer());
				if(!(stack == null) && !stack.equals(stackT))
					continue;
			}
			if(!(data.getMode() == TankMode.NONE)){
				if(from == data.getDirection() || from == ForgeDirection.UNKNOWN || data.getDirection() == ForgeDirection.UNKNOWN){
					return data.getTank().drain(resource, doDrain);
				}else
					continue;
			}else
				continue;	
		}
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain, ModuleStack stack, IModular modular) {
		if (maxDrain < 0)
			return null;
		for(int i = 0;i < datas.length;i++){
			TankData data = datas[i];
			if(data == null || data.getTank().isEmpty())
				continue;
			if(data.getTank().getFluid().amount < 0)
				continue;
			if(modular.getFluidProducers() != null && !modular.getFluidProducers().isEmpty()){
				ModuleStack stackT = modular.getFluidProducers().get(data.getProducer());
				if(!(stack == null) && !stack.equals(stackT))
					continue;
			}
			if(!(data.getMode() == TankMode.NONE)){
				if(from == data.getDirection() || from == ForgeDirection.UNKNOWN || data.getDirection() == ForgeDirection.UNKNOWN){
					return data.getTank().drain(maxDrain, doDrain);
				}else
					continue;	
			}else
				continue;	
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
		for (int i = 0; i < datas.length; i++) {
			if (datas[i] != null)
				infos.add(datas[i].getTank().getInfo());
		}
		return infos.toArray(new FluidTankInfo[infos.size()]);
	}
	
	@Override
	public int getMaxTabs() {
		if(datas == null || datas.length == 0)
			return 1;
		return 1 + datas.length / 3;
	}
	
	@Override
	public void setTab(int tab) {
		this.tab = tab;
	}
	
	@Override
	public int getTab() {
		return tab;
	}
	
	@Override
	public void addTank(int id, IProducerTank tank) {
		if(tank == null)
			datas[id] = null;
		else
			datas[id] = new TankData(new FluidTankBasic(tank.getCapacity()));
	}
	
	@Override
	public List<ITankData> getDatas(IModular modular, ModuleStack producer, TankMode mode) {
		ArrayList<ITankData> datasL = Lists.newArrayList();
		if(producer != null){
			if(modular.getFluidProducers() == null || modular.getFluidProducers().isEmpty() || !modular.getFluidProducers().contains(producer))
				return datasL;
			for(int ID = 0;ID < datas.length;ID++){
				TankData data = datas[ID];
				if(data != null){
					if(mode == data.getMode()){
						if(data.getProducer() == modular.getFluidProducers().indexOf(producer))
							datasL.add(data);
					}
				}
			}
		}	
		return datasL;
	}
	
	
	@Override
	public ITankData getData(int id) {
		return datas[id];
	}
	
	@Override
	public ITankData[] getDatas() {
		return datas;
	}
	
	public class SlotTank extends SlotModular{

		protected int ID;
		
		public SlotTank(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_, ModuleStack stack, int ID) {
			super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_, stack);
			this.ID = ID;
		}
		
		@Override
		public int getSlotStackLimit() {
			return 1;
		}
		
		@Override
		public boolean isItemValid(ItemStack stack) {
			ModuleStack stackModule = ModuleRegistry.getModuleItem(stack);
			if(stackModule != null && stackModule.getModule() != null && stackModule.getProducer() != null && stackModule.getProducer() instanceof IProducerTank)
				return true;
			return false;
		}
		
		@Override
		public void onPickupFromSlot(EntityPlayer  player, ItemStack stack) {
			super.onPickupFromSlot( player, stack);
			IModularTileEntity tileModular = (IModularTileEntity) inventory;
			IModular modular = tileModular.getModular();
			ModuleStack module = ModuleRegistry.getModuleItem(stack);
			if(module != null && module.getModule() != null && module.getProducer() != null && module.getProducer() instanceof IProducerTank){
				((IProducerTank)module.getProducer()).setStorageFluid(getData(ID).getTank().getFluid(), module, stack); 
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
				getData(ID).getTank().setFluid(((IProducerTank)module.getProducer()).getStorageFluid(module, stack));
			}
		}
		
	}

}
