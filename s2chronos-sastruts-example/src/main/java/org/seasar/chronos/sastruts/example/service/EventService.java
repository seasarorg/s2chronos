package org.seasar.chronos.sastruts.example.service;

import org.seasar.chronos.sastruts.example.entity.Event;

public class EventService extends AbstractService<Event> {

    public Event findById(Long eventId) {
        return select().id(eventId).getSingleResult();
    }
}