package com.example.githubuserapi.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapi.ui.adapter.SectionsPagerAdapter
import com.example.githubuserapi.ui.adapter.SectionsPagerAdapter.Companion.SECTION_NUMBER
import com.example.githubuserapi.ui.adapter.RecyclerViewAdapter
import com.example.githubuserapi.databinding.FragmentFollowBinding
import com.example.githubuserapi.recycleview.RecyclerViewData
import com.example.githubuserapi.data.remote.response.ItemsItem
import com.example.githubuserapi.ui.viewmodel.UsersViewModel
import com.example.githubuserapi.ui.viewmodel.UsersViewModelFactory

class FollowFragment : Fragment() {
    private lateinit var binding: FragmentFollowBinding
    private val userViewModel: UsersViewModel by viewModels {
        UsersViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val position = arguments?.getInt(SECTION_NUMBER)

        val login = arguments?.getString(SectionsPagerAdapter.DATA_LOGIN)

        login?.let {
            if (position == 1) {
                userViewModel.showFollowers(login)
                userViewModel.isLoading.observe(requireActivity()) {
                    showLoading(it)
                }
                userViewModel.listFollowers.observe(viewLifecycleOwner) {
                    showFollowersFollowing(it)
                }
            } else {
                userViewModel.showFollowing(login)
                userViewModel.isLoading.observe(requireActivity()) {
                    showLoading(it)
                }
                userViewModel.listFollowing.observe(viewLifecycleOwner) {
                    showFollowersFollowing(it)
                }
            }
        }
    }

    private fun showFollowersFollowing(datas: List<ItemsItem>) {
        val a = ArrayList<RecyclerViewData>()
        datas.map {
            val data = RecyclerViewData(it.avatarUrl, it.login)
            a.add(data)
        }

        val adapter = RecyclerViewAdapter(a)
        binding.rvListUser.adapter = adapter
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvListUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvListUser.addItemDecoration(itemDecoration)

        adapter.setOnItemClickCallback(object : RecyclerViewAdapter.OnItemClickCallback {
            override fun onItemClicked(data: RecyclerViewData) {
                val detailIntent = Intent(requireActivity(), DetailUserActivity::class.java)
                detailIntent.putExtra(DetailUserActivity.DATA_LOGIN, data.login)
                startActivity(detailIntent)
            }
        })
    }


    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}