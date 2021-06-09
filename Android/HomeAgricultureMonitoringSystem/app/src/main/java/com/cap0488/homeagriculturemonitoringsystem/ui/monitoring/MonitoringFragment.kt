package com.cap0488.homeagriculturemonitoringsystem.ui.monitoring

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
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
            binding.progresBar.visibility = View.INVISIBLE

        })

       monitoringViewModel.lamp.observe(requireActivity(),{
           binding.btnKipas.text = it.pump

           binding.btnLampu.text = it.lamp

       })

        binding.btnKipas.setOnClickListener {

            var state =  binding.btnKipas.text.toString()

            if (state == "on"){
                state = "off"
                binding.btnKipas.text = "off"
            }
            else if (state == "off"){
                state = "on"
                binding.btnKipas.text = "on"

            }
            Log.d("cekdalam",state)


            monitoringViewModel.postCondition("pump",state)
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
            //Log.d("cekdalam",binding.switchKipas.showText.toString())

        }

        binding.btnLampu.setOnClickListener {

            var state =  binding.btnLampu.text.toString()

            if (state == "on"){
                state = "off"
                binding.btnLampu.text = "off"
            }
            else if (state == "off"){
                state = "on"
                binding.btnLampu.text = "on"
            }
            Log.d("cekdalam",state)

            monitoringViewModel.postCondition("lamp",state)
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)

        }





    }


}