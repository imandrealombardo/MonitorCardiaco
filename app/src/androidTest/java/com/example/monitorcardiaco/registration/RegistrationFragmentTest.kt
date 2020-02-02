package com.example.monitorcardiaco.registration

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.monitorcardiaco.R
import com.example.monitorcardiaco.ServiceLocator
import com.example.monitorcardiaco.database.FakeAndroidTestUserRepository
import com.example.monitorcardiaco.database.User
import com.example.monitorcardiaco.repository.IUserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
@MediumTest
@ExperimentalCoroutinesApi
class RegistrationFragmentTest {

    private lateinit var repository: IUserRepository

    @Before
    fun initRepository() {
        repository = FakeAndroidTestUserRepository()
        ServiceLocator.userRepository = repository
    }

    @Test
    fun clickRegisterButton_navigateToOverviewFragmentOne() = runBlockingTest {

        repository.registerUser(User(0,"Name", "Surname", "LVAD",
                                "Maschio", "18/11/1997", "Bologna", "Bologna"))

        // GIVEN - On the home screen
        val scenario = launchFragmentInContainer<RegistrationFragment>(Bundle(), R.style.AppTheme)

        val navController = mock(NavController::class.java)

        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }

        // WHEN - Click on the register button
        onView(withId(R.id.register_button))
            .perform(click())

        // THEN - Verify that we navigate to the overview Screen
        verify(navController).navigate(
            RegistrationFragmentDirections.actionRegistrationFragmentToOverviewFragment())

    }

    @After
    fun cleanupDb() = runBlockingTest {
        ServiceLocator.resetRepository()
    }

}