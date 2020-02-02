package com.example.monitorcardiaco.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.monitorcardiaco.BaseViewModelFactory
import com.example.monitorcardiaco.DateInputMask
import com.example.monitorcardiaco.MonitorCardiacoApplication
import com.example.monitorcardiaco.R
import com.example.monitorcardiaco.databinding.FragmentRegistrationBinding
import com.example.monitorcardiaco.databinding.FragmentRegistrationParamBinding

class RegistrationParamFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentRegistrationParamBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_registration_param, container, false
        )

        //val application = requireNotNull(this.activity).application

        val viewModel = activity?.getViewModel()
        { RegistrationViewModel((requireContext().applicationContext as MonitorCardiacoApplication).userRepository) }

        binding.registrationViewModel = viewModel
        binding.setLifecycleOwner(this)

        //TODO: Creare classe Adapter Dropdown
        val adapterGender = ArrayAdapter<String>(context!!, R.layout.dropdown_menu_popup_item2, viewModel!!.userGenderList)

        val editTextFilledExposedDropdownGender: AutoCompleteTextView = binding.filledExposedDropdown2
        editTextFilledExposedDropdownGender.setAdapter(adapterGender)

        // Format mask in UI
        DateInputMask(binding.birthdateTextinputedittext)

        viewModel.navigateToRegistration.observe(this, Observer {
                event ->
            event?.let {
                this.findNavController().navigate(RegistrationParamFragmentDirections.actionRegistrationParamFragmentToRegistrationFragment())
                viewModel.doneNavigatingRegistration()
            }
        })

        return binding.root
    }

    inline fun <reified T : ViewModel> FragmentActivity.getViewModel(noinline creator: (() -> T)? = null): T {
        return if (creator == null)
            ViewModelProviders.of(this).get(T::class.java)
        else
            ViewModelProviders.of(this, BaseViewModelFactory(creator)).get(T::class.java)
    }
}