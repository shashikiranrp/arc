package org.kelvin.arc.type;

import com.google.common.base.Preconditions;

/**
 * @author <a href="mailto:shasrp@yahoo-inc.com">Shashikiran</a>
 */
public abstract class RedisType
{
    protected final String key;

    public RedisType(String key) {
        this.key = Preconditions.checkNotNull(key);
    }
}
