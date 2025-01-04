package com.example.cs50final;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import adpters.VideoAdapter;
import api.RetrofitClient;
import api.YouTubeApiService;
import models.YouTubeResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    //private static final String YOUTUBE_API_KEY = BuildConfig.YOUTUBE_API_KEY;
    private YouTubeApiService youTubeService;
    private VideoAdapter videoAdapter;
    private EditText searchEditText;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar Views
        searchEditText = findViewById(com.example.cs50final.R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);

        // Inicializar RecyclerView
        RecyclerView recyclerView = findViewById(R.id.videosRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        videoAdapter = new VideoAdapter(this);
        recyclerView.setAdapter(videoAdapter);

        // Configurar botão de busca
        searchButton.setOnClickListener(v -> {
            String query = searchEditText.getText().toString();
            if (!query.isEmpty()) {
                searchVideos(query);
                // Esconder o teclado após a busca
                hideKeyboard();
            }
        });

        // Ainda mantemos a busca pelo teclado também
        searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String query = searchEditText.getText().toString();
                if (!query.isEmpty()) {
                    searchVideos(query);
                    hideKeyboard();
                }
                return true;
            }
            return false;
        });

        youTubeService = RetrofitClient.getYouTubeService();
        // Busca inicial
        searchVideos("android development");
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchEditText.getWindowToken(), 0);
    }

    private void searchVideos(String query) {
        youTubeService.searchVideos("snippet", query, "video", BuildConfig.YOUTUBE_API_KEY, 25)
                .enqueue(new Callback<YouTubeResponse>() {
                    @Override
                    public void onResponse(Call<YouTubeResponse> call, Response<YouTubeResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            videoAdapter.setVideos(response.body().items);
                        }
                    }

                    @Override
                    public void onFailure(Call<YouTubeResponse> call, Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(MainActivity.this,
                                "Error loading videos", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
