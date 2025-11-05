package Utils;

import Default.Song;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Quản lý danh sách phát hiện tại (queue) để PlayerBar dùng cho Next/Prev.
 * UI (Home/Playlist/Favourite) cần gọi setQueue trước khi gọi play(song).
 */
public final class PlaybackQueue {
    private static ObservableList<Song> currentQueue = FXCollections.observableArrayList();

    private PlaybackQueue() {}

    public static void setQueue(ObservableList<Song> queue) {
        currentQueue = (queue != null) ? queue : FXCollections.observableArrayList();
    }

    public static ObservableList<Song> getQueue() {
        return currentQueue;
    }
}




