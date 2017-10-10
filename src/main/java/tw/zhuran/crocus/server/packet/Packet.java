package tw.zhuran.crocus.server.packet;

public abstract class Packet {
    protected PacketType type;

    public PacketType getType() {
        return type;
    }

    public void setType(PacketType type) {
        this.type = type;
    }

    public abstract byte[] bytes();
}
