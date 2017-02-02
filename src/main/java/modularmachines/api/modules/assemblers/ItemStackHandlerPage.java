package modularmachines.api.modules.assemblers;

import net.minecraftforge.items.ItemStackHandler;

public class ItemStackHandlerPage extends ItemStackHandler {

	private final IAssembler assembler;
	private final IStoragePage page;
	
    public ItemStackHandlerPage(int size, IStoragePage page){
        super(size);
        this.assembler = page.getAssembler();
        this.page = page;
    }
    
    @Override
    protected void onContentsChanged(int slot) {
    	if(page.wasInitialized() && slot == 0){
    		assembler.updatePages();
    	}
    }
    
	@Override
	public int getSlotLimit(int slot) {
		return 1;
	}
	
}
