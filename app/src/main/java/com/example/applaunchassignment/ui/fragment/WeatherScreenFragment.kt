package com.example.applaunchassignment.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.applaunchassignment.R
import com.example.applaunchassignment.data.Response
import com.example.applaunchassignment.databinding.FragmentWeatherScreenBinding
import com.example.applaunchassignment.ui.viewmodel.UserViewModel
import com.example.applaunchassignment.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherScreenFragment : Fragment() {

    private var _binding : FragmentWeatherScreenBinding? = null
    private val binding get() = _binding!!

    private val viewModel : UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentWeatherScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadWeatherReport()
        viewModel.weatherReport.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Response.Success -> {
                    binding.temp.text = it.data!!.current.temp.toString()
                    binding.humidity.text = it.data.current.humidity.toString()
                    binding.windSpeed.text = it.data.current.wind_speed.toString()
                    binding.weatherType.text = it.data.current.weather[0].main
                }
            }
        })
        binding.backButton.setOnClickListener{
            requireActivity().onBackPressed()
        }
        binding.logoutButton.setOnClickListener{
            Utils.editLoginStatus(false, requireActivity().applicationContext)
            findNavController().navigate(R.id.action_weatherScreenFragment_to_homePageFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}