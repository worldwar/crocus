package tw.zhuran.crocus.server.packet.order;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import tw.zhuran.crocus.domain.GameEndReason;
import tw.zhuran.crocus.domain.GameResult;
import tw.zhuran.crocus.util.Meta;

public class EndGamePacket extends OrderPacket {
    private GameResult result;
    private GameEndReason reason;

    public EndGamePacket(OrderType type, GameResult result, GameEndReason reason) {
        super(type);
        this.result = result;
        this.reason = reason;
    }

    @Override
    public byte[] bytes() {
        ByteBuf byteBuf = Unpooled.buffer(8);
        byteBuf.writeInt(4);
        byteBuf.writeByte(Meta.enumToInt(type));
        byteBuf.writeByte(Meta.enumToInt(orderType));
        byteBuf.writeByte(Meta.enumToInt(result));
        byteBuf.writeByte(Meta.enumToInt(reason));
        return byteBuf.array();
    }

    public GameResult getResult() {
        return result;
    }

    public EndGamePacket setResult(GameResult result) {
        this.result = result;
        return this;
    }

    public GameEndReason getReason() {
        return reason;
    }

    public EndGamePacket setReason(GameEndReason reason) {
        this.reason = reason;
        return this;
    }
}
