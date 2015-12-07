package org.kelvin.arc.client.codec;

/**
 * @author <a href="mailto:shasrp@yahoo-inc.com">Shashikiran</a>
 */
public class RedisError
{
    public static final byte ERROR_START_BYTE = '-';
    private final String errorString;

    public RedisError(String errorString)
    {
        this.errorString = errorString;
    }

    public String getErrorString()
    {
        return errorString;
    }
}
