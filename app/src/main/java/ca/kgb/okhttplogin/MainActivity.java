package ca.kgb.okhttplogin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private String mUrl = "http://www.mocky.io/v2/57a4dfb40f0000821dc9a3b8";
    private EditText user;
    private EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = (EditText)findViewById(R.id.user);
        password = (EditText)findViewById(R.id.password);
    }

    public void doMagic(View view) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(mUrl)
                .build();

        client.newCall(request).enqueue(new Callback() {
            public static final String TAG = "EnqueueRequest";

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                Gson gson = new GsonBuilder().create();
                Type listType = new TypeToken<List<Student>>() {
                }.getType();

                ArrayList<Student> students = gson.fromJson(json, listType);
                Log.d(TAG, "onResponse: " + students.toString());
            }
        });
    }
}
