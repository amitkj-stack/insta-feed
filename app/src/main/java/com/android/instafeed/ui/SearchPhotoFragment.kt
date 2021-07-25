package com.android.instafeed.ui


import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.android.instafeed.R
import com.android.instafeed.adapter.ImageListAdapter
import com.android.instafeed.databinding.FragmentSearchPhotoBinding
import com.android.instafeed.network.NetworkState
import com.android.instafeed.support.BaseFragment
import com.android.instafeed.support.OnItemClickListener
import com.android.instafeed.support.RecyclerTouchListener
import com.android.instafeed.support.SpacesItemDecoration
import com.android.instafeed.support.Utils.hideKeyboard
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 *
 */
class SearchPhotoFragment : BaseFragment<FragmentSearchPhotoBinding>(), OnItemClickListener {

    private val viewModel: SearchPhotoViewModel by viewModel()

    override val layoutId: Int
        get() = R.layout.fragment_search_photo

    private lateinit var listAdapter: ImageListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        setHasOptionsMenu(true)
    }

    private fun initAdapter() {
        val spanCount =
            if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 3 else 5
        listAdapter = ImageListAdapter { viewModel.refreshAllList() }
        binding.recyclerView.layoutManager = GridLayoutManager(activity, spanCount)
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.dp_4)
        binding.recyclerView.addItemDecoration(SpacesItemDecoration(spacingInPixels, spanCount))
        binding.recyclerView.adapter = listAdapter
        context?.let { RecyclerTouchListener(it, this) }?.let {
            binding.recyclerView.addOnItemTouchListener(
                it
            )
        }
        configureObservables()
    }

    private fun configureObservables() {
        viewModel.networkState?.observe(viewLifecycleOwner, { state ->
            binding.progressBar.visibility =
                if (listAdapter.currentList.isNullOrEmpty() && state == NetworkState.RUNNING) View.VISIBLE else View.GONE
            binding.txtError.visibility =
                if (listAdapter.currentList.isNullOrEmpty() && (state == NetworkState.FAILED || state == NetworkState.SUCCESS)) View.VISIBLE else View.GONE
            if (!listAdapter.currentList.isNullOrEmpty()) {
                listAdapter.setState(state ?: NetworkState.SUCCESS)
            }
        })
        viewModel.messageLiveData.observe(viewLifecycleOwner, { message ->
            communicator.frgShowSnackBarMessage(message)
        })
        viewModel.photoList.observe(viewLifecycleOwner, {
            listAdapter.submitList(it)
        })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        viewModel.search()
    }

    override fun onResume() {
        super.onResume()
        binding.txtError.setOnClickListener { viewModel.refreshAllList() }
    }

    override fun onPause() {
        super.onPause()
        binding.txtError.setOnClickListener(null)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        initMenu(menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun initMenu(menu: Menu) {
        val searchMenuItem = menu.findItem(R.id.search)
        val searchView = searchMenuItem.actionView as SearchView
//        searchView.imeOptions = EditorInfo.IME_ACTION_GO
        val possibleExistingQuery = viewModel.getCurrentQuery()
        if (possibleExistingQuery.isNotEmpty()) {
            searchMenuItem.expandActionView()
            searchView.setQuery(possibleExistingQuery, false)
            searchView.clearFocus()
        }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                listAdapter.clear()
                activity?.hideKeyboard()
                viewModel.search(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    override fun onClick(view: View, position: Int) {
        val newFragment = ShowImageFragment()
        newFragment.arguments = listAdapter.buildBundle(position)
        newFragment.show(childFragmentManager, "ShowImageFragment")
    }

}
