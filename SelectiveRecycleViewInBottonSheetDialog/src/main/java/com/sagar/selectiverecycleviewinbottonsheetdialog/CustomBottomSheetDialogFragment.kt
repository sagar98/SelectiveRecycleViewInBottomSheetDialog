package com.sagar.selectiverecycleviewinbottonsheetdialog

import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sagar.selectiverecycleviewinbottonsheetdialog.adapter.BottomsheetAdapter
import com.sagar.selectiverecycleviewinbottonsheetdialog.databinding.BottomsheetdialogLayout2Binding
import com.sagar.selectiverecycleviewinbottonsheetdialog.interfaces.CustomBottomSheetDialogInterface
import com.sagar.selectiverecycleviewinbottonsheetdialog.interfaces.OnFilterResultListener
import com.sagar.selectiverecycleviewinbottonsheetdialog.model.SelectionListObject
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CustomBottomSheetDialogFragment(
    private var listenerContext: CustomBottomSheetDialogInterface,
    private var title: String,
    private var selectionList: ArrayList<SelectionListObject> = ArrayList(),
    private var isMultiSelectAllowed: Boolean = false,
    private var showSearch: Boolean = false,
) : BottomSheetDialogFragment(), OnFilterResultListener {

    private lateinit var binding: BottomsheetdialogLayout2Binding
    private lateinit var bottomSheetAdapter: BottomsheetAdapter
    private var tempSelectionList: ArrayList<SelectionListObject> = ArrayList() //to save temp selection values

    private val mainScope = MainScope()
    private var filterJob: Job? = null

    companion object {
        const val TAG = "BottomSheetSelectionFragment"

        fun safeShow(fragmentManager: FragmentManager, tag: String, fragment: CustomBottomSheetDialogFragment) {
            if (fragment.isAdded) return
            if (fragmentManager.findFragmentByTag(tag) == null) {
                fragment.show(fragmentManager, tag)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tempSelectionList = ArrayList(selectionList)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomsheetdialogLayout2Binding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomSheetBehavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(view.parent as View)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

        bottomSheetAdapter = BottomsheetAdapter(tempSelectionList, isMultiSelectAllowed,this)

        binding.apply {
            textTitle.text = title
            selectAll.isEnabled = isMultiSelectAllowed && selectionList.isNotEmpty()
            clearAll.isEnabled = selectionList.isNotEmpty()
            recyclerView.isVisible = selectionList.isNotEmpty()
            emptyList.isVisible = selectionList.isEmpty()
            searchView.isVisible = showSearch && selectionList.isNotEmpty()

            recyclerView.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = bottomSheetAdapter
            }
        }

        setupClickListeners()
        setupSearchWatcher()
    }

    private fun setupClickListeners() {
        binding.apply {
            btnApply.setOnClickListener {
                // Apply temp selection state to the original list
                selectionList.forEachIndexed { i, item ->
                    item.isSelected = tempSelectionList.getOrNull(i)?.isNewlySelected == true
                }
                listenerContext.onCustomBottomSheetSelection(title)
                dismiss()
            }

            btnCancel.setOnClickListener {
                // Reset temp state to original selection
                tempSelectionList.forEachIndexed { i, item ->
                    item.isNewlySelected = selectionList.getOrNull(i)?.isSelected == true
                }
                binding.etSearch.setText("")
                dismiss()
            }

            selectAll.setOnClickListener {
                if (isMultiSelectAllowed) {
                    tempSelectionList.forEach { it.isNewlySelected = true }
                    bottomSheetAdapter.notifyDataSetChanged()
                }
            }

            clearAll.setOnClickListener {
                tempSelectionList.forEach { it.isNewlySelected = false }
                bottomSheetAdapter.notifyDataSetChanged()
            }

            close.setOnClickListener { dismiss() }
        }
    }

    private fun setupSearchWatcher() {
        binding.etSearch.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterJob?.cancel()
                filterJob = mainScope.launch {
                    delay(200)
                    bottomSheetAdapter.filter(s.toString())
                }

                //bottomSheetAdapter.filter(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    override fun onFilterResult(isListEmpty: Boolean) {
        binding.emptySearchList.isVisible = isListEmpty
        binding.recyclerView.isVisible = !isListEmpty
    }

    override fun onDismiss(dialog: DialogInterface) {
        // Reset temp selection states to original
        tempSelectionList.forEachIndexed { i, item ->
            item.isNewlySelected = selectionList.getOrNull(i)?.isSelected == true
        }
        super.onDismiss(dialog)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        filterJob?.cancel()
        mainScope.cancel()
    }

}