package com.cap0488.homeagriculturemonitoringsystem.ui.page2.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.cap0488.homeagriculturemonitoringsystem.R
import com.cap0488.homeagriculturemonitoringsystem.data.DataDummy
import com.cap0488.homeagriculturemonitoringsystem.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var binding: FragmentSearchBinding
    private val searchAdapter = SearchAdapter()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSearchBinding.inflate(layoutInflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        searchAdapter.setSearchPlant(DataDummy.genereatePlantType())
        showGridList()


    }

    private fun showGridList(){
        with(binding.rvArticle){
            layoutManager = GridLayoutManager(requireContext(),2)
            setHasFixedSize(true)
            adapter = searchAdapter
        }
    }



}