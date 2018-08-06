package tomax.loo.lesson.cache.memcached.transcoder;

import net.spy.memcached.transcoders.SerializingTranscoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

/**
 * @program: base-core
 * @description:
 * @author: Tomax
 * @create: 2018-07-27 17:35
 **/
public class KdSpyMemSerializingTranscoder extends SerializingTranscoder {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    protected Object deserialize(byte[] bytes) {
        final ClassLoader currentClassLoder = Thread.currentThread().getContextClassLoader();
        ObjectInputStream in = null;
        try {
            ByteArrayInputStream bs = new ByteArrayInputStream(bytes);
             in = new ObjectInputStream(bs){
                 @Override
                 protected Class<ObjectStreamClass> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
                     try {
                         return (Class<ObjectStreamClass>) currentClassLoder.loadClass(desc.getName());
                     } catch (ClassNotFoundException e) {
                         log.warn("使用自定义序列化转换出错", e.getMessage());
                         return (Class<ObjectStreamClass>) super.resolveClass(desc);
                     }
                 }
             };
            return in.readObject();
        } catch (Exception e) {
        log.error("使用自定义序列化转换出错", e.getMessage());
            throw new RuntimeException(e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
