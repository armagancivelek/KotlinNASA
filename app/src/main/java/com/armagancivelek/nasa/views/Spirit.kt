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
import com.armagancivelek.nasa.databinding.FragmentSpiritBinding
import com.armagancivelek.nasa.utils.CAMERAS
import com.armagancivelek.nasa.utils.Constants
import com.armagancivelek.nasa.utils.Rovers
import com.armagancivelek.nasa.viewmodel.NasaViewModel
import com.armagancivelek.nasa.views.adapter.NasaAdapter


class Spirit : Fragment(R.layout.fragment_spirit) {


    private lateinit var binding: FragmentSpiritBinding
    private val sharedViewModel: NasaViewModel by viewModels()
    private val spiritAdapter = NasaAdapter(arrayListOf())
    private var isLoading = false
    private var isLastPage = false
    private var isScrolling = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        init(view)
        observeLiveData()
        eventHandler()


    }


    private fun eventHandler() {
        binding.spiritRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
                    sharedViewModel.getData(Rovers.spirit)
                    isScrolling = false
                }

            }
        })

        spiritAdapter.setOnClickListener { position, photo ->

            val bundle = Bundle().apply {

                putSerializable("photo", photo)

            }

            findNavController().navigate(R.id.action_spirit_to_bottomSheetDialog, bundle)


        }


    }


    private fun observeLiveData() {


        sharedViewModel.photos.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it?.let {
                binding.spiritTvEror.visibility = View.VISIBLE
                spiritAdapter.updatePhotos(it as List<MarsRoverPhotos.Photo>)
            }
            if (it.size == 0) {
                binding.spiritTvEror.run {
                    text = "Bu kameraya ait görüntü yok"
                    visibility = View.VISIBLE
                }
            }


        })
        sharedViewModel.error.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it?.let {

                if (it) {
                    binding.spiritTvEror.run {
                        text = "Bağlantınızı kontrol edin "
                        visibility = View.VISIBLE
                    }
                } else {
                    binding.spiritTvEror.visibility = View.INVISIBLE
                }
            }
        })
        sharedViewModel.loading.observe(viewLifecycleOwner, androidx.lifecycle.Observer {

            it?.let {
                if (it) {
                    binding.spiritProgressBar.visibility = View.VISIBLE
                } else
                    binding.spiritProgressBar.visibility = View.INVISIBLE
            }

        })


    }

    fun init(view: View) {

        binding = FragmentSpiritBinding.bind(view)
        binding.spiritRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = spiritAdapter
            sharedViewModel.refreshData(Rovers.spirit)//  first time fetching data

        }



        setHasOptionsMenu(true)


    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.spirit_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.spirit_rhaz ->
                sharedViewModel.refreshData(Rovers.spirit, CAMERAS.RHAZ.name)
            R.id.spirit_fhaz ->
                sharedViewModel.refreshData(Rovers.spirit, CAMERAS.FHAZ.name)
            R.id.spirit_navcam ->
                sharedViewModel.refreshData(Rovers.spirit, CAMERAS.NAVCAM.name)
            R.id.spirit_pancam ->
                sharedViewModel.refreshData(Rovers.spirit, CAMERAS.PANCAM.name)
            R.id.spirit_minites ->
                sharedViewModel.refreshData(Rovers.spirit, CAMERAS.MINITES.name)


        }


        return true
    }


}