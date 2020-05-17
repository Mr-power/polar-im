import com.aias.polar.IMServer;
import com.aias.polar.jwt.utils.JwtUtils;
import com.aias.polar.storage.api.ImageStorage;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

/**
 * @author liuhy
 * @since 2020/3/28
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IMServer.class)
public class ImTest {

    @Resource
    private JwtUtils jwtUtils;

    @Resource(name = "imageSMStorage")
    private ImageStorage imageStorage;

    @Test
    public void test() throws IOException {
        byte[] bytes = FileUtils.readFileToByteArray(new File("C:\\Users\\18931\\Desktop\\dockerpic" +
                "\\0.png"));
        imageStorage.save(bytes,"微信图片_20200326112431.jpg");
    }
}
