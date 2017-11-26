package tw.zhuran.crocus.server.packet.order;

import tw.zhuran.crocus.server.packet.Packet;
import tw.zhuran.crocus.server.packet.PacketType;

public abstract class OrderPacket extends Packet {
    protected OrderType orderType;

    public OrderPacket(OrderType orderType) {
        super(PacketType.ORDER);
        this.orderType = orderType;
    }
    public OrderType getOrderType() {
        return orderType;
    }

    public OrderPacket setOrderType(OrderType orderType) {
        this.orderType = orderType;
        return this;
    }
}
