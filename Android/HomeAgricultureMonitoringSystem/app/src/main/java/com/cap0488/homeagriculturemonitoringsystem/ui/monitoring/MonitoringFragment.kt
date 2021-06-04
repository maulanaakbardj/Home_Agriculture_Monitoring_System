package com.cap0488.homeagriculturemonitoringsystem.ui.monitoring

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.cap0488.homeagriculturemonitoringsystem.databinding.FragmentMonitoringBinding

class MonitoringFragment : Fragment() {

    private lateinit var binding: FragmentMonitoringBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMonitoringBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}