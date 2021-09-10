package com.example.lista_de_contatos

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.CalendarView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.content.edit
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lista_de_contatos.DetailActivity.Companion.EXTRA_CONTACT
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class MainActivity : AppCompatActivity(), ClickItemContactListener {

    private val rvList: RecyclerView by lazy {
        findViewById<RecyclerView>(R.id.rv_List)
    }

    private val adapter = ContactAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawer_menu)

        updateList()
        initDrawer()
        fetchListContact()
        bindViews()
        getInstanceSharePreferences()
    }

    private fun fetchListContact() {
        val list = arrayListOf(
            Contact(
                "Douglas Ribeiro Sanguino",
                "(43) 99968-9655",
                "img.png"
            ),
            Contact(
                "Jeferson Santos",
                "(43) 99868-7755",
                "img.png"
            )
        )
        getInstanceSharePreferences().edit {
            putString("contacts", Gson().toJson(list))
            commit()
        }
    }
    private fun getInstanceSharePreferences() : SharedPreferences {
        return getSharedPreferences("Lista_de_Contatos.PREFERENCES", Context.MODE_PRIVATE)
    }
    private fun initDrawer() {
        val drawerLayout = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.opendrawer, R.string.closedrawer)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun bindViews() {
        rvList.adapter = adapter
        rvList.layoutManager = LinearLayoutManager(this)
        updateList()
    }

    private fun getListContacts() : List<Contact> {
        val list = getInstanceSharePreferences().getString("contacts", "[]")
        val turnsTypes = object :TypeToken<List<Contact>>() {}.type
        return Gson().fromJson(list, turnsTypes)
    }
    private fun updateList() {
        adapter.updateList(getListContacts())
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_menu1 -> {
                showToast("Você clicou no item menu 1")
                return true
            }
            R.id.item_menu2 -> {
                showToast("Você clicou no item menu 2")
                return true
            }
            else -> onOptionsItemSelected(item)
        }
    }

    override fun clickItemContact(contact: Contact) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(EXTRA_CONTACT, contact)
        startActivity(intent)
    }
}