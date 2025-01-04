package models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class YouTubeResponse {
    public List<Item> items;

    public static class Item {
        public Id id;
        public Snippet snippet;
    }

    public static class Id {
        public String videoId;
    }

    public static class Snippet {
        public String title;
        public String description;
        public Thumbnails thumbnails;
    }

    public static class Thumbnails {
        @SerializedName("default")
        public Thumbnail defaultThumb;
        public Thumbnail medium;
        public Thumbnail high;
    }

    public static class Thumbnail {
        public String url;
        public int width;
        public int height;
    }
}