package com.example.calculatorekstra

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.calculatorekstra.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var tasarim: ActivityMainBinding
    private var point = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tasarim = ActivityMainBinding.inflate(layoutInflater)
        setContentView(tasarim.root)

        // Başlangıçta toplama işlemi ile başla
        yeniSoru(1)

        // İşlem düğmeleri
        tasarim.button1.setOnClickListener { yeniSoru(1) } // Toplama
        tasarim.button2.setOnClickListener { yeniSoru(2) } // Çıkarma
        tasarim.button3.setOnClickListener { yeniSoru(3) } // Çarpma
        tasarim.button4.setOnClickListener { yeniSoru(4) } // Bölme
    }

    private fun yeniSoru(flag: Int) {
        val sonuc = goster(flag) // İşlemin sonucunu getir
        val correctButtonIndex = Random.nextInt(1, 4) // 1, 2 veya 3 seçilecek
        val buttons = listOf(tasarim.button5, tasarim.button6, tasarim.button7)

        // Tüm butonların önceki dinleyicilerini sıfırla
        clearButtonListeners()

        // Doğru cevabı bir butona ata ve diğerlerine yanlış cevaplar ata
        buttons.forEachIndexed { index, button ->
            if (index + 1 == correctButtonIndex) {
                button.text = sonuc.toString()
                button.setOnClickListener { dogruCevap(flag) }
            } else {
                button.text = (sonuc + Random.nextInt(1, 10)).toString() // Rastgele yanlış değerler
                button.setOnClickListener { yanlisCevap(flag) }
            }
        }
    }

    private fun dogruCevap(flag: Int) {
        point++
        tasarim.textView2.text = "point: $point"

        // 1 saniye sonra yeni soru oluştur
        Handler(Looper.getMainLooper()).postDelayed({
            yeniSoru(flag)
        }, 1000)
    }

    private fun yanlisCevap(flag: Int) {
        point--
        tasarim.textView2.text = "point: $point"

        // 1 saniye sonra yeni soru oluştur
        Handler(Looper.getMainLooper()).postDelayed({
            yeniSoru(flag)
        }, 1000)
    }

    private fun goster(flag: Int): Int {
        return when (flag) {
            1 -> { // Topla
                val randomInt1 = Random.nextInt(1, 100)
                val randomInt2 = Random.nextInt(1, 100)
                tasarim.textView.text = "$randomInt1 + $randomInt2"
                randomInt1 + randomInt2
            }
            2 -> { // Çıkar
                val randomInt1 = Random.nextInt(1, 100)
                val randomInt2 = Random.nextInt(1, 100)
                tasarim.textView.text = "$randomInt1 - $randomInt2"
                randomInt1 - randomInt2
            }
            3 -> { // Çarp
                val randomInt1 = Random.nextInt(1, 10)
                val randomInt2 = Random.nextInt(1, 10)
                tasarim.textView.text = "$randomInt1 x $randomInt2"
                randomInt1 * randomInt2
            }
            4 -> { // Böl
                val randomInt2 = Random.nextInt(1, 10)
                var randomInt1: Int
                do {
                    randomInt1 = Random.nextInt(1, 100)
                } while (randomInt1 % randomInt2 != 0)
                tasarim.textView.text = "$randomInt1 ÷ $randomInt2"
                randomInt1 / randomInt2
            }
            else -> {
                tasarim.textView.text = "Geçersiz işlem"
                0
            }
        }
    }

    private fun clearButtonListeners() {
        // Butonların tüm tıklama dinleyicilerini kaldır
        tasarim.button5.setOnClickListener(null)
        tasarim.button6.setOnClickListener(null)
        tasarim.button7.setOnClickListener(null)
    }
}
