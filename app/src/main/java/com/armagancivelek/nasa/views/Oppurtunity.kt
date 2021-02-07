package com.armagancivelek.nasa.views

import android.os.Bundle
import android.view.*
import android.widget.AbsListView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.armagancivelek.nasa.R
import com.armagancivelek.nasa.data.model.MarsRoverPhotos
import com.armagancivelek.nasa.utils.CAMERAS
import com.armagancivelek.nasa.utils.Constants
import com.armagancivelek.nasa.utils.Rovers
import com.armagancivelek.nasa.viewmodel.NasaViewModel
import com.armagancivelek.nasa.views.adapter.NasaAdapter

class Oppurtunity : Fragment() {

    lateinit var v: View
    private val sharedViewModel: NasaViewModel by viewModels()
    private lateinit var opportunityRecycler: RecyclerView
    private val opportunityAdapter = NasaAdapter(arrayListOf())
    lateinit var opportunityErrorText: TextView
    lateinit var opportunityProgress: ProgressBar

    private var isLoading = false
    private var isLastPage = false
    private var isScrolling = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v = inflater.inflate(R.layout.fragment_oppurtunity, container, false)

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init()
        observeLiveData()
        eventHandler()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.opportunity_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.opportunity_rhaz ->
                sharedViewModel.refreshData(Rovers.opportunity, CAMERAS.RHAZ.name)
            R.id.opportunity_fhaz ->
                sharedViewModel.refreshData(Rovers.opportunity, CAMERAS.FHAZ.name)
            R.id.opportunity_navcam ->
                sharedViewModel.refreshData(Rovers.opportunity, CAMERAS.NAVCAM.name)
            R.id.opportunity_pancam ->
                sharedViewModel.refreshData(Rovers.opportunity, CAMERAS.PANCAM.name)
            R.id.opportunity_minites ->
                sharedViewModel.refreshData(Rovers.opportunity, CAMERAS.MINITES.name)

        }

        return true
    }

    fun init() {
        setHasOptionsMenu(true)
        opportunityProgress = v.findViewById(R.id.opportunity_progress_bar)
        opportunityErrorText = v.findViewById(R.id.opportunity_tv_eror)

        opportunityRecycler = v.findViewById<RecyclerView>(R.id.opportunity_recycler).apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = opportunityAdapter
            sharedViewModel.refreshData(Rovers.opportunity)//  first time fetching data

        }

    }


    private fun observeLiveData() {


        sharedViewModel.photos.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it?.let {
                opportunityRecycler.visibility = View.VISIBLE
                opportunityAdapter.updatePhotos(it as List<MarsRoverPhotos.Photo>)
            }
            if (it.size == 0) {
                opportunityErrorText.run {
                    text = "Bu kameraya ait görüntü yok"
                    visibility = View.VISIBLE
                }
            }


        })
        sharedViewModel.error.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it?.let {

                if (it) {
                    opportunityErrorText.run {
                        text = "Bağlantınızı kontrol edin "
                        visibility = View.VISIBLE
                    }
                } else {
                    opportunityErrorText.visibility = View.INVISIBLE
                }
            }
        })
        sharedViewModel.loading.observe(viewLifecycleOwner, androidx.lifecycle.Observer {

            it?.let {
                if (it) {
                    opportunityProgress.visibility = View.VISIBLE
                } else
                    opportunityProgress.visibility = View.INVISIBLE
            }

        })


    }

    private fun eventHandler() {
        opportunityRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
                    sharedViewModel.getData(Rovers.opportunity)
                    isScrolling = false
                }

            }
        })

        opportunityAdapter.setOnClickListener { position, photo ->

            val bundle = Bundle().apply {

                putSerializable("photo", photo)

            }

            findNavController().navigate(R.id.action_oppurtunity_to_bottomSheetDialog, bundle)


        }


    }
}
