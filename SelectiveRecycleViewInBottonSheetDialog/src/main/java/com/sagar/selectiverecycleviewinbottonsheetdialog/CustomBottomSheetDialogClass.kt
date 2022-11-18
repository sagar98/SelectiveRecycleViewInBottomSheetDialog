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
import com.sagar.selectiverecycleviewinbottonsheetdialog.adapter.BottomsheetAdapter
import com.sagar.selectiverecycleviewinbottonsheetdialog.dataclass.SelectionListObject
import com.sagar.selectiverecycleviewinbottonsheetdialog.interfaces.CustomBottomSheetDialogInterface

class CustomBottomSheetDialogClass(
    activity: Activity, listenerContext: CustomBottomSheetDialogInterface,
    private var title: String, selectionList: ArrayList<SelectionListObject>,
    isMultiSelectAllowed: Boolean
) :
    BottomSheetDialog(activity), View.OnClickListener {

    private var bottomsheetTitle: TextView? = null
    private lateinit var imgClose: ImageView
    private lateinit var btnApply: Button
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
        btnApply.setOnClickListener(this)
        imgClose = findViewById(R.id.image_close)!!
        imgClose.setOnClickListener(this)
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
                listenerContext.onCustomBottomSheetSelection(title)
                dismiss()
            }
            R.id.image_close -> dismiss()
        }
    }
}