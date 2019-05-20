package tullyj2.cs412.wwu.tullyj2_a2.restaurants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class RestaurantContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<RestaurantItem> ITEMS = new ArrayList<RestaurantItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, RestaurantItem> ITEM_MAP = new HashMap<String, RestaurantItem>();

    private static final int COUNT = 16;

    public static void addItem(RestaurantItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static RestaurantItem createRestaurantItem(int position, String restaurant_name) {
        return new RestaurantItem(String.valueOf(position), restaurant_name);
    }

//    private static String makeDetails(int position) {
//        StringBuilder builder = new StringBuilder();
//        builder.append("Details about Item: ").append(position);
//        for (int i = 0; i < position; i++) {
//            builder.append("\nMore details information here.");
//        }
//        return builder.toString();
//    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class RestaurantItem {
        public final String id;
        public final String content;
        public String details;
        public int score;
        public boolean edited;

        public RestaurantItem(String id, String content) {
            this.id = id;
            this.content = content;
            this.details = null;
            this.score = -1;
            this.edited=false;

        }

        @Override
        public String toString() {
            return content;
        }
    }
}
