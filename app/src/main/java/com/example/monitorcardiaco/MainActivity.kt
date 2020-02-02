package com.example.monitorcardiaco

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.monitorcardiaco.repository.IUserRepository
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {

    val userRepository: IUserRepository
        get() = ServiceLocator.provideUserRepository(this)

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var toolbar: Toolbar
    private lateinit var drawerToggle: ActionBarDrawerToggle
    private lateinit var navController: NavController
    private var navHost: NavHostFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigation()
        setupNavigationDrawer()

        // Setup appBarConfiguration
        appBarConfiguration =
            AppBarConfiguration(
                setOf(R.id.overviewFragment, R.id.registrationFragment, R.id.registrationParamFragment), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        findViewById<NavigationView>(R.id.nav_view)
            .setupWithNavController(navController)

    }

    /**
     * Setup FTUE conditional navigation
     */
    private fun setupNavigation() {

        navHost =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        navController = navHost!!.navController

        val navInflater = navController.navInflater
        val graph = navInflater.inflate(R.navigation.navigation)

        /**
         * Conditional Navigation: if it's a FTUE, go to the Registration Parameters Fragment
         */
        if (checkFirstRun()) {
            graph.startDestination = R.id.registrationParamFragment
        } else {
            graph.startDestination = R.id.overviewFragment
        }

        navController.graph = graph

    }

    /**
     * Set the toolbar, set the drawerLayout, set the drawerToggle
     */
    private fun setupNavigationDrawer() {
        toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)

        drawerLayout = (findViewById<DrawerLayout>(R.id.drawer_layout))
            .apply {
                setStatusBarBackground(R.color.colorPrimaryDark)
            }
        drawerToggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        drawerToggle.setDrawerIndicatorEnabled(true)
        drawerToggle.syncState()

        drawerLayout.addDrawerListener(drawerToggle)

    }


    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
        drawerToggle.syncState()
    }

    /**
     * Ensures that the drawer is closed
     * before the app switches back to the previous fragment
     */
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    /**
     * Util function to check if it's the first run of the app
     */
    private fun checkFirstRun(): Boolean {
        val PREFS_NAME = "MyPrefsFile"
        val PREF_VERSION_CODE_KEY = "version_code"
        val DOESNT_EXIST = -1

        // Get current version code
        val currentVersionCode = BuildConfig.VERSION_CODE
        // Get saved version code
        val prefs =
            getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val savedVersionCode = prefs.getInt(PREF_VERSION_CODE_KEY, DOESNT_EXIST)
        // Check for first run or upgrade
        if (currentVersionCode == savedVersionCode) { // This is just a normal run
            return false
        } else if (savedVersionCode == DOESNT_EXIST) {
            // TODO This is a new install (or the user cleared the shared preferences)
        } else if (currentVersionCode > savedVersionCode) { // TODO This is an upgrade
        }
        // Update the shared preferences with the current version code
        prefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).apply()

        return true
    }
}
