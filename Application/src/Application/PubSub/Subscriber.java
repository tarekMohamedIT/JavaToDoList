package Application.PubSub;

public interface Subscriber {
    void onMessage(Object item, Object source);
    void onError(Throwable throwable);
}
