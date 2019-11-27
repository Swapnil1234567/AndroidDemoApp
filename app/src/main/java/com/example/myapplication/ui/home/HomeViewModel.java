package com.example.myapplication.ui.home;

import android.util.Log;

import androidx.core.content.res.ConfigurationHelper;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.MainActivity;
import com.example.myapplication.Model.Movie;
import com.example.myapplication.Model.MovieResponse;
import com.example.myapplication.Network.MovieApiService;
import com.example.myapplication.Network.RetrofitHelper;
import com.example.myapplication.R;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeViewModel extends ViewModel {


    private MutableLiveData<List<Movie>> movies;

    private final static String API_KEY = "088e27ed1b424eb0efbeddc207838516";
    private static final String TAG = MainActivity.class.getSimpleName();

    public HomeViewModel() {
        movies = new MutableLiveData<>();

    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }
    public void connectAndGetApiData(){

        MovieApiService movieApiService = RetrofitHelper.getRetrofitInstance().create(MovieApiService.class);
        Call<MovieResponse> call = movieApiService.getTopRatedMovies(API_KEY);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                List<Movie> movie = response.body().getResults();
                movies.postValue(movie);
            }
            @Override
            public void onFailure(Call<MovieResponse> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        });
    }
}