package com.sagar.selectiverecycleviewinbottomsheetdialog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import com.sagar.selectiverecycleviewinbottomsheetdialog.databinding.ActivityMainBinding
import com.sagar.selectiverecycleviewinbottonsheetdialog.CustomBottomSheetDialogClass
import com.sagar.selectiverecycleviewinbottonsheetdialog.dataclass.SelectionListObject
import com.sagar.selectiverecycleviewinbottonsheetdialog.interfaces.CustomBottomSheetDialogInterface

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

        val cityObject1 = SelectionListObject("1", "Pune", false)
        val cityObject2 = SelectionListObject("3", "Bangalore", false)
        val cityObject3 = SelectionListObject("6", "Mumbai", false)
        val cityObject4 = SelectionListObject("9", "Chennai", false)

        cityList.add(cityObject1)
        cityList.add(cityObject2)
        cityList.add(cityObject3)
        cityList.add(cityObject4)

        binding.btnRole.setOnClickListener{
            val rolesBottomSheetDialog = CustomBottomSheetDialogClass(this,
                this, "Select Role", roleList, false)
            rolesBottomSheetDialog.show()
            rolesBottomSheetDialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
        }

        binding.btnCities.setOnClickListener{
            val cityBottomSheetDialog = CustomBottomSheetDialogClass(this,
                this, "Select Cities", cityList, true)
            cityBottomSheetDialog.show()
            cityBottomSheetDialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
        }
    }

    override fun onCustomBottomSheetSelection(type: String) {
        when (type) {
            "Select Role" -> {
                selectedRole = ""
                selectedRoleId = ""
                for (obj in roleList) {
                    if(obj.isSelected) {
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
                    if(obj.isSelected) {
                        selectedCities = if(selectedCities!="") {
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