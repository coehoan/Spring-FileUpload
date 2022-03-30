package site.metacoding.fileupload;

import java.util.UUID;

import org.junit.jupiter.api.Test;

public class UUIDTest {

    @Test
    public void uuid_test() {
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid.toString());
    }
}
