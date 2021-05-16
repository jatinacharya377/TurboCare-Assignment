package com.example.turbocareassignment.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.turbocareassignment.R
import com.example.turbocareassignment.repository.roomdb.VehicleEntity
import com.example.turbocareassignment.repository.sharedpreferences.BackButtonVisibilityPreference
import com.example.turbocareassignment.ui.adapters.VehicleListAdapter
import com.example.turbocareassignment.viewmodel.VehicleViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class VehicleListActivity : AppCompatActivity(), VehicleListAdapter.VehicleItemClickListener {

    private lateinit var vehicleList: ArrayList<VehicleEntity>
    private lateinit var backButtonVisibilityPreference: BackButtonVisibilityPreference
    private lateinit var addFab: FloatingActionButton
    private lateinit var loadingProgressBar: ProgressBar
    private lateinit var vehicleListAdapter: VehicleListAdapter
    private lateinit var vehicleViewModel: VehicleViewModel

    private fun initializeViews() {

        addFab = findViewById(R.id.addFab)
        backButtonVisibilityPreference = BackButtonVisibilityPreference(this)
        loadingProgressBar = findViewById(R.id.loadingProgressBar)
        vehicleList = ArrayList()
        vehicleListAdapter = VehicleListAdapter(vehicleList)
        vehicleListAdapter.setVehicleItemClickListener(this)
        val vehicleListRecyclerView: RecyclerView = findViewById(R.id.vehicleListRecyclerView)
        vehicleListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = vehicleListAdapter
        }
        vehicleViewModel = ViewModelProvider(this).get(VehicleViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle_list)

        initializeViews()
    }

    override fun onResume() {
        super.onResume()

        addFab.setOnClickListener {

            backButtonVisibilityPreference.setPreference(true)
            startActivity(Intent(this, MainActivity::class.java))
        }

        vehicleViewModel.getVehicleList().observe(this, object: Observer<List<VehicleEntity>> {

            override fun onChanged(vehicleList: List<VehicleEntity>?) {

                loadingProgressBar.visibility = View.GONE
                this@VehicleListActivity.vehicleList = vehicleList as ArrayList<VehicleEntity>
                vehicleListAdapter.updateTheList(vehicleList)
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()

        backButtonVisibilityPreference.setPreference(false)
    }

    override fun onVehicleItemClick(id: Long) {

        val intent = Intent(this, VehicleProfileActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }
}