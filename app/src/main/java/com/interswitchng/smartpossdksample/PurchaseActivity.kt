package com.interswitchng.smartpossdksample

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.interswitchng.smartpos.IswPos
import com.interswitchng.smartpos.shared.errors.NotConfiguredException
import com.interswitchng.smartpos.shared.models.core.Transaction
import com.interswitchng.smartpos.shared.models.results.IswTransactionResult
import com.interswitchng.smartpos.shared.models.transaction.PaymentType
import com.interswitchng.smartpossdksample.databinding.ActivityPurchaseBinding
import com.interswitchng.smartpossdksample.utils.PrefUtils
import com.interswitchng.smartpossdksample.utils.StringUtils
import com.interswitchng.smartpossdksample.utils.ViewBindingProvider
import com.interswitchng.smartpossdksample.utils.ViewUtils.showSnackBar
import com.interswitchng.smartpossdksample.utils.setToolBarTitle
import com.interswitchng.smartpossdksample.utils.vectorDrawableToBitmap

class PurchaseActivity : AppCompatActivity(), IswPos.IswPaymentCallback, ViewBindingProvider {

    private var _binding: ActivityPurchaseBinding? = null
    private val binding get() = _binding!!

    private val iswPosInstance: IswPos by lazy { IswPos.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        _binding = ActivityPurchaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolBarTitle(binding.toolBar, "Purchase", this)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        attachViewListeners()
        attemptSetCompanyLogo()
    }

    private fun attachViewListeners() {
        binding.btnMakePurchase.setOnClickListener {
            try {
                initiatePayment()
            } catch (ex: Exception) {
                showSnackBar("Amount cannot be empty")
            }
        }
    }

    private fun attemptSetCompanyLogo() {
        val companyLogo = PrefUtils.getSavedCompanyLogoOption(this)

        if (companyLogo != CompanyOption.NONE) {
            when (companyLogo) {
                CompanyOption.INTERSWITCH -> setLogo(com.interswitchng.smartpos.R.drawable.isw_logo, true)
                CompanyOption.GOOGLE -> setLogo(R.drawable.ic_isw_google, false)
                CompanyOption.AMAZON -> setLogo(R.drawable.ic_isw_amazon, false)
                CompanyOption.APPLE -> setLogo(R.drawable.ic_isw_apple, false)
                CompanyOption.NONE -> {}
            }
        } else {
            showSnackBar(
                "Company logo is not set. Printing will not be possible.",
                Snackbar.LENGTH_LONG
            )
        }
    }

    private fun initiatePayment() {
        val kobo = 100L
        val amount = binding.etAmount.text.toString().toDouble().toInt() * kobo

        val transaction: Transaction = Transaction.Purchase(PaymentType.Card)

        try {
            iswPosInstance.pay(amount, this, transaction)
        } catch (ex: NotConfiguredException) {
            showSnackBar("Error - Terminal not configured")
        }
    }

    private fun setLogo(logoResId: Int, isAlreadyBitmap: Boolean) {
        this.resources?.let { resources ->

            // Interswitch logo is a png file, and thus can be converted to bitmap using
            // BitmapFactory. The others aren't
            if (isAlreadyBitmap) {
                IswPos.setGeneralCompanyLogo(BitmapFactory.decodeResource(resources, logoResId))
            } else {
                vectorDrawableToBitmap(this, logoResId)?.let {
                    IswPos.setGeneralCompanyLogo(it)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun getRootView(): View = binding.root
    override fun onPaymentCompleted(result: IswTransactionResult) {
        val formattedResult = StringUtils.formatResult(result)
        binding.tvTransactionResult.text = formattedResult
    }

    override fun onUserCancel() {
        binding.tvTransactionResult.text = getString(R.string.user_cancelled_the_transaction)
    }
}