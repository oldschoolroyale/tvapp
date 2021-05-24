package com.brmlab.shophelp.ui.activity

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.brmlab.shophelp.R
import com.brmlab.shophelp.utils.Status
import com.brmlab.shophelp.ui.vm.MovieViewModel
import com.wang.avi.AVLoadingIndicatorView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashActivity : FragmentActivity() {

    var avi: AVLoadingIndicatorView? = null
    private val movieViewMode : MovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        avi = findViewById(R.id.activitySplashAv)

        val packageName = packageManager
        val compName = ComponentName(this, SplashActivity::class.java)
        packageName.setComponentEnabledSetting(compName,
        PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP)

        

        movieViewMode.slide_list.observe(this, Observer {
            when(it.status){
                Status.LOADING -> {
                    avi?.visibility = View.VISIBLE
                }
                Status.ERROR ->{
                    avi?.visibility = View.INVISIBLE
                    if (it.response?.status == 401){
                        Toast.makeText(this, "Неверный токен",
                        Toast.LENGTH_LONG).show()
                    }
                    else{
                        Toast.makeText(this, it.response?.error,
                        Toast.LENGTH_LONG).show()
                    }
                }
                Status.SUCCESS -> {
                    avi?.visibility = View.INVISIBLE
                    if (it.response?.data?.results?.isNotEmpty() == true){
                        Handler(Looper.getMainLooper()).postDelayed({
                            com.brmlab.shophelp.ui.fragment.ListFragment.list.clear()
                           com.brmlab.shophelp.ui.fragment.ListFragment.list.addAll(
                                it.response?.data?.results
                            )
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }, 1500)
                    }
                    else{
                        Toast.makeText(this, "Данных нет", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })

        movieViewMode.person_list.observe(this, Observer {
            when(it.status){
                Status.LOADING -> {
                    avi?.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                        com.brmlab.shophelp.ui.fragment.ListFragment.personList.clear()
                        com.brmlab.shophelp.ui.fragment.ListFragment.personList.addAll(
                            it.response?.data?.results!!)
                    topRated()
                }
                Status.ERROR ->{
                    Toast.makeText(this, "Error ${it.response?.error}", Toast.LENGTH_LONG).show()
                }
            }
        })

        movieViewMode.top_rated.observe(this, Observer {
            when(it.status){
                Status.LOADING -> {
                    avi?.visibility = View.VISIBLE
                }
                Status.ERROR ->{
                    avi?.visibility = View.INVISIBLE
                    if (it.response?.status == 401){
                        Toast.makeText(this, "Неверный токен",
                            Toast.LENGTH_LONG).show()
                    }
                    else{
                        Toast.makeText(this, it.response?.error,
                            Toast.LENGTH_LONG).show()
                    }
                }
                Status.SUCCESS -> {
                    avi?.visibility = View.INVISIBLE
                    if (it.response?.data?.results?.isNotEmpty() == true){
                            com.brmlab.shophelp.ui.fragment.ListFragment.topRated.clear()
                            com.brmlab.shophelp.ui.fragment.ListFragment.topRated.addAll(
                                it.response.data.results
                            )
                        tryGoMain()
                    }

                    else{
                        Toast.makeText(this, "Данных нет", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })

        personRet()

    }

    private fun topRated() {
        Handler(Looper.getMainLooper()).post {
            movieViewMode.getTopRated()
        }
    }

    private fun tryGoMain() {
        Handler(Looper.getMainLooper()).post {
            movieViewMode.getMovies()
        }
    }

    private fun personRet(){
        Handler(Looper.getMainLooper()).post {
            movieViewMode.getPerson()
        }
    }
}