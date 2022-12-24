package pt.ips.pam.biblioteca_anonima_android;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DatabaseRequest {

    private final Context currentContext;
    private ProgressDialog progDialog;
    public DatabaseRequest(Context context) {
        currentContext = context;
        progDialog = new ProgressDialog(context);
    }

    private static final String Host = "https://biblioteca-anonima.onrender.com";
    private String URL = "";

    private JSONObject JSONObject;
    private JSONArray JSONArray;
    private StringRequest request;

    public void getBooks(VolleyHandler.callback callback) {
        URL = Host + "/getBooks";

        progDialog.setMessage("Obtendo os livros da biblioteca...");
        progDialog.show();
        request = new StringRequest(Request.Method.GET, URL,
                onResponse(new VolleyHandler.callback() {
                    @Override
                    public void onSuccess(ArrayList<String> data) {
                        callback.onSuccess(data);
                        progDialog.dismiss();
                    }
                }),
                onError("Não foi possível obter os livros.")
        );
        VolleyHandler.getInstance(currentContext).addToRequestQueue(request);
    }

    public void create() {
        URL = Host + "/create";

        progDialog.setMessage("Criando um novo livro...");
        progDialog.show();
        request = new StringRequest(Request.Method.POST, URL, null, onError("Não foi possível criar o livro.")){
            @Override
            public Map<String, String> getHeaders() {
                Map<String,String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

            @Override
            protected Map<String,String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("key", "value");
                return params;
            }
        };
    }

    public void edit() {
        URL = Host + "/edit";

    }

    public void delete() {
        URL = Host + "/delete";

    }

    private Response.Listener<String> onResponse(VolleyHandler.callback callback) {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject = new JSONObject(response);
                    callback.onSuccess(convertFromJSON(JSONObject));
                }
                catch (JSONException error) {
                    Log.d("volleyError", error.toString());
                    Toast.makeText(currentContext, "JSON Conversion Failed.",Toast.LENGTH_LONG).show();
                }
            }
        };
    }

    private Response.ErrorListener onError(String text) {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("volleyError", error.toString());
                Toast.makeText(currentContext, text,Toast.LENGTH_LONG).show();
                progDialog.dismiss();
            }
        };
    }

    private ArrayList<String> data;
    private ArrayList<String> convertFromJSON(JSONObject object) {

        return data;
    }
}
