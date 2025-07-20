package com.sagar.selectiverecycleviewinbottonsheetdialog.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.radiobutton.MaterialRadioButton
import com.sagar.selectiverecycleviewinbottonsheetdialog.R
import com.sagar.selectiverecycleviewinbottonsheetdialog.interfaces.OnFilterResultListener
import com.sagar.selectiverecycleviewinbottonsheetdialog.model.SelectionListObject

class BottomsheetAdapter(
    private val selectionList: ArrayList<SelectionListObject>,
    private val isMultiSelectAllowed: Boolean,
    private val filterResultListener: OnFilterResultListener? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var filteredList: ArrayList<SelectionListObject> = ArrayList(selectionList)
    private var filterQuery: String = ""

    // Optional: For performance, stable IDs (causing issue)
    /*init {
        setHasStableIds(true)
    }*/

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
            (viewHolder as ViewHolderMultiSelect).bind(filteredList[position])
        } else {
            (viewHolder as ViewHolderSingleSelect).bind(filteredList[position])
        }
    }

    override fun getItemCount(): Int = filteredList.size

    fun filter(query: String) {
        filterQuery = query

        val lowerQuery = query.lowercase()
        filteredList = if (lowerQuery.isEmpty()) {
            ArrayList(selectionList)
        } else {
            ArrayList(selectionList.filter { it.value.lowercase().contains(lowerQuery) })
        }
        notifyDataSetChanged()
        filterResultListener?.onFilterResult(filteredList.isEmpty())
    }

    inner class ViewHolderMultiSelect(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val checkBox: MaterialCheckBox = itemView.findViewById(R.id.checkBox)

        init {
            this.setIsRecyclable(false)

            checkBox.setOnClickListener {
                val pos = bindingAdapterPosition
                if (pos == RecyclerView.NO_POSITION) return@setOnClickListener

                val item = filteredList[pos]

                selectionList.find { it == item }?.let {
                    it.isNewlySelected = !it.isNewlySelected
                }
            }
        }

        fun bind(item: SelectionListObject){
            checkBox.text = item.value
            checkBox.isChecked = item.isNewlySelected
        }
    }

    inner class ViewHolderSingleSelect(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val radioButton: MaterialRadioButton = itemView.findViewById(R.id.radioButton)

        init {
            this.setIsRecyclable(false)

            radioButton.setOnClickListener {
                val position = bindingAdapterPosition
                if (position == RecyclerView.NO_POSITION) return@setOnClickListener

                // Find currently selected and newly selected items
                val newlySelectedItem = filteredList[position]
                val previouslySelectedIndex = selectionList.indexOfFirst { it.isNewlySelected }
                val newlySelectedIndex = selectionList.indexOfFirst { it == newlySelectedItem }

                // Deselect previous
                if (previouslySelectedIndex != -1) {
                    selectionList[previouslySelectedIndex].isNewlySelected = false
                }

                // Select new
                if (newlySelectedIndex != -1) {
                    selectionList[newlySelectedIndex].isNewlySelected = true
                }
                // Re-filter to update filteredList
                filter(filterQuery)

                // Notify affected items efficiently
                if (previouslySelectedIndex != -1) {
                    val prevFilteredIdx = filteredList.indexOf(selectionList[previouslySelectedIndex])
                    if (prevFilteredIdx != -1) notifyItemChanged(prevFilteredIdx)
                }
                val currFilteredIdx = filteredList.indexOf(selectionList[newlySelectedIndex])
                if (currFilteredIdx != -1) notifyItemChanged(currFilteredIdx)

            }
        }

        fun bind(item: SelectionListObject){
            radioButton.text = item.value
            radioButton.isChecked = item.isNewlySelected
        }

    }


}