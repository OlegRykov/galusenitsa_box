package cloud.server.service.impl.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.*;

public class FilesWriteHandler extends ChannelInboundHandlerAdapter {
    private File file;

    public FilesWriteHandler(File file) {
        this.file = file;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object sendFile) {

        try (OutputStream os = new BufferedOutputStream(new FileOutputStream(file, true))) {
            os.write((byte[]) sendFile);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        ctx.pipeline().remove("fileHandler");
    }
}
