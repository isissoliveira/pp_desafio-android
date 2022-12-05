package com.picpay.desafio.android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.presentation.adapter.UserListAdapter
import com.picpay.desafio.android.presentation.viewModel.UserViewModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.detail_user.view.*
import kotlinx.android.synthetic.main.list_item_user.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailUserFragment : Fragment() {
    private lateinit var usernameView: TextView
    private lateinit var nameView: TextView
    private val argName by lazy {
        arguments?.getString(KEY_NAME)
            ?: throw java.lang.IllegalArgumentException("Invalid arg name")
    }
    private val argUserName by lazy {
        arguments?.getString(KEY_USERNAME)
            ?: throw java.lang.IllegalArgumentException("Invalid arg username")
    }
    private val argImg by lazy {
        arguments?.getString(KEY_IMG)
            ?: throw java.lang.IllegalArgumentException("Invalid arg img")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detail_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
    }

    private fun setupView(view: View) {
        usernameView = view.findViewById(R.id.username)
        usernameView.text = argUserName

        nameView = view.findViewById(R.id.name)
        nameView.text = argName

        Picasso.get()
            .load(argImg)
            .error(R.drawable.ic_round_account_circle)
            .into(view.detailPicture)
    }

    companion object {
        val KEY_NAME = "name"
        val KEY_USERNAME = "username"
        val KEY_IMG = "img"
    }
}