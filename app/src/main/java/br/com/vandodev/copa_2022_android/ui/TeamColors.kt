package br.com.vandodev.copa_2022_android.ui

import androidx.compose.ui.graphics.Color

object TeamColors {
    private val colors = mapOf(
        "Catar" to Color(0xFF5A0034),
        "Equador" to Color(0xFFFDD100),
        "Senegal" to Color(0xFF00853F),
        "Holanda" to Color(0xFFF36C21),
        "Inglaterra" to Color(0xFFFFFFFF),
        "Irã" to Color(0xFF239F40),
        "EUA" to Color(0xFF3C3B6E),
        "País de Gales" to Color(0xFFAE2532),
        "Argentina" to Color(0xFF75AADB),
        "Arábia Saudita" to Color(0xFF006C35),
        "México" to Color(0xFF006847),
        "Polônia" to Color(0xFFDC143C),
        "França" to Color(0xFF0055A4),
        "Austrália" to Color(0xFF00008B),
        "Dinamarca" to Color(0xFFC8102E),
        "Tunísia" to Color(0xFFE70013),
        "Marrocos" to Color(0xFFC1272D),
        "Croácia" to Color(0xFFED1C24),
        "Alemanha" to Color(0xFF000000),
        "Japão" to Color(0xFFBC002D),
        "Bélgica" to Color(0xFFED2939),
        "Canadá" to Color(0xFFFF0000),
        "Suíça" to Color(0xFFFF0000),
        "Camarões" to Color(0xFF007A5E),
        "Uruguai" to Color(0xFF5A94D7),
        "Coreia do Sul" to Color(0xFF000000),
        "Portugal" to Color(0xFF006600),
        "Gana" to Color(0xFF006B3F),
        "Brasil" to Color(0xFF009C3B),
        // Adicione outras seleções conforme necessário
    )

    fun getColorForTeam(teamName: String): Color {
        return colors[teamName] ?: Color.LightGray
    }
}

fun Color.isDark() = (red * 299 + green * 587 + blue * 114) / 1000 < 0.5
