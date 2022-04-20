package com.lbcalbums.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lbcalbums.R
import com.lbcalbums.databinding.ActivityMainBinding
import com.lbcalbums.ui.adapter.AlbumAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainActivityViewModel by viewModels()

    private var viewAdapter: AlbumAdapter = AlbumAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // load the album list
        viewModel.loadAlbums()

        // setup the adapter
        binding.activityMainRecyclerView.apply {
            setHasFixedSize(true)
            this.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = viewAdapter
        }

        // observe the listAlbums LiveData and update the view in consequence
        viewModel.listAlbums.observe(
            this,
            {
                if (it.isNullOrEmpty()) {
                    // update progress bar visibility
                    binding.activityMainProgressBar.visibility = View.VISIBLE
                } else {
                    // update progress bar visibility
                    binding.activityMainProgressBar.visibility = View.GONE
                    // fill up the adapter
                    viewAdapter.listAlbums = it
                }
            }
        )

        // observe the getAlbumsError LiveData and update the view in consequence
        viewModel.getAlbumsError.observe(
            this,
            { isError ->
                if (isError) {
                    binding.activityMainProgressBar.visibility = View.GONE
                    binding.activityMainErrorMessage.visibility = View.VISIBLE
                    binding.activityMainErrorMessage.text = resources.getString(R.string.error_get_albums)
                    binding.activityMainRecyclerView.visibility = View.GONE
                }
            }
        )

        viewModel.noData.observe(
            this,
            { noData ->
                if (noData) {
                    binding.activityMainProgressBar.visibility = View.GONE
                    binding.activityMainErrorMessage.visibility = View.VISIBLE
                    binding.activityMainErrorMessage.text = resources.getString(R.string.message_no_data)
                    binding.activityMainRecyclerView.visibility = View.GONE
                }
            }
        )
    }
}
