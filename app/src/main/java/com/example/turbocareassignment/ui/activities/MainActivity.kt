package com.example.turbocareassignment.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.turbocareassignment.R
import com.example.turbocareassignment.repository.roomdb.VehicleEntity
import com.example.turbocareassignment.repository.sharedpreferences.BackButtonVisibilityPreference
import com.example.turbocareassignment.ui.fragments.SelectMakeFragment
import com.example.turbocareassignment.ui.fragments.VehicleNumberInputFragment
import com.example.turbocareassignment.ui.fragments.VehicleTypeFragment
import com.example.turbocareassignment.viewmodel.VehicleViewModel
import java.util.regex.Pattern

class MainActivity : AppCompatActivity(), SelectMakeFragment.ItemClickListener, VehicleNumberInputFragment.VehicleNoFragmentListener, VehicleTypeFragment.VehicleTypeListener {

    private lateinit var backButtonVisibilityPreference: BackButtonVisibilityPreference
    private lateinit var backButtonCardView: CardView

    private lateinit var fragmentContainer: FrameLayout
    private lateinit var selectMakeFragment: SelectMakeFragment
    private lateinit var toolbarTextView: TextView
    private lateinit var vehicleNumberInputFragment: VehicleNumberInputFragment
    private lateinit var vehicleTypeFragment: VehicleTypeFragment
    private lateinit var vehicleViewModel: VehicleViewModel
    private var fuelType: String = ""
    private var make: String = ""
    private var model: String = ""
    private var vehicleNumber: String = ""

    private fun isRegistrationNoValid(regNo: String): Boolean {

        val pattern: Pattern = Pattern.compile("^[A-Z]{2}[0-9]{2}[A-Z]{2}[0-9]{4}$")
        return pattern.matcher(regNo).matches()
    }

    private fun initializeViews() {


        backButtonCardView = findViewById(R.id.backButtonCardView)
        backButtonVisibilityPreference = BackButtonVisibilityPreference(this)
        fragmentContainer = findViewById(R.id.fragmentContainer)
        selectMakeFragment = SelectMakeFragment()
        toolbarTextView = findViewById(R.id.toolbarTextView)
        setVisibilityAndToolbarText(View.GONE, getString(R.string.toolbar_text))
        vehicleNumberInputFragment = VehicleNumberInputFragment()
        vehicleTypeFragment = VehicleTypeFragment()
        vehicleViewModel = ViewModelProvider(this).get(VehicleViewModel::class.java)
    }

    private fun loadTheFragment(fragment: Fragment) {

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    private fun loadTheFragmentWithSlideLeftCustomAnimation(fragment: Fragment) {

        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.animator.fragment_slide_left_enter, R.animator.fragment_slide_left_exit)
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    private fun loadTheFragmentWithSlideRightCustomAnimation(fragment: Fragment) {

        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.animator.fragment_slide_right_enter, R.animator.fragment_slide_right_exit)
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    private fun setVisibilityAndToolbarText(visibility: Int, text: String) {

        backButtonCardView.visibility = visibility
        toolbarTextView.text = text
    }

    private fun showToast(msg: String) {

        Toast.makeText(
            this,
            msg,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeViews()
    }

    override fun onResume() {
        super.onResume()

        if (backButtonVisibilityPreference.isFromVehicleListActivity()) {

            backButtonCardView.visibility = View.VISIBLE

        }

        loadTheFragment(vehicleNumberInputFragment)
        backButtonCardView.setOnClickListener {

            if (backButtonVisibilityPreference.isFromVehicleListActivity()) {

                finish()

            } else {

                if (selectMakeFragment.isVisible) {

                    setVisibilityAndToolbarText(View.VISIBLE, getString(R.string.toolbar_text_2))
                    loadTheFragmentWithSlideRightCustomAnimation(vehicleTypeFragment)

                } else if (vehicleTypeFragment.isVisible) {

                    setVisibilityAndToolbarText(View.GONE, getString(R.string.toolbar_text))
                    loadTheFragmentWithSlideRightCustomAnimation(vehicleNumberInputFragment)

                }

                vehicleTypeFragment.resetTheValues()

            }

        }
    }

    override fun onBackPressed() {

        when {

            selectMakeFragment.isVisible -> {

                setVisibilityAndToolbarText(View.VISIBLE, getString(R.string.toolbar_text_2))
                loadTheFragmentWithSlideRightCustomAnimation(vehicleNumberInputFragment)

            }

            vehicleTypeFragment.isVisible -> {

                setVisibilityAndToolbarText(View.GONE, getString(R.string.toolbar_text))
                loadTheFragmentWithSlideRightCustomAnimation(vehicleNumberInputFragment)

            }

            vehicleNumberInputFragment.isVisible -> {

                super.onBackPressed()

            }

        }

    }

    override fun onContinue(isSelected: Boolean, vehicleType: String) {

        if (isSelected) {

            selectMakeFragment.setTheVehicleType(vehicleType)
            loadTheFragmentWithSlideLeftCustomAnimation(selectMakeFragment)
            setVisibilityAndToolbarText(View.VISIBLE, getString(R.string.toolbar_text_3))

        } else {

            showToast("Please, select your vehicle type!")

        }

    }

    override fun onItemClick(isModel: Boolean, isFuel: Boolean, isTransmission: Boolean, make: String, model: String, fuelType: String, transmission: String) {

        when {

            isModel -> {

                this.make = make
                setVisibilityAndToolbarText(View.VISIBLE, getString(R.string.toolbar_text_4))

            }

            isFuel -> {

                this.model = model
                setVisibilityAndToolbarText(View.VISIBLE, getString(R.string.toolbar_text_5))

            }

            isTransmission -> {

                this.fuelType = fuelType
                setVisibilityAndToolbarText(View.VISIBLE, getString(R.string.toolbar_text_6))

            }

            else -> {

                val vehicleEntity = VehicleEntity(
                    this.fuelType,
                    this.make,
                    this.model,
                    transmission,
                    vehicleNumber
                )
                vehicleViewModel.insertVehicleEntity(vehicleEntity).observe(this, { isAdded ->

                    if (isAdded == true) {

                        showToast("Vehicle Added Successfully!")
                        startActivity(Intent(this@MainActivity, VehicleListActivity::class.java))
                        finishAffinity()

                    }

                })

            }

        }

    }

    override fun onNext(vehicleNumber: String) {

        if (vehicleNumber == "") {

            showToast("Please, enter a vehicle number!")

        } else {

            if (isRegistrationNoValid(vehicleNumber)) {

                this.vehicleNumber = vehicleNumber
                setVisibilityAndToolbarText(View.VISIBLE, getString(R.string.toolbar_text_2))
                loadTheFragmentWithSlideLeftCustomAnimation(vehicleTypeFragment)

            } else {

                showToast("Please, enter a valid vehicle number!")

            }

        }

    }
}