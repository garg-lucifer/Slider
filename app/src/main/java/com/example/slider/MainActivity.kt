package com.example.slider

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.slider.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnChange {
    private lateinit var binding: ActivityMainBinding
    private lateinit var slideViewModel: SlideViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val dao = SlideDatabase.getInstance(application).slideDataDao
        val repository = SlideRepository(dao)
        val factory = SlideViewModelFactory(repository)
        slideViewModel = ViewModelProvider(this, factory)[SlideViewModel::class.java]
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        getAllData()
    }

    private fun getAllData() {
        slideViewModel.slideData.observe(this, Observer {
            if (it.isEmpty()) slideViewModel.insert(SlideData(1, 100, 100))
            binding.recyclerView.adapter = Adapter(it, this)
            Log.i("Data", it.toString())
        })
    }

    override fun displayToast() {
        Toast.makeText(this, "Minimum value must be 2!", Toast.LENGTH_SHORT).show()
    }

    var lock = 1;
    var lock2 = 1;
    override fun valueStop(slideData: SlideData) {
        if (lock == 1) {
            val prev = slideData.intEnd
            slideData.intEnd = slideData.curr
            slideViewModel.update(slideData)
            val slideData2 = SlideData(slideData.intEnd + 1, prev, prev);
            slideViewModel.insert(slideData2)
            lock = 0
        }
    }

    override fun valueStart() {
        lock = 1
        lock2 = 1
    }

    override fun delete(slideData: SlideData) {
        if (lock2 == 1) {
            slideViewModel.size().observe(this, Observer{
                if(it != null){
                    if(it == 1){
                        Toast.makeText(
                            this,
                            "Single row can't be deleted!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }else {
                        slideViewModel.getNextSize(slideData.intEnd).observe(this, Observer {
                            if (it != null) {
                                if (it == 0) {
                                    slideViewModel.getPrev(slideData.intStart - 1).observe(this, Observer {
                                        if (it != null) {
                                            it.intEnd = 100
                                            it.curr = 100
                                            slideViewModel.delete(slideData)
                                            slideViewModel.update(it)
                                        }
                                    })
                                } else {
                                    slideViewModel.getNext(slideData.intEnd + 1).observe(this, Observer {
                                        if (it != null) {
                                            val sl = SlideData(slideData.intStart, it.intEnd, it.intStart)
                                            slideViewModel.delete(it)
                                            slideViewModel.delete(slideData)
                                            slideViewModel.insert(sl)
                                        }
                                    })
                                }
                            }
                        })
                    }
                }
            })
            lock2 = 0
        }
    }
}