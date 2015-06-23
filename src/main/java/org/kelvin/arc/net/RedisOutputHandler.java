package org.kelvin.arc.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.util.List;

/**
 * @author <a href="mailto:shasrp@yahoo-inc.com">Shashikiran</a>
 */
public class RedisOutputHandler extends MessageToByteEncoder<List<String>>
{
    public static final RedisOutputHandler INSTANCE = new RedisOutputHandler();

    private RedisOutputHandler()
    {

    }

    @Override
    protected void encode(ChannelHandlerContext ctx, List<String> msg, ByteBuf out) throws Exception
    {
        if (null == msg || msg.isEmpty()) {
            return;
        }

        out.writeBytes(msg.stream().reduce("*" + msg.size() + RedisCodecs.CRLF, (a, b) -> {
            return a.concat(RedisCodecs.INSTANCE.encodeBulkString(b));
        }).getBytes("UTF-8"));
    }
}
