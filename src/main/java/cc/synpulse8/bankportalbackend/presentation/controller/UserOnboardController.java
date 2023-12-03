package cc.synpulse8.bankportalbackend.presentation.controller;

import cc.synpulse8.bankportalbackend.application.UserOnboardService;
import cc.synpulse8.bankportalbackend.presentation.dto.request.LoginRequest;
import cc.synpulse8.bankportalbackend.presentation.dto.response.LoginResponse;
import cc.synpulse8.bankportalbackend.util.rest.RestfulResponse;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/end-user")
public class UserOnboardController {

    private final UserOnboardService userOnboardService;

    public UserOnboardController(UserOnboardService userOnboardService) {
        this.userOnboardService = userOnboardService;
    }

    @PostMapping("/login")
    public RestfulResponse<LoginResponse> login(@Valid @RequestBody LoginRequest loginUserRequest) {
        return userOnboardService.login(loginUserRequest);
    }

}
