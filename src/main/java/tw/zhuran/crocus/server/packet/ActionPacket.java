package tw.zhuran.crocus.server.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import tw.zhuran.crocus.domain.*;
import tw.zhuran.crocus.util.Meta;

public class ActionPacket extends Packet {

    private Force force;

    private Kind kind;

    private ActionType actionType;

    private Position from;

    private Position to;

    public ActionPacket() {
        this.type = PacketType.ACTION;
    }

    public ActionPacket(Force force, Kind kind, ActionType actionType, Position from, Position to) {
        this();
        this.force = force;
        this.kind = kind;
        this.actionType = actionType;
        this.from = from;
        this.to = to;
    }

    public Force getForce() {
        return force;
    }

    public void setForce(Force force) {
        this.force = force;
    }

    public Kind getKind() {
        return kind;
    }

    public void setKind(Kind kind) {
        this.kind = kind;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public Position getFrom() {
        return from;
    }

    public void setFrom(Position from) {
        this.from = from;
    }

    public Position getTo() {
        return to;
    }

    public void setTo(Position to) {
        this.to = to;
    }

    @Override
    public byte[] bytes() {
        ByteBuf byteBuf = Unpooled.buffer(12);
        byteBuf.writeInt(8);
        byteBuf.writeByte(Meta.enumToInt(type));
        byteBuf.writeByte(Meta.enumToInt(force));
        byteBuf.writeByte(Meta.enumToInt(kind));
        byteBuf.writeByte(Meta.enumToInt(actionType));
        byteBuf.writeByte(from.x);
        byteBuf.writeByte(from.y);
        byteBuf.writeByte(to.x);
        byteBuf.writeByte(to.y);
        return byteBuf.array();
    }

    public Action action() {
        return new Action(new Piece(0, kind, from, force), to, actionType);
    }
}
