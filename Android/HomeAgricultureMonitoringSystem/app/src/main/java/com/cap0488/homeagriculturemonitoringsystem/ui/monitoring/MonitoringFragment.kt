package com.cap0488.homeagriculturemonitoringsystem.ui.monitoring

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.cap0488.homeagriculturemonitoringsystem.databinding.FragmentMonitoringBinding

class MonitoringFragment : Fragment() {

    private lateinit var binding: FragmentMonitoringBinding
    private val monitoringViewModel = MonitoringViewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{

        binding = FragmentMonitoringBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        monitoringViewModel.plantData.observe(requireActivity(),{

            binding.soilMoisture.text = it.soilMoisture+"%"
            binding.humidity.text = it.humidity+"%"
            binding.temperature.text = it.temperature+"°C"
        })




    }


}