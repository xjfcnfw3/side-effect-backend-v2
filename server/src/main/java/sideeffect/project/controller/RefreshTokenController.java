package sideeffect.project.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;
import sideeffect.project.common.exception.AuthException;
import sideeffect.project.common.exception.ErrorCode;
import sideeffect.project.security.RefreshTokenProvider;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/token")
public class RefreshTokenController {

    private final RefreshTokenProvider refreshTokenProvider;

    @PostMapping("/at-issue")
    public void issue(
        @CookieValue(value = "token", required = false) String refreshToken,
        HttpServletResponse response){
        if (refreshToken == null) {
            throw new AuthException(ErrorCode.REFRESH_TOKEN_NOT_REQUEST);
        }
        String accessToken = refreshTokenProvider.issueAccessToken(refreshToken);
        response.addHeader("Authorization", accessToken);
    }

    @DeleteMapping("/at-issue")
    public void logout(@CookieValue(value = "token", required = false) String refreshToken, HttpServletResponse response) {
        if (refreshToken != null) {
            refreshTokenProvider.deleteToken(refreshToken);
        }

        response.addHeader(HttpHeaders.SET_COOKIE, createBlankCookie().toString());
    }

    private ResponseCookie createBlankCookie() {
        return ResponseCookie.from("token", null)
            .sameSite("None")
            .secure(true)
            .path("/api/token/at-issue")
            .httpOnly(true)
            .maxAge(0)
            .build();
    }
}
