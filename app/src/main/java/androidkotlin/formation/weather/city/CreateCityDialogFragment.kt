package androidkotlin.formation.weather.city

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidkotlin.formation.weather.R
import androidx.fragment.app.DialogFragment

class CreateCityDialogFragment: DialogFragment() {

    interface CreateCityDialogListener {
        fun onDialogPositiveClick(cityName: String)
        fun onDialogNegativeClick()
    }

    var listener: CreateCityDialogListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context)

        val input = EditText(context)
        with(input) {
            inputType = InputType.TYPE_CLASS_TEXT
            hint = context.getString(R.string.createcity_hint)
        }

        builder.setTitle(getString(R.string.creatcity_title))
            .setView(input)
            .setPositiveButton(getString(R.string.createcity_positive)) { _, _ -> listener?.onDialogPositiveClick(input.text.toString()) }
            .setNegativeButton(getString(R.string.createlist_cancel)) { _, _ -> listener?.onDialogNegativeClick() }


        return builder.create()
    }
}