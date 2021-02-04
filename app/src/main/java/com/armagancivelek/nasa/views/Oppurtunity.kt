package com.armagancivelek.nasa.views

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.armagancivelek.nasa.R

class Oppurtunity : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_oppurtunity, container, false)

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)// menu set up
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.opportunity_menu, menu)
    }

}