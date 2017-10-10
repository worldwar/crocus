package tw.zhuran.crocus.server.handler;

import tw.zhuran.crocus.server.packet.PacketType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Handler {
    PacketType value();
}
