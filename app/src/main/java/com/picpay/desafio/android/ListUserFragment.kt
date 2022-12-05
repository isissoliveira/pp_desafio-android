package com.picpay.desafio.android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.DetailUserFragment.Companion.KEY_IMG
import com.picpay.desafio.android.DetailUserFragment.Companion.KEY_NAME
import com.picpay.desafio.android.DetailUserFragment.Companion.KEY_USERNAME
import com.picpay.desafio.android.presentation.adapter.UserListAdapter
import com.picpay.desafio.android.presentation.model.UserVO
import com.picpay.desafio.android.presentation.viewModel.UserViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListUserFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: UserListAdapter
    private lateinit var noRecordsTextView: TextView

    private val userViewModel: UserViewModel by viewModel()
    private val navController by lazy {
        findNavController()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userViewModel.getUsers()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        setupObserver()
    }

    private fun setupView(view: View) {
        adapter = UserListAdapter()
        adapter.onClick = this::onUserClicked

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        progressBar = view.findViewById(R.id.user_list_progress_bar)
        progressBar.visibility = View.VISIBLE

        noRecordsTextView = view.findViewById(R.id.subTitle)
    }

    private fun onUserClicked(user: UserVO){
        val bundle = Bundle()
        bundle.putString(KEY_NAME, user.name )
        bundle.putString(KEY_USERNAME, user.username )
        bundle.putString(KEY_IMG, user.img )
        navController.navigate(R.id.detailUserFragment, bundle)
    }

    private fun setupObserver() {
        userViewModel.usersListState
            .observe(viewLifecycleOwner) { state ->
                when (state) {
                    is UserViewModel.UserState.Success -> {
                        adapter.users = state.listUsersVO
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        noRecordsTextView.visibility = View.GONE
                    }
                    is UserViewModel.UserState.Empty -> {
                        recyclerView.visibility = View.GONE
                        progressBar.visibility = View.GONE
                        noRecordsTextView.visibility = View.VISIBLE
                    }
                    is UserViewModel.UserState.Failure -> {
                        recyclerView.visibility = View.GONE
                        progressBar.visibility = View.GONE
                        noRecordsTextView.visibility = View.VISIBLE
                        val message = getString(R.string.error)
                        Toast.makeText(context, message, Toast.LENGTH_SHORT)
                            .show()
                    }
                    else -> {
                        progressBar.visibility = View.VISIBLE
                    }
                }

            }
    }
}