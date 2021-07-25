package com.android.instafeed.ui

import android.os.Bundle
import android.view.Menu
import com.android.instafeed.R
import com.android.instafeed.databinding.ActivityMainBinding
import com.android.instafeed.support.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configureAndShowFragment()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return configureToolbar(menu)
    }

    // CONFIGURATION ---

    private fun configureAndShowFragment() {
        var fragment = supportFragmentManager.findFragmentById(R.id.activity_main_container) as SearchPhotoFragment?
        if (fragment == null) {
            fragment = SearchPhotoFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.activity_main_container, fragment)
                .commit()
        }
    }

    private fun configureToolbar(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchItem = menu?.findItem(R.id.search)
        val searchView = searchItem?.actionView as? android.widget.SearchView
        searchView?.maxWidth = Integer.MAX_VALUE;
        searchView?.queryHint = getString(R.string.search)
        return true
    }
}