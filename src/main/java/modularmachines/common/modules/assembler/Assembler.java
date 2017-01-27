package modularmachines.common.modules.assembler;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import modularmachines.api.ILocatable;
import modularmachines.api.modules.IModuleLogic;
import modularmachines.api.modules.Module;
import modularmachines.api.modules.ModuleData;
import modularmachines.api.modules.ModuleHelper;
import modularmachines.api.modules.assemblers.AssemblerError;
import modularmachines.api.modules.assemblers.EmptyStoragePage;
import modularmachines.api.modules.assemblers.IAssembler;
import modularmachines.api.modules.assemblers.IStoragePage;
import modularmachines.api.modules.containers.IModuleContainer;
import modularmachines.api.modules.storages.EnumStoragePosition;
import modularmachines.api.modules.storages.IStorage;
import modularmachines.api.modules.storages.IStoragePosition;
import modularmachines.client.gui.GuiAssembler;
import modularmachines.common.config.Config;
import modularmachines.common.containers.ContainerAssembler;
import modularmachines.common.core.ModularMachines;
import modularmachines.common.network.PacketHandler;
import modularmachines.common.network.packets.PacketSyncHandlerState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;

public class Assembler implements IAssembler {

	protected final Map<IStoragePosition, IStoragePage> pages;
	protected final List<IStoragePosition> positions;
	protected boolean hasChange = false;
	protected IStoragePosition selectedPosition;
	protected ILocatable locatable;
	
	public Assembler(ILocatable locatable, List<IStoragePosition> positions, NBTTagCompound compound) {
		this(locatable, positions);
		readFromNBT(compound);
	}
	
	public Assembler(ILocatable locatable, List<IStoragePosition> positions) {
		this(locatable, positions, new LinkedHashMap<>());
		createEmptyPages();
	}

	public Assembler(ILocatable locatable, List<IStoragePosition> positions, Map<IStoragePosition, IStoragePage> pages) {
		this.locatable = locatable;
		this.pages = pages;
		this.positions = positions;
		this.selectedPosition = positions.get(0);
		updatePages();
	}

	private void createEmptyPages() {
		for (IStoragePosition position : positions) {
			if(!pages.containsKey(position)){
				pages.put(position, new EmptyStoragePage(this, position));
			}
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		NBTTagList list = new NBTTagList();
		for (IStoragePage page : pages.values()) {
			if (!page.isEmpty()) {
				NBTTagCompound nbtTag = new NBTTagCompound();
				nbtTag.setString("Pos", page.getPosition().getName());
				nbtTag.setTag("Item", page.getStorageStack().writeToNBT(new NBTTagCompound()));
				nbtTag.setTag("Page", page.writeToNBT(new NBTTagCompound()));
				list.appendTag(nbtTag);
			}
		}
		compound.setTag("Pages", list);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		pages.clear();
		createEmptyPages();
		NBTTagList list = compound.getTagList("Pages", 10);
		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound nbtTag = list.getCompoundTagAt(i);
			ItemStack itemStack = new ItemStack(compound.getCompoundTag("Item"));
			String positionName = compound.getString("Pos");
			IStoragePosition position = EnumStoragePosition.NONE;
			for(IStoragePosition pos : positions){
				if(position.getName().equals(positionName)){
					position = pos;
					break;
				}
			}
			if(position != EnumStoragePosition.NONE){
				createPage(itemStack, position);
			}
		}
		updatePages();
	}

	@Override
	public void setSelectedPosition(IStoragePosition position) {
		this.selectedPosition = position;
	}

	@Override
	public IStoragePosition getSelectedPosition() {
		return selectedPosition;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public GuiContainer createGui(InventoryPlayer inventory) {
		return new GuiAssembler(this, inventory);
	}

	@Override
	public Container createContainer(InventoryPlayer inventory) {
		return new ContainerAssembler(this, inventory);
	}
	
	@Override
	public void disassemble(IModuleLogic logic, EntityPlayer player) {
		pages.clear();
		createEmptyPages();
		Map<IStoragePosition, IStoragePage> pages = new HashMap<>();
		for (IStoragePosition position : positions) {
			IStorage storage = logic.getStorage(position);
			if (storage != null) {
				Module module = storage.getModule();
				ModuleData data = module.getData();
				IStoragePage page = data.createStoragePage(this, position);
				pages.put(position, page);
				Collection<IStoragePosition> childPositions = data.getChildPositions(position);
				if (!childPositions.isEmpty()) {
					for(IStoragePosition  childPosition : childPositions){
						IStoragePage childPage = data.createChildPage(this, childPosition);
						page.addChild(childPage);
						pages.put(childPosition, childPage);
					}
				}
			} else {
				pages.put(position, new EmptyStoragePage(this, position));
			}
		}
		if(locatable != null){
			World world = locatable.getWorldObj();
			BlockPos pos = locatable.getCoordinates();
			if (world.isRemote) {
				world.markBlockRangeForRenderUpdate(pos, pos);
			} else {
				if(world instanceof WorldServer){
					//TODO: markDirty
					//modularHandler.markDirty();
					WorldServer server = (WorldServer) world;
					IBlockState blockState = server.getBlockState(pos);
					
					PacketHandler.sendToNetwork(new PacketSyncHandlerState(this, false), pos, server);
					server.notifyBlockUpdate(pos, blockState, blockState, 3);
					player.openGui(ModularMachines.instance, 0, server, pos.getX(), pos.getY(), pos.getZ());
				}
			}
		}
	}

	protected void isToComplex(List<AssemblerError> errors) {
		int complexity = getComplexity();
		int allowedComplexity = getAllowedComplexity();
		if (complexity > allowedComplexity) {
			if (allowedComplexity == Config.defaultAllowedComplexity) {
				errors.add(new AssemblerError(I18n.translateToLocal("modular.assembler.error.no.controller")));
			}
			errors.add(new AssemblerError(I18n.translateToLocal("modular.assembler.error.complexity")));
		}
	}

	@Override
	public int getComplexity() {
		int complexity = 0;
		for (IStoragePage page : pages.values()) {
			if (!page.isEmpty()) {
				complexity += page.getComplexity();
			}
		}
		return complexity;
	}

	@Override
	public int getAllowedComplexity() {
		int allowedComplexity = 0;
		for (IStoragePage page : pages.values()) {
			if (!page.isEmpty()) {
				allowedComplexity += page.getAllowedComplexity();
			}
		}
		return allowedComplexity;
	}

	@Override
	public Collection<IStoragePage> getPages() {
		return pages.values();
	}
	
	@Override
	public IStoragePage getPage(IStoragePosition position){
		return pages.get(position);
	}
	
	@Override
	public List<AssemblerError> canAssemble(){
		List<AssemblerError> errors = new LinkedList<>();
		for(IStoragePage page : pages.values()){
			if(!page.isEmpty()){
				page.canAssemble(this, errors);
				IItemHandler itemHandler = page.getItemHandler();
				for(int i = 0;i < itemHandler.getSlots();i++){
					ItemStack itemStack = itemHandler.getStackInSlot(i);
					IModuleContainer container = ModuleHelper.getContainerFromItem(itemStack);
					if(container != null ){
						ModuleData moduleData = container.getData();
							moduleData.canAssemble(this, errors);
						
					}
				}
			}
		}
		if (getPage(EnumStoragePosition.CASING).isEmpty()) {
			errors.add(new AssemblerError(I18n.translateToLocal("modular.assembler.error.no.casing")));
		}
		
		isToComplex(errors);
		return errors;
	}

	@Override
	public void updatePages() {
		if(pages.isEmpty()){
			createEmptyPages();
		}
		Iterator<IStoragePosition> positions = this.positions.iterator();
		while(positions.hasNext()) {
			IStoragePosition position = positions.next();
			IStoragePage page = getPage(position);
			ItemStack itemStack = page.getStorageStack();
			if (page.isEmpty()) {
				createPage(itemStack, position);
			} else {
				if (itemStack == null && (page.getParent() == null || page.getParent().getStorageStack() == null)) {
					pages.put(position, new EmptyStoragePage(this, position));
				}
			}
		}
		//TODO: markDirty
		//modularHandler.markDirty();
	}
	
	public void createPage(ItemStack itemStack, IStoragePosition position){
		if(itemStack == null){
			return;
		}
		IModuleContainer moduleContainer = ModuleHelper.getContainerFromItem(itemStack);
		if (moduleContainer != null) {
			ModuleData data = moduleContainer.getData();
			if(data.isStorage(position)){
				IStoragePage page = data.createStoragePage(this, position);
				this.pages.put(position, page);
				Collection<IStoragePosition> childPositions = data.getChildPositions(position);
				for(IStoragePosition childPosition : childPositions){
					IStoragePage child = data.createChildPage(this, childPosition);
					page.addChild(child);
					pages.put(childPosition, child);
				}
			}
			
		}
	}

	@Override
	public void onStorageSlotChange() {
		hasChange = true;
	}
	
	@Override
	public ILocatable getLocatable() {
		return locatable;
	}
	
	@Override
	public IStoragePosition getPosition(int index) {
		return positions.get(index);
	}
	
	@Override
	public int getIndex(IStoragePosition position) {
		return positions.indexOf(position);
	}
	
	@Override
	public List<IStoragePosition> getPositions() {
		return positions;
	}
	
}
