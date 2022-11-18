package com.sagar.selectiverecycleviewinbottonsheetdialog.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sagar.selectiverecycleviewinbottonsheetdialog.R
import com.sagar.selectiverecycleviewinbottonsheetdialog.dataclass.SelectionListObject

class BottomsheetAdapter(
    selectionList: ArrayList<SelectionListObject>,
    isMultiSelectAllowed: Boolean
) : RecyclerView.Adapter<BottomsheetAdapter.ViewHolder>() {

    private val selectionList: ArrayList<SelectionListObject>
    private val isMultiSelectAllowed: Boolean

    init {
        this.selectionList = selectionList
        this.isMultiSelectAllowed = isMultiSelectAllowed
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(
            R.layout.bottomsheet_recycleview_item_layout, parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtTitle.text = selectionList[position].value
        if (selectionList[position].isSelected) {
            holder.btnSelection.setBackgroundResource(R.drawable.ic_selected_box)
        } else {
            holder.btnSelection.setBackgroundResource(R.drawable.ic_unselected_box)
        }
    }

    override fun getItemCount(): Int {
        return selectionList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtTitle: TextView
        var btnSelection: ImageButton

        init {
            this.setIsRecyclable(false)
            txtTitle = itemView.findViewById(R.id.tv_item_title)
            btnSelection = itemView.findViewById(R.id.bt_item_select)
            btnSelection.setOnClickListener {
                if (isMultiSelectAllowed) {
                    selectionList[adapterPosition].isSelected =
                        !selectionList[adapterPosition].isSelected
                } else {
                    for (i in selectionList.indices) {
                        if (selectionList[i].isSelected) {
                            selectionList[i].isSelected = false
                            break
                        }
                    }
                    selectionList[adapterPosition].isSelected = true
                }
                notifyDataSetChanged()
            }
        }
    }

}