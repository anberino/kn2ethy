package com.example.kneethy2

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.example.kneethy2.databinding.ActivityNavigationBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import android.widget.Toast
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import com.google.android.gms.common.SignInButton

class navigation : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityNavigationBinding

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarNavigation.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_navigation)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.navigation, menu)
        val email = intent.getStringExtra(EMAIL)
        val displayName = intent.getStringExtra(DISPLAYNAME)
        findViewById<TextView>(R.id.nav_header_email).apply {
            text = email
        }
        findViewById<TextView>(R.id.nav_header_name).apply {
            text = displayName
        }

        return true
    }

    //Handling Action Bar button click
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.action_settings -> {
                Firebase.auth.signOut()
                val intent = Intent(this, MainActivity::class.java).apply {}
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_navigation)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}