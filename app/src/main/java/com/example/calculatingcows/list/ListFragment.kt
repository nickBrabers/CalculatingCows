package com.example.calculatingcows.list

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.calculatingcows.Filter
import com.example.calculatingcows.R
import com.example.calculatingcows.data.CowDatabase
import com.example.calculatingcows.data.CowDatabaseDao
import com.example.calculatingcows.databinding.ListFragmentBinding



class ListFragment : Fragment() {


    private val dataSource: CowDatabaseDao by lazy {
        CowDatabase.getInstance(requireNotNull(this.activity).application).cowDatabaseDao
    }
    private val viewModelFactory: ListViewModelFactory by lazy {
        ListViewModelFactory(dataSource, requireNotNull(this.activity).application)
    }
    private val listViewModel: ListViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(ListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: ListFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.list_fragment, container, false)



        binding.lifecycleOwner = this
        binding.listViewModel = listViewModel

        val adapter = FancyListAdapter()
        binding.listRecyclerView.adapter = adapter


        listViewModel.cows.observe(viewLifecycleOwner, { mutableLiveData ->
            mutableLiveData?.let { liveData ->
            liveData.observe(viewLifecycleOwner, {
                adapter.submitList(it)
            })
            }
        })

        listViewModel.eventNavigateToAdd.observe(
            viewLifecycleOwner,
            { shouldNavigateToAdd ->
                if (shouldNavigateToAdd) {
                    this.findNavController()
                        .navigate(ListFragmentDirections.actionListFragmentToAddFragment())
                    listViewModel.onNavigateDone()
                }
            })

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.item_delete -> {
                listViewModel.deleteAll()
                true
            }
            R.id.item_desc -> {
                listViewModel.updateFilter(Filter.SHOW_DESC)
                true
            }
            R.id.item_asc -> {
                listViewModel.updateFilter(Filter.SHOW_ASC)
                true
            }

            else -> false
        }
    }
}