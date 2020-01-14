package com.stacktest.countries

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.androidjunior.coroutinestest.R
import com.androidjunior.coroutinestest.databinding.ActivityCountriesBinding
import com.androidjunior.coroutinestest.presentation.repos.details.RegionsActivity
import com.androidjunior.coroutinestest.presentation.repos.details.RegionsActivity.Companion.COUNTRY_ID
import com.stacktest.domain.countries.Country

@MainThread
class CountriesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityCountriesBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_countries)
        val viewModel = ViewModelProviders.of(this).get(CountriesViewModel::class.java)
        findViewById<RecyclerView>(R.id.countries).adapter =
            CountriesAdapter(
                { showRegions(it) },
                { viewModel.requestNewPage() }
            )
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    override fun onResume() {
        super.onResume()
        System.gc()
    }

    private fun showRegions(country: Country) {
        Log.d("BLABLA", "show regions for: ${country.title}")
        val intent = Intent(this, RegionsActivity::class.java)
        intent.putExtra(COUNTRY_ID, country.id)
        startActivity(intent)
    }
}