package org.kelvin.arc.client.codec;

import org.kelvin.arc.GenericResult;

/**
 * @author <a href="mailto:shasrp@yahoo-inc.com">Shashikiran</a>
 */
public class BooleanDecoder extends RedisDecoder<Boolean>
{

    public BooleanDecoder(GenericResult<Either<Boolean, RedisError>> result)
    {
        super(result);
    }

    @Override
    public Boolean parse(String msg)
    {
        return ":1".equals(msg);
    }
}
