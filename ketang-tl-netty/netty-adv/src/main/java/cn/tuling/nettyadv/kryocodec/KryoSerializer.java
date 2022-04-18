package cn.tuling.nettyadv.kryocodec;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 类说明：Kryo的序列化器，负责序列化和反序列化
 */
public class KryoSerializer {
    private static Kryo kryo = KryoFactory.createKryo();

    /*序列化*/
    public static void serialize(Object object, ByteBuf out) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Output output = new Output(baos);
        kryo.writeClassAndObject(output, object);
        output.flush();
        output.close();

        byte[] b = baos.toByteArray();
        try {
            baos.flush();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.writeBytes(b);
    }

    /*反序列化*/
    public static Object deserialize(ByteBuf out) {
        if (out == null) {
            return null;
        }
        Input input = new Input(new ByteBufInputStream(out));
        return kryo.readClassAndObject(input);
    }
}
