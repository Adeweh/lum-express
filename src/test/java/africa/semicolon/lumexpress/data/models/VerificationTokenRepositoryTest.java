package africa.semicolon.lumexpress.data.models;

import africa.semicolon.lumexpress.data.repositories.VerificationTokenRepository;
import africa.semicolon.lumexpress.exceptions.VerificationTokenException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Slf4j
class VerificationTokenRepositoryTest {
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    private VerificationToken verificationToken;


    @BeforeEach
    void setUp() {
        verificationToken = new VerificationToken();
        verificationToken.setToken("2209");
        verificationToken.setUserEmail("test@email.com");
        verificationTokenRepository.deleteAll();
    }

    @Test
    void findByUserEmailTest() {
        verificationTokenRepository.save(verificationToken);
        VerificationToken foundToken = verificationTokenRepository.findByUserEmail("test@email.com").orElseThrow(() -> new VerificationTokenException("token not found"));
        log.info("found token :: {}", foundToken);
        assertThat(foundToken).isNotNull();
        assertThat(foundToken.getToken()).isEqualTo(verificationToken.getToken());
    }

    @Test
    void findByTokenTest() {
        verificationTokenRepository.save(verificationToken);
        VerificationToken token = verificationTokenRepository.findByToken(verificationToken.getToken()).orElseThrow(()-> new VerificationTokenException("token not found"));
        assertThat(token).isNotNull();
        assertThat(token.getToken()).isEqualTo("2209");
    }
}