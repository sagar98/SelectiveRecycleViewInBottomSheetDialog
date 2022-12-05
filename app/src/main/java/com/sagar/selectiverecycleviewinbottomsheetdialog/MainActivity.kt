package com.sagar.selectiverecycleviewinbottomsheetdialog

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sagar.selectiverecycleviewinbottomsheetdialog.databinding.ActivityMainBinding
import com.sagar.selectiverecycleviewinbottonsheetdialog.CustomBottomSheetDialogFragment
import com.sagar.selectiverecycleviewinbottonsheetdialog.interfaces.CustomBottomSheetDialogInterface
import com.sagar.selectiverecycleviewinbottonsheetdialog.model.SelectionListObject

class MainActivity : AppCompatActivity(), CustomBottomSheetDialogInterface {

    private lateinit var binding: ActivityMainBinding
    private var roleList: ArrayList<SelectionListObject> = ArrayList()
    private var cityList: ArrayList<SelectionListObject> = ArrayList()
    private lateinit var selectedRole: String
    private lateinit var selectedRoleId: String
    private lateinit var selectedCities: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val roleObject1 = SelectionListObject("1", "Developer", false)
        val roleObject2 = SelectionListObject("4", "Project Manager", false)
        val roleObject3 = SelectionListObject("2", "Team Lead", false)
        val roleObject4 = SelectionListObject("7", "Analyst", false)

        roleList.add(roleObject1)
        roleList.add(roleObject2)
        roleList.add(roleObject3)
        roleList.add(roleObject4)

        cityList.addAll(
            arrayOf(
                SelectionListObject("1", "Pune", false),
                SelectionListObject("2", "Bangalore", false),
                SelectionListObject("3", "Mumbai", false),
                SelectionListObject("4", "Chennai", false),
                SelectionListObject("5", "Kolkata", false),
                SelectionListObject("6", "Nashik", false),
                SelectionListObject("7", "Noida", false),
                SelectionListObject("8", "City2", false),
                SelectionListObject("9", "City3", false),
                SelectionListObject("10", "City4", false),
                SelectionListObject("11", "City5", false),
                SelectionListObject("12", "City6", false),
                SelectionListObject("13", "City7", false),
                SelectionListObject("14", "City8", false),
                SelectionListObject("15", "City5", false),
                SelectionListObject("16", "City6", false),
            )
        )

        binding.btnRole.setOnClickListener {
            val rolesBottomDialogFragment = CustomBottomSheetDialogFragment(
                this, "Select Role",
                roleList,
                false
            )
            rolesBottomDialogFragment.show(supportFragmentManager,
                CustomBottomSheetDialogFragment.TAG
            )
        }

        binding.btnCities.setOnClickListener {
            val cityBottomDialogFragment = CustomBottomSheetDialogFragment(
                this, "Select Cities",
                cityList,
                true
            )
            cityBottomDialogFragment.show(supportFragmentManager,
                CustomBottomSheetDialogFragment.TAG
            )
        }
    }

    override fun onCustomBottomSheetSelection(type: String) {
        when (type) {
            "Select Role" -> {
                selectedRole = ""
                selectedRoleId = ""
                for (obj in roleList) {
                    if (obj.isSelected) {
                        selectedRole = obj.value
                        selectedRoleId = obj.id
                        break
                    }
                }
                binding.tvSelectedRole.text = selectedRole
            }
            "Select Cities" -> {
                selectedCities = ""
                for (obj in cityList) {
                    if (obj.isSelected) {
                        selectedCities = if (selectedCities != "") {
                            selectedCities + "," + obj.value
                        } else {
                            obj.value
                        }
                    }
                }
                binding.tvSelectedCities.text = selectedCities
            }
        }
    }
}