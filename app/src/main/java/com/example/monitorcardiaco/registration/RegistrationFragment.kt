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
import com.example.monitorcardiaco.*
import com.example.monitorcardiaco.databinding.FragmentRegistrationBinding
import com.example.monitorcardiaco.repository.UserRepository

class RegistrationFragment: Fragment() {

    /**
     * Called when the Fragment is ready to display content to the screen.
     *
     * This function uses DataBindingUtil to inflate R.layout.fragment_sleep_quality.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentRegistrationBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_registration, container, false)

        //val application = requireNotNull(this.activity).application

        val viewModel = activity?.getViewModel()
        { RegistrationViewModel((requireContext().applicationContext as MonitorCardiacoApplication).userRepository) }

        binding.registrationViewModel = viewModel
        binding.setLifecycleOwner(this)

        val adapterType = ArrayAdapter<String>(context!!, R.layout.dropdown_menu_popup_item, viewModel!!.userTypeList)

        val editTextFilledExposedDropdownType: AutoCompleteTextView = binding.filledExposedDropdown
        editTextFilledExposedDropdownType.setAdapter(adapterType)

        // TODO: Creare una classe adapter per i Dropdown
        val adapterDevice = ArrayAdapter<String>(context!!, R.layout.dropdown_menu_popup_item3, viewModel.userDeviceList)

        val editTextFilledExposedDropdownDevice: AutoCompleteTextView = binding.filledExposedDropdown3
        editTextFilledExposedDropdownDevice.setAdapter(adapterDevice)

        viewModel.navigateToOverview.observe(this, Observer {
                event ->
            event?.let {
                this.findNavController().navigate(RegistrationFragmentDirections.actionRegistrationFragmentToOverviewFragment())
                viewModel.doneNavigating()
            }
        })

        viewModel.type.observe(this, Observer { type ->
                type?.let {
                    viewModel.hasLvad.value = type == "LVAD"
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