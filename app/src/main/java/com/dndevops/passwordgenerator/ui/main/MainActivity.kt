package com.dndevops.passwordgenerator.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dndevops.passwordgenerator.R
import com.dndevops.passwordgenerator.model.PasswordItem
import com.dndevops.passwordgenerator.ui.add.AddActivity
import com.dndevops.passwordgenerator.ui.add.AddActivity.Companion.EXTRA_PASSWORD_ITEM
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {

    val SHOW_ADD_REQUEST_CODE = 100
    private val passwordItemList = arrayListOf<PasswordItem>()
    private val passwordItemAdapter = PasswordItemAdapter(passwordItemList)

    private lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        initViews()
        initViewModel()
    }

    private fun initViews() {

        fab.setOnClickListener {
            startAddActivity()
        }

        rvItems.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvItems.adapter = passwordItemAdapter

        passwordItemAdapter.notifyDataSetChanged()

        makeItemTouchHelper().attachToRecyclerView(rvItems)

    }

    private fun startAddActivity() {
        val intent = Intent(this, AddActivity::class.java)
        startActivityForResult(intent, SHOW_ADD_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                SHOW_ADD_REQUEST_CODE -> {
                    val passwordItemBacklogItem =
                        data!!.getParcelableExtra<PasswordItem>(EXTRA_PASSWORD_ITEM)
                    mainActivityViewModel.insertPasswordItemBacklogItem(passwordItemBacklogItem)
                }
            }
        }
    }

    private fun initViewModel() {
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        mainActivityViewModel.passwordItems.observe(this, Observer { PasswordItem ->
            if (PasswordItem != null) {
                this@MainActivity.passwordItemList.clear()
                this@MainActivity.passwordItemList.addAll(PasswordItem)
                passwordItemAdapter.notifyDataSetChanged()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.btnDeleteAllItems -> {
                mainActivityViewModel.deleteAllPasswordItems()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun makeItemTouchHelper(): ItemTouchHelper {

        // Callback which is used to create the ItemTouch helper. Only enables left swipe.
        // Use ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) to also enable right swipe.
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            // Enables or Disables the ability to move items up and down.
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            // Callback triggered when a user swiped an item.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val delPasswordItem = passwordItemList.removeAt(position)
                mainActivityViewModel.deletePasswordItem(delPasswordItem)
            }
        }
        return ItemTouchHelper(callback)
    }
}
