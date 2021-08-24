package com.example.calculatingcows.add

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.calculatingcows.R
import com.example.calculatingcows.convert
import com.example.calculatingcows.data.Cow
import com.example.calculatingcows.data.CowDatabase
import com.example.calculatingcows.databinding.AddFragmentBinding
import com.google.android.material.internal.ContextUtils
import com.google.android.material.textfield.TextInputEditText

private const val TAG = "Room"

class AddFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: AddFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.add_fragment, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = CowDatabase.getInstance(application).cowDatabaseDao

        val viewModelFactory = AddViewModelFactory(dataSource, application)

        val addViewModel = ViewModelProvider(this, viewModelFactory).get(AddViewModel::class.java)

        binding.addViewModel = addViewModel

        binding.lifecycleOwner = this



        binding.sendFab.setOnClickListener {
            val editTextNumber = binding.editTextNumber.text.toString().toIntOrNull()
            val editTextAge = binding.editTextAge.text.toString().toIntOrNull()



            if (editTextNumber != null && editTextAge != null) {
                addViewModel.onSaveClick(editTextNumber, editTextAge)
            }
        }


        addViewModel.eventSend.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                this.findNavController()
                    .navigate(AddFragmentDirections.actionAddFragmentToListFragment())
            }
        })


        return binding.root
    }
}