package com.zaus_app.foxbit.view.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.zaus_app.foxbit.R
import com.zaus_app.foxbit.databinding.FragmentHomeBinding
import com.zaus_app.foxbit.view.viewmodels.HomeViewModel
import com.zaus_app.foxbit.view.vp_adapters.MainPagerAdapter
import kotlinx.coroutines.delay

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val TAB_TITLES = arrayOf(
        R.string.songs_tab,
        R.string.albums_tab
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val permReqLuncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            if (it) {
                Toast.makeText(requireContext(),"Permission granted",Toast.LENGTH_SHORT).show()
            } else {
              ActivityResultContracts.RequestPermission()
            }
        }
        if (!checkPermission()) {
            permReqLuncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

    }

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

        binding.pager.adapter = MainPagerAdapter(fragments, childFragmentManager, lifecycle)
        TabLayoutMediator(binding.tabs, binding.pager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun checkPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            1
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}