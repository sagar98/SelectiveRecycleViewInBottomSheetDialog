package com.sagar.selectiverecycleviewinbottonsheetdialog.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.radiobutton.MaterialRadioButton
import com.sagar.selectiverecycleviewinbottonsheetdialog.R
import com.sagar.selectiverecycleviewinbottonsheetdialog.model.SelectionListObject

class BottomsheetAdapter(
    selectionList: ArrayList<SelectionListObject>,
    isMultiSelectAllowed: Boolean
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val selectionList: ArrayList<SelectionListObject>
    private val isMultiSelectAllowed: Boolean

    init {
        this.selectionList = selectionList
        this.isMultiSelectAllowed = isMultiSelectAllowed
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        return if (isMultiSelectAllowed) {
            val view = layoutInflater.inflate(R.layout.list_item_multi_select, parent, false)
            ViewHolderMultiSelect(view)
        } else {
            val view = layoutInflater.inflate(R.layout.list_item_single_select, parent, false)
            ViewHolderSingleSelect(view)
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (isMultiSelectAllowed) {
            val holder = viewHolder as ViewHolderMultiSelect
            holder.checkBox.text = selectionList[position].value
            holder.checkBox.isChecked = selectionList[position].isSelected
        } else {
            val holder = viewHolder as ViewHolderSingleSelect
            holder.radioButton.text = selectionList[position].value
            holder.radioButton.isChecked = selectionList[position].isNewlySelected
        }
    }

    override fun getItemCount(): Int {
        return selectionList.size
    }

    inner class ViewHolderMultiSelect(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var checkBox: MaterialCheckBox

        init {
            this.setIsRecyclable(false)

            checkBox = itemView.findViewById(R.id.checkBox)

            checkBox.setOnClickListener {
                selectionList[adapterPosition].isNewlySelected =
                    !selectionList[adapterPosition].isNewlySelected
            }
        }
    }

    inner class ViewHolderSingleSelect(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var radioButton: MaterialRadioButton

        init {
            this.setIsRecyclable(false)

            radioButton = itemView.findViewById(R.id.radioButton)

            radioButton.setOnClickListener {
                for (i in selectionList.indices) {
                    if (selectionList[i].isNewlySelected) {
                        selectionList[i].isNewlySelected = false
                        break
                    }
                }
                selectionList[adapterPosition].isNewlySelected = true
                notifyDataSetChanged()
            }
        }
    }


}