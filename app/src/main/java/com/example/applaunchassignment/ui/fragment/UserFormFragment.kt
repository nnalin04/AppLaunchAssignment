package com.example.applaunchassignment.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.applaunchassignment.R
import com.example.applaunchassignment.data.Response
import com.example.applaunchassignment.data.model.User
import com.example.applaunchassignment.databinding.FragmentUserFormBinding
import com.example.applaunchassignment.ui.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFormFragment : Fragment() {

    private var _binding: FragmentUserFormBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserViewModel by viewModels()

    private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUserFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.saveButton.setOnClickListener {
            val email = binding.emailTextField.editText?.text.toString()
            val firstName = binding.firstnameTextField.editText?.text.toString()
            val lastName = binding.lastnameTextField.editText?.text.toString()
            user = User(firstName, lastName, email)
            viewModel.getUserByEmail(user.email)
            viewModel.userByEmail.observe(viewLifecycleOwner, Observer {
                when (it) {
                    is Response.Success -> {
                        if (it.data!!.isNotEmpty()) {
                            binding.emailTextField.error = "User with email $email already exist"
                            Log.d("MyTag", findNavController().currentDestination.toString())
                        } else {
                            Log.d("MyTag", findNavController().currentDestination.toString())
                            saveUserData()
                        }
                    }
                }
            })
        }
        binding.cancelButton.setOnClickListener {
            findNavController().navigate(R.id.action_userFormFragment_to_userListFragment)
        }
    }

    private fun saveUserData() {
        viewModel.addUser(user)
        findNavController().navigate(R.id.action_userFormFragment_to_userListFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}