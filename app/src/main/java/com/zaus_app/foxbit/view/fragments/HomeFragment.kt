package com.zaus_app.foxbit.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.zaus_app.foxbit.R
import com.zaus_app.foxbit.databinding.FragmentHomeBinding
import com.zaus_app.foxbit.view.viewmodels.HomeViewModel
import com.zaus_app.foxbit.view.vp_adapters.MainPagerAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val TAB_TITLES = arrayOf(
        R.string.songs_tab,
        R.string.albums_tab
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragments: ArrayList<Fragment> = arrayListOf(
            SongsFragment(),
            AlbumsFragment()
        )

        binding.pager.adapter =  MainPagerAdapter(fragments, childFragmentManager,lifecycle)
        TabLayoutMediator(binding.tabs,binding.pager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}