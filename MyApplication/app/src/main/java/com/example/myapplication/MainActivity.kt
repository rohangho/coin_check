package com.example.myapplication

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
import com.example.coin.viewmodel.HomeViewModel
import com.example.coin.viewmodel.ScreenState
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.ui.ProductsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.getValue

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val homeViewModel: HomeViewModel by viewModels()
    val adapter = ProductsAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.productsRecycler.adapter = adapter
        binding.productsRecycler.layoutManager = LinearLayoutManager(this)
        binding.errorButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                homeViewModel.getData()
            }
        })
        observeData()
    }


    fun showProgress() {
        binding.progressBar.visibility = View.VISIBLE
    }

    fun hideProgress() {
        binding.progressBar.visibility = View.GONE
    }

    fun showError() {
        binding.errorButton.visibility = View.VISIBLE
    }

    fun hideError() {
        binding.errorButton.visibility = View.GONE
    }


    private fun observeData() {
        homeViewModel.getData()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                homeViewModel.screenState.collect {
                    when (it) {
                        is ScreenState.Error -> {
                            hideProgress()
                            showError()
                        }

                        is ScreenState.Loading -> {
                            hideError()
                            showProgress()
                        }

                        is ScreenState.SuccessData -> {
                            hideProgress()
                            hideError()
                            adapter.updateList(it.data)
                        }
                    }
                }

            }
        }
    }
}