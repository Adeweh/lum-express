package africa.semicolon.lumexpress.services.cloud;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
@AllArgsConstructor
public class CloudinaryCloudServiceImpl implements CloudService{
    private final Cloudinary cloudinary;
  /*  @Value("${cloudinary.cloud.name")
    private final String CLOUD_NAME;

    @Value("${cloudinary.api.key")
    private final String API_KEY;

    @Value("${cloudinary.api.secret")
    private final String API_SECRET;*/
//    private final Cloudinary cloudinary = new Cloudinary(ObjectUtils
//            .asMap("cloud_name", "dlijhpzt4", "api_key", "976485962697338", "api_secret", "X4L045_k6scTBc9ukCATJHprR-0", "secure", true));

    @Override
    public String upload(byte[] imageBytes, Map<?, ?> map) throws IOException {
        var uploadResponse= cloudinary.uploader().upload(imageBytes, ObjectUtils.emptyMap());
        return uploadResponse.get("url").toString();
    }
}
