package com.hamzaerdas.sharephoto.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hamzaerdas.sharephoto.R
import com.hamzaerdas.sharephoto.adapter.PostAdapter
import com.hamzaerdas.sharephoto.databinding.ActivityFeedBinding
import com.hamzaerdas.sharephoto.databinding.ActivityMainBinding
import com.hamzaerdas.sharephoto.model.Post

class FeedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFeedBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var postList: ArrayList<Post>
    private lateinit var postAdapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth
        db = Firebase.firestore
        postList = ArrayList()

        getData()

        postAdapter = PostAdapter(postList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this@FeedActivity)
        binding.recyclerView.adapter = postAdapter

    }

    fun getData(){

        val imageReference = db.collection("post")
        imageReference.orderBy("dateTime", Query.Direction.DESCENDING).addSnapshotListener{ snapshot, e ->
            if(e != null){
                println("Listen failed $e")
                Toast.makeText(this@FeedActivity, e.localizedMessage, Toast.LENGTH_LONG).show()
            } else {
                if(snapshot != null){
                    if (!snapshot.isEmpty){
                        val documents = snapshot.documents
                        postList.clear()
                        for(document in documents){
                            // casting
                            val comment = document.get("comment") as String
                            val userEmail = document.get("userEmail") as String
                            val dowloadUrl = document.get("dowloadUrl") as String

                            val post = Post(userEmail, comment, dowloadUrl)
                            postList.add(post)
                        }
                        postAdapter.notifyDataSetChanged()
                    } else{
                        println("Current data: null")
                    }
                }
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.share_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.share){
            val intent = Intent(this@FeedActivity, UploadActivity::class.java)
            startActivity(intent)
        } else if(item.itemId == R.id.signout){
            auth.signOut()
            Toast.makeText(this, "Çýkýþ Yapýldý", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@FeedActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}