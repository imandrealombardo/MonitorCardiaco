package com.example.monitorcardiaco.registration

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.monitorcardiaco.database.FakeUserDatabaseDao
import com.example.monitorcardiaco.database.FakeUserRepository
import com.example.monitorcardiaco.database.User
import com.example.monitorcardiaco.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.not
import org.hamcrest.CoreMatchers.nullValue
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

//@RunWith(AndroidJUnit4::class)
//@Config(sdk = [Build.VERSION_CODES.O_MR1])
@UseExperimental
class RegistrationViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    // Replace Dispatchers.main with a testing dispatcher
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    // Use a fake repository to be injected into the viewmodel
    private lateinit var userRepository: FakeUserRepository

    private lateinit var dataSource: FakeUserDatabaseDao

    private lateinit var registrationViewModel: RegistrationViewModel

    private val _user1 = MutableLiveData<User>()

    val user1: LiveData<User>
        get() = _user1

    @Before
    fun setupViewModel() {
        _user1.value = User(0,"Name", "Surname","LVAD",
            "Maschio", "18/11/1980", "Italy","Italy", null, null, null)
        dataSource = FakeUserDatabaseDao(_user1)
        userRepository = FakeUserRepository(dataSource)
        runBlocking { userRepository.insertUser(User(0,"Name", "Surname","LVAD",
            "Maschio", "18/11/1980", "Italy","Italy", null, null, null)) }

        registrationViewModel = RegistrationViewModel(userRepository)

        Dispatchers.setMain(mainThreadSurrogate)
    }

    @Test
    fun registerUser_setsDoneNavigating() {
 //       launch(Dispatchers.Main) {
        // Given a fresh RegistrationViewModel
//        val registrationViewModel = RegistrationViewModel(ApplicationProvider.getApplicationContext())

        // When register a new User
        runBlocking { registrationViewModel.onRegister() }

        // Then the navigation variable is set to newUser
        val value = registrationViewModel.navigateToOverview.getOrAwaitValue()

        assertThat(value.getContentIfNotHandled(), not(nullValue()))
//        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
  //      mainThreadSurrogate.close()
    }
}