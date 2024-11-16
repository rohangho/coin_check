package com.example.coin

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coin.adapter.CoinAdapter
import com.example.coin.databinding.ActivityMainBinding
import com.example.coin.viewmodel.HomeViewModel
import com.example.coin.viewmodel.ScreenState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    private  var adapter = CoinAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        homeViewModel.getData()
        observeData()

    }

    private fun showCoinList() {
        binding.coinRecycler.visibility = View.VISIBLE
        binding.coinRecycler.adapter = adapter
        binding.coinRecycler.layoutManager = LinearLayoutManager(this)
    }

    fun showProgress() {
        binding.loader.visibility = View.VISIBLE
    }
    fun hideProgress() {
        binding.loader.visibility = View.GONE
    }
    fun showError()
    {
        binding.errorImg.visibility = View.VISIBLE
    }

    private fun observeData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                homeViewModel.screenState.collect {
                    when(it){
                        is ScreenState.Error -> {
                            hideProgress()
                            showError()
                        }
                        is ScreenState.Loading -> {
                            showProgress()
                        }
                        is ScreenState.SuccessData -> {
                            hideProgress()
                            showCoinList()
                            adapter.updateList(it.data)
                        }
                    }
                }
            }
        }
    }
}