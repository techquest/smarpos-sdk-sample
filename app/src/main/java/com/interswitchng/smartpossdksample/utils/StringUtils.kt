package com.interswitchng.smartpossdksample.utils

import com.interswitchng.smartpos.shared.models.results.IswTransactionResult

object StringUtils {

    fun formatResult(result: IswTransactionResult): String {
        val strResult = StringBuilder()

        strResult.append("Transaction status: ".plus(if (result.isSuccessful) "SUCCESSFUL\n" else "FAILED\n"))
        strResult.append("Response Code: ".plus(result.responseCode).plus("\n"))
        strResult.append("Response Message: ".plus(result.responseMessage).plus("\n"))
        strResult.append("Amount: ".plus(result.amount/100).plus("\n"))
        strResult.append("Card Brand: ".plus(result.cardType.code).plus("\n"))
        strResult.append("Transaction Type: ".plus(result.transactionType.name).plus("\n"))
        strResult.append("CardHolder: ".plus(result.cardHolderName).plus("\n"))
        strResult.append("Card PAN: ".plus(maskCardPan( result.cardPan)).plus("\n"))
        strResult.append("Expiry: ".plus(result.cardExpiry).plus("\n"))
        strResult.append("AID: ".plus(result.AID).plus("\n"))
        strResult.append("Date/Time: ".plus(DateUtils.formatToUserFriendlyDate(result.dateTime)).plus("\n"))

        return strResult.toString()
    }

    private fun maskCardPan(pan: String): String {
        if (pan.length < 12 || pan.length > 25) return pan
        val firstSix = pan.take(6)
        val lastFour = pan.takeLast(4)
        val maskedSection = pan.substring(6, pan.length - 4).map { '*' }.joinToString("")
        return firstSix + maskedSection + lastFour
    }
}