package tw.zhuran.crocus.server.packet;

import io.netty.buffer.ByteBuf;
import tw.zhuran.crocus.domain.Action;
import tw.zhuran.crocus.domain.Force;
import tw.zhuran.crocus.domain.Kind;
import tw.zhuran.crocus.domain.Position;
import tw.zhuran.crocus.util.Meta;

public class Packets {
    public static Packet packet(ByteBuf buf) {
        int type = buf.readInt();
        PacketType packetType = Meta.enumFromInt(PacketType.class, type);
        return parse(packetType, buf);
    }

    private static Packet parse(PacketType packetType, ByteBuf buf) {
        switch (packetType) {
            case ACTION:
                byte[] content = new byte[6];
                buf.readBytes(content);
                Force force = Meta.enumFromInt(Force.class, content[0]);
                Kind kind = Meta.enumFromInt(Kind.class, content[1]);
                Position from = new Position(content[2], content[3]);
                Position to = new Position(content[4], content[5]);
                return new ActionPacket(force, kind, from, to);
        }
        return null;
    }

    public static Packet packet(Action action) {
        return new ActionPacket(action.getPiece().getForce(), action.getPiece().getKind(), action.getPiece().getPosition(), action.getTarget());
    }
}
