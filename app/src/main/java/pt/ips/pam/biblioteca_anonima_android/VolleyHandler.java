package pt.ips.pam.biblioteca_anonima_android;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class VolleyHandler {

    private static VolleyHandler handler;
    private RequestQueue queue;
    private final Context finalContext;

    public VolleyHandler(Context context) {
        finalContext = context;
        queue = getRequestQueue();
    }

    public interface callback {
        void onSuccess(ArrayList<String> data);
    }

    public static VolleyHandler getInstance(Context context) {
        if (handler == null) handler = new VolleyHandler(context);
        return handler;
    }

    public RequestQueue getRequestQueue() {
        if (queue == null) queue = Volley.newRequestQueue(finalContext.getApplicationContext());
        return queue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}