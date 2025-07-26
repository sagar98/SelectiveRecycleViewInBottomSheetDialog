package com.sagar.selectiverecycleviewinbottomsheetdialog

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.sagar.selectiverecycleviewinbottomsheetdialog.databinding.ActivityMainBinding
import com.sagar.selectiverecycleviewinbottonsheetdialog.CustomBottomSheetDialogFragment
import com.sagar.selectiverecycleviewinbottonsheetdialog.CustomBottomSheetDialogLambdaFragment
import com.sagar.selectiverecycleviewinbottonsheetdialog.interfaces.CustomBottomSheetDialogInterface
import com.sagar.selectiverecycleviewinbottonsheetdialog.model.SelectionListObject

class MainActivity : AppCompatActivity(), CustomBottomSheetDialogInterface {

    private lateinit var binding: ActivityMainBinding
    private var roleList: ArrayList<SelectionListObject> = DataList.roleList
    private var cityList: ArrayList<SelectionListObject> = DataList.cityList
    private var emptyList: ArrayList<SelectionListObject> = ArrayList()
    private lateinit var selectedRole: String
    private lateinit var selectedRoleId: String
    private lateinit var selectedCities: String

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val systemBarsInsets =
                insets.getInsets(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout() or WindowInsetsCompat.Type.ime())
            view.updatePadding(
                left = systemBarsInsets.left,
                top = systemBarsInsets.top,
                right = systemBarsInsets.right,
                bottom = systemBarsInsets.bottom
            )
            insets
        }

        //todo update description about the library text inside layout here

        binding.btnRole.setOnClickListener {
            val rolesBottomDialogFragment = CustomBottomSheetDialogLambdaFragment(
                "Select Role",
                roleList,
                false,
                showSearch = true
            ) {
                selectedRole = ""
                selectedRoleId = ""
                for (obj in roleList) {
                    if (obj.isSelected) {
                        selectedRole = obj.value
                        selectedRoleId = obj.id
                        break
                    }
                }
                if (selectedRole.isNotBlank()) binding.tvSelectedRole.text = selectedRole
                else binding.tvSelectedRole.text = "No Role Selected"
            }

            CustomBottomSheetDialogLambdaFragment.safeShow(
                supportFragmentManager,
                CustomBottomSheetDialogFragment.TAG,
                rolesBottomDialogFragment
            )

            /**Implementation using CustomBottomSheetDialogFragment and listening to callback*/
            /*val rolesBottomDialogFragment = CustomBottomSheetDialogFragment(
                this, "Select Role",
                roleList,
                false,
                showSearch = true
            )
            CustomBottomSheetDialogFragment.safeShow(
                supportFragmentManager,
                CustomBottomSheetDialogFragment.TAG,
                rolesBottomDialogFragment
            )*/

        }

        binding.btnCities.setOnClickListener {
            val cityBottomDialogFragment =
                CustomBottomSheetDialogLambdaFragment(
                    "Select Cities", cityList,
                    isMultiSelectAllowed = true,
                    showSearch = true
                ) {
                    selectedCities = ""
                    for (obj in cityList) {
                        if (obj.isSelected) {
                            selectedCities = if (selectedCities != "") {
                                selectedCities + ", " + obj.value
                            } else {
                                obj.value
                            }
                        }
                    }

                    if (selectedCities.isNotBlank()) binding.tvSelectedCities.text = selectedCities
                    else binding.tvSelectedCities.text = "No City Selected"
                }

            CustomBottomSheetDialogLambdaFragment.safeShow(
                supportFragmentManager,
                CustomBottomSheetDialogFragment.TAG,
                cityBottomDialogFragment
            )

            /**Implementation using CustomBottomSheetDialogFragment and listening to callback*/
           /* val cityBottomDialogFragment = CustomBottomSheetDialogFragment(
                this, "Select Cities",
                cityList,
                true,
                showSearch = true
            )

            CustomBottomSheetDialogFragment.safeShow(
                supportFragmentManager,
                CustomBottomSheetDialogFragment.TAG,
                cityBottomDialogFragment
            )*/

        }

        binding.btnNoData.setOnClickListener {
            val cityBottomDialogFragment =
                CustomBottomSheetDialogLambdaFragment("List Title", emptyList, true) {}

            CustomBottomSheetDialogLambdaFragment.safeShow(
                supportFragmentManager,
                CustomBottomSheetDialogFragment.TAG,
                cityBottomDialogFragment
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
                            selectedCities + ", " + obj.value
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