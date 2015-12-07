package org.kelvin.arc;

import io.netty.bootstrap.Bootstrap;
import org.kelvin.arc.client.NettyBasedRedisService;
import org.kelvin.arc.client.RedisCacheService;
import org.kelvin.arc.client.RedisNode;

/**
 * @author <a href="mailto:shasrp@yahoo-inc.com">Shashikiran</a>
 */
public class App {


    public static void main(String[] args) throws Exception {
        final String KEY = args.length < 1 ? "test_key" : args[0];
        RedisNode redisNode = new RedisNode("127.0.0.1", 6379);
        try(RedisCacheService redisCacheService = new NettyBasedRedisService(redisNode)) {
            if (redisCacheService.keyExists(KEY).get()) {
                System.out.println(KEY + " is present in " + redisNode);
            } else {
                System.out.println(KEY + " is absent in " + redisNode);
            }
        }
    }
}
