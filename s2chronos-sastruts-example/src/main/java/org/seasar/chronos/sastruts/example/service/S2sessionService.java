package org.seasar.chronos.sastruts.example.service;

import org.seasar.chronos.sastruts.example.entity.S2session;

public class S2sessionService extends AbstractService<S2session> {

    public S2session findById(String sessionId,String name) {
        return select().id(sessionId, name).getSingleResult();
    }
}