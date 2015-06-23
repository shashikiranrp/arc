package org.kelvin.arc.net;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;

/**
 * @author <a href="mailto:shasrp@yahoo-inc.com">Shashikiran</a>
 */
public class RedisInstanceChannels
{
    public static final int REDIS_MAX_STR_LEN = 512 * 1024 * 1024;

    static final boolean STRIP_CRLF = true;
    static final boolean FAIL_FAST = false;

    protected final EventLoopGroup eventLoopGroup;

    public RedisInstanceChannels(EventLoopGroup eventLoopGroup)
    {
        this.eventLoopGroup = eventLoopGroup;
    }

    public void shutdownGroup()
    {
        this.eventLoopGroup.shutdownGracefully().awaitUninterruptibly();
    }

    public ChannelFuture getChannel(String inetAddress, int port, ChannelHandler... handlers)
    {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(this.eventLoopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        /**
                         * outbound handler
                         */
                        ch.pipeline().addLast(RedisOutputHandler.INSTANCE);
                        /**
                         * redis basic decoder
                         */
                        ch.pipeline().addLast(new LineBasedFrameDecoder(REDIS_MAX_STR_LEN, STRIP_CRLF, FAIL_FAST));
                        /**
                         * client handlers
                         */
                        ch.pipeline().addLast(handlers);
                    }
                });
        return bootstrap.connect(inetAddress, port);
    }
}
