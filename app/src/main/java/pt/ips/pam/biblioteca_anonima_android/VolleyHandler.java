//processamento da RequestQueue (Volley) utilizando métodos convenientes e a implementação de um callback.

package pt.ips.pam.biblioteca_anonima_android;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

public class VolleyHandler {

    private static VolleyHandler handler;
    private RequestQueue queue;
    private final Context finalContext;
    private final CustomHurlStack customHurlStack = new CustomHurlStack();

    public VolleyHandler(Context context) {
        finalContext = context;
        queue = getRequestQueue();
    }

    public interface callback {
        void onSuccess(JSONArray data) throws JSONException;
    }

    public static VolleyHandler getInstance(Context context) {
        if (handler == null) handler = new VolleyHandler(context);
        return handler;
    }

    public RequestQueue getRequestQueue() {
        if (queue == null) queue = Volley.newRequestQueue(finalContext.getApplicationContext(), customHurlStack);
        return queue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}