package com.example.myalarmmanager

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class TimePickerFragment: DialogFragment(), TimePickerDialog.OnTimeSetListener {
    private var mListener: DialogTimeListener?= null
    interface DialogTimeListener{
        fun onDialogTimeSet(tag: String?, hoursOfDay: Int, minute: Int)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = context as DialogTimeListener?
    }

    override fun onDetach() {
        super.onDetach()
        if (mListener != null){
            mListener = null
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.YEAR)
        val minute = calendar.get(Calendar.MONTH)
        val formatHour24 = true
        return TimePickerDialog(activity, this, hour, minute, formatHour24)
    }

    override fun onTimeSet(view: TimePicker, hoursOfDay: Int, minute: Int) {
        mListener?.onDialogTimeSet(tag, hoursOfDay, minute)
    }
}