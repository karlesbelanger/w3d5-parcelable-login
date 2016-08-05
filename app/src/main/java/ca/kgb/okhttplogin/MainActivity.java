package ca.kgb.okhttplogin;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
    public static final String STUDENT_BUNDLE_KEY = "STUDENT_BUNDLE_KEY";
    private String mUrl = "http://www.mocky.io/v2/57a4dfb40f0000821dc9a3b8";
    private EditText mEditTextUser;
    private EditText mEditTextPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditTextUser = (EditText)findViewById(R.id.user);
        mEditTextPassword = (EditText)findViewById(R.id.password);
    }

    public void doMagic(View view) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(mUrl)
                .build();
        final String user = mEditTextUser.getText().toString();
        final String password = mEditTextPassword.getText().toString();
        final View doMagicView = view;
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
                boolean authenticated = false;
                for (int i=0; i<students.size(); i++){
                    if(password.equals(students.get(i).getPassword()) &&
                            user.equals(students.get(i).getName()) ) {
                        authenticated = true;
                        showWelcome(students.get(i));
                        break;
                    }
                }
                if(!authenticated)
                    doToast();


                Log.d(TAG, "onResponse: " + students.toString());
            }
        });
    }

    private void showWelcome(Student student) {
        Intent intent = new Intent(this, Details.class);
        intent.putExtra(STUDENT_BUNDLE_KEY, student);
        startActivity(intent);
    }


    private void doToast() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this,
                        "Invalide user name or password!",
                        Toast.LENGTH_LONG).show();

            }
        });
    }
}
