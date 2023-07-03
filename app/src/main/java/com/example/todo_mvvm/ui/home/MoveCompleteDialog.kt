package com.example.todo_mvvm.ui.home

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.Window
import androidx.databinding.DataBindingUtil
import com.example.todo_mvvm.R
import com.example.todo_mvvm.databinding.MoveCompleteDialogBinding
import com.example.todo_mvvm.db.TodoModel

class MoveCompleteDialog(private val context: Context) {

    private lateinit var binding: MoveCompleteDialogBinding

    fun showDlg(todoModel: TodoModel) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.move_complete_dialog, null, false)

        val dlg = AlertDialog.Builder(context, R.style.CustomAlertDialog)
            .setView(binding.root)
            .setCancelable(false)
            .create()

        binding.todo = todoModel

        binding.dialogOkButton.setOnClickListener {

            dlg.dismiss()
        }

        binding.dialogCancelButton.setOnClickListener {
            dlg.dismiss()
        }

        dlg.show()
    }
}