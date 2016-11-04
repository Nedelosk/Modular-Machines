package modularmachines.api.modules.transport;

public abstract class TransportCycle<H> implements ITransportCycle<H> {

	protected final int time;
	protected final int property;
	protected final ITransportHandlerWrapper<H> startHandler;
	protected final int[] startSlots;
	protected final ITransportHandlerWrapper<H> endHandler;
	protected final int[] endSlots;
	protected final int amount;

	public TransportCycle(ITransportHandlerWrapper<H> startHandler, int[] startSlots, ITransportHandlerWrapper<H> endHandler, int[] endSlots, int time, int property, int amount) {
		this.startHandler = startHandler;
		if (startSlots == null) {
			startSlots = generateDefaultSlots(startHandler.getHandler());
		}
		if (endSlots == null) {
			endSlots = generateDefaultSlots(endHandler.getHandler());
		}
		this.startSlots = startSlots;
		this.endHandler = endHandler;
		this.endSlots = endSlots;
		this.time = time;
		this.property = property;
		this.amount = amount;
	}

	protected abstract int[] generateDefaultSlots(H handler);

	@Override
	public int getTime() {
		return time;
	}

	@Override
	public int getProperty() {
		return property;
	}

	@Override
	public int[] getStartSlots() {
		return startSlots;
	}

	@Override
	public ITransportHandlerWrapper<H> getStartHandler() {
		return startHandler;
	}

	@Override
	public int[] getEndSlots() {
		return endSlots;
	}

	@Override
	public ITransportHandlerWrapper<H> getEndHandler() {
		return endHandler;
	}
}
