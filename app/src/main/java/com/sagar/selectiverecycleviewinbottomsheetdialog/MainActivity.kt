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
	private var roleList: ArrayList<SelectionListObject> = ArrayList()
	private var cityList: ArrayList<SelectionListObject> = ArrayList()
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

		roleList = arrayListOf(
			SelectionListObject("1", "Developer", false),
			SelectionListObject("2", "Team Lead", false),
			SelectionListObject("3", "Senior Manager", false),
			SelectionListObject("4", "Project Manager", false),
			SelectionListObject("5", "CTO", false),
			SelectionListObject("6", "CEO", false),
			SelectionListObject("7", "Business Analyst", false),
			SelectionListObject("8", "Quality Analyst", false),
		)

		cityList = arrayListOf(
			SelectionListObject("1", "Pune", false),
			SelectionListObject("2", "Bangalore", false),
			SelectionListObject("3", "Mumbai", false),
			SelectionListObject("4", "Chennai", false),
			SelectionListObject("5", "Kolkata", false),
			SelectionListObject("6", "Nashik", false),
			SelectionListObject("7", "Noida", false),
			SelectionListObject("8", "Jaipur", false),
			SelectionListObject("9", "Lucknow", false),
			SelectionListObject("10", "Ahmedabad", false),
			SelectionListObject("11", "Hyderabad", false),
			SelectionListObject("12", "Delhi", false),
			SelectionListObject("13", "Kanpur", false),
			SelectionListObject("14", "Patna", false),
			SelectionListObject("15", "Surat", false),
			SelectionListObject("16", "Visakhapatnam", false),
		)

		binding.btnRole.setOnClickListener {
			val rolesBottomDialogFragment = CustomBottomSheetDialogLambdaFragment(
				"Select Role",
				roleList,
				false,
                showSearch = true
			){
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
			/*val rolesBottomDialogFragment = CustomBottomSheetDialogFragment(
				this, "Select Role",
				roleList,
				false,
				showSearch = true
			)*/

			rolesBottomDialogFragment.show(
				supportFragmentManager,
				CustomBottomSheetDialogFragment.TAG
			)
		}

		binding.btnCities.setOnClickListener {
			val cityBottomDialogFragment =
				CustomBottomSheetDialogLambdaFragment("Select Cities", cityList,
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
			/*val cityBottomDialogFragment = CustomBottomSheetDialogFragment(
				this, "Select Cities",
				cityList,
				true,
				showSearch = true
			)*/
			cityBottomDialogFragment.show(
				supportFragmentManager,
				CustomBottomSheetDialogFragment.TAG
			)
		}

		binding.btnNoData.setOnClickListener {
			val cityBottomDialogFragment =
				CustomBottomSheetDialogLambdaFragment("List Title", emptyList, true) {

				}
			cityBottomDialogFragment.show(
				supportFragmentManager,
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