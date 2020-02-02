package com.example.monitorcardiaco.registration

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.monitorcardiaco.Event
import com.example.monitorcardiaco.database.BodyParams
import com.example.monitorcardiaco.database.CardioParams
import com.example.monitorcardiaco.database.Lvad
import com.example.monitorcardiaco.database.User
import com.example.monitorcardiaco.repository.IUserRepository
import com.example.monitorcardiaco.repository.UserRepository
import kotlinx.coroutines.*
import java.time.LocalDate
import kotlin.contracts.contract

/**
 * ViewModel for RegistrationFragment.
 *
 *
 */
class RegistrationViewModel(private val repository: IUserRepository) : ViewModel() {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)

    private val _user = MutableLiveData<User>()

    val user: LiveData<User>
        get() = _user

    // TODO: Add a MediatorLiveData
    val email = MutableLiveData<String>()
    //val password = MutableLiveData<String>()
    val name = MutableLiveData<String>()
    val surname = MutableLiveData<String>()
    val type = MutableLiveData<String>()
    val gender = MutableLiveData<String>()
    val birthdate = MutableLiveData<String>()
    val birthplace = MutableLiveData<String>()
    val residence = MutableLiveData<String>()
    val device = MutableLiveData<String>()
    var pathologyType = MutableLiveData<String>()
    var nyhaClass = MutableLiveData<String>()
    var intermacsClass = MutableLiveData<String>()
    var height = MutableLiveData<String>()
    var weight = MutableLiveData<String>()

    var hasLvad = MutableLiveData<Boolean>()

    lateinit var userEmail: String
    lateinit var userName: String
    lateinit var userSurname: String
    lateinit var userType: String
    lateinit var userGender: String
    lateinit var userBirthdate: String
    lateinit var userBirthplace: String
    lateinit var userResidence: String
    var userDevice: String? = null
    lateinit var userPathologyType: String
    var userNyhaClass = -1
    var userIntermacsClass = -1
    var userHeight = -1
    var userWeight = -1


    val userTypeList = listOf("No LVAD", "LVAD")
    val userDeviceList = listOf("HVAD", "HM3")
    val userGenderList = listOf("Maschio", "Femmina", "Non specificato")

    private val _navigateToOverview = MutableLiveData<Event<Unit>>()

    val navigateToOverview: LiveData<Event<Unit>>
        get() = _navigateToOverview

    private val _navigateToRegistration = MutableLiveData<Event<Unit>>()

    val navigateToRegistration: LiveData<Event<Unit>>
        get() = _navigateToRegistration

    fun doneNavigating() {
        _navigateToOverview.value = null
    }

    fun doneNavigatingRegistration() {
        _navigateToRegistration.value = null
    }

    fun onRegisterParams() {
        onRegister1()
    }

    fun onRegister() {
        var lvad: Lvad? = null

        this.userName = name.value.toString()
        this.userSurname = surname.value.toString()
        this.userBirthdate = birthdate.value.toString()
        this.userBirthplace = birthplace.value.toString()
        this.userGender = gender.value.toString()
        this.userType = type.value.toString()
        this.userResidence = residence.value.toString()
        this.userDevice = device.value.toString()
        this.userPathologyType = pathologyType.value.toString()
        this.userNyhaClass = nyhaClass.value!!.toInt()
        this.userIntermacsClass = intermacsClass.value!!.toInt()
        this.userHeight = height.value!!.toInt()
        this.userWeight = weight.value!!.toInt()

        if (userDevice != null) {
            lvad = Lvad(userDevice!!, null, null, null, null, null, null)
        }

        val bodyParams = BodyParams(userHeight, userWeight, null, null, null)

        val cardioParams = CardioParams(userPathologyType, userNyhaClass, userIntermacsClass, null, null)
        //this.userEmail = email.value.toString()
        //val userPassword = password.value.toString()

        val newUser = User(0, userName, userSurname, userType, userGender,
            userBirthdate, userBirthplace, userResidence, lvad, bodyParams, cardioParams)

        //TODO: Implement null check

//        if (userEmail == null || userPassword == null) {
//            // TODO: Implement snackbar
//            //_snackbarText.value = Event(R.string.empty_task_message)
//            Log.w("Registration", "Email or Password cannot be empty")
//            return
//        }
//        if (newUser.isEmpty) {
//            // TODO: Implement Snackbar
//            //_snackbarText.value = Event(R.string.empty_task_message)
//            Log.w("Registration", "User cannot be empty")
//            return
//        }
        registerUser(newUser)
    }

    private fun registerUser(newUser: User) = uiScope.launch {
        repository.registerUser(newUser)
        _user.value = newUser
        registerNewUser()
        // TODO: Apply Event instead of LiveData observation
    }

    private fun registerNewUser() {
        _navigateToOverview.value = Event(Unit)
    }

    fun onRegister1() {
        _navigateToRegistration.value = Event(Unit)
    }

    override fun onCleared() {
        super.onCleared()
    }


}