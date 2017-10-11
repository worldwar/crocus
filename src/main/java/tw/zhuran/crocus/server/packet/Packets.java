package tw.zhuran.crocus.server.packet;

import io.netty.buffer.ByteBuf;
import tw.zhuran.crocus.domain.*;
import tw.zhuran.crocus.util.Meta;

public class Packets {
    public static Packet packet(ByteBuf buf) {
        int type = buf.readByte();
        PacketType packetType = Meta.enumFromInt(PacketType.class, type);
        return parse(packetType, buf);
    }

    private static Packet parse(PacketType packetType, ByteBuf buf) {
        switch (packetType) {
            case ACTION:
                byte[] content = new byte[7];
                buf.readBytes(content);
                Force force = Meta.enumFromInt(Force.class, content[0]);
                Kind kind = Meta.enumFromInt(Kind.class, content[1]);
                ActionType actionType = Meta.enumFromInt(ActionType.class, content[2]);
                Position from = new Position(content[3], content[4]);
                Position to = new Position(content[5], content[6]);
                return new ActionPacket(force, kind, actionType, from, to);
        }
        return null;
    }

    public static Packet packet(Action action) {
        return new ActionPacket(action.getPiece().getForce(), action.getPiece().getKind(), action.getType(), action.getPiece().getPosition(), action.getTarget());
    }
}
