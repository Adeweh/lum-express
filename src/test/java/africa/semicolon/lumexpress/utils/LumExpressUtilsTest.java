package africa.semicolon.lumexpress.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class LumExpressUtilsTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void generateVerificationTokenTest() {
        String token = LumExpressUtils.generateToken();
        assertThat(token).isNotNull();
        assertThat(token.length()).isEqualTo(5);
    }
}