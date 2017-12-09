package tw.zhuran.crocus.server.packet.order;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import tw.zhuran.crocus.util.Meta;

public class UnreadyPacket extends OrderPacket {
    public UnreadyPacket() {
        super(OrderType.UNREADY);
    }

    @Override
    public byte[] bytes() {
        ByteBuf byteBuf = Unpooled.buffer(6);
        byteBuf.writeInt(2);
        byteBuf.writeByte(Meta.enumToInt(type));
        byteBuf.writeByte(Meta.enumToInt(orderType));
        return byteBuf.array();
    }
}
