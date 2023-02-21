package com.sagar.selectiverecycleviewinbottonsheetdialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.sagar.selectiverecycleviewinbottonsheetdialog.adapter.BottomsheetAdapter
import com.sagar.selectiverecycleviewinbottonsheetdialog.interfaces.CustomBottomSheetDialogInterface
import com.sagar.selectiverecycleviewinbottonsheetdialog.model.SelectionListObject

class CustomBottomSheetDialogFragment(
    listenerContext: CustomBottomSheetDialogInterface, private var title: String,
    selectionList: ArrayList<SelectionListObject>, isMultiSelectAllowed: Boolean
) : BottomSheetDialogFragment(), View.OnClickListener {

    private var bottomsheetTitle: TextView? = null

    private lateinit var btnApply: MaterialButton
    private lateinit var btnClose: MaterialButton
    private lateinit var btnClearAll: MaterialButton
    private lateinit var btnSelectAll: MaterialButton
    private lateinit var rvBottomSheet: RecyclerView
    private lateinit var bottomsheetAdapter: BottomsheetAdapter
    private var selectionList: ArrayList<SelectionListObject> = ArrayList()
    private var listenerContext: CustomBottomSheetDialogInterface
    private var isMultiSelectAllowed: Boolean = false

    companion object{
        const val TAG = "BottomSheetFragment"
    }

    init {
        this.listenerContext = listenerContext
        this.selectionList = selectionList
        this.isMultiSelectAllowed = isMultiSelectAllowed
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottomsheetdialog_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomSheetBehavior: BottomSheetBehavior<*> =
            BottomSheetBehavior.from(view.parent as View)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

        btnApply = view.findViewById(R.id.btn_apply)!!
        btnClose = view.findViewById(R.id.btn_cancel)!!
        btnClearAll = view.findViewById(R.id.bt_clear_all)!!
        btnSelectAll = view.findViewById(R.id.btn_select_all)!!
        btnApply.setOnClickListener(this)
        btnClose.setOnClickListener(this)
        btnClearAll.setOnClickListener(this)
        btnSelectAll.setOnClickListener(this)
        bottomsheetTitle = view.findViewById(R.id.text_title)
        bottomsheetTitle!!.text = title

        btnSelectAll.isVisible = isMultiSelectAllowed

        rvBottomSheet = view.findViewById(R.id.rv_bottomsheet_dialog)!!
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)
        rvBottomSheet.layoutManager = layoutManager
        bottomsheetAdapter = BottomsheetAdapter(selectionList, isMultiSelectAllowed)
        rvBottomSheet.adapter = bottomsheetAdapter

    }

    override fun onDismiss(dialog: DialogInterface) {
        for (i in selectionList.indices) {
            selectionList[i].isNewlySelected = selectionList[i].isSelected
        }
        super.onDismiss(dialog)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_apply -> {
                for (i in selectionList.indices) {
                    selectionList[i].isSelected = selectionList[i].isNewlySelected
                }
                listenerContext.onCustomBottomSheetSelection(title)
                dismiss()
            }

            R.id.btn_cancel -> {
                for (i in selectionList.indices) {
                    selectionList[i].isNewlySelected = selectionList[i].isSelected
                }
                dismiss()
            }

            R.id.bt_clear_all -> {
                if (isMultiSelectAllowed) {
                    for (i in selectionList.indices) {
                        selectionList[i].isSelected = false
                        selectionList[i].isNewlySelected = false
                    }
                } else {
                    for (i in selectionList.indices) {
                        selectionList[i].isSelected = false
                        selectionList[i].isNewlySelected = false
                    }
                }

                bottomsheetAdapter.notifyDataSetChanged()
                listenerContext.onCustomBottomSheetSelection(title)
                dismiss()
            }

            R.id.btn_select_all -> {
                if (isMultiSelectAllowed) {
                    for (i in selectionList.indices) {
                        selectionList[i].isSelected = true
                        selectionList[i].isNewlySelected = true
                    }
                }
                bottomsheetAdapter.notifyDataSetChanged()
                listenerContext.onCustomBottomSheetSelection(title)
                //dismiss()
            }
        }
    }
}