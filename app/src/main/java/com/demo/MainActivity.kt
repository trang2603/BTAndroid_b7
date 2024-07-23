package com.demo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: AdapterClass
    private var isLoading = false
    private var currentPage = 1
    private var itemsPerPage = 20
    private var totalItems = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecycleView()
        setupRefreshLayout()
    }

    private fun setupRefreshLayout() {
        binding.swipeRefresh.setOnRefreshListener {
            refreshItems()
        }
    }

    private fun refreshItems() {
        // dat lại currentPage de lam moi tu trang dau
        currentPage = 1
        // nap du lieu moi
        val newProfiles = generateFakeData(currentPage, itemsPerPage)
        // cap nhat du lieu vao adapter
        adapter.setItems(newProfiles)
        // an vong tron lam moi
        binding.swipeRefresh.isRefreshing = false
    }

    private fun setupRecycleView() {
        val profiles = generateFakeData(currentPage, itemsPerPage)
        adapter = AdapterClass(profiles)

        binding.recycleView.layoutManager = LinearLayoutManager(this)
        binding.recycleView.adapter = adapter

        binding.recycleView.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(
                    recyclerView: RecyclerView,
                    dx: Int,
                    dy: Int,
                ) {
                    super.onScrolled(recyclerView, dx, dy)

                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    // số lượng mục đang hiển thị trên màn hình
                    val visibleItemCount = layoutManager.childCount

                    // tong so muc trong recycleView
                    val totalItemCount = layoutManager.itemCount

                    // vi tri dau tien cua muc dang hien thi
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                    if (!isLoading && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                        loadMoreItems()
                        Log.e("", "$visibleItemCount")
                        Log.e("", "$firstVisibleItemPosition")
                        Log.e("", "$totalItemCount")
                    }
                }
            },
        )
    }

    private fun loadMoreItems() {
        isLoading = true
        currentPage++

        if (currentPage * itemsPerPage <= totalItems) {
            val newProfiles = generateFakeData(currentPage, itemsPerPage)
            adapter.addData(newProfiles)
        }
        isLoading = false
    }

    fun generateFakeData(
        page: Int,
        itemsPerPage: Int,
    ): ArrayList<Profile> {
        val start = (page - 1) * itemsPerPage + 1
        val end = minOf(page * itemsPerPage, totalItems)
        val fakeData = ArrayList<Profile>()
        for (i in start..end) {
            fakeData.add(
                Profile(
                    img = R.drawable.img,
                    name = "Name $i",
                    status = i % 2 == 0,
                    phone = "036***${i.toString().padStart(4, '0')}",
                    category = "Category $i",
                    consultPrice = "Tư vấn giá $i",
                    productConsult = "Tư vấn sản phẩm $i",
                ),
            )
        }
        return fakeData
    }
}
