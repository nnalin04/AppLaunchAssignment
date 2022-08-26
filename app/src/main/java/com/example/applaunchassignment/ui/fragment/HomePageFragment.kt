package com.example.applaunchassignment.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.applaunchassignment.R
import com.example.applaunchassignment.databinding.FragmentHomePageBinding
import com.example.applaunchassignment.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomePageFragment : Fragment() {

    private var _binding : FragmentHomePageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomePageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.clickToLoginPage.setOnClickListener{
            if (Utils.getCurrentUserStatus(requireActivity().applicationContext).loginStatus) {
                findNavController().navigate(R.id.action_homePageFragment_to_userListFragment)
            } else {
                findNavController().navigate(R.id.action_homePageFragment_to_loginPageFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}