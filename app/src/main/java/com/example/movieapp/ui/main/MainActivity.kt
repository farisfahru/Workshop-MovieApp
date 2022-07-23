package com.example.movieapp.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.R
import com.example.movieapp.databinding.ActivityMainBinding
import com.example.movieapp.response.MovieResponse
import com.example.movieapp.ui.detail.DetailActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MainAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Movies"

        // init
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]
        adapter = MainAdapter().apply {
            onClick { id ->
                Intent(this@MainActivity, DetailActivity::class.java).also { intent ->
                    intent.putExtra(DetailActivity.EXTRA_DATA_ID, id)
                    startActivity(intent)
                }
            }
        }

        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        binding.apply {
            showLoading(true)

            viewModel.getMovies(this@MainActivity).observe(this@MainActivity){ movies ->
                if (movies != null){
                    adapter.movies = movies as MutableList<MovieResponse>
                    rvMovies.adapter = adapter
                    rvMovies.setHasFixedSize(true)

                    showLoading(false)
                }else{
                    Toast.makeText(this@MainActivity, getString(R.string.data_not_found), Toast.LENGTH_SHORT).show()
                    showLoading(false)
                }
            }
        }
    }

    private fun showLoading(state: Boolean) {
        binding.apply {
            if (state){
                rvMovies.visibility = View.GONE
                loading.visibility = View.VISIBLE
            }else{
                rvMovies.visibility = View.VISIBLE
                loading.visibility = View.GONE
            }
        }
    }
}