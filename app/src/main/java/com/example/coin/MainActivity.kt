package com.example.coin

import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
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
import com.example.coin.viewmodel.UIEvent
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    private var adapter = CoinAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        homeViewModel.getData()
        observeData()
        bindFilterChips()

        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (homeViewModel.backSearchMode) {
                    homeViewModel.processEvent(UIEvent.BackToOriginal)
                    updateSearchIconUI()
                } else
                    finish()

            }
        })
    }

    private fun updateSearchIconUI() {
        if (homeViewModel.backSearchMode)
            binding.searchIcon.setImageResource(R.drawable.baseline_arrow_back_24)
        else
            binding.searchIcon.setImageResource(R.drawable.icons8_search)


        binding.searchIcon.setOnClickListener {

            if (homeViewModel.backSearchMode) {
                homeViewModel.backSearchMode = false
                binding.searchIcon.setImageResource(R.drawable.icons8_search)
                adapter.updateList(homeViewModel.returnAllList())

            } else {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Search")
                val input = EditText(this)
                input.inputType = InputType.TYPE_CLASS_TEXT
                input.hint = "Search Name or Symbol"
                builder.setView(input)
                builder.setPositiveButton("OK") { dialog,
                                                  which ->
                    binding.searchIcon.setImageResource(R.drawable.baseline_arrow_back_24)
                    homeViewModel.processEvent(UIEvent.SearchData(input.text.toString()))
                }
                builder.setNegativeButton("Cancel") { dialog, which ->
                    dialog.cancel()
                }
                builder.show()
            }
        }
    }

    private fun bindFilterChips() {
        binding.chipGroup.setOnCheckedStateChangeListener(object :
            ChipGroup.OnCheckedStateChangeListener {
            override fun onCheckedChanged(
                group: ChipGroup,
                checkedIds: List<Int?>
            ) {

                val listOfFilter: MutableList<MutableList<String>> = mutableListOf()
                listOfFilter.add(mutableListOf()) // for active
                listOfFilter.add(mutableListOf()) // for coin
                listOfFilter.add(mutableListOf()) //for isNew


                if (checkedIds.contains(R.id.chip_active))
                    listOfFilter[0].add("true")
                if (checkedIds.contains(R.id.chip_inactive))
                    listOfFilter[0].add("false")
                if (checkedIds.contains(R.id.chip_coin))
                    listOfFilter[1].add("coin")
                if (checkedIds.contains(R.id.chip_token))
                    listOfFilter[1].add("token")
                if (checkedIds.contains(R.id.chip_newcoin))
                    listOfFilter[2].add("true")
                if (checkedIds.contains(R.id.chip_oldcoin))
                    listOfFilter[2].add("false")

                homeViewModel.processEvent(UIEvent.FilterResult(listOfFilter))

            }
        })
    }

    private fun showCoinList() {
        binding.chipGroup.visibility = View.VISIBLE
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

    fun showError() {
        binding.errorImg.visibility = View.VISIBLE
    }

    private fun observeData() {
        homeViewModel.coinList.observe(this) {
            hideProgress()
            adapter.updateList(it)
            showCoinList()
            updateSearchIconUI()
        }
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
                            showProgress()
                        }

                        is ScreenState.SuccessData -> {
                            homeViewModel.syncData()

                        }
                    }
                }

            }
        }

    }
}