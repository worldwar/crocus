package tw.zhuran.crocus.server.packet;

public class Packet {
    protected PacketType type;

    public Packet(PacketType type) {
        this.type = type;
    }
    public PacketType getType() {
        return type;
    }

    public void setType(PacketType type) {
        this.type = type;
    }

    public byte[] bytes() {
        return null;
    }
}
