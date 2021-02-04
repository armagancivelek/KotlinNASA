package com.armagancivelek.nasa.views

import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.armagancivelek.nasa.R
import com.armagancivelek.nasa.viewmodel.CuriosityFeedViewModel
import kotlinx.android.synthetic.main.fragment_curiosity.*


class Curiosity : Fragment() {
    private val TAG = "ABC"
    private var dataList: ArrayList<String?> = arrayListOf()
    private val curiosityViewModel: CuriosityFeedViewModel by viewModels()
    private lateinit var curiosityRecycler: RecyclerView
    lateinit var v: View
    lateinit var curiosityErrorText: TextView
    lateinit var curiosityProgress: ProgressBar


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        avedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_curiosity, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        init()
        observeLiveData()

//        val job = Job()
//
//        val scope = CoroutineScope(job + Dispatchers.IO)
//        scope.launch {
//
//
//            try {
//                val response = Repository().getPhotos(Rovers.curiosity,1,"mast" )
//
//
//                 val list=response.photos
//                   list?.forEach {
//
//                       dataList.add(it?.imgSrc)
//
//                   }
//                 Log.d(TAG,"${dataList.toString()}")
//            }
//            catch (e:Exception)
//            {
//                withContext(Dispatchers.Main)
//                {
//                     Toast.makeText(context,e.toString(),Toast.LENGTH_LONG).show()
//
//                }
//
//
//            }
//
//
//
//
//
//
//
//
//
//
//
//        }


    }

    private fun observeLiveData() {

        curiosityViewModel.photos.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it?.let {
                curiosity_recycler.visibility = View.VISIBLE
                curiosity_progress_bar.visibility = View.GONE
                curiosity_tv_eror.visibility = View.GONE
            }

        })
        curiosityViewModel.error.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it?.let {

                if (it) {
                    curiosityErrorText.visibility = View.VISIBLE
                } else {
                    curiosityErrorText.visibility = View.GONE
                }
            }
        })
        curiosityViewModel.loading.observe(viewLifecycleOwner, androidx.lifecycle.Observer {

            it?.let {
                if (it) {

                }
            }

        })


    }

    fun init() {


        curiosityRecycler = v.findViewById<RecyclerView>(R.id.curiosity_recycler).apply {
            layoutManager = LinearLayoutManager(context)
            // adapter = CuriosityAdapter()
            curiosityViewModel.refreshData()//  first time fetching data

        }

        curiosityProgress = v.findViewById(R.id.curiosity_progress_bar)
        curiosityErrorText = v.findViewById(R.id.curiosity_tv_eror)

        setHasOptionsMenu(true)//set menu

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.crusitory_menu, menu)
    }


}