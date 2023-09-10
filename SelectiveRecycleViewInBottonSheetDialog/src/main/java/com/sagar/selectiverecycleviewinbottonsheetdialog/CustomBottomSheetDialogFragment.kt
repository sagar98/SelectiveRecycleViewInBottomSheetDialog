package com.sagar.selectiverecycleviewinbottonsheetdialog

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sagar.selectiverecycleviewinbottonsheetdialog.adapter.BottomsheetAdapter
import com.sagar.selectiverecycleviewinbottonsheetdialog.databinding.BottomsheetdialogLayout2Binding
import com.sagar.selectiverecycleviewinbottonsheetdialog.interfaces.CustomBottomSheetDialogInterface
import com.sagar.selectiverecycleviewinbottonsheetdialog.model.SelectionListObject


class CustomBottomSheetDialogFragment(
    listenerContext: CustomBottomSheetDialogInterface,
    private var title: String,
    selectionList: ArrayList<SelectionListObject>,
    isMultiSelectAllowed: Boolean
) : BottomSheetDialogFragment(), PopupMenu.OnMenuItemClickListener {

    private lateinit var binding: BottomsheetdialogLayout2Binding
    // private lateinit var binding : BottomsheetdialogLayoutBinding

    private lateinit var bottomSheetAdapter: BottomsheetAdapter

    private var selectionList: ArrayList<SelectionListObject> = ArrayList()
    private var tempSelectionList: ArrayList<SelectionListObject> =
        ArrayList() //to save temp selection values

    private var listenerContext: CustomBottomSheetDialogInterface
    private var isMultiSelectAllowed: Boolean = false

    companion object {
        const val TAG = "BottomSheetSelectionFragment"
    }

    init {
        this.listenerContext = listenerContext
        this.selectionList = selectionList
        this.tempSelectionList = selectionList
        this.isMultiSelectAllowed = isMultiSelectAllowed
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setStyle(STYLE_NORMAL, R.style.BottomSheetStyle)
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

        val bottomSheetBehavior: BottomSheetBehavior<*> =
            BottomSheetBehavior.from(view.parent as View)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

        bottomSheetAdapter = BottomsheetAdapter(tempSelectionList, isMultiSelectAllowed)

        binding.apply {
            textTitle.text = title
           // btnSelectAll.isVisible = isMultiSelectAllowed
            selectAll.isEnabled = isMultiSelectAllowed

            recyclerView.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = bottomSheetAdapter
            }
        }

        clickListener()

    }

    private fun clickListener() {
        binding.apply {
            /* btnSelectAll.setOnClickListener {
                 if (isMultiSelectAllowed) {
                     for (i in tempSelectionList.indices) {
                         //tempSelectionList[i].isSelected = true
                         tempSelectionList[i].isNewlySelected = true
                     }
                 }
                 bottomSheetAdapter.notifyDataSetChanged()
                 //listenerContext.onCustomBottomSheetSelection(title)
                 //dismiss()
             }

             btClearAll.setOnClickListener {
                 if (isMultiSelectAllowed) {
                     for (i in tempSelectionList.indices) {
                         // tempSelectionList[i].isSelected = false
                         tempSelectionList[i].isNewlySelected = false
                     }
                 } else {
                     for (i in tempSelectionList.indices) {
                         // tempSelectionList[i].isSelected = false
                         tempSelectionList[i].isNewlySelected = false
                     }
                 }

                 bottomSheetAdapter.notifyDataSetChanged()
                 //listenerContext.onCustomBottomSheetSelection(title)
                 //dismiss()
             }*/

            btnApply.setOnClickListener {
                selectionList = tempSelectionList
                for (i in selectionList.indices) {
                    selectionList[i].isSelected = selectionList[i].isNewlySelected
                }
                listenerContext.onCustomBottomSheetSelection(title)
                dismiss()
            }

            btnCancel.setOnClickListener {
                for (i in tempSelectionList.indices) {
                    tempSelectionList[i].isNewlySelected = tempSelectionList[i].isSelected
                }
                dismiss()
            }

            selectAll.setOnClickListener {
                if (isMultiSelectAllowed) {
                    for (i in tempSelectionList.indices) {
                        //tempSelectionList[i].isSelected = true
                        tempSelectionList[i].isNewlySelected = true
                    }
                }
                bottomSheetAdapter.notifyDataSetChanged()
                //listenerContext.onCustomBottomSheetSelection(title)
                //dismiss()
            }

            clearAll.setOnClickListener {
                if (isMultiSelectAllowed) {
                    for (i in tempSelectionList.indices) {
                        // tempSelectionList[i].isSelected = false
                        tempSelectionList[i].isNewlySelected = false
                    }
                } else {
                    for (i in tempSelectionList.indices) {
                        // tempSelectionList[i].isSelected = false
                        tempSelectionList[i].isNewlySelected = false
                    }
                }

                bottomSheetAdapter.notifyDataSetChanged()
                //listenerContext.onCustomBottomSheetSelection(title)
                //dismiss()
            }

           /* options.setOnClickListener {
                showPopupMenuOptions(it)
            }*/

            close.setOnClickListener {
                dismiss()
            }
        }
    }

    private fun showPopupMenuOptions(v: View) {
        PopupMenu(activity, v).apply {
            setOnMenuItemClickListener(this@CustomBottomSheetDialogFragment)
            inflate(R.menu.menu_options)
            show()
        }
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_clear_all -> {
                if (isMultiSelectAllowed) {
                    for (i in tempSelectionList.indices) {
                        // tempSelectionList[i].isSelected = false
                        tempSelectionList[i].isNewlySelected = false
                    }
                } else {
                    for (i in tempSelectionList.indices) {
                        // tempSelectionList[i].isSelected = false
                        tempSelectionList[i].isNewlySelected = false
                    }
                }

                bottomSheetAdapter.notifyDataSetChanged()
                //listenerContext.onCustomBottomSheetSelection(title)
                //dismiss()
                true
            }

            R.id.action_select_all -> {
                if (isMultiSelectAllowed) {
                    for (i in tempSelectionList.indices) {
                        //tempSelectionList[i].isSelected = true
                        tempSelectionList[i].isNewlySelected = true
                    }
                }
                bottomSheetAdapter.notifyDataSetChanged()
                //listenerContext.onCustomBottomSheetSelection(title)
                //dismiss()
                true
            }
            else -> false
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        for (i in tempSelectionList.indices) {
            tempSelectionList[i].isNewlySelected = tempSelectionList[i].isSelected
        }
        super.onDismiss(dialog)
    }

}