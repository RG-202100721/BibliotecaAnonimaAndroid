//processamento dos pedidos CRUD para a API utilizando Volley e ProgressDialog para indicação do progresso.

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import pt.ips.pam.biblioteca_anonima_android.MainActivity;

public class DatabaseRequest {

    private final Context currentContext;
    private final ProgressDialog progDialog;

    public DatabaseRequest(Context context) {
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

    protected void getData() {
        URL = Host + "/getAll";

        progDialog.setMessage("Obtendo os registos da biblioteca...");
        progDialog.setProgress(0);
        progDialog.show();

        request = new JsonObjectRequest(Request.Method.GET, URL, null,
                onResponseWithData(new VolleyHandler.callbackWithData() {
                    @Override
                    public void onSuccess(JSONArray data) {
                        progDialog.setProgress(75);
                        progDialog.setMessage("Atualizando os dados locais...");
                        new SQLiteStorage(currentContext).copyToLocalDB(data, new VolleyHandler.callback() {
                            @Override
                            public void onSuccess() {
                                progDialog.setMessage("Operação concluída.");
                                progDialog.setProgress(100);
                                progDialog.dismiss();
                            }
                        });
                    }
                }),
                onError("Não foi possível obter os registos.")
        );

        progDialog.setProgress(25);
        VolleyHandler.getInstance(currentContext).addToRequestQueue(request);
    }

    public void create(DatabaseTables table, JSONObject newData, @Nullable VolleyHandler.callback callback) {
        URL = Host + "/create";

        if (table.checkJSON(table, newData)) {
            progDialog.setMessage("Criando um novo registo...");
            progDialog.setProgress(0);
            progDialog.show();

            try {
                newData.put("Tabela", String.valueOf(table));

                request = new JsonObjectRequest(Request.Method.POST, URL, newData,
                        onResponse("O registo foi criado.", new VolleyHandler.callback() {
                            @Override
                            public void onSuccess() {
                                new SQLiteStorage(currentContext).addLocalDB(table, newData, new VolleyHandler.callback() {
                                    @Override
                                    public void onSuccess() {
                                        if (callback != null) callback.onSuccess();
                                    }
                                });
                            }
                        }),
                        onError("Não foi possível criar o registo.")
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
            catch (JSONException e) {
                Log.d("volleyError", e.toString());
                Toast.makeText(currentContext, "Algo correu mal com os parâmetros do Volley Request.", Toast.LENGTH_LONG).show();
                progDialog.dismiss();
            }
        }
        else Toast.makeText(currentContext, "Operação cancelada.\nJSON está errado.\nLogcat para detalhes.", Toast.LENGTH_LONG).show();
    }

    public void edit(DatabaseTables table, int rowID, JSONObject newData, @Nullable VolleyHandler.callback callback) {
        URL = Host + "/edit";

        if (table.checkJSON(table, newData)) {
            progDialog.setMessage("Editando o registo...");
            progDialog.setProgress(0);
            progDialog.show();

            try {
                newData.put("Tabela", String.valueOf(table));
                newData.put("ID", String.valueOf(rowID));

                request = new JsonObjectRequest(Request.Method.PUT, URL, newData,
                        onResponse("O registo foi editado.", new VolleyHandler.callback() {
                            @Override
                            public void onSuccess() {
                                new SQLiteStorage(currentContext).updateLocalDB(table, newData, new VolleyHandler.callback() {
                                    @Override
                                    public void onSuccess() {
                                        if (callback != null) callback.onSuccess();
                                    }
                                });
                            }
                        }),
                        onError("Não foi possível editar o registo.")
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
            catch (JSONException e) {
                Log.d("volleyError", e.toString());
                Toast.makeText(currentContext, "Algo correu mal com os parâmetros do Volley Request.", Toast.LENGTH_LONG).show();
                progDialog.dismiss();
            }
        }
        else Toast.makeText(currentContext, "Operação cancelada.\nJSON está errado.\nLogcat para detalhes.", Toast.LENGTH_LONG).show();
    }

    public void delete(DatabaseTables table, int rowID, @Nullable VolleyHandler.callback callback) {
        URL = Host + "/delete";

        progDialog.setMessage("Apagando o registo...");
        progDialog.setProgress(0);
        progDialog.show();

        try {
            JSONObject newData = new JSONObject();
            newData.put("Tabela", String.valueOf(table));
            newData.put("ID", String.valueOf(rowID));

            request = new JsonObjectRequest(Request.Method.DELETE, URL, newData,
                    onResponse("O registo foi apagado.", new VolleyHandler.callback() {
                        @Override
                        public void onSuccess() {
                            new SQLiteStorage(currentContext).deleteLocalDB(table, rowID, new VolleyHandler.callback() {
                                @Override
                                public void onSuccess() {
                                    if (callback != null) callback.onSuccess();
                                }
                            });
                        }
                    }),
                    onError("Não foi possível apagar o registo.")
            ) {
                @Override
                public Map<String, String> getHeaders() { return requestHeaders(); }

                @Override
                protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) { return super.parseNetworkResponse(getStatusCode(response)); }
            };

            progDialog.setProgress(25);
            VolleyHandler.getInstance(currentContext).addToRequestQueue(request);
        }
        catch (JSONException e) {
            Log.d("volleyError", e.toString());
            Toast.makeText(currentContext, "Algo correu mal com os parâmetros do Volley Request.", Toast.LENGTH_LONG).show();
            progDialog.dismiss();
        }
    }

    private Response.Listener<JSONObject> onResponseWithData(VolleyHandler.callbackWithData callback) {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    progDialog.setMessage("Obtendo resposta do servidor...");
                    progDialog.setProgress(50);
                    JSONArray JSONArray = response.getJSONArray("data");
                    callback.onSuccess(JSONArray);
                }
                catch (JSONException error) {
                    Log.d("volleyError", error.toString());
                    Toast.makeText(currentContext, "Conversão para JSON falhou.",Toast.LENGTH_LONG).show();
                    progDialog.dismiss();
                }
            }
        };
    }

    private Response.Listener<JSONObject> onResponse(String text, VolleyHandler.callback callback) {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    progDialog.setMessage("Obtendo resposta do servidor...");
                    progDialog.setProgress(50);
                    if (statusCode != 200) {
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
                if (error instanceof AuthFailureError) {
                    Log.d("volleyError", error + " HTTP code: 401");
                    Toast.makeText(currentContext, "Não tem acesso à operação.\nNão está autenticado.", Toast.LENGTH_LONG).show();
                    new SQLiteStorage(currentContext).reset(new VolleyHandler.callback() {
                        @Override
                        public void onSuccess() { goMain(); }
                    });
                }
                else {
                    Log.d("volleyError", error.toString() + " HTTP code:" + statusCode);
                    Toast.makeText(currentContext, text, Toast.LENGTH_LONG).show();
                }
                progDialog.dismiss();
            }
        };
    }

    private Map<String, String> requestHeaders() {
        Map<String,String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json; charset=UTF-8");
        return headers;
    }

    private NetworkResponse getStatusCode(NetworkResponse response) {
        statusCode = response.statusCode;
        return response;
    }

    private void goMain() {
        Intent goToStart = new Intent(currentContext, MainActivity.class);
        ContextCompat.startActivity(currentContext, goToStart, null);
    }
}