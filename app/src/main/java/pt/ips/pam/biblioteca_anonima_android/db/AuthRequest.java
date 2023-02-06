//processamento dos pedidos de autenticação para a API utilizando Volley e ProgressDialog para indicação do progresso.

package pt.ips.pam.biblioteca_anonima_android.db;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import pt.ips.pam.biblioteca_anonima_android.MainActivity;
import pt.ips.pam.biblioteca_anonima_android.SplashActivity;

public class AuthRequest {

    private final Context currentContext;
    private final ProgressDialog progDialog;

    public AuthRequest(Context context) {
        currentContext = context;

        progDialog = new ProgressDialog(context);
        progDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progDialog.setMax(100);
        progDialog.setCancelable(false);

        CookieHandler.getDefault();
    }

    private static final String Host = "https://biblioteca-anonima.onrender.com";
    private String URL = "";
    private JsonObjectRequest request;
    private int statusCode = 0;
    private String admin = "";

    public void login(JSONObject data, @Nullable VolleyHandler.callback callback) {
        URL = Host + "/login";

        if (DatabaseTables.ADMIN.checkJSON(DatabaseTables.ADMIN, data, currentContext)) {
            progDialog.setMessage("Criando uma nova sessão...");
            progDialog.setProgress(0);
            progDialog.show();

            request = new JsonObjectRequest(Request.Method.POST, URL, data,
                    onResponse("Login efetuado com sucesso.", new VolleyHandler.callback() {
                        @Override
                        public void onSuccess() {
                            new SQLiteStorage(currentContext).setAdmin(admin, new VolleyHandler.callback() {
                                @Override
                                public void onSuccess() {
                                    progDialog.dismiss();
                                    if (callback != null) callback.onSuccess();
                                }
                            });
                        }
                    }),
                    onError("Não foi possível efetuar o login.")
            ) {
                @Override
                public Map<String, String> getHeaders() {
                    return requestHeaders();
                }

                @Override
                protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) { return super.parseNetworkResponse(getStatusCode(response)); }
            };

            progDialog.setProgress(25);
            VolleyHandler.getInstance(currentContext).addToRequestQueue(request);
        }
        else Toast.makeText(currentContext, "Operação cancelada.\nDados são inválidos.\nLogcat para detalhes.", Toast.LENGTH_LONG).show();
    }

    public void logout() {
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
                        new SQLiteStorage(currentContext).reset(new VolleyHandler.callback() {
                            @Override
                            public void onSuccess() {
                                progDialog.setProgress(100);
                                progDialog.dismiss();
                                goMain();
                            }
                        });
                    }
                }),
                onError("Não foi possível efetuar o logout.")
        ) {
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) { return super.parseNetworkResponse(getStatusCode(response)); }
        };

        progDialog.setProgress(25);
        VolleyHandler.getInstance(currentContext).addToRequestQueue(request);
    }

    private Response.Listener<JSONObject> onResponse(String text, VolleyHandler.callback callback) {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    progDialog.setMessage("Autenticando o administrador...");
                    progDialog.setProgress(50);
                    if (statusCode != 200 && statusCode != 304) {
                        Log.d("volleyLogAuth", String.valueOf(statusCode));
                        Log.d("volleyLogAuth", "Algo correu mal durante a operação.");
                        Toast.makeText(currentContext, "Algo correu mal durante a operação.", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Log.d("volleyLogAuth", response.getString("message"));
                        if (response.has("name")) admin = response.getString("name");
                        progDialog.setMessage("Operação concluída.");
                        progDialog.setProgress(100);
                        Toast.makeText(currentContext, text, Toast.LENGTH_LONG).show();
                    }
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
                if (error instanceof AuthFailureError) {
                    Log.d("volleyError", error + " HTTP Code: 401");
                    Toast.makeText(currentContext, "Não tem acesso à operação.\nNão está autenticado.", Toast.LENGTH_LONG).show();
                    new SQLiteStorage(currentContext).reset(new VolleyHandler.callback() {
                        @Override
                        public void onSuccess() { goMain(); }
                    });
                }
                else if (error.networkResponse.statusCode == 400) {
                    Log.d("volleyError", error + " HTTP Code: 400");
                    try {
                        String message = new JSONObject(new String(error.networkResponse.data, StandardCharsets.UTF_8)).getString("message");
                        Toast.makeText(currentContext, message, Toast.LENGTH_LONG).show();
                    }
                    catch (JSONException e) { Log.d("JSONException", e.toString()); }
                }
                else {
                    Log.d("volleyError", error + " HTTP Code:" + statusCode);
                    Toast.makeText(currentContext, text, Toast.LENGTH_LONG).show();
                }
                progDialog.dismiss();
            }
        };
    }

    private Map<String, String> requestHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json; charset=UTF-8");
        return headers;
    }

    private NetworkResponse getStatusCode(NetworkResponse response) {
        statusCode = response.statusCode;
        return response;
    }

    private void goMain() {
        Intent goToStart = new Intent(currentContext, SplashActivity.class);
        ContextCompat.startActivity(currentContext, goToStart, null);
    }
}