package com.sagar.selectiverecycleviewinbottonsheetdialog

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.sagar.selectiverecycleviewinbottonsheetdialog.adapter.BottomsheetAdapter
import com.sagar.selectiverecycleviewinbottonsheetdialog.model.SelectionListObject
import com.sagar.selectiverecycleviewinbottonsheetdialog.interfaces.CustomBottomSheetDialogInterface

class CustomBottomSheetDialogClass(
    activity: Activity, listenerContext: CustomBottomSheetDialogInterface,
    private var title: String, selectionList: ArrayList<SelectionListObject>,
    isMultiSelectAllowed: Boolean
) :
    BottomSheetDialog(activity), View.OnClickListener {

    private var bottomsheetTitle: TextView? = null

    // private lateinit var imgClose: ImageView
    private lateinit var btnApply: MaterialButton
    private lateinit var btnClose: MaterialButton
    private lateinit var btnClearAll: MaterialButton
    private lateinit var rvBottomSheet: RecyclerView
    private lateinit var bottomsheetAdapter: BottomsheetAdapter
    private var selectionList: ArrayList<SelectionListObject> = ArrayList()
    private var listenerContext: CustomBottomSheetDialogInterface
    private var isMultiSelectAllowed: Boolean = false

    init {
        this.listenerContext = listenerContext
        this.selectionList = selectionList
        this.isMultiSelectAllowed = isMultiSelectAllowed
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bottomSheetView: View = layoutInflater.inflate(R.layout.bottomsheetdialog_layout, null)
        setContentView(bottomSheetView)
        val bottomSheetBehavior: BottomSheetBehavior<*> =
            BottomSheetBehavior.from(bottomSheetView.parent as View)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

        btnApply = findViewById(R.id.btn_apply)!!
        btnClose = findViewById(R.id.btn_cancel)!!
        btnClearAll = findViewById(R.id.bt_clear_all)!!
        btnApply.setOnClickListener(this)
        btnClose.setOnClickListener(this)
        btnClearAll.setOnClickListener(this)
        bottomsheetTitle = findViewById(R.id.text_title)
        bottomsheetTitle!!.text = title

        rvBottomSheet = findViewById(R.id.rv_bottomsheet_dialog)!!
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(ownerActivity)
        rvBottomSheet.layoutManager = layoutManager
        bottomsheetAdapter = BottomsheetAdapter(selectionList, isMultiSelectAllowed)
        rvBottomSheet.adapter = bottomsheetAdapter
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
                        if (selectionList[i].isSelected) {
                            selectionList[i].isSelected = false
                            selectionList[i].isNewlySelected = false
                            break
                        }
                    }
                }

                bottomsheetAdapter.notifyDataSetChanged()
                listenerContext.onCustomBottomSheetSelection(title)
                dismiss()
            }
        }
    }
}