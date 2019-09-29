package pe.edu.cibertec.capitulo1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextView textViewMessage;
    ProgressBar progressBar;
    RecyclerView recyclerViewPosts;
    FloatingActionButton fbadd;
    PostAdapter postAdapter;
    List<Post> postList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewMessage = findViewById(R.id.textViewMessage);
        progressBar = findViewById(R.id.progressBar);
        recyclerViewPosts = findViewById(R.id.recyclerViewPosts);
        fbadd = findViewById(R.id.fbadd);
        postList = new ArrayList<>();
        postAdapter = new PostAdapter(postList);
        recyclerViewPosts.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewPosts.setAdapter(postAdapter);
        fbadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreateActivity.class);
                startActivity(intent);
            }
        });
        callService();
    }

    private void callService(){
        progressBar.setVisibility(View.VISIBLE);
        textViewMessage.setVisibility(View.GONE);
        // LLAMAREMOS AL SERVICIO

        Call<List<Post>> call = RetrofitUtil.getClientApi().getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                textViewMessage.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                if(!response.isSuccessful()){
                    textViewMessage.setText("Code: " + response.code());
                } else {
                    postList.clear();
                    postList.addAll(response.body());
                    callService2();
                    postAdapter.notifyDataSetChanged();
                    /*List<Post> posts = response.body();
                    for(Post post : posts) {
                        String content = "";
                        content += "Id: " + post.getId() + "\n";
                        content += "userId: " + post.getUserId() + "\n";
                        content += "Title: " + post.getTitle() + "\n";
                        content += "Body: " + post.getText() + "\n\n";
                        textViewMessage.append(content);
                    }*/
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textViewMessage.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                textViewMessage.setText(t.getMessage());
                t.printStackTrace();
            }
        });
    }
    private void callService2(){

    }
}
