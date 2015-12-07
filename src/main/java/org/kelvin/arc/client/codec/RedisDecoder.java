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
        if (RedisError.ERROR_START_BYTE == in.getByte(0)) {
            result.setData(Either.getRight(new RedisError(in.toString(Charset.forName("UTF-8")))));
        } else {
            result.setData(Either.getLeft(parse(in.toString(Charset.forName("UTF-8")))));
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
