package com.androidjunior.coroutinestest.presentation.repos.details

import android.os.Bundle
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.androidjunior.coroutinestest.R
import com.androidjunior.coroutinestest.databinding.ActivityRegionsBinding
import com.stacktest.regions.RegionsAdapter
import com.stacktest.regions.RegionsViewModel
import kotlin.time.ExperimentalTime

@MainThread
class RegionsActivity : AppCompatActivity() {

    @ExperimentalTime
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val countryId = intent.getLongExtra(COUNTRY_ID, -1L)
        if (countryId == -1L) {
            finish()
            return
        }

        val binding: ActivityRegionsBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_regions)
        val viewModel = ViewModelProviders.of(this).get(RegionsViewModel::class.java)
        viewModel.countryId = countryId
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        findViewById<RecyclerView>(R.id.regions).adapter =
            RegionsAdapter { viewModel.requestNewPage() }

    }

    companion object {
        const val COUNTRY_ID = "country"
    }
}