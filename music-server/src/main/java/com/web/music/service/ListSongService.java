package com.web.music.service;

import com.web.music.domain.ListSong;

import java.util.List;

public interface ListSongService {

    boolean addListSong(ListSong listSong);

    boolean updateListSongMsg(ListSong listSong);

    boolean deleteListSong(Integer songId);

    List<ListSong> allListSong();

    List<ListSong> listSongOfSongId(Integer songListId);
}
