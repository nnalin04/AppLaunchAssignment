package com.example.applaunchassignment.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.applaunchassignment.R
import com.example.applaunchassignment.data.Response
import com.example.applaunchassignment.data.model.User
import com.example.applaunchassignment.databinding.FragmentUserListBinding
import com.example.applaunchassignment.ui.RecyclerViewClickListener
import com.example.applaunchassignment.ui.adapter.UserAdapter
import com.example.applaunchassignment.ui.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserListFragment : Fragment(), RecyclerViewClickListener {

    private var _binding: FragmentUserListBinding? = null
    private val binding get() = _binding!!

    private lateinit var data: ArrayList<User>
    private lateinit var adapter: UserAdapter

    private val viewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUserListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        data = ArrayList()
        viewModel.loadUserData()
        setRecycleView()
        setDataUsingViewModel()
        setOnSwipeDelete()
        setClickListener()
    }

    private fun setClickListener() {
        binding.addUserButton.setOnClickListener {
            findNavController().navigate(R.id.action_userListFragment_to_userFormFragment)
        }
    }

    private fun setDataUsingViewModel() {
        viewModel.userList.observe(viewLifecycleOwner) {
            when (it) {
                is Response.Loading -> {
                    Log.d("MyTag", "data is loading")
                }
                is Response.Success -> {
                    data = (it.data as ArrayList<User>?)!!
                    adapter.loadData(data)
                }
                is Response.Error -> {
                    Log.d("MyTag", it.errorMessage!!)
                }
            }
        }
    }

    private fun setRecycleView() {
        val layoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(requireActivity().applicationContext)
        adapter = UserAdapter(data,this)
        binding.userRecycleView.layoutManager = layoutManager
        binding.userRecycleView.adapter = adapter
    }

    private fun setOnSwipeDelete() {
        ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedUser: User = data[viewHolder.adapterPosition]
                data.removeAt(viewHolder.adapterPosition)
                adapter.notifyItemRemoved(viewHolder.adapterPosition)
                deleteUser(deletedUser)
            }
        }).attachToRecyclerView(binding.userRecycleView)
    }

    private fun deleteUser(user: User) {
        viewModel.deleteUser(user)
    }

    override fun onRecyclerViewItemClick(view: View) {
        findNavController().navigate(R.id.action_userListFragment_to_weatherScreenFragment)
    }

}