package com.tg.rpc.core.pool;

import com.tg.rpc.core.bootstrap.Client;
import io.netty.channel.Channel;
import lombok.Data;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;


/**
 * Created by twogoods on 17/2/16.
 */
@Data
public class ChannelPoolWrapper {
    private String host;
    private int port;
    private long borrowMaxWaitMillis;
    private GenericObjectPool<Channel> pool;

    public ChannelPoolWrapper(Client client, String host, int port) {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(client.getMaxTotal());
        config.setMaxIdle(client.getMaxIdle());
        config.setMinIdle(client.getMinIdle());
        this.borrowMaxWaitMillis = client.getBorrowMaxWaitMillis();
        this.host = host;
        this.port = port;
        pool = new GenericObjectPool<Channel>(new ChannelConnectionFactory(client, host, port), config);
        try {
            pool.preparePool();
        } catch (Exception e) {
            throw new RuntimeException("{init connectionpool error: {}}", e);
        }
    }

    public void close() {
        pool.close();
    }

    public Channel getObject() throws Exception {
        return pool.borrowObject(borrowMaxWaitMillis);
    }

    public void returnObject(Channel channel) {
        pool.returnObject(channel);
    }
}
