package miscellaneou;

import com.google.gson.Gson;

public class SharedGson {
    private static Gson gsonInstance;

    private SharedGson() {
        // No instance
    }

    public static Gson getGson() {
        if (gsonInstance == null) {
            gsonInstance = new Gson();
        }
        return gsonInstance;
    }
}
