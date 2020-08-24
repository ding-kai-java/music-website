package com.web.music.service;

import com.web.music.domain.MusicOrder;

public interface MusicOrderService {
    String doPay(MusicOrder musicOrder);
}
