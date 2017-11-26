package tw.zhuran.crocus.server.packet.order;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import tw.zhuran.crocus.domain.Force;
import tw.zhuran.crocus.util.Meta;

public class StartGamePacket extends OrderPacket {

    private Force force;

    public StartGamePacket(Force force) {
        super(OrderType.START_GAME);
        this.force = force;
    }

    @Override
    public byte[] bytes() {
        ByteBuf byteBuf = Unpooled.buffer(7);
        byteBuf.writeInt(3);
        byteBuf.writeByte(Meta.enumToInt(type));
        byteBuf.writeByte(Meta.enumToInt(orderType));
        byteBuf.writeByte(Meta.enumToInt(force));
        return byteBuf.array();
    }
}
