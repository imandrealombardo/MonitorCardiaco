package com.example.monitorcardiaco.overview

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import org.junit.runner.RunWith
import androidx.test.filters.MediumTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.monitorcardiaco.R
import com.example.monitorcardiaco.ServiceLocator
import com.example.monitorcardiaco.database.User
import com.example.monitorcardiaco.repository.IUserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test

@MediumTest
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4ClassRunner::class)
class OverviewFragmentTest {


    val context = ApplicationProvider.getApplicationContext<Context>()

    private lateinit var repository: IUserRepository

    @Before
    fun initRepository() {
        //repository = FakeAndroidTestUserRepository()
        repository = ServiceLocator.provideUserRepository(context)
        ServiceLocator.userRepository = repository
    }

    @Test
    fun userOverview_DisplayedInUi() {

        runBlocking {
            // GIVEN - Add user to the DB
            val user = User(0,"Name", "Surname","LVAD",
                "Maschio", "18/11/1980", "Italy","Italy", null, null, null)
            repository.insertUser(user)

            // WHEN - Overview fragment launched to display User
            val args = Bundle()
            launchFragmentInContainer<OverviewFragment>(args, R.style.AppTheme)

            // THEN - User details are displayed on the screen
            onView(withId(R.id.user_name_textview)).check(matches(isDisplayed()))
            onView(withId(R.id.user_name_textview)).check(matches(withText("Name")))
            onView(withId(R.id.user_surname_textview)).check(matches(isDisplayed()))
            onView(withId(R.id.user_surname_textview)).check(matches(withText("Surname")))
            onView(withId(R.id.user_type_textview)).check(matches(isDisplayed()))
            onView(withId(R.id.user_type_textview)).check(matches(withText("LVAD")))
            onView(withId(R.id.user_gender_textview)).check(matches(isDisplayed()))
            onView(withId(R.id.user_gender_textview)).check(matches(withText("Maschio")))
            onView(withId(R.id.user_birthdate_textview)).check(matches(isDisplayed()))
            onView(withId(R.id.user_birthdate_textview)).check(matches(withText("18/11/1997")))
            onView(withId(R.id.user_birthplace_textview)).check(matches(isDisplayed()))
            onView(withId(R.id.user_birthplace_textview)).check(matches(withText("Bologna")))
            onView(withId(R.id.user_residence_textview)).check(matches(isDisplayed()))
            onView(withId(R.id.user_residence_textview)).check(matches(withText("Bologna")))
        }

        Thread.sleep(10000)
    }

    @After
    fun cleanupDb() = runBlockingTest {
        ServiceLocator.resetRepository()
    }
}