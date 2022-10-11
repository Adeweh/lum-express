package africa.semicolon.lumexpress.services;

import africa.semicolon.lumexpress.data.models.VerificationToken;
import africa.semicolon.lumexpress.data.repositories.VerificationTokenRepository;
import africa.semicolon.lumexpress.exceptions.VerificationTokenException;
import africa.semicolon.lumexpress.utils.LumExpressUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class VerificationTokenServiceImpl implements VerificationTokenService {

    private final VerificationTokenRepository verificationTokenRepository;
    @Override
    public VerificationToken createToken(String userEmail) {
        String tokenString = LumExpressUtils.generateToken();
        var verificationToken = VerificationToken.builder()
                .token(tokenString)
                .userEmail(userEmail)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(5)).build();

        return verificationTokenRepository.save(verificationToken);
    }

    @Override
    public boolean isValidVerificationToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token)
                .orElseThrow(()-> new VerificationTokenException("token not found"));
        if(isTokenNotExpired(verificationToken)) return true;
        throw new VerificationTokenException("token has expired");
    }

    private boolean isTokenNotExpired(VerificationToken verificationToken) {
        return LocalDateTime.now().isBefore(verificationToken.getExpiresAt());
    }
}
