package com.interactuamovil.contactosms.api;

/**
 *
 * @author sergeiw
 */
public interface InboxMessageCallback {

    public void handleInboxMessage(String inboxJsonString);

}
