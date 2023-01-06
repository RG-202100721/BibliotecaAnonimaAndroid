package pt.ips.pam.biblioteca_anonima_android.db;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpResponse;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AuthRequest {

    private final Context currentContext;
    private final ProgressDialog progDialog;

    public AuthRequest(Context context) {
        currentContext = context;

        progDialog = new ProgressDialog(context);
        progDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progDialog.setMax(100);
        progDialog.setCancelable(false);
    }

    //private static final String Host = "https://biblioteca-anonima.onrender.com";
    private static final String Host = "http://192.168.56.1:8081";
    private String URL = "";
    private JsonObjectRequest request;
    private int statusCode = 0;

    public void login(JSONObject data, @Nullable VolleyHandler.callback callback) {
        URL = Host + "/login";

        if (DatabaseTables.ADMIN.checkJSON(DatabaseTables.ADMIN, data)) {
            progDialog.setMessage("Criando uma nova sessão...");
            progDialog.setProgress(0);
            progDialog.show();

            request = new JsonObjectRequest(Request.Method.POST, URL, data,
                    onResponse("Login efetuado com sucesso.", new VolleyHandler.callback() {
                        @Override
                        public void onSuccess() {
                            new SQLiteStorage().addLocalDB(data);
                            if (callback != null) callback.onSuccess();
                        }
                    }),
                    onError("Não foi possível efetuar o login.")
            ) {
                @Override
                public Map<String, String> getHeaders() {
                    return requestHeaders();
                }

                @Override
                protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                    statusCode = response.statusCode;
                    return super.parseNetworkResponse(response);
                }
            };

            progDialog.setProgress(25);
            VolleyHandler.getInstance(currentContext).addToRequestQueue(request);
        }
        else {
            Toast.makeText(currentContext, "Operação cancelada.\nJSON está errado.\nLogcat para detalhes.", Toast.LENGTH_LONG).show();
        }
    }

    public void logout(@Nullable VolleyHandler.callback callback) {
        URL = Host + "/logout";

        progDialog.setMessage("Fechando a sessão...");
        progDialog.setProgress(0);
        progDialog.show();

        request = new JsonObjectRequest(Request.Method.GET, URL, null,
                onResponse("Logout efetuado com sucesso.", new VolleyHandler.callback() {
                    @Override
                    public void onSuccess() {
                        progDialog.setProgress(75);
                        progDialog.setMessage("Atualizando os dados locais...");
                        new SQLiteStorage().reset(new VolleyHandler.callback() {
                            @Override
                            public void onSuccess() {
                                if (callback != null) callback.onSuccess();
                                progDialog.setProgress(100);
                                progDialog.dismiss();
                            }
                        });
                    }
                }),
                onError("Não foi possível efetuar o logout.")
        ) {
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                statusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };
        progDialog.setProgress(25);
        VolleyHandler.getInstance(currentContext).addToRequestQueue(request);
    }

    public void noPermission(String message) {

    }

    private Response.Listener<JSONObject> onResponse(String text, VolleyHandler.callback callback) {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    progDialog.setMessage("Autenticando o administrador...");
                    progDialog.setProgress(50);
                    if (response.getString("message").equals("0 results.")) {
                        Log.d("volleyLog", "0 results.");
                        Toast.makeText(currentContext, "Algo correu mal durante a operação.", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Log.d("volleyLog", response.getString("message"));
                        progDialog.setMessage("Operação concluída.");
                        progDialog.setProgress(100);
                        Toast.makeText(currentContext, text, Toast.LENGTH_LONG).show();
                    }
                    progDialog.dismiss();
                    callback.onSuccess();
                }
                catch (JSONException error) {
                    Log.d("volleyError", error.toString());
                    Toast.makeText(currentContext, "Conversão para JSON falhou.",Toast.LENGTH_LONG).show();
                    progDialog.dismiss();
                }
            }
        };
    }

    private Response.ErrorListener onError(String text) {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("volleyError", error.toString());
                if (error instanceof AuthFailureError) {
                    Toast.makeText(currentContext, "Não tem acesso à operação.\nNão está autenticado.", Toast.LENGTH_LONG).show();
                    new SQLiteStorage().reset(null);
                }
                else Toast.makeText(currentContext, statusCode + text, Toast.LENGTH_LONG).show();
                progDialog.dismiss();
            }
        };
    }

    private Map<String, String> requestHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json; charset=UTF-8");
        return headers;
    }
}
