package com.example.movieapp.ui.main

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.api.ApiClient
import com.example.movieapp.response.ListResponse
import com.example.movieapp.response.MovieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val movies = MutableLiveData<List<MovieResponse>>()

    fun getMovies(context: Context): LiveData<List<MovieResponse>> {
        ApiClient.instance
            .getMovies()
            .enqueue(object : Callback<ListResponse<MovieResponse>> {
                override fun onResponse(
                    call: Call<ListResponse<MovieResponse>>,
                    response: Response<ListResponse<MovieResponse>>
                ) {
                    if (response.isSuccessful)
                        movies.postValue(response.body()?.results)
                    else
                        Toast.makeText(context, response.message().toString(), Toast.LENGTH_SHORT)
                            .show()
                }

                override fun onFailure(call: Call<ListResponse<MovieResponse>>, t: Throwable) {
                    Toast.makeText(context, t.message.toString(), Toast.LENGTH_SHORT).show()
                }

            })
        return movies
    }

}