package com.dmyaniuk.lb_13

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dmyaniuk.constants.usersLink
import com.dmyaniuk.constants.heroesLink
import com.dmyaniuk.constants.postsLink
import com.dmyaniuk.models.GenericEntity
import com.dmyaniuk.models.Post
import com.dmyaniuk.models.Superhero
import com.dmyaniuk.models.User
import com.dmyaniuk.retrofit.CommonClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

class MainActivity : AppCompatActivity() {
    private lateinit var radioGroup: RadioGroup
    private lateinit var dataRecyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var initText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.initializeElements()
    }

    private fun initializeElements() {
        radioGroup = findViewById(R.id.radioApisGroup)
        dataRecyclerView = findViewById(R.id.dataRecycleView)
        progressBar = findViewById(R.id.progressbar)
        initText = findViewById(R.id.startLabel)

        this.radioGroup.setOnCheckedChangeListener { radioGroup, _ ->
           if (initText.visibility == View.VISIBLE) {
               initText.visibility = View.INVISIBLE
           }

           when (radioGroup.checkedRadioButtonId) {
               R.id.marvelApi -> {
                   this.renderHeroes()
               }
               R.id.postsApi -> {
                   this.renderPosts()
               }
               R.id.catsApi -> {
                   this.renderUsers()
               }
           }
        }
    }

    private fun renderHeroes() {
        progressBar.visibility = View.VISIBLE

        GlobalScope.launch(Dispatchers.IO) {
            val response = CommonClient(heroesLink).genericRetrofitService.getSuperheroes().awaitResponse()

            if (response.isSuccessful) {
                val data: List<GenericEntity> = response.body()!!

                withContext(Dispatchers.Main) {
                    render(data, heroesLink)
                }
            }
        }
    }

    private fun renderPosts() {
        progressBar.visibility = View.VISIBLE

        GlobalScope.launch(Dispatchers.IO) {
            val response = CommonClient(postsLink).genericRetrofitService.getPosts().awaitResponse()

            if (response.isSuccessful) {
                val data: List<GenericEntity> = response.body()!!

                withContext(Dispatchers.Main) {
                    render(data, postsLink)
                }
            }
        }
    }

    private fun renderUsers() {
        progressBar.visibility = View.VISIBLE

        GlobalScope.launch(Dispatchers.IO) {
            val response = CommonClient(usersLink).genericRetrofitService.getUsers().awaitResponse()

            if (response.isSuccessful) {
                val data: List<GenericEntity> = response.body()!!

                withContext(Dispatchers.Main) {
                    render(data, usersLink)
                }
            }
        }
    }

    private fun render(data: List<GenericEntity>, link: String) {
        progressBar.visibility = View.GONE

        val adapter = when (link) {
            heroesLink -> CustomRecyclerView(data as List<Superhero>)
            postsLink -> CustomRecyclerView(data as List<Post>)
            else -> CustomRecyclerView(data as List<User>)
        }

        val layoutManager = LinearLayoutManager(this@MainActivity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        dataRecyclerView.layoutManager = layoutManager
        dataRecyclerView.adapter = adapter
    }
}