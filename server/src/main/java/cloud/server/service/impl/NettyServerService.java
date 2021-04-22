package cloud.server.service.impl;

import cloud.server.factory.Factory;
import cloud.server.service.CommandDirectory;
import cloud.server.service.ServerService;
import cloud.server.service.impl.handler.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;



public class NettyServerService implements ServerService {

    private static final int SERVER_PORT = 8190;

    private static NettyServerService nettyServer;


    public static NettyServerService getNettyServer() {
        if (nettyServer == null) {
            nettyServer = new NettyServerService();
        }

        return nettyServer;
    }

    @Override
    public void startServer() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            System.out.println("Клиент подключился");
                            socketChannel.pipeline().addLast(new StringDecoder(),
                                    new StringEncoder(), new CommandInBoundHandler(newCommandDir()));
                        }
                    });

            ChannelFuture future = bootstrap.bind(SERVER_PORT).sync();
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            System.out.println("Сервер упал");

        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    public CommandDirectory newCommandDir(){
        return Factory.getCommandDirectory();
    }

}
