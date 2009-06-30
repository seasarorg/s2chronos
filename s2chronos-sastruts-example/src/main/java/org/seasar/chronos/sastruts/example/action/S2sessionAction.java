package org.seasar.chronos.sastruts.example.action;

import java.sql.Timestamp;


import javax.annotation.Resource;

import org.apache.struts.action.ActionMessages;
import org.seasar.framework.beans.util.Beans;
import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;
import java.util.List;

import org.seasar.chronos.sastruts.example.entity.S2session;
import org.seasar.chronos.sastruts.example.service.S2sessionService;
import org.seasar.chronos.sastruts.example.form.S2sessionForm;

public class S2sessionAction {

    public List<S2session> s2sessionItems;
    
    @ActionForm
    @Resource
    protected S2sessionForm s2sessionForm;

    @Resource
    protected S2sessionService s2sessionService;

    @Execute(validator = false)
    public String index() {
        s2sessionItems = s2sessionService.findAll();
        return "list.html";
    }

    @Execute(validator = false, urlPattern = "show/{sessionId}/{name}")
    public String show() {
        S2session entity = s2sessionService.findById(s2sessionForm.sessionId, s2sessionForm.name);
        Beans.copy(entity, s2sessionForm).execute();
        return "show.html";
    }

    @Execute(validator = false, urlPattern = "edit/{sessionId}/{name}")
    public String edit() {
        S2session entity = s2sessionService.findById(s2sessionForm.sessionId, s2sessionForm.name);
        Beans.copy(entity, s2sessionForm).execute();
        return "edit.html";
    }

    @Execute(validator = false)
    public String create() {
        return "create.html";
    }

    @Execute(validator = false, urlPattern = "delete/{sessionId}/{name}", redirect = true)
    public String delete() {
        S2session entity = Beans.createAndCopy(S2session.class, s2sessionForm).execute();
        s2sessionService.delete(entity);
        return "/s2session/";
    }

    @Execute(input = "create.html", redirect = true)
    public String insert() {
        S2session entity = Beans.createAndCopy(S2session.class, s2sessionForm).execute();
        s2sessionService.insert(entity);
        return "/s2session/";
    }

    @Execute(input = "edit.html", redirect = true)
    public String update() {
        S2session entity = Beans.createAndCopy(S2session.class, s2sessionForm).execute();
        s2sessionService.update(entity);
        return "/s2session/";
    }
}