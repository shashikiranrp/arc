package org.kelvin.arc.client;

import java.util.Objects;

/**
 * @author <a href="mailto:shasrp@yahoo-inc.com">Shashikiran</a>
 */
public class RedisNode
{
    public final String ip;
    public final int port;

    public RedisNode(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RedisNode redisNode = (RedisNode) o;
        return Objects.equals(port, redisNode.port) &&
                Objects.equals(ip, redisNode.ip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip, port);
    }

    @Override
    public String toString() {
        return "RedisNode{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                '}';
    }
}
