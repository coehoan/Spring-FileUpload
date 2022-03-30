package site.metacoding.fileupload;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/joinForm")
    public String joinForm() {
        return "joinForm";
    }

    @GetMapping("/main")
    public String main(Model model) {
        List<User> users = userRepository.findAll();

        model.addAttribute("user", users.get(0));
        return "main";
    }

    @PostMapping("/join")
    public String join(JoinDto joinDto) { // 버퍼로읽는거 1.json 2. 있는 그대로의 문자열

        UUID uuid = UUID.randomUUID();

        String requestFileName = joinDto.getFile().getOriginalFilename();
        String imgurl = uuid + "_" + requestFileName;
        // 확장자 충돌을 위해 uuid를 앞에

        // 메모리에 있는 file 데이터를 파일시스템으로 옮겨야함.
        // 1. 빈 파일 생성 haha.png
        // File file = new File("경로");

        // 2. 빈 파일에 스트림 연결

        // 3. for문 사용해서 바이트로 쓰면 됨. FileWriter 객체
        // 파일의 이미 다운받았기때문에 사이즈를 알고있어서 buffer를 쓸 필요가 없다.

        try {
            // 1. 폴더를 먼저 만들어놔야함
            // 2. 리눅스 = / 윈도우 = \
            // 풀경로 imgUrl = C:/upload/a.png
            // 리눅스는 경로시작이 /이고 저장소 위치가 변경되는 경우를 위해
            // imgUrl = a.png 로 파일 이름만 저장한다.
            // 우리는 상대경로를 사용할예정
            Path filePath = Paths.get("src/main/resources/static/upload/" + imgurl);
            Files.write(filePath, joinDto.getFile().getBytes());// 경로와 사진파일

            userRepository.save(joinDto.toEntity(imgurl));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "joinComplete";
    }
}
