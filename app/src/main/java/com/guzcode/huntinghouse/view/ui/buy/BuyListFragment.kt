package com.guzcode.huntinghouse.view.ui.buy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.guzcode.huntinghouse.R
import com.guzcode.huntinghouse.model.Property
import com.guzcode.huntinghouse.view.adapter.PropertyAdapter
import com.guzcode.huntinghouse.view.adapter.PropertyListener
import com.guzcode.huntinghouse.viewmodel.PropertyViewModel
import kotlinx.android.synthetic.main.fragment_buy_list.*


class BuyListFragment : Fragment(), PropertyListener {
    private lateinit var propertyAdapter: PropertyAdapter
    private lateinit var viewModel: PropertyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buy_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PropertyViewModel::class.java)
        viewModel.refresh()

        propertyAdapter= PropertyAdapter(this)

        rvBuyList.apply {
            layoutManager= LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL,false)
            adapter= propertyAdapter
        }
        observeViewModel()
    }
    fun observeViewModel(){
        viewModel.listProperties.observe(viewLifecycleOwner, Observer<List<Property>>{property->
            propertyAdapter.updateData(property)
        })
        viewModel.isLoading.observe(viewLifecycleOwner, Observer<Boolean> {
            if( it != null)
                rlBaseBuyList.visibility=View.INVISIBLE

        })
}

    override fun onHouseClick(property: Property, position: Int) {
        val bundle = bundleOf("property" to property)
        findNavController().navigate(R.id.buyDetailsFragment,bundle)
    }
}