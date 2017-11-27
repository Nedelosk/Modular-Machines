package modularmachines.api.modules.events;

public interface IEventListener<E extends Event> {
	void onEvent(E event);
}
