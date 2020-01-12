package com.dndevops.passwordgenerator.ui.add

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dndevops.passwordgenerator.R
import com.dndevops.passwordgenerator.model.PasswordItem
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.content_add.*
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeParseException
import java.util.*

class AddActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Add Password"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initViews()
    }

    private fun initViews() {
        fab.setOnClickListener {
            savePasswordItem()
        }
        fab_refresh.setOnClickListener()
        {
            etPassword.setText(createPassword())
        }
        etPassword.setText(createPassword())
    }


    private fun createPassword(): String {
        val length = 10
        val valid = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!@#$%"
        val strBuilder = StringBuilder()
        val rnd = Random()
        for (i in 0 until length) {
            strBuilder.append(valid[rnd.nextInt(valid.length)]);
        }
        return strBuilder.toString();
    }


    private fun savePasswordItem() {
        if (isValidPasswordItem()) {
            try {
                val localDate = LocalDate.now()
                val date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
                val passwordItem = PasswordItem(
                    etTitle.text.toString(), etPassword.text.toString(), date
                )
                val resultIntent = Intent()
                resultIntent.putExtra(EXTRA_PASSWORD_ITEM, passwordItem)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            } catch (e: DateTimeParseException) {
                Toast.makeText(this, getString(R.string.date_invalid_error), Toast.LENGTH_SHORT)
                    .show()
                return
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun isValidPasswordItem(): Boolean {
        return when {
            etTitle.text.toString().isBlank() -> {
                Toast.makeText(this, getString(R.string.title_invalid_error), Toast.LENGTH_SHORT)
                    .show()
                false
            }
            etPassword.text.toString().isBlank() -> {
                Toast.makeText(
                    this,
                    getString(R.string.password_invalid_error),
                    Toast.LENGTH_SHORT
                ).show()
                false
            }
            else -> true
        }
    }

    companion object {
        const val EXTRA_PASSWORD_ITEM = "EXTRA_PASSWORD_ITEM"
    }

}
