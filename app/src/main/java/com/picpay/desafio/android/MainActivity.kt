package com.picpay.desafio.android

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.presentation.adapter.UserListAdapter
import com.picpay.desafio.android.presentation.model.UserVO
import com.picpay.desafio.android.presentation.viewModel.UserViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: UserListAdapter

    private val userViewModel: UserViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupView()
        setupObserver()
    }

    private fun setupView() {
        adapter = UserListAdapter()

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        progressBar = findViewById(R.id.user_list_progress_bar)
        progressBar.visibility = View.VISIBLE
    }

    private fun setupObserver() {
        val userObserver = Observer<Result<List<UserVO>?>> { result ->
            result
                .onSuccess { listUsers ->
                    listUsers?.let { users ->
                        adapter.users = users
                        recyclerView.visibility = View.VISIBLE
                    } ?: run {
                        recyclerView.visibility = View.GONE
                        val message = getString(R.string.empty)
                        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                .onFailure {
                    recyclerView.visibility = View.GONE
                    val message = getString(R.string.error)
                    Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT)
                        .show()
                }
            progressBar.visibility = View.GONE
        }
        userViewModel.usersListState.observe(this, userObserver)
    }

/*    override fun onResume() {
        super.onResume()

        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.user_list_progress_bar)

        adapter = UserListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        progressBar.visibility = View.VISIBLE
        service.getUsers()
            .enqueue(object : Callback<List<UserResponse>> {
                override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                    val message = getString(R.string.error)

                    progressBar.visibility = View.GONE
                    recyclerView.visibility = View.GONE

                    Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onResponse(
                    call: Call<List<UserResponse>>,
                    response: Response<List<UserResponse>>
                ) {
                    progressBar.visibility = View.GONE

                    adapter.users = response.body()!!
                }
            })
    }*/
}
