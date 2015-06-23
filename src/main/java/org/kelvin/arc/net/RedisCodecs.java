package org.kelvin.arc.net;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:shasrp@yahoo-inc.com">Shashikiran</a>
 */
public class RedisCodecs
{

    public static final String CRLF = "\r\n";
    public static final String NULL_BULK_STRING = "$-1".concat(CRLF);
    public static final String NULL_ARRAY = "*-1".concat(CRLF);
    public static final RedisCodecs INSTANCE = new RedisCodecs();

    public String encodeInteger(int value)
    {
        return ":".concat(String.valueOf(value)).concat(CRLF);
    }

    public String encodeSimpleString(String value)
    {
        return "+".concat(value).concat(CRLF);
    }

    public String encodeErrorString(String value)
    {
        return "-".concat(value).concat(CRLF);
    }

    public String encodeBulkString(String value)
    {
        if (null == value) {
            return NULL_BULK_STRING;
        }
        return "$".concat(String.valueOf(value.length())).concat(CRLF).concat(value).concat(CRLF);
    }

    public <T> String encodeArray(List<T> arraay)
    {
        if (null == arraay) {
            return NULL_ARRAY;
        }

        final StringBuilder builder = new StringBuilder("*").append(arraay.size()).append(CRLF);
        arraay.stream().forEach((Object obj) -> {
            if (null == obj) {
                builder.append(encodeBulkString(null));
                return;
            }
            else if (Integer.class.isAssignableFrom(obj.getClass())) {
                builder.append(encodeInteger((int) obj));
            } else if (String.class.isAssignableFrom(obj.getClass())) {
                builder.append(encodeBulkString((String) obj));
            } else if (List.class.isAssignableFrom(obj.getClass())) {
                builder.append(encodeArray((List<Object>) obj));
            } else {
                throw new IllegalArgumentException("can't encode type: ".concat(obj.getClass().getCanonicalName()));
            }
        });
        return builder.toString();
    }

    public int decodeInteger(String value)
    {
        int len = value.length();
        return Integer.valueOf(value.substring(1, len - 1));
    }

    public String decodeSimpleString(String value)
    {
        int len = value.length();
        return value.substring(1, len - 1);
    }

    public String decodeErrorString(String value)
    {
        return decodeSimpleString(value);
    }

    public String decodeBulkString(String value)
    {
        if (NULL_BULK_STRING.equals(value)) {
            return null;
        }
        int firstCRLFIndex = value.indexOf(CRLF);
        if (-1 == firstCRLFIndex) {
            throw new IllegalArgumentException("not a bulk string decode: ".concat(value));
        }

        return value.substring(firstCRLFIndex + 1, value.length() - 1);
    }

    public List<Object> decodeArray(String value)
    {
        if (NULL_ARRAY.equals(value)) {
            return null;
        }

        List<Object> array = new ArrayList<>();
        return array;
    }
}
