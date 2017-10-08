package modularmachines.common.modules.assembler;

import com.google.common.base.Preconditions;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.ILocatable;
import modularmachines.api.modules.IModuleType;
import modularmachines.api.modules.Module;
import modularmachines.api.modules.ModuleData;
import modularmachines.api.modules.ModuleHelper;
import modularmachines.api.modules.assemblers.AssemblerError;
import modularmachines.api.modules.assemblers.IAssembler;
import modularmachines.api.modules.assemblers.IModuleSlot;
import modularmachines.api.modules.assemblers.IModuleSlots;
import modularmachines.api.modules.assemblers.IStoragePage;
import modularmachines.api.modules.containers.IModuleContainer;
import modularmachines.api.modules.logic.IModuleLogic;
import modularmachines.api.modules.storages.EnumStoragePosition;
import modularmachines.api.modules.storages.IStorage;
import modularmachines.api.modules.storages.IStoragePosition;
import modularmachines.client.gui.GuiAssembler;
import modularmachines.common.config.Config;
import modularmachines.common.containers.ContainerAssembler;
import modularmachines.common.modules.assembler.page.EmptyStoragePage;
import modularmachines.common.network.PacketHandler;
import modularmachines.common.network.packets.PacketSyncHandlerState;
import modularmachines.common.utils.ContainerUtil;
import modularmachines.common.utils.ItemUtil;
import modularmachines.common.utils.Translator;

public class Assembler implements IAssembler {

	protected final Map<IStoragePosition, IStoragePage> pages;
	protected final List<IStoragePosition> positions;
	protected final Map<IModuleType, ModuleTypeData> types;
	protected boolean hasChange = false;
	protected IStoragePosition selectedPosition;
	protected ILocatable locatable;
	
	public Assembler(ILocatable locatable, List<IStoragePosition> positions) {
		this(locatable, positions, new LinkedHashMap<>());
		createEmptyPages();
	}

	public Assembler(ILocatable locatable, List<IStoragePosition> positions, Map<IStoragePosition, IStoragePage> pages) {
		Preconditions.checkNotNull(locatable);
		Preconditions.checkNotNull(positions);
		Preconditions.checkNotNull(pages);
		this.locatable = locatable;
		this.pages = pages;
		this.positions = positions;
		this.selectedPosition = positions.get(0);
		this.types = new HashMap<>();
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
			ItemStack itemStack = new ItemStack(nbtTag.getCompoundTag("Item"));
			String positionName = nbtTag.getString("Pos");
			IStoragePosition position = EnumStoragePosition.NONE;
			for(IStoragePosition pos : positions){
				if(pos.getName().equals(positionName)){
					position = pos;
					break;
				}
			}
			if(position != EnumStoragePosition.NONE){
				IStoragePage page = createPage(itemStack, position, null);
				page.readFromNBT(nbtTag.getCompoundTag("Page"));
				pages.put(position, page);
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
		for (IStoragePosition position : positions) {
			IStorage storage = logic.getStorage(position);
			if (storage != null) {
				Module module = storage.getModule();
				ModuleData data = module.getData();
				IStoragePage page = data.createStoragePage(this, position, storage);
				pages.put(position, page);
			} else {
				pages.put(position, new EmptyStoragePage(this, position));
			}
		}
		logic.clear();
		World world = locatable.getWorldObj();
		BlockPos pos = locatable.getCoordinates();
		if (world.isRemote) {
			world.markBlockRangeForRenderUpdate(pos, pos);
		} else {
			if(world instanceof WorldServer){
				WorldServer server = (WorldServer) world;
				IBlockState blockState = server.getBlockState(pos);
				
				PacketHandler.sendToNetwork(new PacketSyncHandlerState(this, false), pos, server);
				server.notifyBlockUpdate(pos, blockState, blockState, 3);
				ContainerUtil.openOrCloseGuiSave(logic, 0, true);
			}
		}
	}
	
	@Override
	public void clear() {
		pages.clear();
		createEmptyPages();
	}

	protected void isToComplex(List<AssemblerError> errors) {
		int complexity = getComplexity();
		int allowedComplexity = getAllowedComplexity();
		if (complexity > allowedComplexity) {
			if (allowedComplexity == Config.defaultAllowedComplexity) {
				errors.add(new AssemblerError(Translator.translateToLocal("modular.assembler.error.no.controller")));
			}
			errors.add(new AssemblerError(Translator.translateToLocal("modular.assembler.error.complexity")));
		}
	}
	
	protected void checkTypes(List<AssemblerError> errors) {
		for(ModuleTypeData typeData : types.values()){
			IModuleType type = typeData.type;
			type.isValidModuleCount(this, typeData.moduleCount, errors);
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
		types.clear();
		for(IStoragePage page : pages.values()){
			if(!page.isEmpty()){
				page.canAssemble(this, errors);
				IModuleSlots slots = page.getSlots();
				for(IModuleSlot slot : slots){
					ItemStack itemStack = slot.getItem();
					IModuleContainer container = ModuleHelper.getContainerFromItem(itemStack);
					if(container != null ){
						ModuleData moduleData = container.getData();
						moduleData.canAssemble(this, errors);
						for(IModuleType type : moduleData.getTypes(itemStack)){
							ModuleTypeData typeData = types.computeIfAbsent(type, t -> new ModuleTypeData(t));
							typeData.addModuleCount(1);
						}
					}
				}
			}
		}
		if (getPage(EnumStoragePosition.CASING).isEmpty()) {
			errors.add(new AssemblerError(Translator.translateToLocal("modular.assembler.error.no.casing")));
		}
		
		isToComplex(errors);
		checkTypes(errors);
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
			if (page.isEmpty() && !ItemUtil.isEmpty(itemStack) || !page.isEmpty() && ItemUtil.isEmpty(itemStack) && (page.getParent() == null || ItemUtil.isEmpty(page.getParent().getStorageStack()))) {
				pages.put(position, createPage(itemStack, position, page));
			}
		}
	}
	
	public IStoragePage createPage(ItemStack itemStack, IStoragePosition position, @Nullable IStoragePage oldPage){
		if(ItemUtil.isEmpty(itemStack)){
			return new EmptyStoragePage(this, position);
		}
		IModuleContainer moduleContainer = ModuleHelper.getContainerFromItem(itemStack);
		if (moduleContainer != null) {
			ModuleData data = moduleContainer.getData();
			if(data.isStorage(position)){
				IStoragePage page = data.createStoragePage(this, position, null);
				page.getSlots().setItem(0, itemStack.copy());
				page.init();
				return page;
			}
		}
		return new EmptyStoragePage(this, position);
	}

	@Override
	public void onStorageSlotChange() {
		hasChange = true;
	}
	
	@Override
	public boolean hasChange() {
		return hasChange;
	}
	
	@Override
	public void setHasChange(boolean hasChange) {
		this.hasChange =hasChange;
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
	
	private static class ModuleTypeData{
		public int moduleCount = 0;
		public final IModuleType type;
		
		public ModuleTypeData(IModuleType type) {
			this.type = type;
		}
		
		public void addModuleCount(int moduleCount) {
			this.moduleCount += moduleCount;
		}
	}
	
}
