package ru.autopulse05.android.core.presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberScaffoldState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.autopulse05.android.core.presentation.util.Navigation
import ru.autopulse05.android.feature.user.presentation.profile.util.rememberProfileTabState
import ru.autopulse05.android.shared.presentation.popup.rememberPopupState
import ru.yoomoney.sdk.kassa.payments.Checkout
import ru.yoomoney.sdk.kassa.payments.Checkout.createConfirmationIntent
import ru.yoomoney.sdk.kassa.payments.Checkout.createTokenizationResult
import ru.yoomoney.sdk.kassa.payments.Checkout.createTokenizeIntent
import ru.yoomoney.sdk.kassa.payments.TokenizationResult
import ru.yoomoney.sdk.kassa.payments.checkoutParameters.*
import java.math.BigDecimal
import java.util.*

@AndroidEntryPoint
@OptIn(ExperimentalMaterialApi::class)
class MainActivity : ComponentActivity() {

  lateinit var callback: (String, String)->Unit
  lateinit var callbackConfirm: () -> Unit
  var result: TokenizationResult? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    th = this
    super.onCreate(savedInstanceState)
    setContent {
      val navController = rememberNavController()
      val scaffoldState = rememberScaffoldState()
      val popupState = rememberPopupState()
      val profileTabState = rememberProfileTabState()

      AutopulseApp(
        navController = navController,
        scaffoldState = scaffoldState,
        popupState = popupState,
        profileTabState = profileTabState
      ) {
        Navigation(
          navController = navController,
          scaffoldState = scaffoldState,
          popupState = popupState,
          profileTabState = profileTabState
        )
      }
    }
  }

  companion object {
    lateinit var th: MainActivity

    fun get(): MainActivity {
      return  th;
    }
  }
  fun start(phone: String,sum: Int, callback: (String, String)-> Unit) {
    Log.d("TAG","PHONE $phone")
    this.callback = callback
    val paymentParameters = PaymentParameters(
      amount = Amount(BigDecimal.valueOf(sum.toLong()), Currency.getInstance("RUB")),
      title = "Название товара",
      subtitle = "Описание товара",
      clientApplicationKey = "live_NTUwMTQzmq1-aBdFlUos-GRoVOo8NvoNFNIuBVgyiuU", // ключ для клиентских приложений из личного кабинета ЮKassa
      shopId = "550143", // идентификатор магазина ЮKassa
      savePaymentMethod = SavePaymentMethod.OFF, // флаг выключенного сохранения платежного метода,
      paymentMethodTypes = setOf(PaymentMethodType.SBERBANK,PaymentMethodType.BANK_CARD), // передан весь список доступных методов оплаты
      userPhoneNumber = "+79038970013"
      )
    val intent = createTokenizeIntent(this, paymentParameters, TestParameters(showLogs = true))
    startActivityForResult(intent,100)
  }
  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

    if (requestCode == 100) {
      when (resultCode) {
        RESULT_OK -> {
          // successful tokenization
          result = data?.let { createTokenizationResult(it) }
          callback(result!!.paymentToken, result!!.paymentMethodType.name)
        }
        RESULT_CANCELED -> {
          // user canceled tokenization
        }
      }
    }
    if(requestCode==101) {
      when (resultCode) {
        RESULT_OK -> {
          // successful tokenization
          Log.d("TAG","SUCCESS")
          callbackConfirm()
        }
        RESULT_CANCELED -> {
          // user canceled tokenization
        }
      }
    }
  }
  fun startConfirmSberPay() {
    val intent = createConfirmationIntent(this, "https://invoicing/sberpay", PaymentMethodType.SBERBANK)
    startActivityForResult(intent, 101)
  }
  fun start3DSecure(url: String) {

    val intent = createConfirmationIntent(this, url, PaymentMethodType.BANK_CARD)
    startActivityForResult(intent, 101)
  }
  fun confirm(url: String, callback: () -> Unit) {
    if(result!!.paymentMethodType.ordinal==PaymentMethodType.BANK_CARD.ordinal) {
      start3DSecure(url)
      callbackConfirm = callback
    }
  }
}