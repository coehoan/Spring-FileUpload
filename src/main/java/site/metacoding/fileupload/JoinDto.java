package site.metacoding.fileupload;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JoinDto {
    private String username;
    private MultipartFile file;

    public User toEntity(String imgurl) {
        User user = new User();
        user.setUsername(username);
        user.setImgurl(imgurl);
        return user;
    }
}
