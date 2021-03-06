package tw.zhuran.crocus.server.packet;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import tw.zhuran.crocus.domain.*;
import tw.zhuran.crocus.server.packet.order.*;
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
            case ORDER:
                return parseOrder(buf);
        }
        return null;
    }

    private static Packet parseOrder(ByteBuf buf) {
        int ot = buf.readByte();
        OrderType orderType = Meta.enumFromInt(OrderType.class, ot);
        switch (orderType) {
            case READY:
                return new ReadyPacket();
            case UNREADY:
                return new UnreadyPacket();
        }
        return null;
    }

    public static Packet packet(Action action) {
        return new ActionPacket(action.getPiece().getForce(), action.getPiece().getKind(), action.getType(), action.getPiece().getPosition(), action.getTarget());
    }

    public static Packet startGame(Force force) {
        StartGamePacket startGamePacket = new StartGamePacket(force);
        return startGamePacket;
    }

    public static Packet endGame(GameResult result, GameEndReason reason) {
        return new EndGamePacket(OrderType.END_GAME, result, reason);
    }

    public static Packet packetFromJson(String text) {
        Packet parse = JSON.parseObject(text, Packet.class);
        if (parse.getType() == PacketType.ACTION) {
            return JSON.parseObject(text, ActionPacket.class);
        }
        return null;
    }
}
