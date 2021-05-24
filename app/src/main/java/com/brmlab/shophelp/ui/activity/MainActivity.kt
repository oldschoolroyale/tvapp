package com.brmlab.shophelp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.brmlab.shophelp.R
import com.brmlab.shophelp.model.PopularModel
import com.brmlab.shophelp.model.PopularResult
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings

@AndroidEntryPoint
@WithFragmentBindings
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupActionBarWithNavController(findNavController(R.id.activityMainNavFragment))
    }

    override fun onSupportNavigateUp(): Boolean {
        val navigate = findNavController(R.id.activityMainNavFragment)
        return super.onSupportNavigateUp() || navigate.navigateUp()
    }
}