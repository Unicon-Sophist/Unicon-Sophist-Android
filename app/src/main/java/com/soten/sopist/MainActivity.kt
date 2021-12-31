package com.soten.sopist

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import com.soten.sopist.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val navigationController by lazy {
        (supportFragmentManager.findFragmentById(R.id.mainNavigationHostContainer)
                as NavHostFragment).navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initNavi()
    }

    private fun initNavi() {
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigationCategory,
            R.id.navigationMeet,
            R.id.navigationLike,
            R.id.navigationProfile))

        setSupportActionBar(binding.toolbar)
        binding.navView.setupWithNavController(navigationController)
        setupActionBarWithNavController(navigationController, appBarConfiguration)

        navigationController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.navigationJoin -> binding.navView.visibility = View.GONE
                else -> binding.navView.visibility = View.VISIBLE
            }

            when (destination.id) {
                R.id.navigationMeet -> {
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                    supportActionBar?.setHomeAsUpIndicator(R.drawable.sopist_tolbar)
                    binding.toolbar.setPadding(20, 0, 0, 0)
                }
                else -> {
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                    binding.toolbar.setPadding(0, 0, 0, 0)
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navigationController.navigateUp() || super.onSupportNavigateUp()
    }

}