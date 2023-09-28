package com.geybriyel.music.service;

import com.geybriyel.music.controller.request.AuthenticationReq;
import com.geybriyel.music.controller.request.RegisterReq;
import com.geybriyel.music.controller.response.AuthenticationRes;
import org.springframework.stereotype.Service;

public interface AuthenticationService {

    AuthenticationRes register(RegisterReq req);

    AuthenticationRes authenticate(AuthenticationReq req);
}
