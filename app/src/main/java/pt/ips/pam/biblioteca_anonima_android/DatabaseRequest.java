package pt.ips.pam.biblioteca_anonima_android;

import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class DatabaseRequest {

    public static String Host = "https://biblioteca-anonima.onrender.com";

    public void getBooks() {
        String URL = Host + "/getBooks";

    }

    public void create() {
        String URL = Host + "/create";

    }

    public void edit() {
        String URL = Host + "/edit";

    }

    public void delete() {
        String URL = Host + "/delete";

    }
}
