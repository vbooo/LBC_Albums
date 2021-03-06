package com.lbcalbums.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lbcalbums.databinding.ActivityMainBinding
import com.lbcalbums.ui.adapter.AlbumAdapter
import com.lbcalbums.ui.utils.NetworkUtilsImpl
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainActivityViewModel by viewModels()

    private var viewAdapter: AlbumAdapter = AlbumAdapter()

    @Inject
    lateinit var internetCheck: NetworkUtilsImpl

    lateinit var mainActivityViewModelHelper: MainActivityViewModelHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Implement the MainActivityViewModelHelper
        mainActivityViewModelHelper = MainActivityViewModelHelper(
            context = this,
            isError = false,
            isConnectedToNetwork = internetCheck.hasNetworkConnection()
        )

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
                mainActivityViewModelHelper.listAlbum = it
                mainActivityViewModelHelper.isConnectedToNetwork = internetCheck.hasNetworkConnection()
                // fill up the adapter
                viewAdapter.listAlbums = it

                updateUI()
            }
        )

        // observe the getAlbumsError LiveData and update the view in consequence
        viewModel.getAlbumsError.observe(
            this,
            { isError ->
                if (isError) {
                    mainActivityViewModelHelper.isError = true
                    mainActivityViewModelHelper.isConnectedToNetwork = internetCheck.hasNetworkConnection()
                } else {
                    mainActivityViewModelHelper.isError = false
                }
                updateUI()
            }
        )
    }

    /**
     * Update the UI related informations
     */
    private fun updateUI() {
        // update progress bar visibility
        binding.activityMainProgressBar.visibility = mainActivityViewModelHelper.getProgressBarVisibility()

        // update main message visibility
        binding.activityMainErrorMessage.visibility = mainActivityViewModelHelper.getMessageVisibility()

        // update main message text
        binding.activityMainErrorMessage.text = mainActivityViewModelHelper.getMessageValue()
    }
}
