package com.everis.rickmorty.ui.main.fragments

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.everis.rickmorty.R
import com.everis.rickmorty.databinding.MainFragmentBinding
import com.everis.rickmorty.ui.main.adapters.CharacterAdapter
import com.everis.rickmorty.ui.main.DetailsActivity
import com.everis.rickmorty.ui.main.viewmodel.MainViewModel

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(layoutInflater)
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding = MainFragmentBinding.bind(view)

        viewModel.getData()

        viewModel.userProfileResponse.observe(viewLifecycleOwner, { userProfileResponse ->
            val adapter = CharacterAdapter(userProfileResponse.results) { character ->
                val intent = Intent(requireContext(), DetailsActivity::class.java)
                intent.putExtra("character", character)
                startActivity(intent)
            }

            binding.recyclerView.layoutManager = LinearLayoutManager(context)
            binding.recyclerView.adapter = adapter
        })

        viewModel.error.observe(viewLifecycleOwner, { error ->
            if (error) {
                binding.errorLayout.visibility = View.VISIBLE
            }
        })
    }
}