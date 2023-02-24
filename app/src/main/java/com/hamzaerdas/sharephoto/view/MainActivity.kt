package com.hamzaerdas.sharephoto.view


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hamzaerdas.sharephoto.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private var email: String = ""
    private var password: String = ""

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth

        val currentUser = auth.currentUser
        if(currentUser != null){
            val intent = Intent(this@MainActivity, FeedActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    fun signInClick(view: View){

        email = binding.editEmailText.text.toString()
        password = binding.editPasswordText.text.toString()

        if(email.equals("") || password.equals("")){
            Toast.makeText(this@MainActivity, "Email ve þifre girin", Toast.LENGTH_LONG).show()
        } else{
            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    //Success
                    Toast.makeText(this@MainActivity, "Giriþ baþarýlý", Toast.LENGTH_LONG).show()
                    val intent = Intent(this@MainActivity, FeedActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                .addOnFailureListener{
                    Toast.makeText(this@MainActivity, it.localizedMessage, Toast.LENGTH_LONG).show()
                }
        }

    }

    fun signUpClick(view: View){

        email = binding.editEmailText.text.toString()
        password = binding.editPasswordText.text.toString()

        if(email.equals("") || password.equals("")){
            Toast.makeText(this@MainActivity, "Email ve þifre girin", Toast.LENGTH_LONG).show()
        } else{
            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    //Success
                    Toast.makeText(this@MainActivity, "Kayýt baþarýlý", Toast.LENGTH_LONG).show()
                    val intent = Intent(this@MainActivity, FeedActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                .addOnFailureListener{
                    Toast.makeText(this@MainActivity, it.localizedMessage, Toast.LENGTH_LONG).show()
                }
        }

    }
}