package com.brmlab.shophelp.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.brmlab.shophelp.R
import com.brmlab.shophelp.adapter.MovieListAdapter
import com.brmlab.shophelp.adapter.PersonListAdapter
import com.brmlab.shophelp.model.PersonResult
import com.brmlab.shophelp.model.PopularPerson
import com.brmlab.shophelp.model.PopularResult
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint


class ListFragment : Fragment() {
    companion object{
        val list = ArrayList<PopularResult>()
        val personList = ArrayList<PersonResult>()
        val topRated = ArrayList<PopularResult>()
    }

    private val adapter = MovieListAdapter()
    private val topRatedAdapter = MovieListAdapter()
    private val personAdapter = PersonListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.fragmentListRecycler)
        val bestMoviesRecycler = view.findViewById<RecyclerView>(R.id.fragmentListBestMoviesRecycler)
        val bestPersonRecycler = view.findViewById<RecyclerView>(R.id.fragmentListBestPerson)

        adapter.newList(list)
        topRatedAdapter.newList(topRated)
        personAdapter.newList(personList)
        //RecyclerImp
        bestPersonRecycler.adapter = personAdapter
        bestMoviesRecycler.adapter = topRatedAdapter
        recyclerView.adapter = adapter

        bestPersonRecycler.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        bestMoviesRecycler.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        return view
    }

}