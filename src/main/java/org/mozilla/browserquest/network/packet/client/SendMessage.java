package org.mozilla.browserquest.network.packet.client;

import org.mozilla.browserquest.inject.LazyInject;
import org.mozilla.browserquest.network.packet.Packet;
import org.mozilla.browserquest.service.ChatHandler;

public class SendMessage extends Packet {

    @LazyInject
    private ChatHandler chatHandler;

    private String message;

    @Override
    public void setData(Object[] data) {
        message = (String) data[0];
    }

    @Override
    public void run() {
        chatHandler.handle(getConnection().getPlayer(), message);
    }
}
