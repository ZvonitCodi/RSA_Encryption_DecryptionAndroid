package com.example.rsa

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.math.BigInteger

class DecryptFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_decrypt, container, false)

        val inputEncryptedMessage = view.findViewById<EditText>(R.id.inputEncryptedMessage)
        val inputD = view.findViewById<EditText>(R.id.inputD)
        val inputN = view.findViewById<EditText>(R.id.inputN)
        val btnDecrypt = view.findViewById<Button>(R.id.btnDecrypt)
        val decryptedMessageView = view.findViewById<TextView>(R.id.decryptedMessage)

        btnDecrypt.setOnClickListener {
            val encryptedMessage = inputEncryptedMessage.text.toString()
            val d = inputD.text.toString().toBigIntegerOrNull()
            val n = inputN.text.toString().toBigIntegerOrNull()

            if (encryptedMessage.isEmpty() || d == null || n == null) {
                decryptedMessageView.text = "Введите корректные данные"
                return@setOnClickListener
            }

            try {
                val decryptedMessage = encryptedMessage.split(",").map { cipher ->
                    cipher.toBigInteger().modPow(d, n).toInt().toChar()
                }.joinToString("")

                decryptedMessageView.text = "Расшифрованное сообщение: $decryptedMessage"
            } catch (e: Exception) {
                decryptedMessageView.text = "Не подлинная подпись: ${e.message}"
            }
        }

        return view
    }
}
