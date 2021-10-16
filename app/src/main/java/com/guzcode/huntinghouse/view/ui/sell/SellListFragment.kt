package com.guzcode.huntinghouse.view.ui.sell

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.guzcode.huntinghouse.R
import com.guzcode.huntinghouse.model.Property
import com.guzcode.huntinghouse.view.adapter.SellList.PropertyAdapter
import com.guzcode.huntinghouse.view.adapter.SellList.PropertyListener
import com.guzcode.huntinghouse.viewmodel.PropertyByUserViewModel
import kotlinx.android.synthetic.main.fragment_sell_list.*

class SellListFragment: Fragment(), PropertyListener {
    private lateinit var propertyAdapter: PropertyAdapter
    private lateinit var viewModel: PropertyByUserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sell_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PropertyByUserViewModel::class.java)
        viewModel.refresh()

        propertyAdapter= PropertyAdapter(this)

        rvSellList.apply {
            layoutManager= LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL,false)
            adapter= propertyAdapter
        }
        observeViewModel()
        btnToPublish.setOnClickListener{
            findNavController().navigate(R.id.sellCreateFragment,null)
        }


    }
    fun observeViewModel(){
        viewModel.listProperties.observe(viewLifecycleOwner, Observer<List<Property>>{ property->
            propertyAdapter.updateData(property)
        })
        viewModel.isLoading.observe(viewLifecycleOwner, Observer<Boolean> {
            if( it != null)
                rlBaseSellList.visibility=View.INVISIBLE
            btnToPublish.visibility=View.VISIBLE

        })
    }
    override fun onHouseClicked(property: Property, position: Int) {
        val bundle = bundleOf("property" to property)
        findNavController().navigate(R.id.sellEditFragment,bundle)
    }
}