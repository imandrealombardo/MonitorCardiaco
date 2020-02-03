package com.example.monitorcardiaco.addeditparams

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.example.monitorcardiaco.BaseViewModelFactory
import com.example.monitorcardiaco.MonitorCardiacoApplication
import com.example.monitorcardiaco.R
import com.example.monitorcardiaco.databinding.FragmentPippoBinding

class PippoFragment: Fragment() {
    /**
     * Called when the Fragment is ready to display content to the screen.
     *
     * This function uses DataBindingUtil to inflate R.layout.fragment_sleep_quality.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentPippoBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_pippo, container, false
        )

        val viewModel = activity?.getViewModel()
        { PippoViewModel((requireContext().applicationContext as MonitorCardiacoApplication).userRepository) }

        binding.pippo = viewModel

        return binding.root
    }

    inline fun <reified T : ViewModel> FragmentActivity.getViewModel(noinline creator: (() -> T)? = null): T {
        return if (creator == null)
            ViewModelProviders.of(this).get(T::class.java)
        else
            ViewModelProviders.of(this, BaseViewModelFactory(creator)).get(T::class.java)
    }
}