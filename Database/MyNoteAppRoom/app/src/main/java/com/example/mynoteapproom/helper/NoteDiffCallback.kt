package com.example.mynoteapproom.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.mynoteapproom.database.Note

class NoteDiffCallback(private val mOldNoteList: List<Note>, private val mNewNoteList: List<Note>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldNoteList.size
    }

    override fun getNewListSize(): Int {
        return mNewNoteList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = mOldNoteList[oldItemPosition]
        val newItem = mNewNoteList[newItemPosition]
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = mOldNoteList[oldItemPosition]
        val newItem = mNewNoteList[newItemPosition]
        return oldItem.title == newItem.title
                && oldItem.description == newItem.description
    }
}