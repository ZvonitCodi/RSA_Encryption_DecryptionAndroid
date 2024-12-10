package com.example.rsa

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.math.BigInteger

class EncryptFragment : Fragment() {

    private var encryptedMessage: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_encrypt, container, false)

        val inputP = view.findViewById<EditText>(R.id.inputP)
        val inputQ = view.findViewById<EditText>(R.id.inputQ)
        val inputE = view.findViewById<EditText>(R.id.inputE)
        val inputMessage = view.findViewById<EditText>(R.id.inputMessage)
        val btnEncrypt = view.findViewById<Button>(R.id.btnEncrypt)
        val btnCopy = view.findViewById<Button>(R.id.btnCopy)
        val resultText = view.findViewById<TextView>(R.id.resultText)

        btnEncrypt.setOnClickListener {
            val p = inputP.text.toString().toBigIntegerOrNull()
            val q = inputQ.text.toString().toBigIntegerOrNull()
            val e = inputE.text.toString().toBigIntegerOrNull()
            val message = inputMessage.text.toString()

            if (p == null || q == null || e == null || message.isEmpty()) {
                resultText.text = "Введите корректные данные"
                return@setOnClickListener
            }

            val n = p * q
            encryptedMessage = message.toByteArray().map { char ->
                BigInteger.valueOf(char.toLong()).modPow(e, n)
            }.joinToString(",")

            resultText.text = "Зашифрованное сообщение: $encryptedMessage"
        }

        btnCopy.setOnClickListener {
            if (encryptedMessage.isNotEmpty()) {
                val clipboard =
                    requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("Зашифрованное сообщение", encryptedMessage)
                clipboard.setPrimaryClip(clip)
                Toast.makeText(requireContext(), "Скопировано в буфер обмена", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(requireContext(), "Нет результата для копирования", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
}
