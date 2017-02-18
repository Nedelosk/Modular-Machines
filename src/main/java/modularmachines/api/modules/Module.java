package modularmachines.api.modules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import modularmachines.api.modules.assemblers.IAssembler;
import modularmachines.api.modules.containers.IModuleContainer;
import modularmachines.api.modules.logic.IModuleLogic;
import modularmachines.api.modules.pages.ModulePage;
import modularmachines.api.modules.storages.IStorage;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class Module implements ICapabilityProvider {

	protected final List<ModulePage> pages;
	protected final IModuleStorage storage;
	protected final IModuleLogic logic;
	@Nullable
	protected ModuleData data;
	@Nullable
	protected ItemStack parentItem;
	protected int index;
	
	public Module(IModuleStorage storage) {
		pages = new ArrayList<>();
		this.storage = storage;
		this.logic = storage.getLogic();
		initPages();
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}
	
	@SideOnly(Side.CLIENT)
	public EnumWallType getWallType(){
		return EnumWallType.WALL;
	}

	@SideOnly(Side.CLIENT)
	@Nullable
	public ResourceLocation getWindowLocation(){
		return null;
	}
	
	public List<ModulePage> getPages(){
		return pages;
	}
	
	public ModulePage getPage(int index){
		if(index >= pages.size() || index < 0){
			return null;
		}
		return pages.get(index);
	}
	
	protected void addPage(ModulePage page){
		if(!pages.contains(page)){
			page.setIndex(pages.size());
			pages.add(page);
		}
	}

	/**
	 * @return The item that the module drop.
	 */
	public List<ItemStack> getDrops(){
		return Collections.singletonList(parentItem);
	}
	
	public void onLoad(){
		
	}
	
	public void onAssemble(IAssembler assembler, IModuleLogic logic, IStorage storage){
		
	}
	
	public void onCreateModule(IModuleContainer container, ModuleData data, ItemStack parentItem){
		this.parentItem = parentItem;
		this.data = data;
	}
	
	protected void initPages(){
		
	}

	/* MODULE CONTAINERS */

	/**
	 * Add a tooltip to a item that are registered for a module container with
	 * this module.
	 */
	@SideOnly(Side.CLIENT)
	public void addTooltip(List<String> tooltip, ItemStack stack){
	}

	public boolean isClean(){
		return true;
	}

	public void sendModuleUpdate(){
		
	}
	
    public NBTTagCompound writeToNBT(NBTTagCompound compound){
    	compound.setInteger("Index", index);
    	compound.setTag("Parent", parentItem.serializeNBT());
    	compound.setString("Data", data.getRegistryName().toString());
    	return compound;
    }
    
    public void readFromNBT(NBTTagCompound compound){
    	index = compound.getInteger("Index");
    	parentItem = new ItemStack(compound.getCompoundTag("Parent"));
    	data = GameRegistry.findRegistry(ModuleData.class).getValue(new ResourceLocation(compound.getString("Data")));
    }
	
	public IModuleLogic getLogic() {
		return logic;
	}
	
	public IModuleStorage getStorage() {
		return storage;
	}
	
	@Nullable
	public ItemStack getParentItem() {
		return parentItem;
	}
	
	@Nullable
	public ModuleData getData() {
		return data;
	}
	
	public IItemHandler getItemHandler(){
		return null;
	}
	
	public IFluidHandler getFluidHandler(){
		return null;
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(getItemHandler());
		}else if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(getFluidHandler());
		}
		return null;
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return getFluidHandler() != null && capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || getItemHandler() != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
	}
	
	/* MODEL */
	protected boolean modelNeedReload;
	
	public void setModelNeedReload(boolean modelNeedReload) {
		this.modelNeedReload = modelNeedReload;
	}
	
	public boolean isModelNeedReload() {
		return modelNeedReload;
	}
	
}
