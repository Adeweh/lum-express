package africa.semicolon.lumexpress.services.cloud;

import com.cloudinary.utils.ObjectUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class CloudinaryCloudServiceImplTest {

    @Autowired
    private CloudService cloudService;

    private MultipartFile file;

    @BeforeEach
    void setUp() throws IOException {
        Path path = Paths.get("/home/adeh/Downloads/peak.jpeg");
        file = new MockMultipartFile("peak", Files.readAllBytes(path));
       // request= CreateProductRequest.builder().name("Milk").productCategory("Beverages").price(BigDecimal.valueOf(30.00)).quantity(10).image(file).build();
    }

    @Test
    @DisplayName("Cloudinary upload test")
    void uploadTest() {
        try {
            String response =
            cloudService.upload(file.getBytes(), ObjectUtils.emptyMap());

            assertThat(response).isNotNull();

        }catch (IOException exception){
            exception.printStackTrace();

        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}