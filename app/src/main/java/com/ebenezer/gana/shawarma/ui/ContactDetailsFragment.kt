package com.ebenezer.gana.shawarma.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ebenezer.gana.shawarma.R
import com.ebenezer.gana.shawarma.databinding.FragmentContactDetailsBinding
import com.ebenezer.gana.shawarma.ui.viewModels.OrderViewModel


class ContactDetailsFragment : Fragment() {

    private val sharedViewModel: OrderViewModel by activityViewModels()
    private var _binding: FragmentContactDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContactDetailsBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
            contactFragment = this@ContactDetailsFragment
        }
    }


    fun submitContactDetails() {
        sharedViewModel.setContactDetails(
            this.binding.name.text.toString(),
            this.binding.address.text.toString(),
            this.binding.phoneNumber.text.toString(),
        )
    }


    fun cancelOrder() {
        sharedViewModel.resetOrder()
        findNavController().navigate(R.id.action_contactDetailsFragment_to_homeFragment)
    }

    /**
     * Navigate to the next screen to see the order summary.
     */
    fun goToNextScreen() {
        submitContactDetails()
        findNavController().navigate(R.id.action_contactDetailsFragment_to_summaryFragment)

    }

    /**
     * This fragment lifecycle method is called when the view hierarchy associated with the fragment
     * is being removed. As a result, clear out the binding object.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}