package com.interswitchng.smartpossdksample

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.interswitchng.smartpos.IswPos
import com.interswitchng.smartpos.shared.models.results.IswPrintResult
import com.interswitchng.smartpossdksample.databinding.ActivityPrintBinding
import com.interswitchng.smartpossdksample.utils.PrefUtils
import com.interswitchng.smartpossdksample.utils.PrinterUtil
import com.interswitchng.smartpossdksample.utils.ViewBindingProvider
import com.interswitchng.smartpossdksample.utils.ViewUtils.showSnackBar
import com.interswitchng.smartpossdksample.utils.setToolBarTitle

class PrintActivity : AppCompatActivity(), IswPos.IswPrinterCallback, ViewBindingProvider {

    private var _binding: ActivityPrintBinding? = null
    private val binding get() = _binding!!

    private val iswPosInstance: IswPos by lazy { IswPos.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        _binding = ActivityPrintBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolBarTitle(binding.toolBar, "Print Data", this)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        PrinterUtil.resetPrintData()
        attachViewListeners()
    }

    private fun attachViewListeners() {
        binding.run {
            btnAddPrintText.setOnClickListener {
                extractPrintText()?.let {
                    PrinterUtil.addLine(it.first, it.second)
                    clearFieldsForNextEntry()
                }
            }

            btnAddPrintLogo.setOnClickListener {
                val companyLogo = PrefUtils.getSavedCompanyLogoOption(this@PrintActivity)

                if (companyLogo != CompanyOption.NONE) {
                    when (companyLogo) {
                        CompanyOption.INTERSWITCH -> PrinterUtil.addBitmap(com.interswitchng.smartpos.R.drawable.isw_logo, true, this@PrintActivity)
                        CompanyOption.GOOGLE -> PrinterUtil.addBitmap(R.drawable.ic_isw_google, false, this@PrintActivity)
                        CompanyOption.AMAZON -> PrinterUtil.addBitmap(R.drawable.ic_isw_amazon, false, this@PrintActivity)
                        CompanyOption.APPLE -> PrinterUtil.addBitmap(R.drawable.ic_isw_apple, false, this@PrintActivity)
                        else -> {}
                    }
                } else {
                    showSnackBar(
                        "Adds nothing. Company logo is not set.",
                        Snackbar.LENGTH_LONG
                    )
                }

                clearFieldsForNextEntry()
            }


            btnPrintAll.setOnClickListener {
                if (PrinterUtil.getPrintData().isEmpty()) {
                    showSnackBar("Print data is empty. Add text or logo to begin")
                } else {
                    tryPrint()
                }
            }
        }
    }

    private fun extractPrintText(): Pair<String, Boolean>? {
        binding.run {

           return if (etAmount.text.toString().isEmpty()) {
                showSnackBar("Adds nothing. Text field is empty.")

               null
            } else {
               val text = etAmount.text.toString()
               val isTitleField = swIsTitle.isChecked

               Pair(text, isTitleField)
            }
        }
    }

    private fun tryPrint() {
        val printObjects = PrinterUtil.getPrintData()
        iswPosInstance.print(printObjects, this)
    }

    private fun clearFieldsForNextEntry() {
        binding.run {
            etAmount.setText("")
            swIsTitle.isChecked = false
        }
    }

    override fun getRootView(): View = binding.root

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onError(result: IswPrintResult) {
        // Add means of your choice to switch to main thread
        Handler(Looper.getMainLooper()).post {
            binding.tvPrintResult.text = buildString {
                append("Print status: ")
                append(result.status)
                append("\nMessage: ")
                append(result.message)
            }

            PrinterUtil.resetPrintData()
            clearFieldsForNextEntry()
        }
    }

    override fun onPrintCompleted(result: IswPrintResult) {
        // Add means of your choice to switch to main thread
        Handler(Looper.getMainLooper()).post {
            binding.tvPrintResult.text = buildString {
                append("Print status: ")
                append(result.status)
                append("\nMessage: ")
                append(result.message)
            }

            PrinterUtil.resetPrintData()
            clearFieldsForNextEntry()
        }
    }
}