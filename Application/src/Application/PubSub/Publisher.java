package Application.PubSub;


import Application.Results.Result;
import Application.Results.ResultState;
import Application.Results.ResultsHelper;

import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

public class Publisher {
    private static Publisher _instance;
    public static Publisher getInstance() {
        if (_instance == null) _instance = new Publisher();

        return _instance;
    }

    private final ConcurrentHashMap<String, HashSet<Subscriber>> _subscribers;
    private Publisher(){
        _subscribers = new ConcurrentHashMap<>();
    }

    public Publisher subscribe(String message, Subscriber subscriber){
        if (!_subscribers.containsKey(message))
            _subscribers.put(message, new HashSet<>());

        _subscribers.get(message).add(subscriber);
        return this;
    }

    public void publish(Object itemToSend, Object source,String... messages){
        for (String message: messages) {
            HashSet<Subscriber> meantSubs = _subscribers.get(message);

            if(meantSubs == null) continue;

            for (Subscriber sub : meantSubs) {
                Result result = ResultsHelper.tryDo(() -> sub.onMessage(itemToSend, source));
                if (result.getState() == ResultState.FAIL) sub.onError(result.getException());
            }
        }
    }
}
