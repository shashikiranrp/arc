package org.kelvin.arc.client;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import org.kelvin.arc.GenericResult;
import org.kelvin.arc.client.codec.BooleanDecoder;
import org.kelvin.arc.client.codec.Either;
import org.kelvin.arc.client.codec.RedisError;
import org.kelvin.arc.net.RedisInstanceChannels;
import org.kelvin.arc.type.*;

import java.util.Arrays;
import java.util.concurrent.Future;

/**
 * @author <a href="mailto:shasrp@yahoo-inc.com">Shashikiran</a>
 */
public class NettyBasedRedisService implements RedisCacheService
{

    final RedisNode cacheNodes[];
    static final RedisInstanceChannels REDIS_INSTANCE_CHANNELS =
            new RedisInstanceChannels(new NioEventLoopGroup());

    public NettyBasedRedisService(RedisNode... cacheNodes)
    {
        if (null == cacheNodes || 0 == cacheNodes.length) {
            throw new IllegalArgumentException("cache nodes are empty!");
        }
        this.cacheNodes = cacheNodes;
    }

    RedisNode selectNodeForKey(String key)
    {
        return this.cacheNodes[0];
    }

    protected ChannelFuture getChannelForKey(String key, ChannelHandler... handlers)
    {
        RedisNode redisNode = selectNodeForKey(key);
        return REDIS_INSTANCE_CHANNELS.getChannel(redisNode.ip, redisNode.port, handlers);
    }

    @Override
    public ListenableFuture<Boolean> keyExists(String key)
    {
        final GenericResult<Either<Boolean, RedisError>> result = new GenericResult<>();
        ChannelFuture cf = getChannelForKey(key, new BooleanDecoder(result));
        cf.addListener((connectionFuture) -> {
            cf.channel()
                    .pipeline()
                    .writeAndFlush(Arrays.asList("EXISTS", key));
        });
        return Futures.transform(result, (Either<Boolean, RedisError> either) -> {
            final GenericResult<Boolean> finalResult = new GenericResult<Boolean>();
            if (either.isLeft()) {
                finalResult.setData(either.left());
            } else {
                finalResult.setError(new RuntimeException("error from redis: ".concat(either.right().getErrorString())));
            }
            return finalResult;
        });
    }

    @Override
    public RedisString getString(String key)
    {
        return null;
    }

    @Override
    public RedisList getList(String key)
    {
        return null;
    }

    @Override
    public RedisHash getHash(String key)
    {
        return null;
    }

    @Override
    public RedisSet getSet(String key)
    {
        return null;
    }

    @Override
    public RedisSortedSet getSortedSet(String key)
    {
        return null;
    }

    @Override
    public void shutdown()
    {
        REDIS_INSTANCE_CHANNELS.shutdownGroup();
    }
}
