package com.gmail.picono435.picojobs.api.managers;

import com.gmail.picono435.picojobs.api.events.JobPlayerEvent;

import java.util.*;

public class EventsManager {

    private Map<Class<? extends JobPlayerEvent>, List<JobPlayerEvent.Listener<?>>> registedListeners = new HashMap<>();

    /**
     * Registers a listener that will be notified once the specified event occurs.
     *
     *
     * @param eventClass the class of the event
     * @param listener the listener for the event
     * @see <a href="https://github.com/Picono435/PicoJobs/wiki/Using-The-API">API Documentation</a>
     */
    public <T extends JobPlayerEvent> void registerListener(Class<T> eventClass, JobPlayerEvent.Listener<T> listener) {
        if(registedListeners.containsKey(eventClass)) {
            registedListeners.get(eventClass).add(listener);
        } else {
            registedListeners.put(eventClass, Collections.singletonList(listener));
        }
    }

    public <T extends JobPlayerEvent> T consumeListeners(T event) {
        for(JobPlayerEvent.Listener<?> listener : registedListeners.get(event.getClass())) {
            ((JobPlayerEvent.Listener<T>)listener).onEvent(event);
        }
        return event;
    }

}
