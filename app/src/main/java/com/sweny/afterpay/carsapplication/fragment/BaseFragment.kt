package com.sweny.afterpay.carsapplication.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sweny.afterpay.carsapplication.R
import com.sweny.afterpay.carsapplication.utils.showAlertDialog
import com.sweny.afterpay.carsapplication.viewmodels.BaseViewModel

/**
 * Base fragment
 *
 * Base class used to handle all UI functionality common
 * across fragments in the Cars journey
 *
 * It works in conjunction with BaseViewModel
 *
 * Functionality supported
 *
 * 1. Presentation of Api errors
 *
 */
abstract class BaseFragment : Fragment() {

    abstract val baseViewModel: BaseViewModel
    private var mLoadingSpinner: AlertDialog? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        baseViewModel.loadingSpinner.observe(viewLifecycleOwner)
        { isLoading ->
            mLoadingSpinner?.dismiss()
            if (isLoading) {
                    mLoadingSpinner = showLoadingSpinner()
            }
        }

        baseViewModel.apiError.observe(viewLifecycleOwner)
        {
            showAlertDialog(
                requireContext(),
                getString(R.string.error),
                it,
                getString(R.string.ok_got_it)
            )
        }

    }

    /**
     * Display Android ProgressBar inside alert dialog
     */
    private fun showLoadingSpinner() =
        MaterialAlertDialogBuilder(requireContext(), R.style.BlurredDialogStyle)
            .setView(layoutInflater.inflate(R.layout.loading_spinner, null))
            .setCancelable(false)
            .setBackground(ColorDrawable(Color.TRANSPARENT))
            .show()

}