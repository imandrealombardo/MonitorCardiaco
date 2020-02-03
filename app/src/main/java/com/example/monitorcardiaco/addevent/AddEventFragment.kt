package com.example.monitorcardiaco.addevent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.monitorcardiaco.R
import com.example.monitorcardiaco.databinding.FragmentAddeventBinding

class AddEventFragment: Fragment() {
    /**
     * Called when the Fragment is ready to display content to the screen.
     *
     * This function uses DataBindingUtil to inflate R.layout.fragment_sleep_quality.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentAddeventBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_addevent, container, false
        )

        return binding.root
    }

}