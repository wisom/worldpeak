package com.worldpeak.chnsmilead.oa.dialog

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialog
import com.worldpeak.chnsmilead.R

class DocumentApprovalDialog(
        context: Context,
        val leftCallback: (String) -> Unit,
        val rightCallback: (String) -> Unit
) : AppCompatDialog(context, R.style.dialog_common) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_document_approval)
        setCanceledOnTouchOutside(true)
        window?.attributes?.gravity = Gravity.CENTER
        val etContent = findViewById<EditText>(R.id.etContent)

        findViewById<TextView>(R.id.tvSave)?.apply {
            setOnClickListener {
                val content = etContent?.text.toString().trim()
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(context, "请输入审批内容", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                leftCallback.invoke(content)
                dismiss()
            }
        }
        findViewById<TextView>(R.id.tvSubmit)?.apply {
            setOnClickListener {
                val content = etContent?.text.toString().trim()
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(context, "请输入审批内容", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                rightCallback.invoke(content)
                dismiss()
            }
        }
    }
}