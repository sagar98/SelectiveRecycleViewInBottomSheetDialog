package com.sagar.selectiverecycleviewinbottonsheetdialog.model

data class SelectionListObject(
    val id: String,
    val value: String,
    var isSelected: Boolean,
    var isNewlySelected: Boolean = isSelected
)
