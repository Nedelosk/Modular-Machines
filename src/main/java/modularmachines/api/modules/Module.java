package modularmachines.api.modules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import modularmachines.api.modules.assemblers.IAssembler;
import modularmachines.api.modules.containers.IModuleContainer;
import modularmachines.api.modules.pages.ModulePage;
import modularmachines.api.modules.storages.IStorage;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Module {

	protected final List<ModulePage> pages;
	protected final IModuleStorage storage;
	protected final IModuleLogic logic;
	protected int index;
	protected ItemStack parentItem;
	protected ModuleData data;
	
	public Module(IModuleStorage storage) {
		this.storage = storage;
		this.logic = storage.getLogic();
		pages = new ArrayList<>();
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
		return pages.get(index);
	}
	
	protected void addPage(ModulePage page){
		if(!pages.contains(page)){
			pages.add(page);
			page.setIndex(pages.size()-1);
		}
	}

	/**
	 * @return The item that the module drop.
	 */
	public List<ItemStack> getDrops(){
		return Collections.singletonList(parentItem);
	}
	
	public void onCreateModule(IModuleContainer container, ItemStack parentItem){
		this.parentItem = parentItem;
	}
	
	public void initPages(){
		
	}

	public void assembleModule(IAssembler assembler, IModuleLogic logic, IStorage storage){
		
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
    	return compound;
    }
    
    public void readFromNBT(NBTTagCompound compound){
    	
    }
	
	public IModuleLogic getLogic() {
		return logic;
	}
	
	public ItemStack getParentItem() {
		return parentItem;
	}
	
	public IModuleStorage getStorage() {
		return storage;
	}
	
	public ModuleData getData() {
		return data;
	}
	
}
