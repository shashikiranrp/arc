package org.kelvin.arc.client.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.kelvin.arc.GenericResult;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @author <a href="mailto:shasrp@yahoo-inc.com">Shashikiran</a>
 */
public abstract class RedisDecoder<T> extends ByteToMessageDecoder
{

    final protected GenericResult<Either<T, RedisError>> result;

    public RedisDecoder(GenericResult<Either<T, RedisError>> result)
    {
        this.result = result;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception
    {
        if ('-' == in.getByte(0)) {
            result.setData(new Either<T, RedisError>(null, new RedisError(in.toString(Charset.forName("UTF-8")))));
        } else {
            result.setData(new Either<T, RedisError>(parse(in.toString(Charset.forName("UTF-8"))), null));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        super.exceptionCaught(ctx, cause);
        result.setError(cause);
    }

    abstract public T parse(String msg);
}
