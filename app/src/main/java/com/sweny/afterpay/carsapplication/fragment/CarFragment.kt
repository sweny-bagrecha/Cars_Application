package com.sweny.afterpay.carsapplication.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sweny.afterpay.carsapplication.databinding.FragmentCarsBinding
import com.sweny.afterpay.carsapplication.model.CarData
import com.sweny.afterpay.carsapplication.viewmodels.CarsViewModel
import com.sweny.afterpay.carsapplication.viewmodels.BaseViewModel
import androidx.lifecycle.Observer
import com.sweny.afterpay.carsapplication.R
import com.sweny.afterpay.carsapplication.adapters.CarsAdapter
import com.sweny.afterpay.carsapplication.utils.showAlertDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CarFragment : BaseFragment() {
    private val viewModel: CarsViewModel by viewModels()

    // baseViewModel is being observed by base fragment to handle errors
    override val baseViewModel: BaseViewModel
        get() = viewModel

    private lateinit var binding: FragmentCarsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCarsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //observer
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.CarsList.observe(viewLifecycleOwner, Observer { items ->
            if(items.isEmpty()){
                binding.clNoDataView.visibility = View.VISIBLE
                binding.clListView.visibility = View.GONE
            }
            else{
                binding.clNoDataView.visibility = View.GONE
                binding.clListView.visibility = View.VISIBLE
            }
                setupRv(binding.rvCars, items)
        })

        viewModel.apiError.observe(viewLifecycleOwner, {
            showAlertDialog(
                requireContext(),
                getString(R.string.error),
                it,
                getString(R.string.ok_got_it)
            )
        })
    }

    /**
     * Setup recyclerview and attach to adapter
     *
     */
    private fun setupRv(rv: RecyclerView, Cars: List<CarData>) {
        with(rv) {
            layoutManager = LinearLayoutManager(this.context)
            adapter = CarsAdapter(Cars)
        }
    }
    override fun onResume() {
        viewModel.refreshCars()
        super.onResume()
    }

}
