package cloud.server.service.impl.commands;

import cloud.commands.Command;
import cloud.server.service.CommandDirectory;
import cloud.server.service.CommandWork;
import cloud.server.service.impl.NettyServerService;
import io.netty.buffer.ByteBuf;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class SendFile implements CommandWork {
    private String name;
    private CommandDirectory commandDirectory;

    public SendFile(CommandDirectory commandDirectory) {
        this.commandDirectory = commandDirectory;
        name = Command.SEND_FILE;
    }

    @Override
    public String commandWork(Object msg) {
        String[] nameAndFile = msg.toString().split(System.lineSeparator(), 2);
        Object obj = nameAndFile[1].getBytes(StandardCharsets.UTF_8);
        ByteBuf byteBuf = (ByteBuf) obj;

        try (OutputStream os = new BufferedOutputStream(new FileOutputStream("C:\\galusenitsa_box\\ggg.html",
          true))) {
            System.out.println(commandDirectory.getServerDir()
                    + "\\" + nameAndFile[0]);
            while (byteBuf.isReadable()) {
//                System.out.println(byteBuf.readByte());
                os.write(byteBuf.readByte());

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        byteBuf.release();

        return "Успешно";
    }

    @Override
    public String getName() {
        return name;
    }
}
