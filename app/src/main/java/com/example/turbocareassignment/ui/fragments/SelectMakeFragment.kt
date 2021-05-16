package com.example.turbocareassignment.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.turbocareassignment.R
import com.example.turbocareassignment.ui.adapters.MakeListAdapter
import com.example.turbocareassignment.viewmodel.MakeViewModel
import java.lang.ClassCastException
import java.lang.Exception

class SelectMakeFragment : Fragment(), MakeListAdapter.MakeItemClickListener {

    private lateinit var makeList: ArrayList<String>
    private lateinit var modelList: ArrayList<String>
    private lateinit var itemClickListener: ItemClickListener
    private lateinit var makeListAdapter: MakeListAdapter
    private lateinit var makeViewModel: MakeViewModel
    private lateinit var loadingProgressBar: ProgressBar
    private lateinit var makeListRecyclerView: RecyclerView
    private val fuelTypeList = arrayListOf("Petrol", "Diesel", "CNG", "Petrol + CNG", "Electric", "Hybrid")
    private val transmissionList = arrayListOf("Manual", "Automatic")
    private var vehicleType = ""

    private fun getFourWheelerList() {

        makeViewModel.get4wList().observe(this, { fourWheelerList ->

            loadingProgressBar.visibility = View.GONE
            makeList = fourWheelerList as ArrayList<String>
            makeListAdapter.updateTheList(makeList)
        })
    }

    private fun getTwoWheelerList() {

        makeViewModel.get2wList().observe(this, { twoWheelerList ->

            loadingProgressBar.visibility = View.GONE
            makeList = twoWheelerList as ArrayList<String>
            makeListAdapter.updateTheList(makeList)
        })
    }

    private fun initializeViews(view: View) {

        loadingProgressBar = view.findViewById(R.id.loadingProgressBar)
        makeList = ArrayList()
        makeListAdapter = MakeListAdapter(makeList)
        makeListRecyclerView = view.findViewById(R.id.makeListRecyclerView)
        makeListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = makeListAdapter
        }
        makeViewModel = ViewModelProvider(this).get(MakeViewModel::class.java)
    }

    private fun observerViewModel() {

        if (vehicleType == "Two Wheeler") {

            getTwoWheelerList()

        } else if (vehicleType == "Four Wheeler") {

            getFourWheelerList()

        }

    }

    private fun updateTheAdapter(item: String) {

        if (makeList.contains(item)) {

            itemClickListener.onItemClick(isModel = true, isFuel = false, isTransmission = false, item, "", "", "")
            loadingProgressBar.visibility = View.VISIBLE
            makeListRecyclerView.visibility = View.GONE

            if (vehicleType == "Two Wheeler") {

                makeViewModel.get2wModelsList(item).observe(this, { twoWheelerModelsList ->

                    loadingProgressBar.visibility = View.GONE
                    makeListRecyclerView.visibility = View.VISIBLE
                    modelList = twoWheelerModelsList as ArrayList<String>
                    makeListAdapter.updateTheList(modelList)
                })

            } else if (vehicleType == "Four Wheeler") {

                makeViewModel.get4wModelsList(item).observe(this, { fourWheelerModelsList ->

                    loadingProgressBar.visibility = View.GONE
                    makeListRecyclerView.visibility = View.VISIBLE
                    modelList = fourWheelerModelsList as ArrayList<String>
                    makeListAdapter.updateTheList(modelList)
                })

            }

        } else if (modelList.contains(item)) {

            itemClickListener.onItemClick(isModel = false, isFuel = true, isTransmission = false, "", item, "", "")
            makeListAdapter.updateTheList(fuelTypeList)

        } else if (fuelTypeList.contains(item)) {

            itemClickListener.onItemClick(isModel = false, isFuel = false, isTransmission = true, "", "", item, "")
            makeListAdapter.updateTheList(transmissionList)

        } else if (transmissionList.contains(item)) {

            itemClickListener.onItemClick(isModel = false, isFuel = false, isTransmission = false, "", "", "", item)

        }

    }

    interface ItemClickListener {

        fun onItemClick(isModel: Boolean, isFuel: Boolean, isTransmission: Boolean, make: String, model: String, fuelType: String, transmission: String)
    }

    fun setTheVehicleType(type: String) {

        vehicleType = type
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_select_make, container, false)
        initializeViews(view)
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {

            itemClickListener = context as ItemClickListener

        } catch (e: Exception) {

            throw ClassCastException("$context must implement ItemClickListener!")

        }

    }

    override fun onResume() {
        super.onResume()

        observerViewModel()
        makeListAdapter.setMakeItemClickListener(this)
    }

    override fun onMakeItemClick(item: String) {

        updateTheAdapter(item)
    }
}