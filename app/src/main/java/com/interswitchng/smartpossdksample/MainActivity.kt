package com.interswitchng.smartpossdksample

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.interswitchng.smartpos.IswPos
import com.interswitchng.smartpos.emv.pax.services.POSDeviceImpl
import com.interswitchng.smartpos.shared.models.core.Environment
import com.interswitchng.smartpos.shared.models.core.POSConfig
import com.interswitchng.smartpossdksample.databinding.ActivityMainBinding
import com.interswitchng.smartpossdksample.utils.Constants
import com.interswitchng.smartpossdksample.utils.PrefUtils
import com.interswitchng.smartpossdksample.utils.PrinterUtil
import com.interswitchng.smartpossdksample.utils.ViewBindingProvider

class MainActivity : AppCompatActivity(), ViewBindingProvider {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val iswPosInstance: IswPos by lazy { IswPos.getInstance() }
    private val device by lazy { POSDeviceImpl.create(this.applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setHintsSwitchData()
        attachViewListeners()
        configureTerminal()
    }

    private fun setHintsSwitchData() {
        binding.swDisableHints.isChecked = PrefUtils.isFeedbackDialogDisabled(this)
    }

    private fun attachViewListeners() {
        binding.run {
            btnCallHome.setOnClickListener {
                iswPosInstance.callHome()
            }

            btnPurchase.setOnClickListener {
                val intent = Intent(this@MainActivity, PurchaseActivity::class.java)
                startActivity(intent)
            }

            btnSetCompanyId.setOnClickListener {
                val intent = Intent(this@MainActivity, CompanyActivity::class.java)
                startActivity(intent)
            }

            btnShowSettings.setOnClickListener {
                IswPos.showSettingsScreen()
            }

            btnPrint.setOnClickListener {
                val intent = Intent(this@MainActivity, PrintActivity::class.java)
                startActivity(intent)
            }

            swDisableHints.setOnCheckedChangeListener { _, isChecked ->
                PrefUtils.toggleFeedbackDialog(this@MainActivity, isChecked)
            }
        }
    }

    private fun configureTerminal() {
        val environment = Environment.Production

        val config = POSConfig(
            alias = Constants.ALIAS,
            clientId = Constants.CLIENT_ID,
            clientSecret = Constants.CLIENT_SECRET,
            merchantCode = Constants.MERCHANT_CODE,
            merchantTelephone = Constants.MERCHANT_TELEPHONE,
            environment = environment,
            appVersion = ""
        )

        IswPos.setupTerminal(this.application, device, null, config, true)

        IswPos.setDeviceSetialNumber(device.serialNumber())
        PrinterUtil.setPosDeviceInstance(device)

        // Set company logo here
        // device.setCompanyLogo(logoBitmap)
        // IswPos.setGeneralCompanyLogo(logoBitmap)
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun getRootView(): View = binding.root
}