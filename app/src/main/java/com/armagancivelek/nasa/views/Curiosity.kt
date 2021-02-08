package com.armagancivelek.nasa.views

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.armagancivelek.nasa.R
import com.armagancivelek.nasa.data.model.MarsRoverPhotos
import com.armagancivelek.nasa.databinding.FragmentCuriosityBinding
import com.armagancivelek.nasa.utils.CAMERAS
import com.armagancivelek.nasa.utils.Constants
import com.armagancivelek.nasa.utils.Rovers
import com.armagancivelek.nasa.viewmodel.NasaViewModel
import com.armagancivelek.nasa.views.adapter.NasaAdapter


@Suppress("UNCHECKED_CAST")
class Curiosity : Fragment(R.layout.fragment_curiosity) {

    private lateinit var binding: FragmentCuriosityBinding
    private val sharedViewModel: NasaViewModel by viewModels()
    private val curiosityAdapter = NasaAdapter(arrayListOf())


    private var isLoading = false
    private var isLastPage = false
    private var isScrolling = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        init(view)
        observeLiveData()
        eventHandler()

    }

    private fun eventHandler() {
        binding.curiosityRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount

                val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
                val isLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
                val isNotAtBeginnig = firstVisibleItemPosition >= 0
                val isTotalMoreThanVisible = totalItemCount >= Constants.QUERY_PAGE_SIZE
                val shouldPaginate = isNotLoadingAndNotLastPage && isLastItem && isNotAtBeginnig &&
                        isScrolling &&


                        isTotalMoreThanVisible

                if (shouldPaginate) {
                    sharedViewModel.getData(Rovers.curiosity)
                    isScrolling = false
                }

            }
        })

        curiosityAdapter.setOnClickListener { position, photo ->

            val bundle = Bundle().apply {

                putSerializable("photo", photo)

            }

            findNavController().navigate(R.id.action_curiosity_to_bottomSheetDialog, bundle)


        }


    }


    private fun observeLiveData() {


        sharedViewModel.photos.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it?.let {
                binding.curiosityRecycler.visibility = View.VISIBLE
                curiosityAdapter.updatePhotos(it as List<MarsRoverPhotos.Photo>)
            }
            if (it.size == 0) {
                binding.curiosityTvEror.run {
                    text = "Bu kameraya ait görüntü yok"
                    visibility = View.VISIBLE
                }
            }


        })
        sharedViewModel.error.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it?.let {

                if (it) {
                    binding.curiosityTvEror.run {
                        text = "Bu kameraya ait görüntü yok"
                        visibility = View.VISIBLE
                    }
                } else {
                    binding.curiosityTvEror.visibility = View.INVISIBLE
                }
            }
        })
        sharedViewModel.loading.observe(viewLifecycleOwner, androidx.lifecycle.Observer {

            it?.let {
                if (it) {
                    binding.curiosityProgressBar.visibility = View.VISIBLE
                } else
                    binding.curiosityProgressBar.visibility = View.INVISIBLE
            }

        })


    }

    fun init(view: View) {
        binding = FragmentCuriosityBinding.bind(view)
        setHasOptionsMenu(true)
        binding.curiosityRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = curiosityAdapter
            sharedViewModel.refreshData(Rovers.curiosity)//  first time fetching data

        }


    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.crusitory_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.curiosity_rhaz ->
                sharedViewModel.refreshData(Rovers.curiosity, CAMERAS.RHAZ.name)
            R.id.curiosity_fhaz ->
                sharedViewModel.refreshData(Rovers.curiosity, CAMERAS.FHAZ.name)
            R.id.curiosity_mast ->
                sharedViewModel.refreshData(Rovers.curiosity, CAMERAS.MAST.name)
            R.id.curiosity_chemcam ->
                sharedViewModel.refreshData(Rovers.curiosity, CAMERAS.CHEMCAM.name)
            R.id.curiosity_mahli ->
                sharedViewModel.refreshData(Rovers.curiosity, CAMERAS.MAHLI.name)
            R.id.curiosity_mardi ->
                sharedViewModel.refreshData(Rovers.curiosity, CAMERAS.MARDI.name)
            R.id.curiosity_navcam ->
                sharedViewModel.refreshData(Rovers.curiosity, CAMERAS.NAVCAM.name)

        }




        return true
    }


}