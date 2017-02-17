package com.tg.rpc.core.pool;

import com.tg.rpc.core.bootstrap.Client;
import io.netty.channel.*;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * Created by twogoods on 17/2/16.
 */
public class ChannelConnectionFactory extends BasePooledObjectFactory<Channel> {

    private Client client;

    public ChannelConnectionFactory(Client client) {
        this.client = client;
    }

    @Override
    public Channel create() {
        return client.initConnection();
    }

    @Override
    public PooledObject<Channel> wrap(Channel channel) {
        return new DefaultPooledObject<Channel>(channel);
    }

    @Override
    public void destroyObject(PooledObject<Channel> p) throws Exception {
        p.getObject().close();
    }

    @Override
    public boolean validateObject(PooledObject<Channel> p) {
        return p.getObject().isActive();
    }
}
