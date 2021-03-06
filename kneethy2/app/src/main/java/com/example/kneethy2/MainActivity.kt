package com.example.kneethy2

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.android.gms.auth.api.signin.GoogleSignInAccount


const val EMAIL = "com.example.myfirstapp.EMAIL"
const val DISPLAYNAME = "com.example.myfirstapp.DISPLAYNAME"
var TEMPERATURE = "no data"

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient : GoogleSignInClient
    private var isInHere : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        isInHere = true
        Log.d("ONCREATE","ON CREATE START")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("674310347927-a7umsfcqa1a4b2ekh9ebj9254ds5kv1p.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        auth = Firebase.auth

        val button: SignInButton = findViewById(R.id.sign_in_button)
        button.setOnClickListener {
            Log.d("BUTTON","Button Clicked")
            signIn()
        }

        Log.d("ONCREATE","ON CREATE END")
    }

    private fun signIn() {
        Log.d("SIGNIN","SIGN IN STARTED")
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, 1)
        Log.d("SIGNIN","SIGN IN ENDED")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 1) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        isInHere = false
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            var email = "unindentified"
            var displayName = "unindentified"
            user.email?.let {
                Log.d("REDIRECT", it)
                email = it
            }
            user.displayName?.let {
                Log.d("REDIRECT", it)
                displayName = it
            }
            val intent = Intent(this, navigation::class.java).apply{
                putExtra(EMAIL, email)
                putExtra(DISPLAYNAME, displayName)
            }
            startActivity(intent)
        } else {
        }
    }
}