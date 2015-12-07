package org.kelvin.arc.client;

import com.google.common.util.concurrent.ListenableFuture;
import org.kelvin.arc.type.*;

/**
 * @author <a href="mailto:shasrp@yahoo-inc.com">Shashikiran</a>
 */
public interface RedisCacheService extends AutoCloseable
{
    ListenableFuture<Boolean> keyExists(String key);

    RedisString getString(String key);

    RedisList getList(String key);

    RedisHash getHash(String key);

    RedisSet getSet(String key);

    RedisSortedSet getSortedSet(String key);
}
