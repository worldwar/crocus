package tw.zhuran.crocus.server.packet;

import tw.zhuran.crocus.domain.Force;
import tw.zhuran.crocus.domain.Kind;
import tw.zhuran.crocus.domain.Position;
import tw.zhuran.crocus.util.Meta;

public class ActionPacket extends Packet {

    private Force force;

    private Kind kind;

    private Position from;

    private Position to;

    public ActionPacket() {
        this.type = PacketType.ACTION;
    }

    public ActionPacket(Force force, Kind kind, Position from, Position to) {
        this.type = PacketType.ACTION;
        this.force = force;
        this.kind = kind;
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
        byte[] bytes = new byte[6];
        bytes[0] = (byte)Meta.enumToInt(force);
        bytes[1] = (byte)Meta.enumToInt(kind);
        bytes[2] = (byte)from.x;
        bytes[3] = (byte)from.y;
        bytes[4] = (byte)to.x;
        bytes[5] = (byte)to.x;
        return bytes;
    }
}
