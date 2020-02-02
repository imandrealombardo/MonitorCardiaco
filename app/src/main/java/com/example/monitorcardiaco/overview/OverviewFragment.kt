package com.example.monitorcardiaco.overview

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.monitorcardiaco.*
import com.example.monitorcardiaco.database.User
import com.example.monitorcardiaco.database.UserDatabase
import com.example.monitorcardiaco.databinding.FragmentOverviewBinding
import com.example.monitorcardiaco.repository.UserRepository
import kotlinx.android.synthetic.main.fragment_overview.*

class OverviewFragment : Fragment() {

    /**
     * Called when the Fragment is ready to display content to the screen.
     *
     * This function uses DataBindingUtil to inflate R.layout.fragment_sleep_quality.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentOverviewBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_overview, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = UserDatabase.getInstance(application).userDatabaseDao

        val viewModel = activity?.getViewModel()
        { OverviewViewModel(dataSource, (requireContext().applicationContext as MonitorCardiacoApplication).userRepository) }

        binding.overviewViewModel = viewModel

        // Delay hiding
        val handler = Handler()
        handler.postDelayed({
            ViewAnimation.init(binding.fabParam)
            ViewAnimation.init(binding.fabList)
        }, 50)

        viewModel!!.isRotated.value = false

        binding.fab.setOnClickListener {
            viewModel.isRotated.apply {
                this.value = ViewAnimation.rotateFab(it, (viewModel.isRotated.value!!.not()))

                // Sliding effect not possible with constraint layout (due to the constraints)
                if (this.value == true) {
                    ViewAnimation.showIn(binding.fabParam)
                    ViewAnimation.showIn(binding.fabList)
                } else {
                    ViewAnimation.showOut(binding.fabParam)
                    ViewAnimation.showOut(binding.fabList)
                }
            }
        }

        viewModel.user.observe(this, Observer { user ->
            user?.let {

                //TODO: Apply a Binding Adapter for units
                binding.apply {
                    user_name_textview.text = user.name
                    user_surname_textview.text = user.surname
                    user_type_textview.text = user.type
                    user_gender_textview.text = user.gender
                    user_birthdate_textview.text = user.birthdate
                    user_birthplace_textview.text = user.birthplace
                    user_residence_textview.text = user.residence
                    user_height_textview.text = "${user.bodyparams!!.height} cm"
                    user_weight_textview.text = "${user.bodyparams.weight} kg"

                    if (user.lvad != null) {
                        viewModel.hasLvad.value = true
                        user_device_textview.text = user.lvad.device
                        user_rpm_textview.text = if (user.lvad.rpm != null) "${user.lvad.rpm} rpm" else ""
                        user_watt_textview.text = if (user.lvad.watt != null) "${user.lvad.watt} watt" else ""
                        user_flux_textview.text = if (user.lvad.flux != null) "${user.lvad.flux} watt" else ""
                        user_pi_textview.text = if (user.lvad.pi != null) "${user.lvad.pi}" else ""
                        user_maxmin_textview.text = if (user.lvad.max != null && user.lvad.min != null)
                                                    "${user.lvad.max},${user.lvad.min}" else ""
                    }



                }
            }
        })

//        overviewViewModel.user.observe(this, Observer { user ->
//            user?.let {
//                binding.apply {
//                    textview.text = user.email
//                    textview2.text = user.password
//                }
//            }
//        })

//        overviewViewModel.user.observe(this, Observer {
//            if (overviewViewModel.user.value!!.email != null) {
//                binding.apply {
//                    textview.text = overviewViewModel.user.value!!.email
//                    textview2.text = overviewViewModel.user.value!!.password
//                }
//            }
//        })

        //binding.sleepList.adapter = adapter

        binding.setLifecycleOwner(this)
        return binding.root
    }

    inline fun <reified T : ViewModel> FragmentActivity.getViewModel(noinline creator: (() -> T)? = null): T {
        return if (creator == null)
            ViewModelProviders.of(this).get(T::class.java)
        else
            ViewModelProviders.of(this, BaseViewModelFactory(creator)).get(T::class.java)
    }
}