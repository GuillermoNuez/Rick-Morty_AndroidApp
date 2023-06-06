package com.everis.rickmorty.presentation.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.everis.rickmorty.R
import com.everis.rickmorty.data.repository.CharacterRepositoryImpl
import com.everis.rickmorty.data.source.CharacterDataSourceImpl
import com.everis.rickmorty.databinding.MainFragmentBinding
import com.everis.rickmorty.domain.usecase.CharacterUseCaseImpl
import com.everis.rickmorty.presentation.adapters.CharacterAdapter
import com.everis.rickmorty.presentation.modules.DetailsActivity
import com.everis.rickmorty.presentation.modules.MainViewModel

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }


    private lateinit var binding: MainFragmentBinding

    private var repository = CharacterRepositoryImpl(dataSource = CharacterDataSourceImpl())
    private val usecase = CharacterUseCaseImpl(repository = repository)
    private val viewModel: MainViewModel = MainViewModel(usecase = usecase)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(layoutInflater)
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = MainFragmentBinding.bind(view)

        viewModel.getCharacters()

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