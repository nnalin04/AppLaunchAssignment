package com.example.applaunchassignment.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.applaunchassignment.Constants
import com.example.applaunchassignment.R
import com.example.applaunchassignment.data.model.Admin
import com.example.applaunchassignment.databinding.FragmentLoginPageBinding
import com.example.applaunchassignment.utils.Utils

class LoginPageFragment : Fragment() {

    private var _binding : FragmentLoginPageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTextOnPreviousLogin()
        handleLoginClick()
    }

    private fun setTextOnPreviousLogin() {
        val mainUser = Utils.getCurrentUserStatus(requireActivity().applicationContext)
        if (mainUser.userName != null) {
            binding.usernameTextField.editText?.setText(mainUser.userName)
            binding.passwordTextField.editText?.setText(mainUser.password)
        }
    }

    private fun handleLoginClick() {
        val userName = binding.usernameTextField.editText?.text.toString()
        val password = binding.passwordTextField.editText?.text.toString()
        binding.clickToLoginPage.setOnClickListener {
            binding.usernameTextField.error = ""
            binding.passwordTextField.error = ""
            if (userName != Constants.USERNAME || password != Constants.PASSWORD) {
                if (userName != Constants.USERNAME) {
                    binding.usernameTextField.error = "Please Enter Valid Username"
                }
                if (password != Constants.PASSWORD) {
                    binding.passwordTextField.error = "Please Enter Valid Password"
                }
            } else {
                Utils.addUserData(requireActivity().applicationContext, Admin(userName, password, true))
                findNavController().navigate(R.id.action_loginPageFragment_to_userListFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}