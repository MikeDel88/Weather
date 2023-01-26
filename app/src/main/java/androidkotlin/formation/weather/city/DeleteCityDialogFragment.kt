package androidkotlin.formation.weather.city

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidkotlin.formation.weather.R
import androidx.fragment.app.DialogFragment

class DeleteCityDialogFragment: DialogFragment() {

    interface DeleteCityDialogListener {
        fun onDialogPositiveClick()
        fun onDialogNegativeClick()
    }

    companion object {
        const val EXTRA_CITY_NAME = "androidkotlin.formation.weather.extras.EXTRA_CITY_NAME"

        fun newInstance(cityName: String): DeleteCityDialogFragment{
            val fragment = DeleteCityDialogFragment()
            fragment.arguments = Bundle().apply {
                putString(EXTRA_CITY_NAME, cityName)
            }
            return fragment
        }
    }

    private lateinit var cityName: String
    var listener: DeleteCityDialogListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cityName = requireArguments().getString(EXTRA_CITY_NAME).toString()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context)

        builder.setTitle(getString(R.string.deletecity_title, cityName))
            .setPositiveButton(getString(R.string.deletecity_positive))
            { _, _ -> listener?.onDialogPositiveClick() }
            .setNegativeButton(getString(R.string.deletecity_negative))
            { _, _ -> listener?.onDialogNegativeClick() }

        return builder.create()
    }
}