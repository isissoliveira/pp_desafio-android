package com.picpay.desafio.android

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.presentation.adapter.UserListAdapter
import com.picpay.desafio.android.presentation.viewModel.UserViewModel
import com.picpay.desafio.android.presentation.viewModel.UserViewModel.UserState
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: UserListAdapter
    private lateinit var noRecordsTextView: TextView

    private val userViewModel: UserViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userViewModel.getUsers()
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

        noRecordsTextView = findViewById(R.id.subTitle)
    }

    private fun setupObserver() {
        userViewModel.usersListState
            .observe(this) { state ->
                when(state){
                    is UserState.Success -> {
                        adapter.users = state.listUsersVO
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        noRecordsTextView.visibility = View.GONE
                    }
                    is UserState.Empty -> {
                        recyclerView.visibility = View.GONE
                        progressBar.visibility = View.GONE
                        noRecordsTextView.visibility = View.VISIBLE
                    }
                    is UserState.Failure -> {
                        recyclerView.visibility = View.GONE
                        progressBar.visibility = View.GONE
                        noRecordsTextView.visibility = View.VISIBLE
                        val message = getString(R.string.error)
                        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT)
                            .show()
                    }
                    else -> {
                        progressBar.visibility = View.VISIBLE
                    }
                }

            }
    }
}
